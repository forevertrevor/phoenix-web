package com.mws.phoenix.web.admin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.mws.db.DataStore;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.LoginAlert;
import com.mws.phoenix.db.web.LoginArchive;
import com.mws.phoenix.db.web.LoginFinance;
import com.mws.phoenix.db.web.LoginGroup;
import com.mws.phoenix.db.web.LoginStyle;
import com.mws.phoenix.db.web.WebLogEntry;

/**
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class AdminAction extends Action {

    public static final String ADMIN_INFO = "mediagen.admin.messages"; 
    public static final String ADMIN_ERRORS = "mediagen.admin.errors"; 
    
	
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        AdminForm admin = (AdminForm)form;
        ServletContext application = session.getServletContext();
        Login user = (Login)session.getAttribute("user");
        String parameter = admin.getAction();
        if (parameter == null || parameter.equals("")) {
            parameter = mapping.getParameter();
        }
       
        // Check that user is authorised
        if (!user.getLevel().equals(Login.INTERNAL)) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noaccess", parameter));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
        }
        
        //Get the search term they want to filter by
        String search = admin.getSearch();
        if (search == null) {
            search = "";
        }

        // Find which object they want to list
        if (parameter.equals("alerts")) {
            setAlertAttributes(request, application, search);
        } else if (parameter.equals("archives")) {
            setArchiveAttributes(request, application, search);
        } else if (parameter.equals("users")) {
            setUserAttributes(request, search);
        } else if (parameter.equals("groups")) {
            setGroupAttributes(request, search);
        } else if (parameter.equals("styles")) {
            setStyleAttributes(request, search);
        } else if (parameter.equals("finances")) {
            setFinanceAttributes(request, search);
        } else if (parameter.equals("log")) {
            setLogAttributes(request, search);
        }
        return mapping.findForward(parameter);
    }

    private void setAlertAttributes(HttpServletRequest request, ServletContext application, String search) throws Exception {
        if (search.equals("")) {
            request.setAttribute("alerts", DataStore.store().getAllObjects(LoginAlert.class, "login.loginName"));
        } else {
            if (search.startsWith("group:")) {
                request.setAttribute("alerts", DataStore.store().getObjects(LoginAlert.class, "login.group.groupName", "like", search.substring(search.indexOf(":") + 1).toLowerCase(), "login.loginName"));
            } else if (search.startsWith("style:")) {
                request.setAttribute("alerts", DataStore.store().getObjects(LoginAlert.class, "login.style.styleName", "like", search.substring(search.indexOf(":") + 1).toLowerCase(), "login.loginName"));
            } else if (search.startsWith("active:")) {
                request.setAttribute("alerts", DataStore.store().getObjects(LoginAlert.class, "enabled", "=", search.substring(search.indexOf(":") + 1).equals("true") ? Boolean.TRUE : Boolean.FALSE, "login.loginName"));
            } else {
                request.setAttribute("alerts", DataStore.store().getObjects("select loginAlert from LoginAlert loginAlert where loginAlert.login.loginName like '%" + search.toLowerCase() + "%' or loginAlert.login.userName like '%" + search.toLowerCase() + "%'"));
            }
        }
        request.setAttribute("lastrun", getLastRunDate("alerter", application));
        request.setAttribute("error", getError("alerter", application));
    }
    
    private void setArchiveAttributes(HttpServletRequest request, ServletContext application, String search) throws Exception {
        if (search.equals("")) {
            request.setAttribute("archives", DataStore.store().getAllObjects(LoginArchive.class, "login.loginName"));
        } else {
            if (search.startsWith("group:")) {
                request.setAttribute("archives", DataStore.store().getObjects(LoginArchive.class, "login.group.groupName", "like", search.substring(search.indexOf(":") + 1).toLowerCase(), "login.loginName"));
            } else if (search.startsWith("style:")) {
                request.setAttribute("archives", DataStore.store().getObjects(LoginArchive.class, "login.style.styleName", "like", search.substring(search.indexOf(":") + 1).toLowerCase(), "login.loginName"));
            } else if (search.startsWith("active:")) {
                request.setAttribute("archives", DataStore.store().getObjects(LoginArchive.class, "enabled", "=", search.substring(search.indexOf(":") + 1).equals("true") ? Boolean.TRUE : Boolean.FALSE, "login.loginName"));
            } else {
                request.setAttribute("archives", DataStore.store().getObjects("select loginArchive from LoginArchive loginArchive where loginArchive.login.loginName like '%" + search.toLowerCase() + "%' or loginArchive.login.userName like '%" + search.toLowerCase() + "%'"));
            }
        }
        request.setAttribute("lastrun", getLastRunDate("archiver", application));
        request.setAttribute("error", getError("archiver", application));
    }

    private void setFinanceAttributes(HttpServletRequest request, String search) throws Exception {
        if (search.equals("")) {
            request.setAttribute("finances", DataStore.store().getAllObjects(LoginFinance.class, "login.loginName"));
        } else {
            if (search.startsWith("group:")) {
                request.setAttribute("finances", DataStore.store().getObjects(LoginFinance.class, "login.group.groupName", "like", search.substring(search.indexOf(":") + 1).toLowerCase(), "login.loginName"));
            } else if (search.startsWith("style:")) {
                request.setAttribute("finances", DataStore.store().getObjects(LoginFinance.class, "login.style.styleName", "like", search.substring(search.indexOf(":") + 1).toLowerCase(), "login.loginName"));
            } else if (search.startsWith("active:")) {
                request.setAttribute("finances", DataStore.store().getObjects(LoginFinance.class, "enabled", "=", search.substring(search.indexOf(":") + 1).equals("true") ? Boolean.TRUE : Boolean.FALSE, "login.loginName"));
            } else {
                request.setAttribute("finances", DataStore.store().getObjects("select loginFinance from LoginFinance loginFinance where loginFinance.login.loginName like '%" + search.toLowerCase() + "%' or loginFinance.login.userName like '%" + search.toLowerCase() + "%'"));
            }
        }
    }

    private void setUserAttributes(HttpServletRequest request, String search) throws Exception {
        if (search.equals("")) {
            request.setAttribute("users", DataStore.store().getAllObjects(Login.class, "loginName"));
        } else {
            if (search.startsWith("group:")) {
                request.setAttribute("users", DataStore.store().getObjects(Login.class, "group.groupName", "like", search.substring(search.indexOf(":") + 1), "loginName"));
            } else if (search.startsWith("style:")) {
                request.setAttribute("users", DataStore.store().getObjects(Login.class, "style.styleName", "like", search.substring(search.indexOf(":") + 1), "loginName"));
            } else if (search.startsWith("active:")) {
                request.setAttribute("users", DataStore.store().getObjects(Login.class, "curLogins", search.substring(search.indexOf(":") + 1).equals("true") ? ">" : "=", new Long(0), "loginName"));
            } else {
                request.setAttribute("users", DataStore.store().getObjects("select login from Login login where login.loginName like '%" + search + "%' or login.userName like '%" + search + "%'"));
            }
        }
    }

    private void setGroupAttributes(HttpServletRequest request, String search) throws Exception {
        if (search.equals("")) {
            request.setAttribute("groups", DataStore.store().getAllObjects(LoginGroup.class, "groupName"));
        } else {
            request.setAttribute("groups", DataStore.store().getObjects(LoginGroup.class, "groupName", "like", "%" + search.toLowerCase() + "%", "groupName"));
        }
    }

    private void setStyleAttributes(HttpServletRequest request, String search) throws Exception {
        if (search.equals("")) {
            request.setAttribute("styles", DataStore.store().getAllObjects(LoginStyle.class, "styleName"));
        } else {
            request.setAttribute("styles", DataStore.store().getObjects(LoginStyle.class, "styleName", "like", "%" + search.toLowerCase() + "%", "styleName"));
        }
    }

    private void setLogAttributes(HttpServletRequest request, String search) throws Exception {
        request.setAttribute("entries", DataStore.store().getAllObjects(WebLogEntry.class));
    }
    
    
    
    private Date getLastRunDate(String engine, ServletContext application) {
        ObjectInputStream ois = null;        
        try {
            ois = new ObjectInputStream(application.getResourceAsStream("/WEB-INF/" + engine + "/lastrun.properties"));
            Date date = (Date)ois.readObject();
            return date;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (Throwable t) {
                
            }
        }
        return new Date(0);
    }

    private String getError(String engine, ServletContext application) {
        ObjectInputStream ois = null;        
        try {
            ois = new ObjectInputStream(application.getResourceAsStream("/WEB-INF/" + engine + "/errors.properties"));
            String error = (String)ois.readObject();
            return error;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (Throwable t) {
                
            }
        }
        return new String("Unable to read the error.properties file");
    }

}
