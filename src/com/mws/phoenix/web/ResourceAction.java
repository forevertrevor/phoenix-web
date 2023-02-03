package com.mws.phoenix.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.web.exceptions.ResourcePathException;

/**
 * Action to retrieve and serve a static or dynamic resource to a client. 
 * The request must include either a resource parameter or a property 
 * parameter.
 * resource - The value of the parameter is a filename that can be found
 * in the user's directory or in the default directory.
 * property - The value of the parameter is a property of the 
 * session.user.style object which contains a filename that can be found
 * in the user's directory or in the default directory.
 * 
 * @author Ed Webb
 */
public class ResourceAction extends Action {

    /**
     * Returns the specified resource to the client. If the requested resource
     * exists in the user's directory then this is served to the user. If it does 
     * not then if the resource exists in the default directory that is served.
     * If the requested resource exists in neither directory then a 
     * ResourcePathException is thrown.
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
		ServletContext application = session.getServletContext();

        // Get the name of the file to serve
		String resource = request.getParameter("resource");
		if (null == resource) {
            String prop = request.getParameter("property");
            if (null == prop) {
                return null;
            } else {
                resource = BeanUtils.getProperty(user.getStyle(), prop);
                if (null == resource) {
                    return null;
                }
            }
        }
    
		if (resource.startsWith("/action")) {
		    //TODO if cookies are off need to append the JSESSIONID
		    return new ActionForward(resource);
		}
		
        // Get the user's directory
        String userfolder = WebUtilities.getUserFolder(request);
		
        // Get the default directory
		String defaultfolder = "/default";
		
		String subfolder = WebUtilities.getSubFolder(mapping);
		
        defaultfolder = defaultfolder + "/" + subfolder;
        userfolder = userfolder + "/" + subfolder;
		
        String path = WebUtilities.getResource(application, userfolder, defaultfolder, resource);
        
        if (path == null || path.equals("")) {
            throw new ResourcePathException("action.missing.resource", path);
        } else {
            return new ActionForward(path);
        }
    }

}
