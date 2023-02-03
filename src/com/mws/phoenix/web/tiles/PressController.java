package com.mws.phoenix.web.tiles;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.ControllerSupport;

import com.mws.phoenix.db.web.Login;

/**
 * @author EdW
 * @version 
 * @since
 */
public class PressController extends ControllerSupport {

    Log log = LogFactory.getLog(PressController.class);
    
    /**
     * 
     */
    public PressController() {
        super();
    }

    
    /* (non-Javadoc)
     * @see org.apache.struts.tiles.Controller#perform(org.apache.struts.tiles.ComponentContext, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.ServletContext)
     */
    public void perform(ComponentContext tiles, HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Login user = (Login)session.getAttribute("user");
		ServletContext application = session.getServletContext();

        String path = "";

        // Get the user's directory
		String userfolder = "none/";
		if (user != null) {
		    try {
                userfolder = BeanUtils.getProperty(user.getStyle(), "folder");
            } catch (Exception e) {
                throw new ServletException(e.toString(), e);
            }
		}
		
        // Get the default directory
		String defaultfolder = "default/";
		
        // Append the correct subfolder 
		String subfolder="jsps/";
        defaultfolder = defaultfolder + subfolder;
        userfolder = userfolder + subfolder;
		
        //tiles.putAttribute("title", "Mediagen");
        
        Iterator<String> it = tiles.getAttributeNames();
        while (it.hasNext()) {
            String name = it.next();
            //Object test = tiles.getAttribute(name);
            String resource = (String)tiles.getAttribute(name);
            
            if (resource.endsWith(".jspf")) {
            
                // Search for the file in the user's directory then in the default
                //if it is not found then throw an error else return an ActionForward
                // to the resource
                path = userfolder + resource;
                URL url = application.getResource("/WEB-INF/" + userfolder + resource);
                if (null == url) {
                    path = defaultfolder + resource;
                    url = application.getResource("/WEB-INF/" + defaultfolder + resource);
                }
                if (null == url) {
                    if (log.isWarnEnabled()) {
                        log.warn("Cannot find " + userfolder + resource + " or " + defaultfolder + resource );
                    }
                    throw new ServletException("Cannot find tile " + userfolder + resource + " or " + defaultfolder + resource); 
                }
		
                tiles.putAttribute(name, "/WEB-INF/" + path);
            }
		
        }
    }
}
