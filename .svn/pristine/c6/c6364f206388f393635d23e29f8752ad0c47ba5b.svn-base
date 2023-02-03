package com.mws.phoenix.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.WebLogEntry;
import com.mws.phoenix.web.login.LoginAction;
import com.mws.phoenix.web.press.NewsForm;
import com.mws.phoenix.web.press.SelectedForm;

/**
 * @author Ed Webb
 * @version 
 * @since
 */
public class AutoLoginAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        AutoLoginForm auto = (AutoLoginForm)form;
        
        auto.setUrl(auto.getUrl().replaceAll("~", "&"));
        
        Login user = (Login)session.getAttribute("user");
        if (auto.getHash() == null && user != null && (user.getLoginName().equals(auto.getUser()) || user.getLevel().equals(Login.INTERNAL))) {
        	// Calculate a new Auto Login hash value
        	if (auto.getHash() == null && auto.getPassword() != null) {
                auto.setHash(calculateHash(auto.getUser(), auto.getPassword(), auto.getUrl()));
                return mapping.findForward("success");
            } else if (auto.getHash() != null && auto.getPassword() == null) {
                return new ActionForward(auto.getUrl());
            }
        } else {
            try {
            	System.out.println("** DEBUG: Finding Login for " + auto.getUser());
                user = (Login)DataStore.store().getObject(Login.class, "loginName", auto.getUser());
                if (user == null) {
                	ActionErrors errs = new ActionErrors();
                	errs.add(LoginAction.LOGIN_ERRORS, new ActionMessage("error.login.nouser"));
                    request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
                    return mapping.findForward("failure");
                }
                if (!compareHash(auto, user)) {
                	ActionErrors errs = new ActionErrors();
                	errs.add(LoginAction.LOGIN_ERRORS, new ActionMessage("error.login.nomatch"));
                    request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
                    return mapping.findForward("failure");
                }
                
                session.setAttribute("user", user);

                // execute the Default Report
                //if (user.getDefaultReport() != null) {
                //    ReportUtilities.executeQuery(user.getDefaultReport(), user, (MessageResources)request.getSession().getServletContext().getAttribute("org.apache.struts.action.MESSAGE"));
                //}
                    
                // create newsForm and selectedForm attributes
                session.setAttribute("selectedForm", new SelectedForm());
                session.setAttribute("newsForm", new NewsForm());
                if (auto.getUrl().startsWith("/action/clip") || auto.getUrl().startsWith("/action/tool")) {
                    WebLogEntry.log(WebLogEntry.TYPE_AUTO_LOGIN_SUCCESS, user.getLoginName());
                    session.setMaxInactiveInterval(30);
                } else {
                    WebLogEntry.log(WebLogEntry.TYPE_LOGIN_SUCCESS, user.getLoginName());
                    session.setMaxInactiveInterval(user.getTimeout().intValue() * 60);
                }
                return new ActionForward(auto.getUrl());
            } catch (DataStoreException e) {
                e.printStackTrace();
            	ActionErrors errs = new ActionErrors();
            	errs.add(LoginAction.LOGIN_ERRORS, new ActionMessage("error.login.nouser"));
                request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            }
        }
        
        return mapping.findForward("failure");
    }
    
    /**
     * Compare the hash code in the Autologin form to one calculated on the server
     * 
     * @param form the Autologin form sent by the client
     * @return true if the hashcodes match
     */
    private boolean compareHash(AutoLoginForm form, Login user) {
    	String hash = calculateHash(form.getUser(), user.getPassword(), form.getUrl());
    	if (form.getHash().equals(hash)) {
            return true;
        } else {
            System.out.println("** Hash Mismatch: form " + form.getUser() + " " + form.getUrl() + " " + form.getHash());
            System.out.println("** Hash Mismatch: user " + user.getLoginName() + " " + form.getUrl() + " " + hash);
        	return false;
        }
    }

    private String calculateHash(String user, String password, String url) {
        String plain = user + password + url;
        String hash = Integer.toHexString(plain.hashCode());
        return hash;
    }
}
