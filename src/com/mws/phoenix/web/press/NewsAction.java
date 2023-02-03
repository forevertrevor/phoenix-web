package com.mws.phoenix.web.press;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.mws.db.DataStore;
import com.mws.db.ValueObject;
import com.mws.db.ValueObjectComparator;
import com.mws.phoenix.db.article.Article;
import com.mws.phoenix.db.despatch.Hit;
import com.mws.phoenix.db.despatch.HitSortSection;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.WebFolderItem;
import com.mws.phoenix.web.WebUtilities;
import com.mws.phoenix.web.display.DisplayTagStore;
import com.mws.util.ReflectUtilities;

public class NewsAction extends Action {

    Log log = LogFactory.getLog(NewsAction.class);
    
    public static final String NEWS_ERRORS = "mediagen.news.errors";
    public static final String NEWS_INFO = "mediagen.news.messages";

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        NewsForm newsform = (NewsForm)form;
		Login user = (Login)session.getAttribute("user");
		String path = mapping.getPath();
		String mode = "";
		if (path.startsWith("/news")) {
			mode = "/news";
		} else if (path.startsWith("/archive")) {
			mode = "/archive";
		} else if (path.startsWith("/folder")) {
			mode = "/folder";
		}
		
		// Check the user has access to press
		String error = "";
		if (mode.equals("/news") && !user.getControls().get(WebUtilities.CONTROL_NEWS)) {
			error = "error.news.noaccess";
		} else if (mode.equals("/folder") && !user.getControls().get(WebUtilities.CONTROL_FOLDERS)) {
			error = "error.folders.noaccess";
		} else if (mode.equals("/archive") && !user.getArchive().getEnabled().booleanValue()) {
			error = "error.archive.noaccess";
		}
		if (!error.equals("")) {
            ActionErrors errs = new ActionErrors();
            errs.add("", new ActionMessage(error));
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
            return mapping.findForward("noaccess");
		}
            
		DisplayTagStore dts = (DisplayTagStore)session.getAttribute("displayTagStore" + mode);
		if (dts == null) {
		    dts = new DisplayTagStore();
		    session.setAttribute("displayTagStore" + mode, dts);
		}
		
		// Save the displaytag parameters. We may need them later
		dts.storeParams(request);
		
		DataStore store = DataStore.store();
		
		// If the column being sorted on is the 4th then
		// mark that the articles are being sorted by sector
		if (mode.equals("/news") || mode.equals("/archive")) {
			String sort = dts.getParam("s");
			if (sort == null || sort.equals("4")) {
				newsform.setOrderby("section");
			} else {
				newsform.setOrderby("");
			}
		} else if (mode.equals("/folder")) {
			String sort = dts.getParam("s");
			if (sort == null) {
				newsform.setOrderby("date");
			}
		}

		String eClipsList = ItemsHelper.buildeClipsList(newsform, user);
        String query = ItemsHelper.buildQuery(user, newsform, mode, "hss", eClipsList);
		
		if (log.isDebugEnabled()) {
		    log.debug(query);
        }
		
		List hits = store.getObjects(query);

		if (newsform.getOrderby().equals("section")) {
			Collections.sort(hits, new ValueObjectComparator(HitSortSection.class, new String[] {"hit.brief.briefName", "sortSection.sortSectionOrder", "relevance DESC", "hit.hitID"}));
		} else if (newsform.getOrderby().equals("date")) {
			Collections.sort(hits, new ValueObjectComparator(WebFolderItem.class, "hit.article.articleDate DESC"));
		}
		
		//Mark the matched search word in the headline and summary
		if (!newsform.getSearch().equals("")) {
			Pattern pattern = Pattern.compile(newsform.getSearch(), Pattern.CASE_INSENSITIVE);
			Iterator it = hits.iterator();
		    while (it.hasNext()) {
		        Object o = it.next();
		        Hit h = (Hit)ReflectUtilities.getProperty(o, "hit");

		        highlight(h, pattern, eClipsList);

                Iterator<Hit> alsos = h.getAlsoMentioned().iterator();
                while (alsos.hasNext()) {
                	Hit also = alsos.next();
                	highlight(also, pattern, eClipsList);
                }
		    }
		}
		
		request.setAttribute("items", hits);

		ActionForward fwd = mapping.findForward("success");
		
		//Add those DisplayTag Parameters to the path
		return new ActionForward(dts.getParams(request, fwd));
    }

    private void highlight(Hit hit, Pattern pattern, String eClipsList) {
		String replacePattern = "[b]$0[/b]";
        Article a = hit.getArticle();
		Matcher match;
        
        if (a.getHeadline() != null && a.getHeadline().indexOf("[b]") < 0) {
            match = pattern.matcher(a.getHeadline());
            a.setHeadline(match.replaceAll(replacePattern));
        }

        if (a.getSummary() != null && a.getSummary().indexOf("[b]") < 0) {
            match = pattern.matcher(a.getSummary());
            a.setSummary(match.replaceAll(replacePattern));
        }
        
        if (hit.getSummary() != null && hit.getSummary().indexOf("[b]") < 0) {
            match = pattern.matcher(hit.getSummary());
            hit.setSummary(match.replaceAll(replacePattern));
        }
        
        if (!a.getHeadline().startsWith("* ") && a.getNlaID() != null && eClipsList.contains(a.getNlaID().toString())) {
        	a.setHeadline("* " + a.getHeadline());
        }
    }
    
}