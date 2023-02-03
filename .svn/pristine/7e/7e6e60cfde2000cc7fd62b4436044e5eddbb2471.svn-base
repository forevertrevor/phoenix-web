package com.mws.phoenix.web.admin;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class AdminForm extends ValidatorActionForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String search;
    private String action;
    
    public void reset(ActionMapping mapping, ServletRequest request) {
        super.reset(mapping, request);

        search="";
        action="";
    }
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errs = super.validate(mapping, request);
        request.setAttribute(AdminAction.ADMIN_ERRORS, errs);
        return errs;
    }
    
}
