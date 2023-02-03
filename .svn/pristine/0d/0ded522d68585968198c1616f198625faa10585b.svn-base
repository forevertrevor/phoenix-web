package com.mws.phoenix.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class UserAgentCaseTag extends SimpleTagSupport {

	private String agent = null;
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public void doTag() throws JspException, IOException {
		UserAgentSwitchTag parent = null;
		try {
			 parent = (UserAgentSwitchTag)getParent();
		} catch (ClassCastException e) {
			throw new JspException("switch tag is not parent of case tag.");
		}
		if (parent == null) {
			throw new JspException("case tag not inside switch tag.");
		}
		if (agent == null) {
			throw new JspException("case tag missing agent attribute.");
		}
		System.out.println(agent + " = " + parent.getAgent());
		if (parent.getAgent().equals(agent)) {
			getJspBody().invoke(null);
		}
	}
}
