package com.mws.phoenix.web.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.brief.Brief;
import com.mws.phoenix.db.brief.Client;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.LoginAlert;
import com.mws.phoenix.db.web.LoginArchive;
import com.mws.phoenix.db.web.LoginFinance;
import com.mws.phoenix.db.web.LoginGroup;
import com.mws.phoenix.db.web.LoginStyle;

public class UserAction extends Action {

    public static final String USER_INFO = "mediagen.user.messages";
    public static final String USER_ERRORS = "mediagen.user.errors";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(true);
        UserForm userform = (UserForm)form;
        ActionMessages msgs = new ActionMessages(); 
        Login user = (Login)session.getAttribute("user");

        if (user.getLevel().equals(Login.INTERNAL)) {
            userform.setPage(2);
        } else {
            userform.setPage(1);
        }

        // Get the login to edit
        Login login = null;
        if (userform.getLoginID().equals("-1")) {
            // New User request. Send userform to be filled in
            if (user.getLevel().equals(Login.INTERNAL)) {
                userform.setLoginID("");
                return mapping.findForward("success");
            } else {
                ActionErrors errs = new ActionErrors();
                errs.add("", new ActionMessage("error.admin.noaccess", "user"));
                request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
                return mapping.findForward("noaccess");
            }
        } else if (userform.getLoginID().equals("")) {
            // New Login create. create a new Login to create
            login = new Login();
        } else if (userform.getLoginID().equals("0")) {
            // My Login request
            login = user;
        } else {
            login = (Login)DataStore.store().getObject(Login.class, new Long(userform.getLoginID()));
        }

