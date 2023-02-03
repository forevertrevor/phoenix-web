package com.mws.phoenix.web.press;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.article.Article;
import com.mws.phoenix.db.despatch.Hit;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.web.WebUtilities;

/**
 * @author Ed Webb
 * @version 
 * @since
 */
public class SelectedForm extends ActionForm {
    
    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * All the articles currently selected
     */
    Set<String> selected = new TreeSet<String>();
    
    /**
     * All the articles present on the submitted form
     */
    List<String> present = new ArrayList<String>();

    /**
     * All the articles checked on the submitted form
     */
    List<String> checked = new ArrayList<String>();
    
    /**
     * Flag for clearing the selected list
     */
    boolean clear = false;
    
    /* (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        checked.clear();
        present.clear();
        clear = false;
    }

    /* (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        WebUtilities.debugParameters(request);
        
        
        int before = selected.size();
        
        if (clear) {
        	selected.clear();
        }

        Map<String, Result> result = sortParameters(present, checked);

        Iterator<Result> it = result.values().iterator();
        while (it.hasNext()) {
            Result res = it.next();
            if(selected.contains(res.getId())) {
                if (res.getChecked() < res.getPresent()) {
                    selected.remove(res.getId());
                }
            } else {
                if (res.getChecked() > 0) {
                    selected.add(res.getId());
                }
            }
        }

        int after = selected.size();
        
        ActionMessage msg = null;
        if (after > before) {
            if (after - before == 1) {
                msg = new ActionMessage("info.select.one", new Integer(after - before));
            } else {
                msg = new ActionMessage("info.select.more", new Integer(after - before));
            }
        } else if (before > after) {
            if (before - after == 1) {
                msg = new ActionMessage("info.deselect.one", new Integer(before - after));
            } else {
            	msg = new ActionMessage("info.deselect.more", new Integer(before - after));
            }
        } else {
            if (after == 1) {
                msg = new ActionMessage("info.same.one");
            } else {
                msg = new ActionMessage("info.same.more");
            }
        }
        ActionMessages msgs = new ActionMessages();
        msgs.add(ToolAction.TOOL_INFO, msg);
        request.setAttribute(ToolAction.TOOL_INFO, msgs);
        
        return null;
    }
    
    private Map<String, Result> sortParameters(List<String> present, List<String> checked) {
        Map<String, Result> map = new HashMap<String, Result>(); 
        Result res;
        Collections.sort(present);
        Collections.sort(checked);
        Iterator<String> it; 
        
        it = present.iterator();
        while (it.hasNext()) {
            String id = it.next();
            res = map.get(id);
            if (res == null) {
                res = new Result();
                res.setId(id);
                res.setPresent(1);
                map.put(id, res);
            } else {
                res.setPresent(res.getPresent() + 1);
            }
        }
        
        it = checked.iterator();
        while (it.hasNext()) {
            String id = it.next();
            res = map.get(id);
            if (res == null) {
                res = new Result();
                res.setId(id);
                res.setChecked(1);
                map.put(id, res);
            } else {
                res.setChecked(res.getChecked() + 1);
            }
        }
        return map;
    }
    
    public Set<String> getSelectedSet() {
        return selected;
    }
    /**
     * @return Returns the selected.
     */
    public String[] getSelected() {
        return selected.toArray(new String[selected.size()]);
    }
    /**
     * @param selected The selected to set.
     */
    public void setSelected(String[] sel) {
        this.selected.addAll(Arrays.asList(sel));
    }

    public List<String> getCheckedList() {
        return checked;
    }
    /**
     * NB: Returns the contents of the Selected Set so Struts multibox 
     * tag can select the correct checkboxes.
     * @return Returns the selected articles.
     */
    public String[] getChecked() {
        return selected.toArray(new String[selected.size()]);
    }
    /**
     * @param checked The checked to set.
     */
    public void setChecked(String[] check) {
        this.checked.addAll(Arrays.asList(check));
    }

    public List<String> getPresentList() {
        return present;
    }
    /**
     * @return Returns the present.
     */
    public String[] getPresent() {
        return present.toArray(new String[present.size()]);
    }
    /**
     * @param present The present to set.
     */
    public void setPresent(String[] pres) {
        this.present.addAll(Arrays.asList(pres));
    }

    private class Result {
        int present = 0;
        int checked = 0;
        String id = "";
        
        public int getPresent() {
            return present;
        }
        public void setPresent(int count) {
            present = count;
        }

        public int getChecked() {
            return checked;
        }
        public void setChecked(int count) {
            checked = count;
        }
    
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
    }
    
    public List<Article> getArticles(HttpServletRequest request) {
        String inlist = "";
        Login user = (Login)request.getSession().getAttribute("user"); 
        
        if (selected.size() == 0) {
            //TODO get the current list of _articles_ based on the newsForm
            // settings
        } else {
            String[] selected = getSelected();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < selected.length; i++) {
                if(i > 0) {
                    sb.append(",");
                }
                sb.append(selected[i]);
            }
            inlist = sb.toString();
        }
    
        if (!inlist.equals("")) {
            String query = "select art from Login as login inner join login.briefAccess as brief, Hit hit inner join hit.article as art where login.loginID = " + user.getLoginID() + " and hit.hitID in (" + inlist + ") and brief.briefID = hit.brief.briefID";
            try {
                return DataStore.store().getObjects(query);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return null;
        
    }

    public List<Hit> getHits(HttpServletRequest request) {
        String inlist = "";
        Login user = (Login)request.getSession().getAttribute("user"); 
        
        if (selected.size() == 0) {
            //TODO get the current list of _articles_ based on the newsForm
            // settings
        } else {
            String[] selected = getSelected();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < selected.length; i++) {
                if(i > 0) {
                    sb.append(",");
                }
                sb.append(selected[i]);
            }
            inlist = sb.toString();
        }
    
        if (!inlist.equals("")) {
            String query = "select hit from Login as login inner join login.briefAccess as brief, Hit hit where login.loginID = " + user.getLoginID() + " and hit.hitID in (" + inlist + ") and brief.briefID = hit.brief.briefID";
            try {
                return DataStore.store().getObjects(query);
            } catch (DataStoreException e) {
                return null;
            }
        }
        return null;
        
    }

	public boolean isClear() {
		return clear;
	}

	public void setClear(boolean clear) {
		this.clear = clear;
	}

    
}
