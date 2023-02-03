package com.mws.phoenix.db.web;

import java.sql.Time;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.util.Date;

import javax.swing.Icon;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.db.ValueObject;
import com.mws.phoenix.db.source.SourceCategory;
import com.mws.phoenix.db.source.SourceType;
import com.mws.util.ResourceUtilities;

/**
 * @author Ed Webb
 * @version
 * @since
 */
public class LoginAlert extends ValueObject {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long alertID;
    protected Login login;
    protected Boolean enabled = Boolean.FALSE;
    protected Boolean html = Boolean.TRUE;
    protected Boolean uploadTime = Boolean.TRUE;
    protected Boolean noContent = Boolean.FALSE;
    protected Boolean rss = Boolean.FALSE;
    protected Set<String> emails = new HashSet<String>();
    protected Set<Time> times = new HashSet<Time>();
    protected Set<AlertBrief> briefs = new HashSet<AlertBrief>();
    protected Set<SourceType> sourceTypes = new HashSet<SourceType>();
    protected Set<SourceCategory> sourceCategories = new HashSet<SourceCategory>();
    protected Date lastSent;
    protected String template = "emailalert.xsl";
    
    // Value Object metadata implementation
    public static final String className = "LoginAlert";
    public static final String[] propertyNames = {"Alert ID", "Login", "Enabled", "HTML", "Template", "Emails", "Send When New", "Times", "Briefs", "Last Sent"};
    public static final String[] properties = {"alertID", "login", "enabled", "html", "template", "emails", "uploadTime", "times", "briefs", "lastSent"};
    public static final Class<?>[] propertyClasses = {Long.class, Login.class, Boolean.class, Boolean.class, String.class, String.class, Boolean.class, Date.class, AlertBrief.class, Date.class};
    public static final int[] propertySizes = {0, PARENT_PROPERTY, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final Icon icon = ResourceUtilities.getIcon(LoginAlert.class, "login-alert-16x16.gif");

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
        return alertID;
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
        sb.append(alertID);
        sb.append(" : ");
        //[Add all properties to buffer]
        sb.append(", ");
        return sb.toString();
    }
    
    /**
     * @return Returns the alertID.
     */
    public Long getAlertID() {
        return alertID;
    }

    /**
     * @param alertID The alertID to set.
     */
    public void setAlertID(Long alertID) {
        this.alertID = alertID;
    }
    /**
     * @return Returns the emails.
     */
    public Set<String> getEmails() {
        return emails;
    }
    /**
     * @param emails The emails to set.
     */
    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }
    public void addEmail(String email) {
        this.emails.add(email);
    }
    public void removeEmail(String email) {
        this.emails.remove(email);
    }
    
    /**
     * @return Returns the briefs.
     */
    public Set<AlertBrief> getBriefs() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, briefs);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return briefs;
    }
    /**
     * @param briefs The briefs to set.
     */
    public void setBriefs(Set<AlertBrief> briefs) {
        this.briefs = briefs;
    }
    public boolean addBrief(AlertBrief brief) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, briefs);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.briefs.add(brief);
    }
    public boolean removeBrief(AlertBrief brief) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, briefs);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.briefs.remove(brief);
    }

    public Boolean getUploadTime() {
        return uploadTime;
    }
    public void setUploadTime(Boolean uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * @return Returns the times.
     */
    public Set<Date> getDates() {
        Set<Date> dates = new HashSet<Date>();
        Iterator<Time> it = times.iterator();
        while (it.hasNext()) {
            Time time = it.next();
            dates.add(new Date(time.getTime()));
        }
        return dates;
    }
    
    public Set<Time> getTimes() {
        return this.times;
    }
    
    /**
     * @param times The times to set.
     */
    public void setDates(Set<Date> times) {
        this.times.clear();
        Iterator<Date> it = times.iterator();
        while (it.hasNext()) {
            Date date = it.next();
            this.times.add(new Time(date.getTime()));
        }
    }
    public void setTimes(Set<Time> times) {
        this.times = times;
    }
    
    public void addTime(Date time) {
        this.times.add(new Time(time.getTime()));
    }
    public void removeTime(Date time) {
        this.times.remove(new Time(time.getTime()));
    }
    
    public void addTime(Time time) {
        this.times.add(time);
    }
    public void removeTime(Time time) {
        this.times.remove(time);
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
    
    public Boolean getHtml() {
        return html;
    }
    public void setHtml(Boolean html) {
        this.html = html;
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

    public String getTemplate() {
        return template;
    }
    public void setTemplate(String template) {
        this.template = template;
    }

    public Boolean getNoContent() {
		return noContent;
	}

	public void setNoContent(Boolean noContent) {
		this.noContent = noContent;
	}

	public Boolean getRss() {
		return rss;
	}

	public void setRss(Boolean rss) {
		this.rss = rss;
	}

	/**
     * @return Returns the sourceTypes.
     */
    public Set<SourceType> getSourceTypes() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, sourceTypes);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return sourceTypes;
    }
    /**
     * @param sourceTypes The sourceTypes to set.
     */
    public void setSourceTypes(Set<SourceType> sourceTypes) {
        this.sourceTypes = sourceTypes;
    }
    public boolean addSourceType(SourceType sourceType) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, sourceTypes);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.sourceTypes.add(sourceType);
    }
    
    public boolean removeSourceType(SourceType sourceType) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, sourceTypes);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.sourceTypes.remove(sourceType);
    }

    /**
     * @return Returns the publicationTypes.
     */
    public Set<SourceCategory> getSourceCategories() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, sourceCategories);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return sourceCategories;
    }
    /**
     * @param publicationTypes The publication types to set.
     */
    public void setSourceCategories(Set<SourceCategory> sourceCategories) {
        this.sourceCategories = sourceCategories;
    }
    public boolean addSourceCategory(SourceCategory sourceCategory) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, sourceCategories);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.sourceCategories.add(sourceCategory);
    }
    public boolean removeSourceCategory(SourceCategory sourceCategory) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, sourceCategories);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.sourceCategories.remove(sourceCategory);
    }

}
