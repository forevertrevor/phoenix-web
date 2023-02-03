package com.mws.phoenix.db.web;

import java.util.Set;

import javax.swing.Icon;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.db.ValueObject;
import com.mws.util.ResourceUtilities;

public class WebFolder extends ValueObject {

	/**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private Long folderID;
	private String folderName;
	private String desc;
	private Login creator;
	private LoginGroup group;
	private Set<WebFolderItem> items;
	private Long folderSize;
	
    // Value Object metadata implementation
    public static final String className = "Folder";
    public static final String[] propertyNames = {"Folder ID", "Folder Name", "Folder Size", "Description", "Group", "Creator", "Items"};
    public static final String[] properties = {"folderID", "folderName", "folderSize", "desc", "group", "creator", "items"};
    public static final Class<?>[] propertyClasses = {Long.class, String.class, Long.class, String.class, LoginGroup.class, Login.class, WebFolderItem.class};
    public static final int[] propertySizes = {0, 50, 0, INFINITY, 0, 0, 0};
	public static final Icon icon = ResourceUtilities.getIcon(WebFolder.class, "web-folder-16x16.gif");
	
	public Long getID() {
		return folderID;
	}

	public Icon getIcon() {
		return icon;
	}
	
	public String getObjectName() {
		return className;
	}

	public String[] getProperties() {
		return properties;
	}

	public Class<?>[] getPropertyClasses() {
		return propertyClasses;
	}

	public String[] getPropertyNames() {
		return propertyNames;
	}

	public int[] getPropertySizes() {
		return propertySizes;
	}

    public String toString() {
        return folderName;
    }
    
    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append(folderID);
        sb.append(" : ");
        sb.append(folderName);
        sb.append(", ");
        sb.append(desc);
        sb.append(", ");
        if (group != null) {
            sb.append(group);
        } else {
        	sb.append("No group");
        }
        sb.append(", ");
        if (creator != null) {
            sb.append(creator);
        } else {
        	sb.append("No creator");
        }
        return sb.toString();
    }

	public Login getCreator() {
		return creator;
	}

	public void setCreator(Login creator) {
		this.creator = creator;
	}
	
	public LoginGroup getGroup() {
		return group;
	}

	public void setGroup(LoginGroup group) {
		this.group = group;
	}

	public String getFolderDesc() {
		return desc;
	}

	public void setFolderDesc(String desc) {
		this.desc = desc;
	}

	public Long getFolderID() {
		return folderID;
	}

	public void setFolderID(Long folderID) {
		this.folderID = folderID;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
    public Long getFolderSize() {
		return folderSize;
	}

	public void setFolderSize(Long itemCount) {
		this.folderSize = itemCount;
	}

	/**
     * @return Returns the items.
     */
    public Set<WebFolderItem> getItems() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, items);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return items;
    }
    
    /**
     * @param items The logins to set.
     */
    public void setItems(Set<WebFolderItem> items) {
        this.items = items;
    }

    public boolean addItem(WebFolderItem item) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, items);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return items.add(item);
    }

    public boolean removeItem(WebFolderItem item) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, items);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return items.remove(item);
    }
	
}
