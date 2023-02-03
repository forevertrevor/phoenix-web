package com.mws.phoenix.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mws.phoenix.web.exceptions.ResourcePathException;

/**
 * Generic Action for forwarding a request to the correct Action handler based on
 * the value of the dispatch parameter of the request object.
 *
 * @author Ed Webb
 */
public class DispatchAction extends Action {


	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String dispatch = request.getParameter("fwd");
		if (null == dispatch) {
			throw new ResourcePathException("error.dispatch.nomatch","*no parameter found*");
		}
		
		ActionForward forward = null;
		
		if (dispatch.equals("bookmark")) {
			String bookmark = (String)request.getSession().getAttribute("bookmark");
			if (bookmark == null) {
				throw new ResourcePathException("error.dispatch.nobookmark");
			}
			forward = new ActionForward(bookmark);
 		} else {
 			forward = mapping.findForward(dispatch);
 		}
 			if (null == forward) {
 				throw new ResourcePathException("error.dispatch.nomatch",dispatch);
 			}
		
		return forward;
	}

}
