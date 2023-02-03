package com.mws.phoenix.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mws.db.DataStore;

/**
 * This class is registered as a Listener in the web.xml file:
 * 
 * &lt;listener&gt;
 *   &lt;listener-class&gt;com.xtremeinformation.news.listener.DatabaseListener&lt;/listener-class&gt;
 * &lt;/listener&gt;
 * 
 * and uses the resource reference:
 * 
 * &lt;resource-ref&gt;
 *   &lt;description&gt;WebSite Database&lt;/description&gt;
 *   &lt;res-ref-name&gt;jdbc/xi&lt;/res-ref-name&gt;
 *   &lt;res-type&gt;javax.sql.DataSource&lt;/res-type&gt;
 *   &lt;res-auth&gt;Container&lt;/res-auth&gt;
 * &lt;/resource-ref&gt;
 * 
 * The ROOT.xml file contains the settings for the DataSource:
 * 
 * &lt;Resource name="jdbc/xi" auth="Container" type="javax.sql.DataSource"/&gt;
 *   &lt;ResourceParams name="jdbc/xi"&gt;
 *     &lt;parameter&gt;
 *       &lt;name&gt;factory&lt;/name&gt;
 *       &lt;value&gt;org.apache.commons.dbcp.BasicDataSourceFactory&lt;/value&gt;
 *     &lt;/parameter&gt;
 *     &lt;parameter&gt;
 *       &lt;name&gt;username&lt;/name&gt;
 *       &lt;value&gt;web&lt;/value&gt;
 *     &lt;/parameter&gt;
 *     &lt;parameter&gt;
 *       &lt;name&gt;password&lt;/name&gt;
 *       &lt;value&gt;130149&lt;/value&gt;
 *     &lt;/parameter&gt;
 *     &lt;parameter&gt;
 *       &lt;name&gt;driverClassName&lt;/name&gt;
 *       &lt;value&gt;com.microsoft.jdbc.sqlserver.SQLServerDriver&lt;/value&gt;
 *     &lt;/parameter&gt;
 *     &lt;parameter&gt;
 *       &lt;name&gt;url&lt;/name&gt;
 *       &lt;value&gt;jdbc:microsoft:sqlserver://svr-webdb:1433;DatabaseName=WebNews;ProgramName=news.xtremeinformation.com;SelectMethod=cursor&lt;/value&gt;
 *     &lt;/parameter&gt;
 *     &lt;parameter&gt;
 *       &lt;name&gt;maxActive&lt;/name&gt;
 *       &lt;value&gt;100&lt;/value&gt;
 *     &lt;/parameter&gt;
 *     &lt;parameter&gt;
 *       &lt;name&gt;maxIdle&lt;/name&gt;
 *       &lt;value&gt;10&lt;/value&gt;
 *     &lt;/parameter&gt;
 *   &lt;/ResourceParams&gt;
 * &lt;/Resource&gt;
 *
 * The DatabaseListener is called whenever the ServletContext for the web 
 * application is started. It retrieves a DataSource from the JNDI server which 
 * is created by the Resource in the descriptor file and places it in 
 * application scope so it is available to all Actions within the struts 
 * framework.
 * 
 * @author Ed Webb
 */
public class DatabaseListener implements ServletContextListener {

  Log log = LogFactory.getLog(DatabaseListener.class);
    
  /**
   * Looks up the DataSource in the JNDI server and place a reference
   * to it in the ServletContext object (application scope)
   * @param servletContextEvent the {@link ServletContextEvent} this class is listening for 
   */
  public void contextInitialized(ServletContextEvent servletContextEvent) {

    DataStore ds = null;
    log.info("Initializing DataStore");
    
    try {
    	DataStore.setConfig("/hibernate.cfg.xml");
        ds = DataStore.store();
        if(ds == null) {
        log.error("DataStore not created - null returned");
      } else {
        log.info("DataStore created successfully");
      }
    } catch (Throwable t) {
      log.error("DataStore not created - exception thrown", t);
    }
  }

  /**
   * Remove the reference to the DataSource from the ServletContext object
   * (application scope)
   */
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    log.info("DataStore destroyed successfully");
  }
}