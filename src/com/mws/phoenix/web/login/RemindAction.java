package com.mws.phoenix.web.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.mws.db.DataStore;
import com.mws.phoenix.db.web.Login;

public class RemindAction extends Action {

    public static final String REMIND_INFO = "mediagen.remind.messages";
    public static final String REMIND_ERRORS = "mediagen.remind.errors";
    
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		RemindForm remind = (RemindForm)form;
        
		//TODO allow the entry of any email address in loginemail table!
		List<Login> lst = DataStore.store().getObjects(Login.class, new String[] {"loginName", "email"}, new Object[] {remind.getUsername(), remind.getEmail()});
		
		if (lst.size() > 0) {
		    sendReminder(request, session, remind, lst.get(0));
	        ActionMessages msgs = new ActionMessages();
		    msgs.add(LoginAction.LOGIN_INFO, new ActionMessage("info.remind.success",remind.getUsername(),remind.getEmail()));
			request.setAttribute(LoginAction.LOGIN_INFO, msgs);
	        return mapping.findForward("login");
		} else {
		    ActionErrors errs = new ActionErrors();
		    errs.add(REMIND_ERRORS, new ActionMessage("error.remind.nomatch",remind.getUsername(),remind.getEmail()));
		    request.setAttribute(REMIND_ERRORS, errs);
	        return mapping.findForward("failure");
		}
	}

    private void sendReminder(HttpServletRequest request, HttpSession session, RemindForm remind, Login reminder) throws MessagingException, AddressException {
        Session mail = (Session)session.getServletContext().getAttribute("mail");
        MessageResources props = this.getResources(request);
        Message msg = new MimeMessage(mail);
        msg.setFrom(new InternetAddress("mail@xy.media"));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(remind.getEmail()));
        msg.setSubject(props.getMessage("remind.email.subject"));
        msg.setContent(props.getMessage("remind.email.body", reminder.getLoginName(), reminder.getPassword()),"text/plain");
        Transport.send(msg);
    }
}
