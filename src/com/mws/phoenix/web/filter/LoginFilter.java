package com.mws.phoenix.web.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.mws.phoenix.db.web.Login;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Filter to ensure that a user session is logged on before allowing
 * access to protected content.
 */
public class LoginFilter implements Filter {

    private Pattern[] allowed = {};
    private Pattern[] bookmarks = {};
    private String logon = "";
    private String home = "";
    private ServletContext application = null;
    //private Logger log = LoggerFactory.getLogger(this.getClass());

    public void init(FilterConfig config) throws ServletException {
        application = config.getServletContext();

        logon = config.getInitParameter("loginPage");
        application.log("** LoginFilter loginPage: " + logon);
        
        home = config.getInitParameter("homePage");
        application.log("** LoginFilter homePage: " + home);

        String[] allow = config.getInitParameter("allowURI").split(";");
        allowed = new Pattern[allow.length];
        for (int i = 0; i < allow.length; i++) {
            allowed[i] = Pattern.compile(allow[i]);
            application.log("** LoginFilter allow url pattern: " + allow[i]);
        }

        String[] bookmark = config.getInitParameter("bookmarkURI").split(";");
        bookmarks = new Pattern[bookmark.length];
        for (int i = 0; i < bookmark.length; i++) {
            bookmarks[i] = Pattern.compile(bookmark[i]);
            application.log("** LoginFilter bookmark url pattern: " + bookmark[i]);
        }
    
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        
        if (session != null) {
        	addBookmark(session, uri + (queryString != null ? "?" + queryString : ""));
        }
        
        // Is there a SESSIONID cookie and no JSESSIONID cookie?
        if (addJSessionID(request, response)) {
            chain.doFilter(req, resp);
            //response.sendRedirect(home);
            return;
        }

        // Is this an allowed URL?
        if (allowedURL(uri)) {
            chain.doFilter(req, resp);
            return;
        } 
        
        // Is there a session with a user attribute?
        if (session != null) {
            Login user = (Login)session.getAttribute("user");
            if (null != user) {
                addSessionID(request, response, user.getTimeout().intValue());
                chain.doFilter(req, resp);
                return;
            }
        }
        
        // If none of the above then redirect to logon URL
        response.sendRedirect(logon);
    }

    private void addBookmark(HttpSession session, String uri) {
        Matcher match = null;
        CharSequence seq = uri;
        for (int i = 0; i < bookmarks.length; i++) {
            match = bookmarks[i].matcher(seq);
            if (match.matches()) {
            	//TODO: Take off the app's path if it is at the start of the URI - You should be able to find out the context path from the servletContext but I can't
            	// Bad hard coded string of /phoenix.
            	String path = "/phoenix";
            	if (uri.startsWith(path)) {
            		uri = uri.substring(path.length());
            	}
            	System.out.println("** Adding Bookmark: " + uri);
                session.setAttribute("bookmark", uri);
                return;
            }
        }
    }
    
    /**
     * @param uri
     */
    private boolean allowedURL(String uri) {
        Matcher match = null;
        CharSequence seq = uri;
        for (int i = 0; i < allowed.length; i++) {
            match = allowed[i].matcher(seq);
            if (match.find()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if there is a MWSID cookie but no JSESSIONID cookie.
     * If so it will create a new JSESSIONID cookie and attempt to redirect
     * the browser to the home page.
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    private boolean addJSessionID(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }
        
        Cookie jsid = null;
        Cookie sid = null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("JSESSIONID")) {
                jsid = cookies[i];
            } else if (cookies[i].getName().equals("SESSIONID")) {
                sid = cookies[i];
            }
        }
        
        if (sid != null && jsid == null) {
            //System.out.println("** Creating a JSESSIONID cookie and redirecting");
            jsid = new Cookie("JSESSIONID", sid.getValue());
            jsid.setPath("/");
            jsid.setMaxAge(-1);
            response.addCookie(jsid);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks to see if there is a JSESSIONID cookie.
     * If so it will create a new SESSIONID cookie which will live as long as
     * the session.
     * 
     * @param request
     * @param response
     * @param timeout
     * @throws IOException
     */
    private boolean addSessionID(HttpServletRequest request, HttpServletResponse response, int timeout) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }
        
        Cookie jsid = null;
        Cookie sid = null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("JSESSIONID")) {
                jsid = cookies[i];
                break;
            }
        }
        
        if (jsid != null) {
            sid = new Cookie("SESSIONID", jsid.getValue());
            sid.setMaxAge(timeout * 60);
            sid.setPath("/");
            response.addCookie(sid);
            return true;
        } else {
            return false;
        }
    }
    
    public void destroy() {
        // Nothing to do
    }
}