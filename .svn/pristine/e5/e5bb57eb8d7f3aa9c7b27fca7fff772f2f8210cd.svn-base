package com.mws.phoenix.db.web;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.db.ValueObject;
import com.mws.phoenix.db.shares.Share;

import com.mws.util.ResourceUtilities;

/**
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class LoginFinance extends ValueObject {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long financeID;
    protected Login login;
    protected Boolean enabled = Boolean.FALSE;
    protected Boolean shareEnabled = Boolean.FALSE;
    
    protected List<Share> shares = new ArrayList<Share>();

    // Value Object metadata implementation
    public static final String className = "Login Finance";
    public static final String[] propertyNames = {"Finance ID", "Login", "Enabled", "Stocks", "Shares"};
    public static final String[] properties = {"financeID", "login", "enabled", "shareEnabled", "shares"};
    public static final Class<?>[] propertyClasses = {Long.class, Login.class, Boolean.class, Boolean.class, Share.class};
    public static final int[] propertySizes = {0, PARENT_PROPERTY, 0, 0, 0};
    public static final Icon icon = ResourceUtilities.getIcon(LoginFinance.class, "login-finance-16x16.gif");
    
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
        return financeID;
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
        sb.append(financeID);
        sb.append(" : ");
        //[Add all properties to buffer]
        sb.append(", ");
        return sb.toString();
    }
    
    /**
     * @return Returns the financeID.
     */
    public Long getFinanceID() {
        return financeID;
    }

    /**
     * @param financeID The financeID to set.
     */
    public void setFinanceID(Long financeID) {
        this.financeID = financeID;
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

    /**
     * @return Returns the shares.
     */
    public Boolean getShareEnabled() {
        return shareEnabled;
    }

    /**
     * @param enabled The enabled to set.
     */
    public void setShareEnabled(Boolean enabled) {
        this.shareEnabled = enabled;
    }
    
    /**
     * @return Returns the shares.
     */
    public List<Share> getShares() {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, shares);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return shares;
    }
    
    /**
     * @param shares The shares to set.
     */
    public void setShares(List<Share> shares) {
        this.shares = shares;
    }
    public boolean addShare(Share share) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, shares);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.shares.add(share);
    }
    
    public boolean removeShare(Share share) {
        if (this.isLoaded()) {
            try {
                DataStore.store().loadCollection(this, shares);
            } catch (DataStoreException e) {
                return false;
            }
        }
        return this.shares.remove(share);
    }
}
