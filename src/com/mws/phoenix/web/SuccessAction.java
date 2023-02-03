package com.mws.phoenix.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mws.phoenix.web.exceptions.ResourcePathException;

/**
* Generic dispatcher to ActionForwards.
*/
public final class SuccessAction extends Action {

    /**
     * Forwards request to "cancel", or "success" mapping.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param actionForm The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception exception is handled by ActionServlet 
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;

        // is the request cancelled?
		forward = mapping.findForward("cancel");
		if(isCancelled(request)) {
			if (null == forward) {
				throw new ResourcePathException("error.forward.nomatch");
			} else {
				return forward;
			}
		}

		forward = mapping.findForward("success");
		if (null == forward) {
			throw new ResourcePathException("error.forward.nomatch");			
		} else {
			return forward;
		}

    } 

} 
