package com.mws.phoenix.web.press;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.mws.phoenix.db.brief.SortSection;
import com.mws.phoenix.db.despatch.Hit;
import com.mws.phoenix.db.despatch.HitSortSection;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.WebFolderItem;

/**
 * Action to carry out CRUD operations on a news item.
 * 
 * @author Ed Webb
 */
public class ItemAction extends Action {


    /* (non-Javadoc)
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        Login user = (Login)session.getAttribute("user");

        ActionErrors errs = new ActionErrors();
        ActionMessages msgs = new ActionMessages();
        request.setAttribute(ToolAction.TOOL_ERRORS, errs);
        request.setAttribute(ToolAction.TOOL_INFO, msgs);
        
        if(user.getLevel().compareTo(Login.EDITOR) < 0) {
            errs.add(ActionErrors.GLOBAL_ERROR, new ActionMessage("error.level.toolow"));
        }
        ItemForm item = (ItemForm)form;
            if (mapping.getParameter().equals("read")) {
                item.setId(request.getParameter("id"));
                readItem(item);
            } else
            if (mapping.getParameter().equals("update")) {
                updateItem(item);
            } else
            if (mapping.getParameter().equals("delete")) {
                if (item.getType().equals("item")) {
                	ActionMessage msg = deleteItem(item, user);
                	if (msg.getKey().startsWith("info")) {
                		msgs.add("", msg);
                	} else {
                		errs.add("", msg);
                	}
                } else {
                	deleteItem(item);
                }
            }
        return mapping.findForward("success");
    }

    /**
     * @param item
     */
    private void deleteItem(ItemForm item) throws Exception {
        if (item.getType().equals("lead")) {
            HitSortSection hss = (HitSortSection)DataStore.store().getObject(HitSortSection.class, new Long(item.getId()));
            DataStore.store().delete(hss);
        } else if (item.getType().equals("also")) {
            Hit hit = (Hit)DataStore.store().getObject(Hit.class, new Long(item.getId()));
            hit.setLeadArticle(null);
            DataStore.store().persist(hit);
        }
    }

    public static ActionMessage deleteItem(ItemForm item, Login user) throws Exception {
    	String[] props;
    	Object[] values;
    	if (user.getLevel().compareTo(Login.ADMIN) >= 0) {
    		props = new String[] {"itemID", "folder.group.groupID"};
    		values = new Object[] {new Long(item.getId()), user.getGroup().getGroupID()};
    	} else {
    		props = new String[] {"itemID", "creator.loginID"};
    		values = new Object[] {new Long(item.getId()), user.getLoginID()};
    	}
    	WebFolderItem wfi = (WebFolderItem)DataStore.store().getObject(WebFolderItem.class, props, values);
        if (null != wfi) {
            DataStore.store().delete(wfi);
            return new ActionMessage("info.folder.item.delete");
        } else {
        	return new ActionMessage("error.folder.item.delete");
        }
    }
    
    /**
     * @param item
     */
    private void updateItem(ItemForm item) throws Exception {
        Hit hit = (Hit)DataStore.store().getObject(Hit.class, new Long(item.getId()));
        hit.getArticle().setHeadline(item.getHeadline());
        hit.getArticle().setWithdrawn(new Boolean(item.isWithdrawn()));
        hit.setSummary(item.getSpecificSummary());
        Iterator<SortSectionRelevance> rels = item.getSortSections().iterator();
        while (rels.hasNext()) {
            SortSectionRelevance rel = rels.next();
            boolean found = false;
            Iterator<HitSortSection> hsss = hit.getHitSortSections().iterator();
            while (hsss.hasNext()) {
                HitSortSection hss = hsss.next();
                if (hss.getSortSection().getSortSectionID().toString().equals(rel.getSortSectionID())) {
                    if (rel.getRelevance().equals("0")) {
                        DataStore.store().delete(hss);
                    }
                    else if (!hss.getRelevance().toString().equals(rel.getRelevance())) {
                        hss.setRelevance(new Long(rel.getRelevance()));
                        DataStore.store().persist(hss);
                    }
                    found = true;
                    break;
                }
            }
            if (!found && !rel.getRelevance().equals("0")) {
                HitSortSection hss = new HitSortSection();
                hss.setHit(hit);
                hss.setSortSection((SortSection)DataStore.store().getObject(SortSection.class, new Long(rel.getSortSectionID())));
                hss.setRelevance(new Long(rel.getRelevance()));
                DataStore.store().persist(hss);
            }
        }
        DataStore.store().persist(hit);
        DataStore.store().persist(hit.getArticle());
    }

    /**
     * @param item
     */
    private void readItem(ItemForm item) throws Exception {
        Hit hit = (Hit)DataStore.store().getObject(Hit.class, new Long(item.getId()));
        item.setHeadline(hit.getArticle().getHeadline());
        if (hit.getSummary() == null || hit.getSummary().length() == 0) {
            item.setSpecificSummary(hit.getArticle().getSummary());
        } else {
            item.setSpecificSummary(hit.getSummary());
        }
        item.setSource(hit.getArticle().getSource().toString());
        item.setArticleDate(hit.getArticle().getSource().getFrequency().getFormat().format(hit.getArticle().getArticleDate()));
        item.setWithdrawn(hit.getArticle().getWithdrawn().booleanValue());
        Set<HitSortSection> hss = hit.getHitSortSections();
        List<SortSection> ss = hit.getBrief().getSortSections();
        item.clearSortSections();
        for (int i = 0; i < ss.size(); i++) {
            SortSection s = ss.get(i);
            Iterator<HitSortSection> it = hss.iterator();
            boolean found = false;
            while (it.hasNext()) {
                HitSortSection h = it.next();
                if (h.getSortSection().getSortSectionID().equals(s.getSortSectionID())) {
                    found = true;
                    item.addSortSection(s.getSortSectionID(), s.getSortSectionName(), h.getRelevance());
                    break;
                }
            }
            if (!found) {
                item.addSortSection(s.getSortSectionID(), s.getSortSectionName(), new Long(0));
            }
        }
    }
}
