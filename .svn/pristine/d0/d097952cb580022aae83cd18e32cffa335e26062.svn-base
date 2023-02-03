package com.mws.phoenix.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.mws.db.DataStore;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.LoginArchive;

public class ArchiveAction extends Action {

    public static final String ARCHIVE_INFO = "mediagen.archive.messages"; 
    public static final String ARCHIVE_ERRORS = "mediagen.archive.errors"; 

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        Login user = (Login)session.getAttribute("user");
        ArchiveForm archiveform = (ArchiveForm)form;

        // Check that user is authorised
        // Only INTERNAL users
        if (!user.getLevel().equals(Login.INTERNAL)) { 
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noaccess", "archive"));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
        }

        // Retrieve the archive object to be edited
        // NB non INTERNAL users cannot edit their own Archive nor can
        // new Archives be created (they are automatically created with a new
        // Login)
        LoginArchive archive = null;
        if (!archiveform.getArchiveID().equals("0")) {
            archive = (LoginArchive)DataStore.store().getObject(LoginArchive.class, new Long(archiveform.getArchiveID()));
        }
        if (archive == null) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noobject", "archive", archiveform.getArchiveID()));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("failure");
        }
        
        ActionMessages msgs = new ActionMessages();
        
        if (archiveform.getAction().equals("update")) {
            // This is a request to update an archive
            BeanUtils.copyProperties(archive, archiveform);
            archive.setLastSent(archiveform.sdf.parse(archiveform.getLastSent()));
            archive.setCreated(archiveform.sdf.parse(archiveform.getCreated()));
            DataStore.store().persist(archive);
            archiveform.setLogin(archive.getLogin());
            msgs.add(ARCHIVE_INFO, new ActionMessage("info.archive.update"));
            request.setAttribute(ARCHIVE_INFO, msgs);
            
        } else {
            BeanUtils.copyProperties(archiveform, archive);
            archiveform.setCreated(archive.getCreated());
            archiveform.setLastSent(archive.getLastSent());
        }
        return mapping.findForward("success");
    }
}
