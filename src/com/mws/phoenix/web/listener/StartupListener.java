package com.mws.phoenix.web.listener;

import java.util.Date;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

import org.apache.commons.beanutils.ConvertUtils;

import com.mws.phoenix.web.DateConverter;
import com.mws.phoenix.web.WebUtilities;

/**
 * This class is registered as a Listener in the web.xml file.
 * It listens for when the Servlet Context is Initialized or Destroyed.
 * It is used to do all those little startup configuration jobs. Currently
 * the only thing it has to do is register a DateConverter with the 
 * ConvertUtils class and place a reference to the Globals object in the
 * application scope
 *  
 * @author Ed Webb  
 */
public class StartupListener implements ServletContextListener{

	/**
     * Registers the DateConverter with the ConvertUtils class and
     * places a reference to the Globals class to application scopr
	 * 
	 */
    public void contextInitialized(ServletContextEvent event) {
        
        //Register my date converter with the BeanUtils
        ConvertUtils.register(new DateConverter(), Date.class);

		//Adds a reference to the Globals class to the application
		event.getServletContext().setAttribute("globals", WebUtilities.getInstance());

		//Adds a reference to the Evaluation class to the application
		//event.getServletContext().setAttribute("eval", Evaluation.getInstance());

    }

    public void contextDestroyed(ServletContextEvent event) {
        // Nothing to do
    }
}
