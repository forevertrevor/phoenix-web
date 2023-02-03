package com.mws.phoenix.db.web;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.swing.Icon;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.db.ValueObject;
import com.mws.phoenix.db.brief.Brief;
import com.mws.phoenix.db.brief.Client;
import com.mws.phoenix.db.source.broadcast.BroadcastCompany;
import com.mws.util.ResourceUtilities;

/**
 * @author Ed Webb
 * @version
 * @since
 */
public class Login extends ValueObject implements HttpSessionBindingListener {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	//User Levels
    public static final Long SUMMARY = new Long(0);
    public static final Long CLIP    = new Long(1);
    public static final Long EDITOR  = new Long(2);
    public static final Long OPERATOR= new Long(3);
    public static final Long ADMIN   = new Long(4);
    public static final Long INTERNAL= new Long(5);
    
    protected Long loginID;
    protected String loginName;
    protected String password;
    protected String userName;
    protected String fax;
    protected String email;
    protected Long level;
    protected Long maxLogins;
    protected Long curLogins;
    protected BitSet controls;
    protected LoginStyle style;
    protected LoginAlert alert;
    protected LoginArchive archive;
    protected LoginFinance finance;
    protected LoginGroup group;
    protected Boolean attachment;
    protected Boolean restrictNI;
    
    // Press Cuttings Brief Access
    protected Set<Brief> briefs = new HashSet<Brief>();
    protected Set<Client> clients = new HashSet<Client>();
    protected Set<Brief> briefAccess = new HashSet<Brief>();
    //protected Set<Topic> topicAccess = new HashSet<Topic>();
    
    //protected Report defaultReport;
    protected Long defaultBrief;
    protected Long defaultSection;
    protected Long nlaUserID;
    protected String nlaPassword;
    protected Long timeout;
    
    // Evaluation Programme Access
    protected Set<BroadcastCompany> broadcastCompanies = new HashSet<BroadcastCompany>();
    //protected Set<ProductionCompany> productionCompanies = new HashSet<ProductionCompany>();
    //protected Set<Genre> genres = new HashSet<Genre>();
    //protected Set<Programme> programmes = new HashSet<Programme>();
    //protected Set<Programme> programmeAccess = new HashSet<Programme>();
    
    // Value Object metadata implementation
    public static final String className = "Logon";
    public static final String[] propertyNames = {"Login ID", "Login Name", "Password", "User Name", "Fax", "Email", "Max Logins", "Controls", "Style", "Briefs", "Clients", "Alert", "Group", "Archive", "Finance", "Restrict NI"};
    public static final String[] properties = {"loginID", "loginName", "password", "userName", "fax", "email", "maxLogins", "controls", "style", "briefs", "Clients", "alert", "group", "archive", "finance", "restrictNI"};
    public static final Class<?>[] propertyClasses = {Long.class, String.class, String.class, String.class, String.class, String.class, Long.class, BitSet.class, LoginStyle.class, Brief.class, Client.class, LoginAlert.class, LoginGroup.class, LoginArchive.class, LoginFinance.class, Boolean.class};
    public static final int[] propertySizes = {0, 50, 50, 100, 20, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final Icon icon = ResourceUtilities.getIcon(Login.class, "login-16x16.gif");
    
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
        return loginID;
    }
    
    public Icon getIcon() {
    	return icon;
    }

