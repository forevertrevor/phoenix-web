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
public class ArchiveForm extends ValidatorForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;

	public final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    
    private Login user;
    
    private String action;
    private String archiveID;
    
    private boolean enabled;
    private boolean includeOthers;

    private String created;
    private String lastSent;
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        action = "";
        archiveID = "0";
        user = null;
        
        enabled = false;
        includeOthers = false;
        created = "";
        lastSent = "";
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (action.equals("")) {
            // Form has not yet been sent to client
            return null;
        } else {
            ActionErrors errs = super.validate(mapping, request);
            request.setAttribute(ArchiveAction.ARCHIVE_ERRORS, errs);
            return errs;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isIncludeOthers() {
		return includeOthers;
	}

	public void setIncludeOthers(boolean includeOthers) {
		this.includeOthers = includeOthers;
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
    public void setLogin(Login user) {
        this.user = user;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setCreated(Date created) {
        if (created == null) {
            created = new Date();
        }
        this.created = sdf.format(created);
    }
    
    public String getLastSent() {
        return lastSent;
    }

    public void setLastSent(String lastSent) {
        this.lastSent = lastSent;
    }
    public void setLastSent(Date lastSent) {
        if (lastSent == null) {
            lastSent = new Date();
        }
        this.lastSent = sdf.format(lastSent);
    }

    public String getArchiveID() {
        return archiveID;
    }

    public void setArchiveID(String archiveID) {
        this.archiveID = archiveID;
    }
}
