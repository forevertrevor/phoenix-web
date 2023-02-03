package com.mws.phoenix.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.mws.phoenix.db.web.Login;

public class UserHashAction extends Action {

    public static final String HASH_INFO = "mediagen.hash.messages";
    public static final String HASH_ERRORS = "mediagen.hash.errors";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        UserHashForm hashform = (UserHashForm)form;
        ActionMessages msgs = new ActionMessages(); 
        Login user = (Login)session.getAttribute("user");
        
        // Only INTERNAL Users can generate auto-logins for other users
        if (!user.getLevel().equals(Login.INTERNAL) || hashform.getLoginname().equals("")) {
            hashform.setLoginname(user.getLoginName());
            hashform.setPassword(user.getPassword());
        }
        
        hashform.setUrl(createUrl(hashform.getLoginname(), hashform.getPassword(), hashform.getPath()));

        msgs.add(HASH_INFO, new ActionMessage("info.hash.success"));
        request.setAttribute(HASH_INFO, msgs);
        
        return mapping.findForward("success");
    }
    
    public static String createUrl(String loginname, String password, String path) {
        String hash = loginname + password + path;
        hash = Integer.toHexString(hash.hashCode());
        return "https://xy.media/action/auto-login?user=" + loginname + "&hash=" + hash + "&url=" + path;
    }
}
