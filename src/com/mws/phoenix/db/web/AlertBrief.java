package com.mws.phoenix.db.web;

import java.util.HashSet;
import java.util.Set;

import javax.swing.Icon;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.db.ValueObject;
import com.mws.phoenix.db.brief.Brief;
import com.mws.phoenix.db.brief.SortSection;


/**
 * @author Ed Webb
 * @version
 * @since
 */
public class AlertBrief extends ValueObject {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long alertBriefID;
    protected Brief brief;
    protected Login login;
    protected Set<SortSection> sortSections = new HashSet<SortSection>();
    //[Add class's properties here]
    
    // Value Object metadata implementation
    public static final String className = "AlertBrief";
    public static final String[] propertyNames = {"AlertBrief ID", "Brief", "Login"};
    public static final String[] properties = {"alertBriefID", "brief", "login"};
    public static final Class<?>[] propertyClasses = {Long.class, Brief.class, Login.class};
    public static final int[] propertySizes = {0, 0, 0};
    public static final Icon icon = Brief.icon;
    
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
        return alertBriefID;
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
        sb.append(alertBriefID);
        sb.append(" : ");
        //[Add all properties to buffer]
        sb.append(", ");
        return sb.toString();
    }
    
    /**
     * @return Returns the alertBriefID.
     */
    public Long getAlertBriefID() {
        return alertBriefID;
    }

    /**
     * @param alertBriefID The alertBriefID to set.
     */
    public void setAlertBriefID(Long alertBriefID) {
        this.alertBriefID = alertBriefID;
    }
    public Brief getBrief() {
        return brief;
    }
    public void setBrief(Brief brief) {
        this.brief = brief;
    }
    public Login getLogin() {
        return login;
    }
    public void setLogin(Login login) {
        this.login = login;
    }
    public Set<SortSection> getSortSections() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, sortSections);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return sortSections;
    }
    public void setSortSections(Set<SortSection> sortSections) {
        this.sortSections = sortSections;
    }
    public boolean addSortSection(SortSection sortSection) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, sortSections);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.sortSections.add(sortSection);
    }
    public boolean removeSortSection(SortSection sortSection) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, sortSections);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.sortSections.remove(sortSection);
    }
}
