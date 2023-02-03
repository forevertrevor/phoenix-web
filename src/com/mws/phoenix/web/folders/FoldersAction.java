package com.mws.phoenix.web.folders;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.despatch.Hit;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.WebFolder;
import com.mws.phoenix.db.web.WebFolderItem;
import com.mws.phoenix.web.WebUtilities;
import com.mws.phoenix.web.press.ItemAction;
import com.mws.phoenix.web.press.ItemForm;
import com.mws.phoenix.web.press.NewsForm;
import com.mws.phoenix.web.press.SelectedForm;
import com.mws.phoenix.web.press.ToolAction;

public class FoldersAction extends Action {

    public static final String FOLDERS_INFO = "mediagen.folders.messages"; 
    public static final String FOLDERS_ERRORS = "mediagen.folders.errors"; 
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FoldersForm folders = (FoldersForm)form;
        HttpSession session = request.getSession();
        Login user = (Login)session.getAttribute("user");
        String command = folders.getCommand(); 

		// Check the user has access to folders
        if (!user.getControls().get(WebUtilities.CONTROL_FOLDERS)) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.folders.noaccess"));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
        }
        
        ActionMessages msgs = new ActionMessages();
        ActionErrors errs = new ActionErrors();
        
        request.setAttribute(FOLDERS_INFO, msgs);
        request.setAttribute(FOLDERS_ERRORS, errs);
        
        if (command.equals(FoldersForm.VIEW_FOLDERS)) {
            viewFolders(folders, user.getGroup().getGroupID());
        } else if (command.equals(FoldersForm.DELETE_FOLDER)) {
            if (deleteFolder(new Long(folders.getFolderid()), user)) {
            	msgs.add(FOLDERS_INFO, new ActionMessage("info.folder.delete.success"));
        		DataStore.store().refresh(user.getGroup());
            } else {
            	errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.delete.fail"));
            }
            viewFolders(folders, user.getGroup().getGroupID());
        } else if (command.equals(FoldersForm.LIST_FOLDER)) {
        	WebFolder folder = getFolder(new Long(folders.getFolderid()), user.getGroup().getGroupID());
        	if (folder == null) {
        		errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.exist"));
                viewFolders(folders, user.getGroup().getGroupID());
        		return mapping.findForward(FoldersForm.VIEW_FOLDERS);
        	} else {
        		request.setAttribute("folder", folder);
        		if (request.getAttribute("items") == null) {
        			// Set the acct property of the folderForm to match the selected folder
        			// And set the date back to show all articles
        			NewsForm frm = (NewsForm)session.getAttribute("folderForm");
        			frm.setAcct(folder.getFolderID().toString());
        			frm.setDatefrom("01-Jan-2000");
        			request.setAttribute("items", folder.getItems());
        		}
        	}
        } else if (command.equals(FoldersForm.ADD_FOLDER)) {
        	WebFolder folder = getFolder(new Long(folders.getFolderid()), user.getGroup().getGroupID());
        	SelectedForm selected = (SelectedForm)session.getAttribute("selectedForm");
        	if (folder != null && selected != null && folder.getItems() != null) {
        		int before = folder.getItems().size();
        		ActionErrors addErrs = addItems(folder, selected.getHits(request), user);
        		errs.add(addErrs);
        		int after = folder.getItems().size();
        		int change = after - before;
        		if (change == 0) {
        			errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.noadd", folder.getFolderName()));
        		} else if (change == 1) {
        			msgs.add(FOLDERS_INFO, new ActionMessage("info.folder.add.one", new Integer(after - before), folder.getFolderName()));
        		} else {
        			msgs.add(FOLDERS_INFO, new ActionMessage("info.folder.add.many", new Integer(after - before), folder.getFolderName()));
        		}
        	} else {
        		if (folder == null) {
        			errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.none"));
        		}
        	}
            request.setAttribute(ToolAction.TOOL_INFO, msgs);
            request.setAttribute(ToolAction.TOOL_ERRORS, errs);
        } else if (command.equals(FoldersForm.REMOVE_FOLDER)) {
        	WebFolder folder = getFolder(new Long(folders.getFolderid()), user.getGroup().getGroupID());
        	SelectedForm selected = (SelectedForm)session.getAttribute("selectedForm");
        	if (folder != null && selected != null) {
        		int before = folder.getItems().size();
        		ActionErrors removeErrs = removeItems(folder, selected.getHits(request), user);
        		errs.add(removeErrs);
        		int after = folder.getItems().size();
        		int change = before - after;
        		if (change == 0) {
        			errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.noremove", folder.getFolderName()));
        		} else if (change == 1) {
        			msgs.add(FOLDERS_INFO, new ActionMessage("info.folder.remove.one", new Integer(change), folder.getFolderName()));
        		} else {
        			msgs.add(FOLDERS_INFO, new ActionMessage("info.folder.remove.many", new Integer(change), folder.getFolderName()));
        		}
        	}
            request.setAttribute(ToolAction.TOOL_INFO, msgs);
            request.setAttribute(ToolAction.TOOL_ERRORS, errs);
        } else if (command.equals(FoldersForm.EDIT_FOLDER)) {
        	WebFolder folder = null;
        	if (!folders.getFolderid().equals("-1")) {
        		folder = getFolder(new Long(folders.getFolderid()), user.getGroup().getGroupID(), user);
            	if (folder == null) {
            		errs.add("", new ActionMessage("error.folder.noedit"));
            		return mapping.getInputForward();
            	} else {
            		folders.setFolderdesc(folder.getFolderDesc());
            		folders.setFolderid(folder.getFolderID().toString());
            		folders.setFoldername(folder.getFolderName());
            	}
        	}
        } else if (command.equals(FoldersForm.SAVE_FOLDER)) {
        	WebFolder folder = null;
        	if (folders.getFolderid().equals("-1")) {
        		folder = new WebFolder();
        		folder.setCreator(user);
        		folder.setGroup(user.getGroup());        		
        	} else {
        		folder = getFolder(new Long(folders.getFolderid()), user.getGroup().getGroupID(), user);
        	}
        	if (folder == null) {
        		errs.add("", new ActionMessage("error.folder.noedit"));
        		return mapping.getInputForward();
        	} else {
        		folder.setFolderDesc(folders.getFolderdesc());
        		folder.setFolderName(folders.getFoldername());
        		DataStore.store().persist(folder);
        		DataStore.store().refresh(user.getGroup());
        	}
        }
        
        return mapping.findForward(command);
	}

	/**
	 * Populates the FoldersForm folders property with the folders
	 * that belong to the user's group.
	 * 
	 * @param folders the FoldersForm object to store the list of report
	 * @param groupID the user's group's id
	 * @throws DataStoreException
	 */
	private void viewFolders(FoldersForm folders, Long groupID) throws DataStoreException {
        folders.setFolders(DataStore.store().getObjects(WebFolder.class, "group.groupID", groupID, "folderName"));
    }
	
	/**
	 * Deletes the specified folder. This method only allows the creator
	 * of the folder or a group administrator to delete a folder.
	 * 
	 * @param folderID the id of the folder to delete
	 * @param user the user requesting the deletion
	 * @return true if successful
	 * @throws DataStoreException
	 */
	private boolean deleteFolder(Long folderID, Login user) throws DataStoreException {
    	WebFolder folder = getFolder(folderID, user.getGroup().getGroupID(), user);
        if (null != folder) {
            DataStore.store().delete(folder);
            return true;
        } else {
        	return false;
        }
    }

	private WebFolder getFolder(Long folderID, Long groupID) throws DataStoreException {
    	return (WebFolder)DataStore.store().getObject(WebFolder.class, new String[] {"folderID", "group.groupID"}, new Object[] {folderID, groupID});
	}

	private WebFolder getFolder(Long folderID, Long groupID, Login user) throws DataStoreException {
    	String[] props;
    	Object[] values;
    	if (user.getLevel().compareTo(Login.ADMIN) >= 0) {
    		props = new String[] {"folderID", "group.groupID"};
    		values = new Object[] {folderID, user.getGroup().getGroupID()};
    	} else {
    		props = new String[] {"folderID", "creator.loginID"};
    		values = new Object[] {folderID, user.getLoginID()};
    	}
    	return (WebFolder)DataStore.store().getObject(WebFolder.class, props, values);
		
	}

	private ActionErrors removeItems(WebFolder folder, List<Hit> hits, Login user) throws DataStoreException {
		ActionErrors errs = new ActionErrors();
		
		if (hits == null) {
			errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.none.selected"));
			return errs;
		}
		Long[] list = new Long[hits.size()];
		for (int i = 0; i < hits.size(); i++) {
			list[i] = hits.get(i).getHitID();
		}
		
		List<WebFolderItem> wfis = DataStore.store().getObjects(WebFolderItem.class, new String[] {"folder.folderID", "hit.hitID"}, new String[] {"=", "in"}, new Object[] {folder.getFolderID(), list}); 
		int diff = hits.size() - wfis.size();
		if (diff > 0) {
			errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.selected.missing", new Integer(diff), folder.getFolderName()));
		}
		Iterator<WebFolderItem> it = wfis.iterator();
		while (it.hasNext()) {
			WebFolderItem wfi = it.next();
			ItemForm item = new ItemForm();
			item.setId(wfi.getItemID().toString());
			try {
				ActionMessage msg = ItemAction.deleteItem(item, user);
				if (msg.getKey().startsWith("error")) {
					errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.delete", wfi.getHit().getArticle().toString(), folder.getFolderName(), "insufficient rights"));
				}
			} catch (Exception e) {
				errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.delete", wfi.getHit().getArticle().toString(), folder.getFolderName(), "an internal error"));
			}
		}
		
		try {
			DataStore.store().refresh(folder);
		} catch (DataStoreException e) {
			errs.add("", new ActionMessage("error.folder.refresh"));
		}
		
		return errs;
	}
	
	private ActionErrors addItems(WebFolder folder, List<Hit> hits, Login user) throws DataStoreException {
		ActionErrors errs = new ActionErrors();
		
		if (hits == null) {
			errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.none.selected"));
			return errs;
		}
		Iterator<Hit> it = hits.iterator();
		while (it.hasNext()) {
			Hit hit = it.next();

			/**
			// This check may take too long for large folders
			// The new item will be created and an attempt to insert
			// it made. If an exception is generated then presume that
			// the hit is already present.
			Iterator items = folder.getItems().iterator();
			boolean duplicate = false;
			while (items.hasNext()) {
				WebFolderItem item = (WebFolderItem)items.next();
				if (item.getHit().equals(hit)) {
					errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.duplicate", hit.getArticle().toString(), folder.getFolderName()));
					duplicate = true;
					break;
				}
			}*/
			
			WebFolderItem item = new WebFolderItem(folder, hit, user);
			folder.addItem(item);
	
			try {
				DataStore.store().persist(item);
			} catch (DataStoreException e) {
				//FIXME This exception may not be caused by a unique key constraint
				//      but there's no easy clean way to get to the actual error message.
				errs.add(FOLDERS_ERRORS, new ActionMessage("error.folder.duplicate", hit.getArticle().toString(), folder.getFolderName()));
			}
		}
		
		try {
			DataStore.store().refresh(folder);
		} catch (DataStoreException e) {
			errs.add("", new ActionMessage("error.folder.refresh"));
		}
		
		return errs;
	}
}
