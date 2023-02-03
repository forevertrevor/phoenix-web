package com.mws.phoenix.web.listener;

import javax.mail.Session;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mws.phoenix.web.archive.Archiver;

/**
 * This class is registered as a Listener in the web.xml file.
 * It listens for when the Servlet Context is Initialized or Destroyed.
 * It looks up a Mail Session from the JNDI Server and places a reference 
 * to it in application scope so that all actions can get connections to 
 * the session from it. When the context is destroyed it removes it from the
 * application scope.
 * 
 * @author Ed Webb
 */
public class ArchiveListener implements ServletContextListener {

    Log log = LogFactory.getLog(ArchiveListener.class);
    
	/**
	 * Looks up the Mail Session in the JNDI server and place a reference
	 * to it in the ServletContext object (application scope)
	 * @param servletContextEvent the {@link ServletContextEvent} this class is listening for 
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {

        log.info("Initializing Archiver");

        ServletContext application = servletContextEvent.getServletContext();
        try {
			InitialContext ic = new InitialContext();
			String ctxName = application.getInitParameter("jndi.mail");
			Session mail = (Session)ic.lookup(ctxName);
        	
            Archiver archiver = new Archiver(application, mail);
			application.setAttribute("archiver", archiver);
			archiver.start();

            log.info("Archiver started successfully");
		} catch (Throwable t) {
			log.error("Archiver not started - threw exception", t);
		}
	}

	/**
	 * Removes the reference to the Mail Session from the ServletContext object
	 * (application scope)
	 * @param servletContextEvent the {@link ServletContextEvent} this class is listening for
	 */
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext application = servletContextEvent.getServletContext();
		Archiver archiver = (Archiver)application.getAttribute("archiver");
		if (archiver != null) {
		    archiver.setStop(true);
		    archiver.interrupt();
		    try {
		        archiver.join(60000);
		        if (archiver.isAlive()) {
		            log.warn("Archiver not shut down within 1 minute");
		        } else {
		            log.info("Archiver successfully shut down");
		        }
		    } catch (InterruptedException e) {
	            log.error("Archiver threw Interrupted Exception", e);
		    }
        }
	}
}