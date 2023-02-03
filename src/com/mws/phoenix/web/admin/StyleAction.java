package com.mws.phoenix.web.admin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.mws.db.DataStore;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.LoginStyle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StyleAction extends Action {

    public static final String STYLE_INFO = "mediagen.style.messages"; 
    public static final String STYLE_ERRORS = "mediagen.style.errors"; 
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        Login user = (Login)session.getAttribute("user");
        StyleForm styleform = (StyleForm)form;
        
        // Check that user is authorised
        // Only INTERNAL users
        if (!user.getLevel().equals(Login.INTERNAL)) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noaccess", "style"));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
        }

        // Retrieve the style object to be edited
        LoginStyle style = null;
        if (styleform.getStyleID().equals("-1")) {
            styleform.setStyleID("");
            return mapping.findForward("success");
        } else if (styleform.getStyleID().equals("")) {
            style = new LoginStyle();
        } else {
            style = (LoginStyle)DataStore.store().getObject(LoginStyle.class, new Long(styleform.getStyleID()));
        }
        if (style == null) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noobject", "style", styleform.getStyleID()));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("failure");
        }
        
        ActionMessages msgs = new ActionMessages();

        if (styleform.getAction().equals("update")) {
            if (style.getStyleID() == null) {
                // This is a request to create a style
                BeanUtils.copyProperties(style, styleform);
                style.setStyleID(null);
                DataStore.store().persist(style);
                msgs.add(STYLE_INFO, new ActionMessage("info.style.insert"));
                request.setAttribute(STYLE_INFO, msgs);
            } else {
                // This is a request to update a style
                BeanUtils.copyProperties(style, styleform);
                DataStore.store().persist(style);
                msgs.add(STYLE_INFO, new ActionMessage("info.style.update"));
                request.setAttribute(STYLE_INFO, msgs);
            }
        } else {
            if (style.getStyleID() != null) {
                BeanUtils.copyProperties(styleform, style);
            }
        }
        return mapping.findForward("success");
    }
}
