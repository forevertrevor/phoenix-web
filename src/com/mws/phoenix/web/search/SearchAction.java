package com.mws.phoenix.web.search;

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

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.article.cutting.Cutting;
import com.mws.phoenix.db.despatch.Hit;
import com.mws.phoenix.db.web.Login;

public class SearchAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        SearchForm searchform = (SearchForm)form;
		Login user = (Login)session.getAttribute("user");
		
		if (user.getLevel().longValue() < Login.INTERNAL.longValue()) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage("error.search.noaccess"));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
		}

		if (!searchform.getSourceID().equals("") && !searchform.getDate().equals("")) {
			List<Hit> list = getArticleList(searchform);
			request.setAttribute("items", list);
		}

		return mapping.findForward("success");
		
	}

	private List<Hit> getArticleList(SearchForm searchform) throws DataStoreException {
		String[] searchPages = searchform.getPage().split("[\\s,]+");

		StringBuffer sb = new StringBuffer();
		sb.append("select h from Hit h, Cutting c where c.articleID = h.article.articleID and ");
		sb.append("h.article.articleDate = '");
		sb.append(searchform.getDate());
		sb.append("' and h.article.source.sourceID = ");
		sb.append(searchform.getSourceID());
		if (!searchform.getPage().equals("")) {
			sb.append(" and (");
			String separator = "";
			for (int i = 0; i < searchPages.length; i++) {
				sb.append(separator);
				sb.append("c.page ilike '%");
				sb.append(searchPages[i]);
				sb.append("%' ");
				separator = "or ";
			}
			sb.append(")");
		}
		sb.append(" order by h.article.source.sourceName, h.article.headline");

		List<Hit> list = DataStore.store().getObjects(sb.toString());
		if (!searchform.getPage().equals("")) {
			for (int index = list.size()-1; index >= 0; index--) {
				Hit hit = list.get(index);
				String[] hitPages = ((Cutting)hit.getArticle()).getPage().split("[\\s,]+");
				boolean match = false;
				for (int i = 0; i < hitPages.length;i++) {
					for (int j = 0; j < searchPages.length;j++) {
						if (searchPages[j].trim().equals(hitPages[i].trim())) {
							match = true;
							break;
						}
					}
					if (match) {
						break;
					}
				}
				if (!match) {
					list.remove(index);
				}
			}
		}
		return list;
	}
	
}
