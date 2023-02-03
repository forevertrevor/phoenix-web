package com.mws.phoenix.db.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.db.ValueObject;
import com.mws.db.ValueObjectComparator;
import com.mws.phoenix.db.brief.Client;
import com.mws.util.ResourceUtilities;

/**
 * @author Ed Webb
 * @version
 * @since
 */
public class LoginGroup extends ValueObject {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long groupID;
    protected String groupName;
    protected String pressMessage;
    protected String evalMessage;
    protected Client client;
    protected Set<Login> logins;
    protected Set<WebFolder> folders;
    
    // Value Object metadata implementation
    public static final String className = "LoginGroup";
    public static final String[] propertyNames = {"Group ID", "Group Name", "Press Message", "Eval Message", "Client", "Logins", "Folders"};
    public static final String[] properties = {"groupID", "groupName", "pressMessage", "evalMessage", "client", "logins", "folders"};
    public static final Class<?>[] propertyClasses = {Long.class, String.class, String.class, String.class, Client.class, Login.class, WebFolder.class};
    public static final int[] propertySizes = {0, 50, 500, 500, 0, 0, 0};
    public static final Icon icon = ResourceUtilities.getIcon(LoginGroup.class, "login-group-16x16.gif");
    
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
        return groupID;
    }

    public Icon getIcon() {
    	return icon;
    }
    
    public String toString() {
        return groupName;
    }
    
    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append(groupID);
        sb.append(" : ");
        sb.append(groupName);
        sb.append(", ");
        sb.append(pressMessage);
        sb.append(", ");
        sb.append(evalMessage);
        return sb.toString();
    }
    
    /**
     * @return Returns the loginGroupID.
     */
    public Long getGroupID() {
        return groupID;
    }

    /**
     * @param groupID The groupID to set.
     */
    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }
    /**
     * @return Returns the groupName.
     */
    public String getGroupName() {
        return groupName;
    }
    /**
     * @param groupName The groupName to set.
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    /**
     * @return Returns the message.
     */
    public String getPressMessage() {
        return pressMessage;
    }
    /**
     * @param message The message to set.
     */
    public void setPressMessage(String message) {
        this.pressMessage = message;
    }
    
    /**
     * @return Returns the message.
     */
    public String getEvalMessage() {
        return evalMessage;
    }
    /**
     * @param message The message to set.
     */
    public void setEvalMessage(String message) {
        this.evalMessage = message;
    }

    /**
     * @return Returns the client.
     */
    public Client getClient() {
        return client;
    }
    /**
     * @param client The client to set.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @return Returns the logins.
     */
    public Set<Login> getLogins() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, logins);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return logins;
    }
    /**
     * @param logins The logins to set.
     */
    public void setLogins(Set<Login> logins) {
        this.logins = logins;
    }

    public boolean addLogin(Login login) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, logins);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return logins.add(login);
    }

    public boolean removeLogin(Login login) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, logins);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return logins.remove(login);
    }

    /**
     * @return Returns the folders.
     */
    public Set<WebFolder> getFolders() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, folders);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return folders;
    }
    public List<WebFolder> getSortedFolders() {
    	List<WebFolder> list = new ArrayList<WebFolder>();
    	list.addAll(getFolders());
    	try {
    		ValueObjectComparator<WebFolder> comp = new ValueObjectComparator<WebFolder>(WebFolder.class, "folderName");
        	Collections.sort(list, comp);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return list;
    }
    
    /**
     * @param folders The folders to set.
     */
    public void setFolders(Set<WebFolder> folders) {
        this.folders = folders;
    }

    public boolean addFolder(WebFolder folder) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, folders);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return folders.add(folder);
    }

    public boolean removeFolder(WebFolder folder) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, folders);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return folders.remove(folder);
    }
}
