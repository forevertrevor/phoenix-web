package com.mws.phoenix.web.login;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import com.mws.db.DataStore;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.WebLogEntry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginAction extends Action {

    public static final String LOGIN_ERRORS = "mediagen.login.errors";
    public static final String LOGIN_INFO = "mediagen.login.messages";

    /**
     * Attempts to log the user into the web site by using the UserFactory.createUser() method
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataStore store = DataStore.store();
        
        HttpSession session = request.getSession(true);
        LoginForm login = (LoginForm)form;
        
        Integer id = store.beginTransaction();
        Login user = (Login)store.getObject(Login.class, "loginName", login.getUsername());

        ActionErrors errors = new ActionErrors();
        
        if (user != null) {
            if (!user.getPassword().equals(login.getPassword())) {
                store.rollbackTransaction(id);
                WebLogEntry.log(WebLogEntry.TYPE_LOGIN_FAIL, login.getUsername() + " incorrect password entered");
                errors.add(LOGIN_ERRORS, new ActionError("error.login.nomatch"));
                request.setAttribute(LOGIN_ERRORS, errors);
                return mapping.getInputForward();
            } else if(user.getMaxLogins().compareTo(user.getCurLogins()) <= 0) {
                store.rollbackTransaction(id);
                WebLogEntry.log(WebLogEntry.TYPE_LOGIN_FAIL, login.getUsername() + " maximum logins exceeded");
                errors.add(LOGIN_ERRORS, new ActionError("error.login.maxusers"));
                request.setAttribute(LOGIN_ERRORS, errors);
                return mapping.getInputForward();
            } else {
                Long curLogins = user.getCurLogins();
                curLogins = new Long(curLogins.longValue() + 1);
                user.setCurLogins(curLogins);
                store.persist(user);
                store.commitTransaction(id);
                WebLogEntry.log(WebLogEntry.TYPE_LOGIN_SUCCESS, login.getUsername());
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(user.getTimeout().intValue() * 60);

                // execute the Default Report
                //if (user.getDefaultReport() != null) {
                //   ReportUtilities.executeQuery(user.getDefaultReport(), user, (MessageResources)request.getSession().getServletContext().getAttribute("org.apache.struts.action.MESSAGE"));
                //}

                return mapping.findForward("success");
            }
        } else {
            store.rollbackTransaction(id);
            WebLogEntry.log(WebLogEntry.TYPE_LOGIN_FAIL, login.getUsername() + " username not recognised");
            errors.add(LOGIN_ERRORS, new ActionError("error.login.nouser"));
            request.setAttribute(LOGIN_ERRORS, errors);
            return mapping.getInputForward();
        }
    }
}