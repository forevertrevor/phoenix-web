package com.mws.phoenix.web.display;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.RequestUtils;
import org.displaytag.decorator.TableDecorator;

import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.LoginGroup;

/**
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class LoginGroupTableDecorator extends TableDecorator {
    
    private String row = "even";

	/**
	 * A flag to tell the decorator methods when they're being sorted and when they're being
	 * displayed. Set to true by startRow and false by finishRow
	 */
	//private boolean display = false;

    public String startRow() {
        //display = true;

        //Update the row count
		if (row.equals("odd")) {
		    row = "even";
		} else {
		    row = "odd";
		}
		return "";
    }

    public String finishRow() {
        LoginGroup curr = (LoginGroup)this.getCurrentRowObject();
        StringBuffer footer = new StringBuffer();
        footer.append("<tr class=\"" + row + "\"><th>Users:</th><td colspan=\"10\">");
        footer.append(getLogins(curr.getLogins()));
        footer.append("</td></tr>");
        footer.append("<tr><td colspan=\"10\" class=\"divider\">&nbsp;</td></tr>");
        //display = false;
        return footer.toString();
    }
    
    public String getLogins() {
        return getLogins(((LoginGroup)this.getCurrentRowObject()).getLogins());
    }
    
    public String getLogins(Set<Login> logins) {
        StringBuffer sb = new StringBuffer();
        Iterator<Login> it = logins.iterator();
        while(it.hasNext()) {
            Login login = it.next();
            try {
                Map<?, ?> params = RequestUtils.computeParameters(this.getPageContext(),null,null,null,null,null,null,null,false);
                String link = RequestUtils.computeURL(this.getPageContext(), null, "/admin-user?loginID=" + login.getLoginID(), null, null, params, null, false, true);
                sb.append("<a href=\"");
                sb.append(link);
                sb.append("\">");
                sb.append(login.getLoginName());
                sb.append("</a>");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                sb.append(login.getLoginName());
            } catch (JspException e) {
                e.printStackTrace();
                sb.append(login.getLoginName());
            }
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
