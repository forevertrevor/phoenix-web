package com.mws.phoenix.web.admin;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.mws.phoenix.db.web.Login;


/**
 * Holds information about a Login Group
 * 
 * @author Ed Webb
 */
public class GroupForm extends ValidatorForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String groupID = "0";
    private String groupName = "";
    private String pressMessage = "";
    private String evalMessage = "";
    private String clientID = "";
    private String action = "";
    private Set<Login> logins;
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        action = "";
        groupID = "0";
        groupName = "";
        pressMessage = "";
        evalMessage = "";
        clientID = "0";
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (action.equals("")) {
            // Form has not yet been sent to client
            return null;
        } else {
            // Form has been returned from client
            ActionErrors errs = super.validate(mapping, request);
            request.setAttribute(GroupAction.GROUP_ERRORS, errs);
            return errs;
        }
    }

    /**
     * @return
     */
    public String getGroupID() {
        return groupID;
    }
    /**
     * @param i
     */
    public void setGroupID(String string) {
        groupID = string;
    }

    /**
     * @param string
     */
    public void setPressMessage(String string) {
        pressMessage = string;
    }
    /**
     * @return
     */
    public String getPressMessage() {
        return pressMessage;
    }

    /**
     * @param string
     */
    public void setEvalMessage(String string) {
        evalMessage = string;
    }
    /**
     * @return
     */
    public String getEvalMessage() {
        return evalMessage;
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

    public String getClientID() {
        return clientID;
    }
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * This is called getUsers and not getLogins so that the
     * BeanUtils.copyProperties() does not overwrite the logins
     * property of the Group object with null.
     * @return
     */
    public Set<Login> getUsers() {
        return logins;
    }

    public void setLogins(Set<Login> logins) {
        this.logins = logins;
    }
}
