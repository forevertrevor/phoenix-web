package com.mws.phoenix.web.admin;

import org.apache.struts.action.ActionForm;

/**
 * @author EdW
 * @version 
 * @since
 */
public class AutoLoginForm extends ActionForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String user;
    private String url;
    private String hash;
    private String password;
    
    public void reset() {
        user = null;
        url = null;
        hash = null;
        password = null;
    }
    
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String page) {
        this.url = page;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @deprecated use setUrl instead. The page parameter interfers with the TilesAction class 
     * @param page
     */
    public void setPage(String page) {
        this.url = page;
    }
}
