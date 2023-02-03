package com.mws.phoenix.web.archive;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.brief.Brief;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.LoginArchive;
import com.mws.phoenix.db.web.WebLogEntry;

/**
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class Archiver extends Thread {
    
    Log log = LogFactory.getLog(Archiver.class);
    
    private ServletContext application;
    private Session mail;
    
    private SimpleDateFormat sdf;
    private boolean stop = false;
    
    public Archiver(ServletContext app, Session mail) {
        super("NLA Archiver");
        this.application = app;
        this.mail = mail;
        this.setDaemon(true);
        this.sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        this.sdf.setTimeZone(TimeZone.getTimeZone("Europe/London"));
    }
    
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    public boolean isStop() {
        return this.stop;
    }
    
    public void run() {
        // Wait for 2 minutes to allow Tomcat to initialize fully
        try {
            Thread.sleep(120000);
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

                List<LoginArchive> archives = null;
                try {
                	archives = DataStore.store().getObjects(LoginArchive.class, "enabled", Boolean.TRUE);
                } catch (DataStoreException e) {
                    if (log.isErrorEnabled()) {
                        log.error("Exception thrown retrieving list of enabled archives.", e);
                    }
                    error.append("Exception thrown retrieving list of enabled archives. " + e.getMessage() + "\n");
                }
                
                if (archives != null) {
                	Iterator<LoginArchive> it = archives.iterator();
                	while (it.hasNext()) {
                		LoginArchive archive = it.next();
                		log.debug(archive.getLogin().getLoginName() + " needs to be checked");

                		Date lastSent = archive.getLastSent() == null ? new Date(0) : archive.getLastSent();
                		String query = getQueryString(archive.getLogin(), lastSent, now);
                		
                		log.debug(query);
                		
                		List<Long> hits = null;
                		try {
                			hits = DataStore.store().getObjects(query);
                		} catch (DataStoreException e) {
                            if (log.isErrorEnabled()) {
                                log.error("Exception thrown retrieving " + archive.getLogin().getLoginName() + " hits", e);
                            }
                            error.append("Exception thrown retrieving " + archive.getLogin().getLoginName() + " hits. " + e.getMessage() + "\n");
                		}
                		
                		if (hits != null && hits.size() > 0) {
                			String result = addToMyArchive(hits, archive.getLogin());
                			if (!result.equals("")) {
                				error.append(result);
                			} else {
                				WebLogEntry.log(WebLogEntry.TYPE_MY_ARCHIVE, archive.getLogin().getLoginName() + " " + hits.size() + " hits sent at " + sdf.format(now));
                			
                				//Update LoginArchive last sent time
                				archive.setLastSent(now);
                				DataStore.store().persist(archive);
                			}
                		}
                	}
                }
                // Successful execution
                if (log.isDebugEnabled()) {
                	log.debug("setting last run date");
                }
                setLastRunDate(now);
            } catch (Throwable t) {
                if (log.isErrorEnabled()) {
                    log.error(t.getMessage(), t);
                }
                error.append(t.getClass().getName() + " thrown processing Archives. " + t.getMessage() + "\n");
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

    private List<String> getBriefIDs(Login login) {
        List<String> list = new ArrayList<String>();
        Iterator<Brief> it = login.getBriefAccess().iterator();
        while (it.hasNext()) {
            Brief brief = it.next();
            list.add(brief.getBriefID().toString());
        }
        return list;
    }
    
    private String getQueryString(Login login, Date last, Date now) {
        StringBuffer sb = new StringBuffer();
        sb.append("select distinct hit.article.nlaID from Hit hit");
        sb.append (" where hit.hitDate between '");
        sb.append(sdf.format(last));
        sb.append("' and '");
        sb.append(sdf.format(now));
        sb.append("' and hit.article.nlaID is not null");
        List<String> list = getBriefIDs(login);
        sb.append(" and hit.brief.briefID in (");
        sb.append(makeInList(list));
        sb.append(")");
        return sb.toString();
    }

    private String makeInList(List<String> list) {
        if (list.size() == 0) {
            return "-999";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(list.get(i));
            }
            return sb.toString();
        }
    }
    
    private String addToMyArchive(List<Long> hits, Login login) {
        StringBuffer error = new StringBuffer();
    	Iterator<Long> it = hits.iterator();
        String call = "http://www.nla-eclips.com/NLAAPI.dll/ManageArchive?UserID=" + login.getNlaUserID() + "&Action=Add&ObjectID=";
        while (it.hasNext()) {
            Long nlaID = it.next();
            try {
                URL url = new URL(call + nlaID.toString());
                BufferedReader ir = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer result = new StringBuffer();
                boolean success = false;
                String line;
                while ((line = ir.readLine()) != null) {
                	if (line.equals("Clipping added to archive.")) {
                		success = true;
                	}
                	result.append(line);
                	log.debug(login.getLoginName() + ":" + nlaID + " = " + result);
                }

                if (!success) {
                	if (log.isErrorEnabled()) {
                		log.error("Unable to add the clipping to the archive.");
                	}
                	error.append(login.getLoginName() + ":" + nlaID + " - " + result + "\n");
                }
            } catch (IOException e) {
                log.error(login.getLoginName() + ":" + nlaID + " ! " + e.getMessage(), e);
            	error.append(login.getLoginName() + ":" + nlaID + " - " + e.getMessage() + "\n");
            }
        }
        return error.toString();
    }
    
    private Date getLastRunDate() {
        ObjectInputStream ois = null;        
        try {
            ois = new ObjectInputStream(application.getResourceAsStream("/WEB-INF/archiver/lastrun.properties"));
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
                oos = new ObjectOutputStream(new FileOutputStream(application.getRealPath("/") + "WEB-INF/archiver/lastrun.properties"));
                oos.writeObject(date);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void setErrorString(String error) {
        error = error.replaceAll("<", "&lt;");
        error = error.replaceAll(">", "&gt;");
    	ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(application.getRealPath("/") + "WEB-INF/archiver/errors.properties"));
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
                msg.setSubject("[Archiver] Warning! Error detected.");
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


