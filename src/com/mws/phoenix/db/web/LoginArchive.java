package com.mws.phoenix.db.web;

import java.util.Date;

import javax.swing.Icon;

import com.mws.db.ValueObject;
import com.mws.util.ResourceUtilities;

/**
 * @author Ed Webb
 * @version
 * @since
 */
public class LoginArchive extends ValueObject {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long archiveID;
    protected Login login;
    protected Boolean enabled = Boolean.FALSE;
    protected Boolean includeOthers = Boolean.FALSE;
    protected Date lastSent;
    protected Date created;
    
    // Value Object metadata implementation
    public static final String className = "Login Archive";
    public static final String[] propertyNames = {"Archive ID", "Login", "Enabled", "Inc. Others", "Last Sent", "Created"};
    public static final String[] properties = {"archiveID", "login", "enabled", "includeOthers", "lastSent", "created"};
    public static final Class<?>[] propertyClasses = {Long.class, Login.class, Boolean.class, Boolean.class, Date.class, Date.class};
    public static final int[] propertySizes = {0, PARENT_PROPERTY, 0, 0, 0, 0};
    public static final Icon icon = ResourceUtilities.getIcon(LoginArchive.class, "login-archive-16x16.gif");
    
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
        return archiveID;
    }

    public Icon getIcon() {
    	return icon;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        //[Add selected properties to buffer]
        return sb.toString();
    }
    
    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append(archiveID);
        sb.append(" : ");
        //[Add all properties to buffer]
        sb.append(", ");
        return sb.toString();
    }
    
    /**
     * @return Returns the archiveID.
     */
    public Long getArchiveID() {
        return archiveID;
    }

    /**
     * @param archiveID The archiveID to set.
     */
    public void setArchiveID(Long archiveID) {
        this.archiveID = archiveID;
    }

    /**
     * @return Returns the enabled.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled The enabled to set.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public Boolean getIncludeOthers() {
		return includeOthers;
	}

	public void setIncludeOthers(Boolean includeOthers) {
		this.includeOthers = includeOthers;
	}

	/**
     * @return Returns the login.
     */
    public Login getLogin() {
        return login;
    }
    /**
     * @param login The login to set.
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    public Date getLastSent() {
        return lastSent;
    }
    public void setLastSent(Date lastSent) {
        this.lastSent = lastSent;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


}
