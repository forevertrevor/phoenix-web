package com.mws.phoenix.web.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class UserHashForm extends ValidatorForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String path;
    private String loginname;
    private String password;
    private String url;
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        path = "";
        loginname = "";
        password = "";
        url = "";
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