        if (login == null) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noobject", "user", userform.getLoginID()));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("failure");
        }
        
        //Check that the current user is allowed to edit this user
        if (accessDenied(user, login, mapping.getPath())) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.admin.noaccess", "user"));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
        }
        
        if (userform.getAction().equals("")) {
            // Load the requested user into the UserForm
            if (login != null) {
                copyToForm(userform, login);
            }
        } else if (userform.getAction().equals("update")) {
            // Update the user details
            if (login.getLoginID() == null) {
                login = insertLogin(login, userform);
                msgs.add(USER_INFO, new ActionMessage("info.profile.insert.success"));
                request.setAttribute( USER_INFO, msgs);
            } else {
                login = updateLogin(login, userform);
                msgs.add(USER_INFO, new ActionMessage("info.profile.update.success"));
                request.setAttribute( USER_INFO, msgs);
            }

            // If updating the currently logged in user update the object stored in session too.
            if (user.getLoginID().equals(login.getLoginID())) {
                session.setAttribute("user", login);
            }

            // Save the login to the userForm in request scope
            copyToForm(userform, login);
            
        } else if (userform.getAction().equals("briefs")) {
            // Update the user brief access
            login = updateBriefs(userform);

            // If updating the currently logged in user update the object stored in session too.
            if (user.getLoginID().equals(login.getLoginID())) {
                session.setAttribute("user", login);
            }
            
            msgs.add(USER_INFO, new ActionMessage("info.profile.briefs.success"));
            request.setAttribute(USER_INFO, msgs);

            // Save the login to the userForm in request scope
            copyToForm(userform, login);
        /*
        } else if (userform.getAction().equals("programmes")) {
            // Update the user brief access
            login = updateProgrammes(userform);

            // If updating the currently logged in user update the object stored in session too.
            if (user.getLoginID().equals(login.getLoginID())) {
                session.setAttribute("user", login);
            }
            
            msgs.add(USER_INFO, new ActionMessage("info.profile.programmes.success"));
            request.setAttribute(USER_INFO, msgs);

            // Save the login to the userForm in request scope
            copyToForm(userform, login);*/
        }
        
        return mapping.findForward("success");
    }

    /**
     * Checks that the logged in user is allowed to edit the selected user.
     * 1. INTERNAL users can edit any other user
     * 2. ADMIN users can edit users in their group except ADMIN and INTERNAL
     * 3. All users can edit themselves
     * 
     * @param user currently logged in user
     * @param login the user to be edited
     * @return true if any of the conditions above is true
     */
    private boolean accessDenied(Login user, Login login, String path) {
        Long ulevel = user.getLevel();
        Long llevel = login.getLevel();
        LoginGroup ugroup = user.getGroup();
        LoginGroup lgroup = login.getGroup();
        if (ulevel.compareTo(Login.INTERNAL) < 0 
           && !path.equals("/admin-user")) {
                return true;
        } else if (ulevel.compareTo(Login.INTERNAL) >= 0 
           || (ulevel.compareTo(Login.ADMIN) >= 0 && ugroup.equals(lgroup) && llevel.compareTo(Login.ADMIN) < 0)
           || (user.equals(login))) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * @param userform
     * @return
     * @throws DataStoreException
     */
    private Login updateBriefs(UserForm userform) throws DataStoreException {
        Login login = (Login)DataStore.store().getObject(Login.class, new Long(userform.getLoginID()));

        Set<Client> clients = login.getClients();
        Set<Brief> briefs = login.getBriefs();
        clients.clear();
        briefs.clear();
        //DataStore.store().persist(login);
        String[] clientIDs = userform.getClient();
        String[] briefIDs = userform.getBrief();
        
        for (int i = 0; i < clientIDs.length; i++) {
            Client client = (Client)DataStore.store().getObject(Client.class, new Long(clientIDs[i]));
            if (client != null) {
                clients.add(client);
            }
        }
        for (int i = 0; i < briefIDs.length; i++) {
            Brief brief = (Brief)DataStore.store().getObject(Brief.class, new Long(briefIDs[i]));
            if (brief != null) {
                briefs.add(brief);
            }
        }
        
        DataStore.store().persist(login);
        return login;
    }

    /**
     * @param userform
     * @return
     * @throws DataStoreException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Login updateLogin(Login login, UserForm userform) throws DataStoreException, IllegalAccessException, InvocationTargetException {

        // To avoid writing "" into the password field!
        if (userform.getPassword().equals("")) {
            userform.setPassword(login.getPassword());
        }
        if (userform.getPage() == 1) {
            login.setUserName(userform.getUserName());
            login.setEmail(userform.getEmail());
            login.setFax(userform.getFax());
            login.setPassword(userform.getPassword());
            BeanUtils.setProperty(login, "level", userform.getLevel());
        }
        else if (userform.getPage() == 2) {
            BeanUtils.copyProperties(login, userform);
            LoginGroup group = (LoginGroup)DataStore.store().getObject(LoginGroup.class, new Long(userform.getGroupID()));
            if (group != null) {
                login.setGroup(group);
            }
            LoginStyle style = (LoginStyle)DataStore.store().getObject(LoginStyle.class, new Long(userform.getStyleID()));
            if (style != null) {
                login.setStyle(style);
            }
        }
        
        DataStore.store().persist(login);
        return login;
    }

    private Login insertLogin(Login login, UserForm userform) throws DataStoreException, IllegalAccessException, InvocationTargetException {
        
        BeanUtils.copyProperties(login, userform);
        
        login.setLoginID(null);
        
        LoginGroup group = (LoginGroup)DataStore.store().getObject(LoginGroup.class, new Long(userform.getGroupID()));
        if (group != null) {
            login.setGroup(group);
        }
        LoginStyle style = (LoginStyle)DataStore.store().getObject(LoginStyle.class, new Long(userform.getStyleID()));
        if (style != null) {
            login.setStyle(style);
        }
        
        DataStore.store().persist(login);
        LoginAlert alert = new LoginAlert();
        alert.setLogin(login);
        login.setAlert(alert);
        LoginArchive archive = new LoginArchive();
        archive.setLogin(login);
        login.setArchive(archive);
        LoginFinance finance = new LoginFinance();
        finance.setLogin(login);
        login.setFinance(finance);

        DataStore.store().persist(archive);
        DataStore.store().persist(alert);
        DataStore.store().persist(finance);
        
        return login;
    }
    
    private void copyToForm(UserForm form, Login user) {
        try {
            BeanUtils.copyProperties(form, user);
            
            // empty the password property 
            form.setPassword("");
            form.setGroupID(user.getGroup().getGroupID().toString());
            form.setStyleID(user.getStyle().getStyleID().toString());
            form.setBriefList(new ArrayList<Brief>(user.getBriefAccess()));
            
            List<String> list;
            Iterator<Brief> it;
            
            list = new ArrayList<String>();
            it = user.getBriefs().iterator();
            while (it.hasNext()) {
                Brief b = it.next();
                list.add(b.getBriefID().toString());
            }
            form.setBrief(list.toArray(new String[list.size()]));

            list = new ArrayList<String>();
            Iterator<Client> xit = user.getClients().iterator();
            while (xit.hasNext()) {
                Client c = xit.next();
                list.add(c.getClientID().toString());
            }
            form.setClient(list.toArray(new String[list.size()]));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param userform
     * @return
     * @throws DataStoreException
     */
    /*
    private Login updateProgrammes(UserForm userform) throws DataStoreException {
        Login login = (Login)DataStore.store().getObject(Login.class, new Long(userform.getLoginID()));

        Set broadcasters = login.getBroadcastCompanies();
        Set producers = login.getProductionCompanies();
        Set genres = login.getGenres();
        Set programmes = login.getProgrammes();
        
        broadcasters.clear();
        producers.clear();
        genres.clear();
        programmes.clear();
        
        //DataStore.store().persist(login);
        String[] broadcasterIDs = userform.getBroadcastCompany();
        String[] producerIDs = userform.getProductionCompany();
        String[] genreIDs = userform.getGenre();
        String[] programmeIDs = userform.getProgrammeChecked();
        
        for (int i = 0; i < broadcasterIDs.length; i++) {
            BroadcastCompany broadcaster = (BroadcastCompany)DataStore.store().getObject(BroadcastCompany.class, new Long(broadcasterIDs[i]));
            if (broadcaster != null) {
                broadcasters.add(broadcaster);
            }
        }
        for (int i = 0; i < producerIDs.length; i++) {
            ProductionCompany producer = (ProductionCompany)DataStore.store().getObject(ProductionCompany.class, new Long(producerIDs[i]));
            if (producer != null) {
                producers.add(producer);
            }
        }
        for (int i = 0; i < genreIDs.length; i++) {
            Genre genre = (Genre)DataStore.store().getObject(Genre.class, new Long(genreIDs[i]));
            if (genre != null) {
                genres.add(genre);
            }
        }
        for (int i = 0; i < programmeIDs.length; i++) {
            Programme programme = (Programme)DataStore.store().getObject(Programme.class, new Long(programmeIDs[i]));
            if (programme != null) {
                programmes.add(programme);
            }
        }
        
        DataStore.store().persist(login);
        return login;
    }
    */
}
