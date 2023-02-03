package com.mws.phoenix.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.mws.db.DataStore;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.LoginFinance;

public class FinanceAction extends Action {
    
    public static final String FINANCE_INFO = "mediagen.finance.messages"; 
    public static final String FINANCE_ERRORS = "mediagen.finance.errors"; 
    
    Log log = LogFactory.getLog(FinanceAction.class);
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        Login user = (Login)session.getAttribute("user");
        FinanceForm financeform = (FinanceForm)form;

        // Retrieve the finance object to be edited
        LoginFinance finance = null;
        if (financeform.getFinanceID().equals("") || financeform.getFinanceID().equals("0")) {
            finance = (LoginFinance)DataStore.store().getObject(LoginFinance.class, user.getLoginID());
        } else {
            finance = (LoginFinance)DataStore.store().getObject(LoginFinance.class, new Long(financeform.getFinanceID()));
        }
        if (finance == null) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noobject", "finance", financeform.getFinanceID()));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("failure");
        }

        // Check that user is authorised
        // Only INTERNAL users
        if (!user.getLevel().equals(Login.INTERNAL)
         && !user.getLoginID().equals(finance.getFinanceID())
         && !(user.getLevel().equals(Login.ADMIN) && user.getGroup().getGroupID().equals(finance.getLogin().getGroup().getGroupID()))) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noaccess", "alert"));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
        }

        ActionMessages msgs = new ActionMessages();

        if (financeform.getAction().equals("update")) {
            // This is a request to update a finance
            BeanUtils.copyProperties(finance, financeform);
            DataStore.store().persist(finance);
            financeform.setUser(finance.getLogin());
            msgs.add(FINANCE_INFO, new ActionMessage("info.finance.update"));
            request.setAttribute(FINANCE_INFO, msgs);
        } else {
            BeanUtils.copyProperties(financeform, finance);
            financeform.setUser(finance.getLogin());
        }
        return mapping.findForward("success");
    }


}

