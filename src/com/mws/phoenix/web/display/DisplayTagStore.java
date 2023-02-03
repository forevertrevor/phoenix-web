package com.mws.phoenix.web.display;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

/**
 * Stores DisplayTag request parameters to be reinserted during a 
 * NewsAction request.
 * @author Ed Webb
 * @version 
 * @since
 */
public class DisplayTagStore implements Serializable {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> params = new HashMap<String, String>();
    
    /**
     * 
     */
    public DisplayTagStore() {
    }

    public void storeParams(HttpServletRequest request) {
        Enumeration<?> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String)enumeration.nextElement();
            if(name.startsWith("d-")) {
                params.put(name, request.getParameter(name));
            }
        }
    }
    
    public String getParam(String name) {
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String param = it.next();
            if (param.startsWith("d-") && param.endsWith("-" + name)) {
                return params.get(param);
            }
        }
        
        return null;
    }
    
    public String getParams(HttpServletRequest request, ActionForward fwd) {
        String separator;
        StringBuffer sb = new StringBuffer();
        sb.append(fwd.getPath());
        Iterator<String> it = params.keySet().iterator();
        if (request.getParameterMap().size() == 0 && fwd.getPath().indexOf("?") < 0) {
           separator = "?";
        } else {
            separator = "&";
        }
        while (it.hasNext()) {
            String name = it.next();
            if (request.getParameter(name) == null) {
                sb.append(separator);
                sb.append(name);
                sb.append("=");
                sb.append(params.get(name));
                separator = "&";
            }
        }
        return sb.toString();
    }
}
