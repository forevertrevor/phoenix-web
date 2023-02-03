package com.mws.phoenix.db.web;

import javax.swing.Icon;

import com.mws.db.ValueObject;
import com.mws.phoenix.db.despatch.Hit;
import com.mws.util.ResourceUtilities;

public class WebFolderItem extends ValueObject {

	/**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private Long itemID;
	private WebFolder folder;
	private Hit hit;
	private Login creator;
	
    // Value Object metadata implementation
    public static final String className = "Folder Item";
    public static final String[] propertyNames = {"Item ID", "Folder", "Hit", "Creator"};
    public static final String[] properties = {"itemID", "folder", "hit", "creator"};
    public static final Class<?>[] propertyClasses = {Long.class, String.class, Hit.class, Login.class};
    public static final int[] propertySizes = {0, 0, 0, 0};
	public static final Icon icon = ResourceUtilities.getIcon(WebFolderItem.class, "web-folder-item-16x16.gif");
    public WebFolderItem() {
    }
    
    public WebFolderItem(WebFolder folder, Hit hit, Login creator) {
    	this.folder = folder;
    	this.hit = hit;
    	this.creator = creator;
    }
    
	public Long getID() {
		return itemID;
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
        if (hit != null) {
        	return hit.getArticle().toString();
        } else {
        	return "No Article";
        }
    }
    
    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append(itemID);
        sb.append(" : ");
        if (folder != null) {
            sb.append(folder);
        } else {
        	sb.append("No folder");
        }
        sb.append(", ");
        if (hit != null) {
            sb.append(hit.getArticle());
        } else {
        	sb.append("No article");
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

	public Hit getHit() {
		return hit;
	}

	public void setHit(Hit hit) {
		this.hit = hit;
	}

	public WebFolder getFolder() {
		return folder;
	}

	public void setFolder(WebFolder folder) {
		this.folder = folder;
	}

	public Long getItemID() {
		return itemID;
	}

	public void setItemID(Long itemID) {
		this.itemID = itemID;
	}
}
