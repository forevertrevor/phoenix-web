package com.mws.phoenix.web.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.mws.phoenix.db.web.Login;

public class FinanceForm extends ActionForm {
	
    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private Login user;
    private String financeID;
    private String action;
    
    private boolean enabled;
    private boolean shareEnabled;

    //private String[] shares;
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        action = "";
        
        user = null;
        
        financeID = "0";
        enabled = false;
        shareEnabled = false;

        //shares = new String[] {};
    }

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFinanceID() {
		return financeID;
	}

	public void setFinanceID(String financeID) {
		this.financeID = financeID;
	}

	public boolean isShareEnabled() {
		return shareEnabled;
	}

	public void setShareEnabled(boolean shareEnabled) {
		this.shareEnabled = shareEnabled;
	}

	/*
	public String[] getShares() {
		return shares;
	}

	public void setShares(String[] shares) {
		this.shares = shares;
	}
	*/
	
	public Login getUser() {
		return user;
	}

	public void setUser(Login user) {
		this.user = user;
	}
}
