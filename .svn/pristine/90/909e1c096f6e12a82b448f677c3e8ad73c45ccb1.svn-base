package com.mws.phoenix.web.press;

import java.util.List;

import javax.servlet.ServletContext;
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
import org.apache.struts.action.ActionMessages;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.article.Article;
import com.mws.phoenix.db.despatch.Hit;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.WebLogEntry;
import com.mws.phoenix.web.WebUtilities;

/**
 * Action to retrieve and serve a static source file for an article.
 * The only reason this is done through an action is to enable the
 * web application to log requests for article source files.
 * 
 * @author Ed Webb
 */
public class ClipAction extends Action {

    Log log = LogFactory.getLog(ClipAction.class);
    
    /**
     * Returns the specified clip to the client. If the requested clip
     * If the requested resource exists in neither directory then a 
     * ResourcePathException is thrown.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param actionForm The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception exception is handled by ActionServlet 
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		Login user = (Login)session.getAttribute("user");
		ServletContext app = session.getServletContext();

		ActionErrors errs = new ActionErrors();
        request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errs);
		
        // Get the name of the file to serve
		// Deprecated: use id parameter and serveArticle method
		String clip = request.getParameter("clip");
		if (null != clip) {
			WebLogEntry.log(WebLogEntry.TYPE_SOURCE_FILE, user.getLoginName() + " requested " + clip );
			return serveClip(clip, response, mapping, app, user, errs);
		}
      	
		// Get the id of the file to serve
		String id = request.getParameter("id");
       	if (null != id) {
   			Hit hit = getHit(new Long(id), user);
   			if (hit == null) {
   				errs.add("", new ActionMessage("error.article.notfound"));
   				return mapping.findForward("failure");
   			} else if (WebUtilities.outOfDate(hit) && !user.getArchive().getEnabled().booleanValue()) {
   				errs.add("", new ActionMessage("error.article.outofdate"));
   				if (hit.getArticle().getNlaID() != null) {
   					ActionMessages msgs = new ActionMessages();
   					msgs.add("", new ActionMessage("nosourcefile.archive"));
   			        request.setAttribute(org.apache.struts.Globals.MESSAGE_KEY, msgs);
   				}
				return mapping.findForward("failure");
   			} else if (hit.getArticle().getNlaID() != null) {
				WebLogEntry.log(WebLogEntry.TYPE_SOURCE_FILE, user.getLoginName() + " requested " + hit.getArticle().getArticleID());
				return serveNLAClip(hit.getArticle(), request, mapping, user);
			} else {
				WebLogEntry.log(WebLogEntry.TYPE_SOURCE_FILE, user.getLoginName() + " requested " + hit.getArticle().getArticleID());
				return serveClip(hit.getArticle().getFilePath(), response, mapping, app, user, errs);
			}
       	}

		errs.add("", new ActionMessage("error.missing.parameter", "an id"));
	    return mapping.findForward("failure");
    }

    /**
     * 
     * @param clip
     * @param response
     * @param mapping
     * @param app
     * @param user
     * @return
     * @throws Exception
     */
    private ActionForward serveClip(String clip, HttpServletResponse response, ActionMapping mapping, ServletContext app, Login user, ActionErrors errs) throws Exception {
		response.setContentType("application/pdf");
		String path = WebUtilities.getResource(app, user.getStyle().getFolder() + "clips/", "default/clips/", clip);
		
		if (null == path) {
			if (log.isWarnEnabled()) {
                log.warn("Cannot find " + path);
            }
			errs.add("", new ActionMessage("error.missing.pdf", clip));
			return mapping.findForward("failure"); 
		}
		return new ActionForward(path);
    	
    }

    /**
     * Retrieve the hit from the database. If the user is an Internal user
     * then allow them to view any hit regardless of their Brief Access.
     * 
     * @param id the id of the Hit
     * @param user the user making the request
     * @return the Hit object requested
     * @throws DataStoreException
     */
    private Hit getHit(Long id, Login user) throws DataStoreException {
    	if (user.getLevel().equals(Login.INTERNAL)) {
    		return (Hit)DataStore.store().getObject(Hit.class, id);
    	} else {
    		List<Hit> list = DataStore.store().getObjects("select hit from Login as login inner join login.briefAccess as brief, Hit hit where login.loginID = " + user.getLoginID() + " and brief.briefID = hit.brief.briefID and hit.hitID = " + id);
    		if (list.size() > 0) {
    			return list.get(0);
    		} else {
    			return null;
    		}
    	}
    }

    private ActionForward serveNLAClip(Article article, HttpServletRequest request, ActionMapping mapping, Login user) throws Exception {
    	String path = WebUtilities.getNlaApiUrl(user, article);
    	request.setAttribute("nlaredirect", path);
    	return mapping.findForward("nla-redirect");
    }
}
