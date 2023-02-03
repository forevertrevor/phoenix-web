package com.mws.phoenix.web.folders;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.mws.phoenix.db.web.WebFolder;

/**
 * @author Ed Webb
 */
public class FoldersForm extends ActionForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String command = "";
    private String folderid = "0";
    private String foldername = "New Folder";
    private String folderdesc = "";
    
    public static final String VIEW_FOLDERS = "view";
    public static final String DELETE_FOLDER = "delete";
    public static final String LIST_FOLDER = "list";
    public static final String ADD_FOLDER = "add";
    public static final String REMOVE_FOLDER = "remove";
    public static final String EDIT_FOLDER = "edit";
    public static final String SAVE_FOLDER = "save";

    private List<WebFolder> folders = new ArrayList<WebFolder>();
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        command = VIEW_FOLDERS;
        folderid = "0";
        foldername = "New Folder";
        folderdesc = "";
        folders.clear();
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (request.getParameter("acct") != null && folderid.equals("0")) {
        	folderid = request.getParameter("acct");
        	command = LIST_FOLDER;
        }
        return super.validate(mapping, request);
    }

    public List<WebFolder> getFolders() {
        return folders;
    }
    
    public void setFolders(List<WebFolder> folders) {
        this.folders = folders;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getFolderid() {
        return folderid;
    }
    public void setFolderid(String id) {
        this.folderid = id;
    }

	public String getFoldername() {
		return foldername;
	}

	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}

	public String getFolderdesc() {
		return folderdesc;
	}

	public void setFolderdesc(String folderdesc) {
		this.folderdesc = folderdesc;
	}
}
