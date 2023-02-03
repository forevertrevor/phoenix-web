package com.mws.phoenix.db.web;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.Icon;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.db.ValueObject;
import com.mws.util.ResourceUtilities;

/**
 * @author Ed Webb
 * @version
 * @since
 */
public class WebLogEntry extends ValueObject {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long entryID;
    protected Date entryTime;
    protected String entryType;
    protected String message;
    
    public final static String TYPE_LOGIN_SUCCESS      = "Login Success";
    public final static String TYPE_AUTO_LOGIN_SUCCESS = "Auto Login";
    public final static String TYPE_LOGIN_FAIL         = "Login Fail";
    public final static String TYPE_SOURCE_FILE        = "Source File";
    public final static String TYPE_EMAIL_ALERT        = "Email Alert";
    public final static String TYPE_MY_ARCHIVE         = "My Archive";

    // Value Object metadata implementation
    public static final String className = "Web Log Entry";
    public static final String[] propertyNames = {"Entry ID", "Entry Time", "Entry Type", "Message"};
    public static final String[] properties = {"entryID", "entryTime", "entryType", "message"};
    public static final Class<?>[] propertyClasses = {Long.class, Date.class, String.class, String.class};
    public static final int[] propertySizes = {0, 0, 20, INFINITY};
    public static final Icon icon = ResourceUtilities.getIcon(WebLogEntry.class, "web-log-entry-16x16.gif");
    
    public WebLogEntry() {
        this.entryTime = new GregorianCalendar(TimeZone.getTimeZone("GMT")).getTime();
    }
    
    public WebLogEntry(String type, String message) {
        this.entryType = type;
        this.entryTime = new GregorianCalendar(TimeZone.getTimeZone("GMT")).getTime();
        this.message = message;
    }
    
    public Icon getIcon() {
    	return icon;
    }
    
    public static void log(String type, String message) {
        WebLogEntry entry = new WebLogEntry(type,message);
        try {
            DataStore.store().persist(entry);
        } catch (DataStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public String getObjectName() {
        return className;
    }

    public String[] getPropertyNames() {
        return propertyNames;
    }

    public String[] getProperties() {
        return properties;
    }

    public Class<?>[] getPropertyClasses() {
        return propertyClasses;
    }

    public int[] getPropertySizes() {
        return propertySizes;
    }

    public Long getID() {
        return entryID;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(entryTime);
        sb.append(" ");
        sb.append(entryType);
        sb.append(": ");
        sb.append(message);
        return sb.toString();
    }
    
    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append(entryID);
        sb.append(" : ");
        sb.append(entryTime);
        sb.append(" ");
        sb.append(entryType);
        sb.append(": ");
        sb.append(message);
        return sb.toString();
    }
    
    /**
     * @return Returns the entryID.
     */
    public Long getEntryID() {
        return entryID;
    }

    /**
     * @param entryID The entryID to set.
     */
    public void setEntryID(Long entryID) {
        this.entryID = entryID;
    }


    public String getEntryType() {
        return entryType;
    }
    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Date getEntryTime() {
        return entryTime;
    }
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
}
