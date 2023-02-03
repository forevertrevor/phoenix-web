package com.mws.phoenix.web.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.mws.phoenix.db.web.Login;

/**
 * 
 * @author Ed Webb
 */
public class AlertForm extends ValidatorForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;

	public final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    
    private Login user;
    private String alertID;
    private String action;
    
    private String lastSent;
    private String template;
    
    private boolean enabled;
    private boolean html;
    private boolean uploadTime;
    
    private boolean noContent;
    
    //Set of Times to delete
    private String[] times;

    //New time to add
    private String hour;
    private String minute;
    
    //Briefs and Section to include
    private boolean all;
    private String[] briefs;
    private String[] sections;
    
    private boolean allsources;
    private String[] sourceTypes;
    private String[] pubTypes;
    
    //Set of emails to remove
    private String[] emails;

    private String email;
    
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        action = "";
        
        user = null;
        
        lastSent = "";
        alertID = "0";
        template = "emailalert.xsl";
        enabled = false;
        html = false;
        uploadTime = false;
        hour = "";
        minute = "";
        times = new String[] {};
        noContent = false;
        
        all = false;
        briefs = new String[] {};
        sections = new String[] {};
        
        allsources = false;
        sourceTypes = new String[] {};
        pubTypes = new String[] {};
        
        emails = new String[] {};
        email = "";
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isHtml() {
        return html;
    }
    public void setHtml(boolean html) {
        this.html = html;
    }

    public boolean isUploadTime() {
        return uploadTime;
    }
    public void setUploadTime(boolean uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    
    public Login getUser() {
        return user;
    }
    public void setUser(Login user) {
        this.user = user;
    }
    
    public boolean isAll() {
        return all;
    }
    public void setAll(boolean all) {
        this.all = all;
    }
    public String[] getBrief() {
        return briefs;
    }
    public void setBrief(String[] brief) {
        this.briefs = brief;
    }
    public String[] getSection() {
        return sections;
    }
    public void setSection(String[] section) {
        this.sections = section;
    }
    
    public void setDeleteTime(String[] time) {
        this.times = time;
    }
    public String[] getDeleteTime() {
        return times;
    }

    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    
    public String getMinute() {
        return minute;
    }
    public void setMinute(String minute) {
        this.minute = minute;
    }

    public boolean isNoContent() {
		return noContent;
	}

	public void setNoContent(boolean noContent) {
		this.noContent = noContent;
	}

	public void setDeleteEmail(String[] email) {
        this.emails = email;
    }
    public String[] getDeleteEmail() {
        return emails;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastSent() {
        return lastSent;
    }

    public void setLastSent(String lastSent) {
        this.lastSent = lastSent;
    }
    
    public void setLastSent(Date lastSent) {
        if (lastSent == null) {
            this.lastSent = "";
        } else {
            this.lastSent = sdf.format(lastSent);
        }
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (action.equals("")) {
            // Form has not yet been sent to client
            return null;
        } else {
            ActionErrors errs = super.validate(mapping, request);
            request.setAttribute(AlertAction.ALERT_ERRORS, errs);
            return errs;
        }
    }

    public String getAlertID() {
        return alertID;
    }

    public void setAlertID(String alertID) {
        this.alertID = alertID;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String[] getSourceType() {
        return sourceTypes;
    }
    public void setSourceType(String[] sourceType) {
        this.sourceTypes = sourceType;
    }

    public String[] getSourceCategories() {
        return pubTypes;
    }
    public void setSourceCategories(String[] pubType) {
        this.pubTypes = pubType;
    }
    public boolean getAllSources() {
    	return allsources;
    }
    public void setAllSources(boolean all) {
    	allsources = all;
    }
}
