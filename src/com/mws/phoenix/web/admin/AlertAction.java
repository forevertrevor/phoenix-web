package com.mws.phoenix.web.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.brief.Brief;
import com.mws.phoenix.db.brief.SortSection;
import com.mws.phoenix.db.source.SourceCategory;
import com.mws.phoenix.db.source.SourceType;
import com.mws.phoenix.db.web.AlertBrief;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.LoginAlert;

public class AlertAction extends Action {
    
    public static final String ALERT_INFO = "mediagen.alert.messages"; 
    public static final String ALERT_ERRORS = "mediagen.alert.errors"; 
    
    Log log = LogFactory.getLog(AlertAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        Login user = (Login)session.getAttribute("user");
        AlertForm alertform = (AlertForm)form;

        // Retrieve the alert object to be edited
        LoginAlert alert = null;
        if (alertform.getAlertID().equals("") || alertform.getAlertID().equals("0")) {
            alert = (LoginAlert)DataStore.store().getObject(LoginAlert.class, user.getLoginID());
        } else {
            alert = (LoginAlert)DataStore.store().getObject(LoginAlert.class, new Long(alertform.getAlertID()));
        }
        if (alert == null) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noobject", "alert", alertform.getAlertID()));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("failure");
        }

        // Check that user is authorised
        // Only INTERNAL users
        if (!user.getLevel().equals(Login.INTERNAL)
         && !user.getLoginID().equals(alert.getAlertID())
         && !(user.getLevel().equals(Login.ADMIN) && user.getGroup().getGroupID().equals(alert.getLogin().getGroup().getGroupID()))) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noaccess", "alert"));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
        }

        ActionMessages msgs = new ActionMessages();

        if (alertform.getAction().equals("")) {
            copyData(alertform, alert);
        } else {
            alertform.setUser(alert.getLogin());
            updateData(alertform, alert);

            //TEMP output the results of the form to sysout
            if (log.isDebugEnabled()) {
                log.debug("Change of Alert for " + alertform.getUser());
                log.debug("New time: " + alertform.getHour() + ":" + alertform.getMinute());
                String deleted = "";
                deleted ="Deleted times: ";
                for (int i = 0; i < alertform.getDeleteTime().length; i++) {
                    deleted = deleted + alertform.getDeleteTime()[i] + ", ";
                }
                log.debug(deleted);
                log.debug("New address: " + alertform.getEmail());
                deleted = "Deleted emails: ";
                for (int i = 0; i < alertform.getDeleteEmail().length; i++) {
                    deleted = deleted + alertform.getDeleteEmail()[i] + ", ";
                }
                log.debug(deleted);
            }
            alertform.reset(mapping, request);
            copyData(alertform, alert);
            msgs.add(ALERT_INFO, new ActionMessage("info.alert.update"));
            request.setAttribute(ALERT_INFO, msgs);
            
        }
        return mapping.findForward("success");
    }


    private void copyData(AlertForm alertform, LoginAlert alert) {
        alertform.setUser(alert.getLogin());
/*        if (alert == null) {
            alertform.setEnabled(false);
            alertform.setHtml(false);
            alertform.setBrief(new String[] {});
            alertform.setSection(new String[] {});
            alertform.setAll(true);
        } else {*/
            alertform.setEnabled(alert.getEnabled().booleanValue());
            alertform.setHtml(alert.getHtml().booleanValue());
            alertform.setUploadTime(alert.getUploadTime().booleanValue());
            alertform.setAlertID(alert.getAlertID().toString());
            alertform.setLastSent(alert.getLastSent());
            alertform.setTemplate(alert.getTemplate());
            alertform.setNoContent(alert.getNoContent().booleanValue());
            
            // Set the brief and section arrays
            Iterator<AlertBrief> it = alert.getBriefs().iterator();
            List<String> brf = new ArrayList<String>();
            List<String> sec = new ArrayList<String>();
            while (it.hasNext()) {
                AlertBrief ab = it.next();
                if (ab.getSortSections().size() == 0) {
                    brf.add(ab.getBrief().getBriefID().toString());
                } else {
                    Iterator<SortSection> secs = ab.getSortSections().iterator();
                    while (secs.hasNext()) {
                        SortSection ss = secs.next();
                        sec.add(ss.getSortSectionID().toString());
                    }
                }
            }
            if (brf.size() == 0 && sec.size() == 0) {
                alertform.setAll(true);
            } else {
                alertform.setAll(false);
            }
            alertform.setBrief(brf.toArray(new String[brf.size()]));
            alertform.setSection(sec.toArray(new String[sec.size()]));
            
            // Set the SourceType and PublicationType arrays
            Iterator<SourceType> xit = alert.getSourceTypes().iterator();
            List<String> sts = new ArrayList<String>();
            while (xit.hasNext()) {
            	SourceType st = xit.next();
            	sts.add(st.getSourceTypeID().toString());
            }
            alertform.setSourceType(sts.toArray(new String[brf.size()]));

            Iterator<SourceCategory> yit = alert.getSourceCategories().iterator();
            List<String> pts = new ArrayList<String>();
            while (yit.hasNext()) {
            	SourceCategory st = yit.next();
            	pts.add(st.getCategoryID().toString());
            }
            alertform.setSourceCategories(pts.toArray(new String[brf.size()]));
            
            if (sts.size() == 0 && pts.size() == 0) {
                alertform.setAllSources(true);
            } else {
                alertform.setAllSources(false);
            }
        //}
    }

    private void updateData(AlertForm alertform, LoginAlert alert) throws DataStoreException, ParseException {
        DataStore store = DataStore.store();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat time = new SimpleDateFormat("H:m");
        /*if (alert == null) {
            alert = new LoginAlert();
            alert.setLogin(login);
            login.setAlert(alert);
        }*/

        if (!alertform.getLastSent().equals("")) {
            alert.setLastSent(alertform.sdf.parse(alertform.getLastSent()));
        }
        if (!alert.getEnabled().booleanValue() && alertform.isEnabled()) {
            alert.setLastSent(new Date());
        }
        alert.setEnabled(new Boolean(alertform.isEnabled()));
        alert.setHtml(new Boolean(alertform.isHtml()));
        alert.setUploadTime(new Boolean(alertform.isUploadTime()));
        alert.setNoContent(new Boolean(alertform.isNoContent()));
        
        // set the template
        alert.setTemplate(alertform.getTemplate());
        
        //update the brief and sectors list.
        updateBriefData(alertform, alert);

        //update the sourcetype list.
        updateSourceData(alertform, alert);
        
        //Update the times list
        if (!alertform.getHour().equals("") && !alertform.getMinute().equals("")) {
            alert.addTime(time.parse(alertform.getHour() + ":" + alertform.getMinute()));
        }

        for (int i = 0; i < alertform.getDeleteTime().length; i++) {
            alert.removeTime(sdf.parse(alertform.getDeleteTime()[i]));
        }
        
        store.persist(alert);
        
        // Update the email list
        if (!alertform.getEmail().equals("")) {
            alert.addEmail(alertform.getEmail());
        }
        for (int i = 0; i < alertform.getDeleteEmail().length; i++) {
            alert.removeEmail(alertform.getDeleteEmail()[i]);
        }
        
        store.persist(alert);
    }

    private void updateBriefData(AlertForm alertform, LoginAlert alert) throws NumberFormatException, DataStoreException {
        DataStore store = DataStore.store();
        
        Set<AlertBrief> briefSet = alert.getBriefs();
        
        briefSet.clear();

        // Remove all existing AlertBriefs
        //Iterator briefs = alert.getBriefs().iterator();
        //while (briefs.hasNext()) {
        //    briefs.next();
        //    briefs.remove();
        //}
        //store.persist(alert);
        
    	if (alertform.isAll()) {
            // Leave alert's briefs empty
        } else {
        
            //Add all selected Briefs
            for (int i = 0; i < alertform.getBrief().length; i++) {
                AlertBrief ab = new AlertBrief();
                ab.setLogin(alert.getLogin());
                ab.setBrief((Brief)store.getObject(Brief.class, new Long(alertform.getBrief()[i])));
                store.persist(ab);
                briefSet.add(ab);
            }
        
            //Add all selected sections from unselected briefs
            for (int i = 0; i < alertform.getSection().length; i++) {
                SortSection ss = (SortSection)store.getObject(SortSection.class, new Long(alertform.getSection()[i]));
                boolean present = false;
                AlertBrief ab = null;
                Iterator<AlertBrief> brief = briefSet.iterator();
                while (brief.hasNext()) {
                    ab = brief.next();
                    if (ab.getBrief().equals(ss.getBrief())) {
                        present = true;
                        break;
                    }
                }
                if (!present) {
                    ab = new AlertBrief();
                    ab.setLogin(alert.getLogin());
                    ab.setBrief(ss.getBrief());
                    ab.addSortSection(ss);
                    store.persist(ab);
                    briefSet.add(ab);
                } else {
                    if (ab.getSortSections().size() != 0) {
                        ab.addSortSection(ss);
                        store.persist(ab);
                    } else {
                        // Do not add the sort section the brief has already been selected
                    }
                }
            }
        }
    	store.persist(alert);
    }
    
    private void updateSourceData(AlertForm alertform, LoginAlert alert) throws DataStoreException {
    	Set<SourceType> sourceTypes = alert.getSourceTypes();
    	Set<SourceCategory> pubTypes = alert.getSourceCategories();
    	
    	sourceTypes.clear();
    	pubTypes.clear();

    	//DataStore.store().persist(alert);
    	if (alertform.getAllSources()) {
    		// Leave alert's sourcetypes empty
    	} else {
    		boolean publication = false;
    		for (int i = 0; i < alertform.getSourceType().length; i++) {
    			SourceType st = (SourceType)DataStore.store().getObject(SourceType.class, "sourceTypeID", new Long(alertform.getSourceType()[i]));
    			if (st.getSourceTypeName().equals("Publication")) {
    				publication = true;
    			}
    			sourceTypes.add(st);
    		}
    		
    		// Only add the publication types if the publication sourceType
    		// is not selected
    		if (!publication) {
    			for (int i = 0; i < alertform.getSourceCategories().length; i++) {
    				SourceCategory pt = (SourceCategory)DataStore.store().getObject(SourceCategory.class, "categoryID", new Long(alertform.getSourceCategories()[i]));
    				pubTypes.add(pt);
    			}
    		}
    	}
    	DataStore.store().persist(alert);
    }
}
