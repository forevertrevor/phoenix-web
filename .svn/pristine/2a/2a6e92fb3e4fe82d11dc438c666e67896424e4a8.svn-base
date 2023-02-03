package com.mws.phoenix.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.util.XMLFactory;

public class TransformServlet extends HttpServlet {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	Log log = LogFactory.getLog(TransformServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	InputStream xmlIs = null;
    	InputStream xslIs = null;
    	try {
    		xmlIs = getInputStream("xml", request, "data/");
    		xslIs = getInputStream("xsl", request, "templates/");
          	response.setContentType("text/html");
            OutputStream os = response.getOutputStream();
            XMLFactory.transform(XMLFactory.source(xmlIs), XMLFactory.source(xslIs), XMLFactory.result(os));
    	} catch (Exception e) {
            log.error(e.getMessage(), e);
    		response.setContentType("text/html");
    		Writer write = response.getWriter();
    		write.write("<h3>Error</h3>");
    		write.write(e.getMessage() + "<br />");
    		if (xmlIs == null) {
    			write.write("Cannot find xml file: " + request.getParameter("xml") + "<br />");
    		}
    		if (xslIs == null) {
    			write.write("Cannot find xsl file: " + request.getParameter("xsl") + "<br />");
    		}
    	} finally {
    		try {
    			xmlIs.close();
    		} catch (Throwable t) { 
    			
    		}
    		try {
    			xslIs.close();
    		} catch (Throwable t) { 
    			
    		}
    	}
    }
    
    private InputStream getInputStream(String parameter, HttpServletRequest request, String subfolder) throws IOException {
    	String path = request.getParameter(parameter);
    	if (path.startsWith("http")) {
    		URL url = new URL(path);
    		return url.openStream();
    	} else {
        	if (request.getParameter("welcome") == null) {
            	HttpSession session = request.getSession();
            	if (session != null) {
            		Login user = (Login)session.getAttribute("user");
            		if (!path.startsWith("/")) {
            			path = WebUtilities.getResource(this.getServletContext(), user.getStyle().getFolder() + subfolder, "default/" + subfolder, path);
            		}
            	}
        	}
    		return this.getServletContext().getResourceAsStream(path);
    	}
    }
    
}
