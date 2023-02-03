package com.mws.phoenix.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mws.phoenix.db.web.Login;

/**
 * Generic dispatcher to Tiles definitions.
 *
 * @author Ed Webb
 */
public class TilesAction extends Action {

   /**
    * Takes an Action Path and a request parameter and constructs an
    * ActionForward to a Tiles Definition from them. A request to 
    * /action/welcome?page=intpress_uk will be converted into an ActionForward
    * to the tile definition .welcome.intpress.uk
    * 
    * @param mapping The ActionMapping used to select this instance
    * @param actionForm The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    *
    * @exception Exception exception is handled by ActionServlet 
    */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		Login user = (Login)session.getAttribute("user");
		ActionErrors errs = new ActionErrors();
        
        if (!WebUtilities.checkAccess(user, mapping.getParameter(), errs, "")) {
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
        }
        
        // Get the name of the file to serve
		String page = request.getParameter("page");
		if (null == page) {
            String prop = request.getParameter("property");
            if (null == prop) {
                return null;
            } else {
                page = BeanUtils.getProperty(user.getStyle(), prop);
                if (null == page) {
                    return null;
                }
            }
        }

		String tile = mapping.getPath().replace('/', '.') + "." + page.replace('_', '.');
		ActionForward forward = new ActionForward(tile);

		return forward;
	}

}
