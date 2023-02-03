package com.mws.phoenix.web.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class RemindForm extends ValidatorForm {

	/**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String username = "";
	private String email = "";
	
    /* (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		username = "";
		email = "";
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errs =  super.validate(mapping, request);
        request.setAttribute(RemindAction.REMIND_ERRORS, errs);
        return errs;
    }
}