    public String toString() {
        return loginName;
    }
    
    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append(loginID);
        sb.append(" : ");
        sb.append(loginName);
        return sb.toString();
    }
    
    /**
     * @return Returns the loginID.
     */
    public Long getLoginID() {
        return loginID;
    }

    /**
     * @param loginID The loginID to set.
     */
    public void setLoginID(Long loginID) {
        this.loginID = loginID;
    }


    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return Returns the style.
     */
    public LoginStyle getStyle() {
        return style;
    }
    /**
     * @param style The style to set.
     */
    public void setStyle(LoginStyle style) {
        this.style = style;
    }
    /**
     * @return Returns the briefs.
     */
    public Set<Brief> getBriefs() {
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
    public void setBriefs(Set<Brief> briefs) {
        this.briefs = briefs;
    }
    
    public boolean addBrief(Brief brief) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, briefs);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.briefs.add(brief);
    }
    public boolean removeBrief(Brief brief) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, briefs);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.briefs.remove(brief);
    }
    /**
     * @return Returns the clients.
     */
    public Set<Client> getClients() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, clients);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return clients;
    }
    /**
     * @param clients The clients to set.
     */
    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
    
    public boolean addClient(Client client) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, clients);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.clients.add(client);
    }
    public boolean removeClient(Client client) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, clients);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.clients.remove(client);
    }
    
    public Set<Brief> getBriefAccess() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, briefAccess);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return briefAccess;
    }
    public void setBriefAccess(Set<Brief> briefaccess) {
        this.briefAccess = briefaccess;
    }

    /**
     * @return Returns the alert.
     */
    public LoginAlert getAlert() {
        return alert;
    }

    /**
     * @param alert The alert to set.
     */
    public void setAlert(LoginAlert alert) {
        this.alert = alert;
    }

    /**
     * @param archive The archive to set.
     */
    public void setArchive(LoginArchive archive) {
        this.archive = archive;
    }

    /**
     * @return Returns the archive.
     */
    public LoginArchive getArchive() {
        return archive;
    }

    /**
     * @param finance The finance to set.
     */
    public void setFinance(LoginFinance finance) {
        this.finance = finance;
    }

    /**
     * @return Returns the finance.
     */
    public LoginFinance getFinance() {
        return finance;
    }

    /**
     * @return Returns the controls.
     */
    public BitSet getControls() {
        return controls;
    }
    /**
     * @param controls The controls to set.
     */
    public void setControls(BitSet controls) {
        this.controls = controls;
    }
    /**
     * @return Returns the curLogins.
     */
    public Long getCurLogins() {
        return curLogins;
    }
    /**
     * @param curLogins The curLogins to set.
     */
    public void setCurLogins(Long curLogins) {
        this.curLogins = curLogins;
    }
    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return Returns the fax.
     */
    public String getFax() {
        return fax;
    }
    /**
     * @param fax The fax to set.
     */
    public void setFax(String fax) {
        this.fax = fax;
    }
    /**
     * @return Returns the level.
     */
    public Long getLevel() {
        return level;
    }
    /**
     * @param level The level to set.
     */
    public void setLevel(Long level) {
        this.level = level;
    }
    /**
     * @return Returns the maxLogins.
     */
    public Long getMaxLogins() {
        return maxLogins;
    }
    /**
     * @param maxLogins The maxLogins to set.
     */
    public void setMaxLogins(Long maxLogins) {
        this.maxLogins = maxLogins;
    }
    /**
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return Returns the group.
     */
    public LoginGroup getGroup() {
        return group;
    }
    /**
     * @param group The group to set.
     */
    public void setGroup(LoginGroup group) {
        this.group = group;
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
            try {
                DataStore store = DataStore.store(); 
                Login user = (Login)event.getValue();
                WebLogEntry.log("Logout Success", loginName);
                if (null != user) {
                    Integer id = store.beginTransaction();
                    Login fresh = (Login)store.getObject(Login.class, user.getID());
                    Long curUsers = fresh.getCurLogins();
                    if (curUsers.longValue() > 0) {
                        curUsers = new Long(curUsers.longValue() - 1);
                    }
                    fresh.setCurLogins(curUsers);
                    store.persist(fresh, false);

                   store.commitTransaction(id);
                }
            } catch (DataStoreException e) {
                e.printStackTrace();
            }
        }
    }

    public void valueBound(HttpSessionBindingEvent event) {
        //nothing to do
    }
    
    /*
    public Set getBroadcastCompanies() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, broadcastCompanies);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return broadcastCompanies;
    }
    public void setBroadcastCompanies(Set broadcastCompanies) {
        this.broadcastCompanies = broadcastCompanies;
    }
    public Set getBroadcastCompanyAccess() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, broadcastCompanies);
            } catch (DataStoreException e) {
                return null;
            }
        }
        if (this.broadcastCompanies.size() == 0) {
            try {
                return new LinkedHashSet(DataStore.store().getAllObjects(BroadcastCompany.class, "broadcastCompanyName"));
            } catch (DataStoreException e) {
                e.printStackTrace();
                return new HashSet();
            }
        } else {
            return (Set)DataStore.store().sortCollection(this.broadcastCompanies, "broadcastCompanyName");
        }
    }
    public Set getChannelAccess() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, broadcastCompanies);
            } catch (DataStoreException e) {
                return null;
            }
        }
        if (this.broadcastCompanies.size() == 0) {
            try {
                return new LinkedHashSet(DataStore.store().getAllObjects(Channel.class, "channelName"));
            } catch (DataStoreException e) {
                e.printStackTrace();
                return new HashSet();
            }
        } else {
            Iterator it = this.getBroadcastCompanyAccess().iterator();
            Set channels = new LinkedHashSet();
            while(it.hasNext()) {
                BroadcastCompany bc = (BroadcastCompany)it.next();
                channels.addAll(bc.getChannels());
            }
            return channels;
        }
    }
    public Set getGenres() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, genres);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return genres;
    }
    public void setGenres(Set genres) {
        this.genres = genres;
    }
    public Set getGenreAccess() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, genres);
            } catch (DataStoreException e) {
                return null;
            }
        }
        if (this.genres.size() == 0) {
            try {
                return new LinkedHashSet(DataStore.store().getAllObjects(Genre.class, "genreName"));
            } catch (DataStoreException e) {
                e.printStackTrace();
                return new HashSet();
            }
        } else {
            return (Set)DataStore.store().sortCollection(this.genres, "genreName");
        }
    }

    public Set getProductionCompanies() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, productionCompanies);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return productionCompanies;
    }
    public void setProductionCompanies(Set productionCompanies) {
        this.productionCompanies = productionCompanies;
    }
    public Set getProductionCompanyAccess() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, productionCompanies);
            } catch (DataStoreException e) {
                return null;
            }
        }
        if (this.productionCompanies.size() == 0) {
            try {
                return new LinkedHashSet(DataStore.store().getAllObjects(ProductionCompany.class, "productionCompanyName"));
            } catch (DataStoreException e) {
                e.printStackTrace();
                return new HashSet();
            }
        } else {
            return (Set)DataStore.store().sortCollection(this.productionCompanies, "productionCompanyName");
        }
    }
    
    public Set getProgrammes() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, programmes);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return programmes;
    }
    public void setProgrammes(Set programmes) {
        this.programmes = programmes;
    }
    public Set getProgrammeAccess() {
        if (this.isLoaded()) {
            // Do not load this collection ever. It is not required except 
            // in HQL queries.
            //try {
            //    DataStore.store().loadCollection(this, programmeAccess);
            //} catch (DataStoreException e) {
            //    return null;
            //}
        }
        return programmeAccess;
    }
    public void setProgrammeAccess(Set programmeAccess) {
        this.programmeAccess = programmeAccess;
    }

    public Set getTopicAccess() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, topicAccess);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return topicAccess;
    }
    public void setTopicAccess(Set topicAccess) {
        this.topicAccess = topicAccess;
    }

    public Report getDefaultReport() {
        return defaultReport;
    }
    public void setDefaultReport(Report defaultReport) {
        this.defaultReport = defaultReport;
    }
*/
    public Long getDefaultBrief() {
        return defaultBrief;
    }
    public void setDefaultBrief(Long defaultBrief) {
        this.defaultBrief = defaultBrief;
    }

    public Long getDefaultSection() {
        return defaultSection;
    }
    public void setDefaultSection(Long defaultSection) {
        this.defaultSection = defaultSection;
    }

    public Long getNlaUserID() {
        return nlaUserID;
    }

    public void setNlaUserID(Long nlaUserID) {
        this.nlaUserID = nlaUserID;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getNlaPassword() {
        return nlaPassword;
    }

    public void setNlaPassword(String nlaPassword) {
        this.nlaPassword = nlaPassword;
    }

	public Boolean getAttachment() {
		return attachment;
	}

	public void setAttachment(Boolean attachment) {
		this.attachment = attachment;
	}

	public Boolean getRestrictNI() {
		return restrictNI;
	}

	public void setRestrictNI(Boolean restrictNI) {
		this.restrictNI = restrictNI;
	}
}
