package com.mws.phoenix.web.login;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 *
 *
 * @author Ed Webb
 */
public class ContactAction extends Action {

    public static final String CONTACT_ERRORS = "mediagen.contact.errors";
    public static final String CONTACT_INFO = "mediagen.contact.messages";
    
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ContactForm contact = (ContactForm)form;
	
		sendMessage(request,contact);
		
		ActionMessages msgs = new ActionMessages();
		msgs.add(CONTACT_INFO, new ActionMessage("info.contactus.success"));
		request.setAttribute(CONTACT_INFO, msgs);
		
		return mapping.findForward("success");
	}

	private void sendMessage(HttpServletRequest request, ContactForm contact) throws MessagingException, AddressException {
		HttpSession session = request.getSession(true);
		Session mail = (Session)session.getServletContext().getAttribute("mail");
		StringBuffer subject = new StringBuffer();
		StringBuffer message = new StringBuffer();
		
		Message msg = new MimeMessage(mail);
		msg.setFrom(new InternetAddress("mail@xy.media"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress("mail@xy.media"));
		
		subject.append("Web enquiry from ");
		message.append(contact.getTitle());
		message.append(" ");
		subject.append(contact.getFirstName());
		subject.append(" ");
		subject.append(contact.getLastName());

		message.append("\nName: ");
		message.append(contact.getTitle());
		message.append(" ");
		message.append(contact.getFirstName());
		message.append(" ");
		message.append(contact.getLastName());
		message.append("\nJob Title: ");
		message.append(contact.getJobTitle());
		message.append("\nCompany: ");
		message.append(contact.getCompany());
		message.append("\nAddress:\n");
		message.append(contact.getAddress1());
		message.append("\n");
		message.append(contact.getAddress2());
		message.append("\n");
		message.append(contact.getAddress3());
		message.append("\n");
		message.append(contact.getAddress4());
		message.append("\n");
		message.append(contact.getPostcode());
		message.append("\n\nTelephone: ");
		message.append(contact.getTelephone());
		message.append("\nFax: ");
		message.append(contact.getFax());
		message.append("\nEmail: ");
		message.append(contact.getEmail());
		message.append("\n\nMessage:\n");
		message.append(contact.getMessage());
		
		
		msg.setSubject(subject.toString());
		msg.setContent(message.toString(), "text/plain");
		Transport.send(msg);
	}

}
