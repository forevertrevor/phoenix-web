package com.mws.phoenix.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class UserAgentSwitchTag extends SimpleTagSupport {

	private String agent = "MSIE";
	private String[] agentName = new String[] {"Opera", "Firefox", "MSIE"};
	private String[] agentMarker = new String[] {"Opera", "Firefox", "MSIE"};
	
	public String getAgent() {
		return agent;
	}
	
	/**
	 * A comma separated list of browser names to be used by nested tags
	 * 
	 * @param names comma separated list of browser names
	 */
	public void setNames(String names) {
		agentName = names.split(",");
	}
	
	/**
	 * A comma separated list of 
	 * @param marks
	 */
	public void setMarkers(String marks) {
		agentMarker = marks.split(",");
	}
	
	public void setDefault(String agent) {
		this.agent = agent; 
	}

	public void doTag() throws JspException, IOException {
		HttpServletRequest request = (HttpServletRequest)((PageContext)getJspContext()).getRequest();
		String userAgent = request.getHeader("User-Agent");
		System.out.println(userAgent);
		for (int i = 0; i < agentMarker.length; i++) {
			if (userAgent.contains(agentMarker[i])) {
				agent = agentName[i];
				break;
			}
		}
		getJspBody().invoke(null);
	}
}
