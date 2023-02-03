package com.mws.phoenix.web.login;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoffAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionMessages msgs = new ActionMessages();
        HttpSession session = request.getSession(true);

        session.removeAttribute("user");
        session.invalidate();
        msgs.add(LoginAction.LOGIN_INFO, new ActionMessage("info.logoff.success"));
        request.setAttribute(LoginAction.LOGIN_INFO, msgs);
        
        //Remove the SESSIONID cookie for the invalidated session
        Cookie cookie = new Cookie("SESSIONID", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        
        return mapping.findForward("login");
    }
}