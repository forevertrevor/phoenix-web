package com.mws.phoenix.web.admin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.brief.Client;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.LoginGroup;
import com.mws.phoenix.web.WebUtilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GroupAction extends Action {

    public static final String GROUP_INFO = "mediagen.group.messages"; 
    public static final String GROUP_ERRORS = "mediagen.group.errors"; 
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        Login user = (Login)session.getAttribute("user");
        GroupForm groupform = (GroupForm)form;

        // Retrieve the group object to be edited
        LoginGroup group = null;
        if (groupform.getGroupID().equals("-1")) {
            // New Group request. Send groupform to be filled in
            if (user.getLevel().equals(Login.INTERNAL)) {
                groupform.setGroupID("");
                return mapping.findForward("success");
            } else {
                ActionErrors errs = new ActionErrors();
                errs.add("", new ActionMessage("error.admin.noaccess", "group"));
                request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
                return mapping.findForward("noaccess");
            }

        } else if (groupform.getGroupID().equals("")) {
            // New Group create. create a new Group to create
            group = new LoginGroup();
        } else if (groupform.getGroupID().equals("0")) {
            // My Group request
            group = user.getGroup();
        } else {
            // Specific Group request
            group = (LoginGroup)DataStore.store().getObject(LoginGroup.class, new Long(groupform.getGroupID()));
        }
        if (group == null) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noobject", "group", groupform.getGroupID()));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("failure");
        }

        // Check that user is authorised
        // Only INTERNAL users or ADMIN users for their own group
        if (!user.getLevel().equals(Login.INTERNAL) 
         && !(user.getLevel().equals(Login.ADMIN) && group.getGroupID().equals(user.getGroup().getGroupID()))) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noaccess", "group"));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
        }
        
        ActionMessages msgs = new ActionMessages();
        if (groupform.getAction().equals("update")) {
            if (group.getGroupID() == null) {
                // This is a request to insert a group
                BeanUtils.copyProperties(group, groupform);
                group.setPressMessage(WebUtilities.HTMLEncode((group.getPressMessage())));
                group.setEvalMessage(WebUtilities.HTMLEncode((group.getEvalMessage())));
                setClient(group, groupform.getClientID());
                group.setGroupID(null);
                DataStore.store().persist(group);
                groupform.setLogins(group.getLogins());
                msgs.add(GROUP_INFO, new ActionMessage("info.group.insert"));
                request.setAttribute(GROUP_INFO, msgs);
            } else {
                // This is a request to update a group
                BeanUtils.copyProperties(group, groupform);
                group.setPressMessage(WebUtilities.HTMLEncode((group.getPressMessage())));
                group.setEvalMessage(WebUtilities.HTMLEncode((group.getEvalMessage())));
                setClient(group, groupform.getClientID());
                DataStore.store().persist(group);
                groupform.setLogins(group.getLogins());
                msgs.add(GROUP_INFO, new ActionMessage("info.group.update"));
                request.setAttribute(GROUP_INFO, msgs);
            }
        } else {
            BeanUtils.copyProperties(groupform, group);
            if (group.getClient()!= null) {
                groupform.setClientID(group.getClient().getClientID().toString());
            }
        }

        return mapping.findForward("success");
    }

    private void setClient(LoginGroup group, String clientID) throws NumberFormatException, DataStoreException {
        if (group.getClient() == null || !clientID.equals(group.getClient().getClientID().toString())) {
            Client client = (Client)DataStore.store().getObject(Client.class, new Long(clientID));
            if (client != null) {
                group.setClient(client);
            }
        }
    }
    
    }
