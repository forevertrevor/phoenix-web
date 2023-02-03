package com.mws.phoenix.web.alert;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.xml.transform.URIResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.article.Article;
import com.mws.phoenix.db.brief.Brief;
import com.mws.phoenix.db.brief.SortSection;
import com.mws.phoenix.db.despatch.AlsoOrder;
import com.mws.phoenix.db.despatch.HitSortSection;
import com.mws.phoenix.db.despatch.StoryOrder;
import com.mws.phoenix.db.source.SourceCategory;
import com.mws.phoenix.db.source.SourceType;
import com.mws.phoenix.db.web.AlertBrief;
import com.mws.phoenix.db.web.LoginAlert;
import com.mws.phoenix.db.web.WebLogEntry;
import com.mws.phoenix.util.XMLFactory;
import com.mws.phoenix.web.MWSWebResolver;
import com.mws.phoenix.web.WebUtilities;
import com.mws.util.date.DateUtilities;
import com.mws.util.xml.XMLException;

/**
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class EmailAlerter extends Thread {
    
    Log log = LogFactory.getLog(EmailAlerter.class);
    
    private HTMLImageAttacher attacher = new HTMLImageAttacher();
    
    private Session mail;
    private ServletContext application;

    private SimpleDateFormat sdf;
    private SimpleDateFormat subject;
    private boolean stop = false;
    
    public EmailAlerter(ServletContext application, Session mail) {
        super("Email Alerter");
        this.mail = mail;
        this.application = application;
        this.setDaemon(true);
        this.sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        this.sdf.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        this.subject = new SimpleDateFormat("E, dd MMMM yyyy HH:mm");
        this.subject.setTimeZone(TimeZone.getTimeZone("Europe/London"));
    }
    
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    public boolean isStop() {
        return this.stop;
    }
    
    public void run() {
        // Wait for 1 minute to allow Tomcat to initialize fully
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            
        }
            
        while (!stop) {
            Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("Europe/London"));
            
            log.debug("getting last run date");
            
            Date then = getLastRunDate();
            Date now = cal.getTime();
            
            log.debug("Time Range = " + then + " - " + now);
            StringBuffer error = new StringBuffer();
            
            try {
                log.debug("Checking Database...");

                List<LoginAlert> alerts = null;
                try {
                    alerts = DataStore.store().getObjects(LoginAlert.class, "enabled", Boolean.TRUE);
                } catch (DataStoreException e) {
                    if (log.isErrorEnabled()) {
                        log.error("Exception thrown retrieving list of enabled alerts.", e);
                    }
                    error.append("Exception thrown retrieving list of enabled alerts. " + e.getMessage() + "\n");
                }
                if (alerts != null) {
                    Iterator<LoginAlert> it = alerts.iterator();
                    while (it.hasNext()) {
                        LoginAlert alert = it.next();
                        try {
                            if (isTime(alert, then, now)) {
                                log.debug(alert.getLogin().getLoginName() + " needs to be checked");
                                String[] briefIDs = getBriefIDs(alert);
                                String[] sortSectionIDs = getSortSectionIDs(alert);
                                String[] sourceTypeIDs = getSourceTypeIDs(alert);
                                String[] pubTypeIDs = getSourceCategoryIDs(alert);
                                List<HitSortSection> hits = null;
                                if (briefIDs.length != 0 || sortSectionIDs.length != 0) {
                                    Date lastSent = alert.getLastSent() == null ? new Date(0) : alert.getLastSent();
                                    String query = getQueryString(briefIDs, sortSectionIDs, sourceTypeIDs, pubTypeIDs, lastSent, now);
                            
                                    log.debug(query);
                                    try {
                                        hits = DataStore.store().getObjects(query);
                                    } catch (DataStoreException e) {
                                        if (log.isErrorEnabled()) {
                                            log.error("Exception thrown retrieving " + alert.getLogin().getLoginName() + " hits", e);
                                        }
                                        error.append("Exception thrown retrieving " + alert.getLogin().getLoginName() + " hits. " + e.getMessage() + "\n");
                                    }
                                }
                        
                                if (hits == null) {
                                	hits = new ArrayList<HitSortSection>();
                                }
                                
                                if (hits.size() > 0 || hasNoContent(alert, then, now)) {
                                    try {
                                        Document doc = createAlertDocument(alert, hits, now);
                                        ByteArrayOutputStream baos = createMessage(alert, doc);
                                        sendEmail(alert, baos);
                                        /*
                                        if (alert.getRSS()) {
                                        	error.append(createRSSFeed(alert, briefIDs, sortSectionIDs, sourceTypeIDs, pubTypeIDs, now));
                                        }
                                        */
                                       	WebLogEntry.log(WebLogEntry.TYPE_EMAIL_ALERT, alert.getLogin().getLoginName() + " " + hits.size() + " hits sent at " + sdf.format(now));

                                        //Update LoginAlert last sent time
                                        alert.setLastSent(now);
                                        try {
                                            DataStore.store().persist(alert);
                                        } catch (DataStoreException e) {
                                            if (log.isErrorEnabled()) {
                                                log.error("Exception thrown updating " + alert.getLogin().getLoginName() + " last sent time.", e);
                                            }
                                            error.append("Exception thrown updating " + alert.getLogin().getLoginName() + " last sent time. " + e.getMessage() + "\n");
                                        }
                                    } catch (XMLException e) {
                                        if (log.isErrorEnabled()) {
                                            log.error(e.getMessage(), e);
                                        }   
                                        error.append("XMLException thrown processing " + alert.getLogin().getLoginName() + " alert. " + e.getMessage() + "\n");
                                    } catch (FileNotFoundException e) {
                                        if (log.isErrorEnabled()) {
                                            log.error(e.getMessage(), e);
                                        }
                                        error.append("FileNotFoundException thrown processing " + alert.getLogin().getLoginName() + " alert. " + e.getMessage() + "\n");
                                    } catch (MessagingException e) {
                                        if (log.isErrorEnabled()) {
                                            log.error(e.getMessage(), e);
                                        }
                                        error.append("MessagingException thrown processing " + alert.getLogin().getLoginName() + " alert. " + e.getMessage() + "\n");
                                    } catch (MalformedURLException e) {
                                        if (log.isErrorEnabled()) {
                                            log.error(e.getMessage(), e);
                                        }
                                        error.append("MalformedURLException thrown processing " + alert.getLogin().getLoginName() + " alert. " + e.getMessage() + "\n");
                                    } catch (UnsupportedEncodingException e) {
                                        if (log.isErrorEnabled()) { 
                                            log.error(e.getMessage(), e);
                                        }
                                        error.append("UnsupportedEncodingException thrown processing " + alert.getLogin().getLoginName() + " alert. " + e.getMessage() + "\n");
                                    }
                                }
                            }
                        } catch (Exception e) {
                            if (log.isErrorEnabled()) {
                                log.error(e.getMessage(), e);
                            }
                            error.append(e.getClass().getName() + " thrown processing " + alert.getLogin().getLoginName() + " alert.\n");
                        }
                    }
                }

                // Successful execution
                log.debug("setting last run date");
                setLastRunDate(now);
            } catch (Throwable t) {
                if (log.isErrorEnabled()) {
                    log.error(t.getMessage(), t);
                }
                error.append(t.getClass().getName() + " thrown processing Email Alerts. " + t.getMessage() + "\n");
            }

            setErrorString(error.toString());
            sendErrorMessage(error.toString());
            
            //Wait for 10 minutes
            log.debug("Waiting");
            try {
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                
            }
        }
    }
    
    /**
     * @param alert
     * @param hits
     * @param now
     * @return
     * @throws MalformedURLException
     * @throws XMLException
     * @throws FileNotFoundException
     */
    private ByteArrayOutputStream createMessage(LoginAlert alert, Document doc) throws MalformedURLException, XMLException, FileNotFoundException {
        String template = WebUtilities.getResource(application, alert.getLogin().getStyle().getFolder() + "templates/", "default/templates/", (alert.getHtml().booleanValue() ? "html" : "plain") + alert.getTemplate());
        InputStream is = application.getResourceAsStream(template);

        URIResolver uriResolver = new MWSWebResolver(application, alert.getLogin().getStyle(), "templates/");

        //TEMP save for testing
        //XMLFactory.saveDocument(doc, application.getRealPath("/WEB-INF/alerter") + "/alerter.xml", uriResolver);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLFactory.transform(XMLFactory.source(doc), XMLFactory.source(is), XMLFactory.result(baos), uriResolver);
        return baos;
    }

    /*
    private StringBuffer createRSSFeed(LoginAlert alert, String[] briefIDs, String[] sortSectionIDs, String[] sourceTypeIDs, String[] pubTypeIDs, Date now) throws MalformedURLException, FileNotFoundException, XMLException {
        StringBuffer error = new StringBuffer();
    	String query = getQueryString(briefIDs, sortSectionIDs, sourceTypeIDs, pubTypeIDs, DateUtilities.date(now), now);
        List<HitSortSection> hits = null;
        
        log.debug(query);
        try {
            hits = DataStore.store().getObjects(query);
        } catch (DataStoreException e) {
            if (log.isErrorEnabled()) {
                log.error("Exception thrown retrieving " + alert.getLogin().getLoginName() + " hits", e);
            }
            error.append("Exception thrown retrieving " + alert.getLogin().getLoginName() + " hits. " + e.getMessage() + "\n");
        }

        if (hits != null && hits.size() > 0) {
        	Document doc = createAlertDocument(alert, hits, now);
        	String template = WebUtilities.getResource(application, alert.getLogin().getStyle().getFolder() + "templates/", "default/templates/", "rss.xsl");
        	InputStream is = application.getResourceAsStream(template);

        	URIResolver uriResolver = new MWSWebResolver(application, alert.getLogin().getStyle(), "templates/");

        	String filename = WebUtilities.getResource(application, alert.getLogin().getStyle().getFolder() + "rss/", "default/rss/", alert.getAlertID() + ".xml");
        	FileOutputStream fos = new FileOutputStream(filename);
        	XMLFactory.transform(XMLFactory.source(doc), XMLFactory.source(is), XMLFactory.result(fos), uriResolver);
        }
        return error;
    }
    */
    
    /**
     * Checks the alert to see if it needs to be sent for the
     * current time period
     * @param alert the alert to check
     * @param then the last time the alerter was run
     * @param now the current time
     * @return true if the alert needs to be sent
     */
    private boolean isTime(LoginAlert alert, Date then, Date now) {
        if (alert.getUploadTime().booleanValue() == true) {
            // This alert needs to be checked every time
            return true;
        } else if (then.compareTo(alert.getLastSent()) <= 0 && now.compareTo(alert.getLastSent()) > 0) {
            // This alert has already been sent for this time period
            return false;
        } else {
            // If one of the alert's send times is within the time period then it must be checked
            Date today = DateUtilities.date(now);
            Iterator<Time> it = alert.getTimes().iterator();
            while (it.hasNext()) {
                Date time = DateUtilities.adjustForDST(new Date(((Date)it.next()).getTime() + today.getTime()));
                if (time.compareTo(then) > 0 && time.compareTo(now) <= 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean hasNoContent(LoginAlert alert, Date then, Date now) {
    	List<Time> times = new ArrayList<Time>(alert.getTimes());
    	Collections.sort(times);
        Date today = DateUtilities.date(now);
    	Date lastTime = today;
    	Iterator<Time> it = times.iterator();
    	while(it.hasNext()) {
    		Date time = DateUtilities.adjustForDST(new Date(((Date)it.next()).getTime() + today.getTime()));
    		if (time.compareTo(then) > 0 && time.compareTo(now) <= 0) {
    			if (lastTime.compareTo(alert.getLastSent()) > 0) {
    				return true;
    			} else {
    				return false;
    			}
    		}
    		// Adjust the last time by 10 minutes to allow for a
    		// no cuts email to have been sent for the last alert time
    		lastTime = new Date(time.getTime() + 600000);
    	}
    	return false;
    }
    
    private String[] getBriefIDs(LoginAlert alert) {
        List<String> list = new ArrayList<String>();
        Set<AlertBrief> briefs = alert.getBriefs();
        if (briefs.size() == 0) {
            Iterator<Brief> it = alert.getLogin().getBriefAccess().iterator();
            while (it.hasNext()) {
                Brief brief = it.next();
                list.add(brief.getBriefID().toString());
            }
        } else {
            Iterator<AlertBrief> it = briefs.iterator();
            while (it.hasNext()) {
                AlertBrief brief = it.next();
                if (brief.getSortSections().size() == 0) {
                    list.add(brief.getBrief().getBriefID().toString());
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    private String[] getSortSectionIDs(LoginAlert alert) {
        List<String> list = new ArrayList<String>();
        Set<AlertBrief> briefs = alert.getBriefs();
        Iterator<AlertBrief> it = briefs.iterator();
        while (it.hasNext()) {
            AlertBrief brief = it.next();
            Iterator<SortSection> ss = brief.getSortSections().iterator();
            while (ss.hasNext()) {
                SortSection s = ss.next();
                list.add(s.getSortSectionID().toString());
            }
        }
        return list.toArray(new String[list.size()]);
    }
    
    private String[] getSourceTypeIDs(LoginAlert alert) {
    	String[] sourceTypes = new String[alert.getSourceTypes().size()];
    	Iterator<SourceType> it = alert.getSourceTypes().iterator();
    	int i = 0;
    	while (it.hasNext()) {
    		SourceType st = it.next();
    		sourceTypes[i++] = st.getSourceTypeID().toString();
    	}
    	return sourceTypes;
    }

    private String[] getSourceCategoryIDs(LoginAlert alert) {
    	String[] pubTypes = new String[alert.getSourceCategories().size()];
    	Iterator<SourceCategory> it = alert.getSourceCategories().iterator();
    	int i = 0;
    	while (it.hasNext()) {
    		SourceCategory pt = it.next();
    		pubTypes[i++] = pt.getCategoryID().toString();
    	}
    	return pubTypes;
    }

    private String getQueryString(String[] briefIDs, String[] sortSectionIDs, String[] sourceTypeIDs, String[] pubTypeIDs, Date last, Date now) {
        StringBuffer sb = new StringBuffer();
        sb.append("select hss from HitSortSection hss");
        sb.append(" inner join hss.hit hit");
        sb.append(" inner join hit.article article");
        sb.append(" inner join hit.brief brief");
        sb.append (" where hit.hitDate between '");
        sb.append(sdf.format(last));
        sb.append("' and '");
        sb.append(sdf.format(now));
        sb.append("'");
        if (briefIDs.length > 0 || sortSectionIDs.length > 0) {
            sb.append(" and (");
        }
        if (briefIDs.length > 0) {
            sb.append("brief.briefID in (");
            sb.append(makeInList(briefIDs));
            sb.append(")");
        }
        if (briefIDs.length > 0 && sortSectionIDs.length > 0) {
            sb.append(" or ");
        }
        if (sortSectionIDs.length > 0) {
            sb.append("hss.sortSection.sortSectionID in (");
            sb.append(makeInList(sortSectionIDs));
            sb.append(")");
        }
        if (briefIDs.length > 0 || sortSectionIDs.length > 0) {
            sb.append(")");
        }
        
        if (sourceTypeIDs.length > 0 || pubTypeIDs.length > 0) {
        	sb.append(" and (");
        }
        if (sourceTypeIDs.length > 0) {
        	sb.append("article.source.sourceType.sourceTypeID in (");
        	sb.append(makeInList(sourceTypeIDs));
            sb.append(")");
        }
        if (sourceTypeIDs.length > 0 && pubTypeIDs.length > 0) {
        	sb.append(" or ");
        }
        if (pubTypeIDs.length > 0) {
        	sb.append("article.source.sourceID in (select p.sourceID from Publication p where p.publicationType.publicationTypeID in (");
        	sb.append(makeInList(pubTypeIDs));
            sb.append("))");
        }

        if (sourceTypeIDs.length > 0 || pubTypeIDs.length > 0) {
        	sb.append(")");
        }
        
        sb.append(" order by brief.briefName, hss.sortSection.sortSectionOrder, hss.relevance DESC, hit.hitID");
        return sb.toString();
    }
    
    private String makeInList(String[] list) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(list[i]);
        }
        return sb.toString();
    }

    /**
     * @param alert
     * @param hits
     * @return
     */
    private Document createAlertDocument(LoginAlert alert, List<HitSortSection> hits, Date now) {
        Element node;
        Map<Long, Article> articles = new LinkedHashMap<Long, Article>();
        List<StoryOrder> stories = new ArrayList<StoryOrder>();
        Iterator<HitSortSection> it = hits.iterator();
        while (it.hasNext()) {
            HitSortSection hss = it.next();
            if (!articles.containsKey(hss.getHit().getArticle().getArticleID())) {
                articles.put(hss.getHit().getArticle().getArticleID(), hss.getHit().getArticle());
            }
            StoryOrder so = new StoryOrder(hss);
            stories.add(so);
            Iterator<AlsoOrder> alsos = so.getAlsoOrder().iterator();
            while (alsos.hasNext()) {
                AlsoOrder ao = alsos.next();
                if(!articles.containsKey(ao.getArticleID())) {
                    articles.put(ao.getArticleID(), ao.getHit().getArticle());
                }
            }
            
        }
        
        XMLFactory factory = XMLFactory.getInstance();
        try {
            Document doc = XMLFactory.newDocument("alert");
            Element root = doc.getDocumentElement();
            node = doc.createElement("user");
            node.setAttribute("id", alert.getLogin().getLoginID().toString());
            if (alert.getLogin().getNlaUserID() != null) {
                node.setAttribute("nlaid", alert.getLogin().getNlaUserID().toString());
                node.setAttribute("exclude-ni", alert.getLogin().getRestrictNI().toString());
            }
            root.appendChild(node);
            XMLFactory.addNode(doc, node, "login-name", alert.getLogin().getLoginName());
            XMLFactory.addNode(doc, node, "password", alert.getLogin().getPassword());
            XMLFactory.addNode(doc, node, "user-name", alert.getLogin().getUserName());
            factory.createDateNode(doc, root, "alert-date", now, subject, null);
            Element snd = doc.createElement("email-addresses");
            node.appendChild(snd);
            Iterator<String> mail = alert.getEmails().iterator();
            while (mail.hasNext()) {
                XMLFactory.addNode(doc, snd, "email-address", mail.next());
            }
            factory.createSortingNode(doc, root, stories, false);
            factory.createArticlesNode(doc, root, articles, false);
           	factory.createSharesNode(doc, root, alert.getLogin().getGroup().getClient().getShares(), DateUtilities.date(now), "client");
           	factory.createSharesNode(doc, root, alert.getLogin().getFinance().getShares(), DateUtilities.date(now), "user");
            
            NodeList nl = doc.getElementsByTagName("article");
            for (int i = 0; i < nl.getLength(); i++) {
                Element article = (Element)nl.item(i);
                Element file = (Element)article.getElementsByTagName("file").item(0);
                String value = alert.getLogin().getLoginName() + alert.getLogin().getPassword() + "/action/clip?clip=" + ((Text)file.getFirstChild()).getData();
                String hash = Integer.toHexString(value.hashCode());
                XMLFactory.addNode(doc, article, "hash", hash);
            }
            
            return doc;
        } catch (XMLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void sendEmail(LoginAlert alert, ByteArrayOutputStream baos) throws MessagingException, UnsupportedEncodingException {
        String message = baos.toString("UTF-8");
        Message msg = new MimeMessage(mail);
        msg.setFrom(new InternetAddress("alert@xy.media"));
        msg.setReplyTo(new InternetAddress[] {new InternetAddress("mail@xy.media")});
        msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("alert@xy.media"));
        Iterator<String> it = alert.getEmails().iterator();
        while (it.hasNext()) {
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(it.next()));
        }
        
        msg.setSubject(getSubject(message, alert.getHtml().booleanValue()));
        
        //msg.setSubject("Email Alert for " + alert.getLogin().getUserName() + " for " + subject.format(now));
        
        if (alert.getHtml().booleanValue()) {
        	msg.setContent(attacher.attachImages(message));
        	//msg.setContent(message, "text/html; charset=utf-8");
        } else {
        	msg.setContent(message, "text/plain; charset=utf-8");
        }
        Transport.send(msg);
    }

    private String getSubject(String message, boolean html) {
    	if (html) {
    		Pattern pattern = Pattern.compile("<title>(.*?)</title>");
    		Matcher matcher = pattern.matcher(message);
    		if (matcher.find()) {
    			return matcher.group(1);
    		} else {
    			return "XY Media Email Alert";
    		}
    	} else {
    		return message.substring(0, message.indexOf("\n"));
    	}
    }
    
    private Date getLastRunDate() {
        ObjectInputStream ois = null;        
        try {
            ois = new ObjectInputStream(application.getResourceAsStream("/WEB-INF/alerter/lastrun.properties"));
            Date date = (Date)ois.readObject();
            return date;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (Throwable t) {
                
            }
        }
        return new Date(0);
    }

    private void setLastRunDate(Date date) {
        ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(application.getRealPath("/") + "WEB-INF/alerter/lastrun.properties"));
                oos.writeObject(date);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void setErrorString(String error) {
        ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(application.getRealPath("/") + "WEB-INF/alerter/errors.properties"));
                oos.writeObject(error);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void sendErrorMessage(String message) {
        if (message != null && message.length() > 0) {
            try {
                Message msg = new MimeMessage(mail);
                msg.setFrom(new InternetAddress("alert@xy.media"));
                msg.setReplyTo(new InternetAddress[] {new InternetAddress("mail@xy.media")});
                msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("mail@xy.media"));
                //msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("ed@mws-systems.co.uk"));
                msg.setSubject("[Email Alerter] Warning! Error detected.");
                msg.setContent(message, "text/plain; charset=utf-8");
                Transport.send(msg);
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}


