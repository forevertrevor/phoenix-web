package com.mws.phoenix.web.display;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.RequestUtils;

import com.mws.phoenix.db.despatch.Hit;
import com.mws.phoenix.db.despatch.HitSortSection;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.web.functions.Formatting;
import com.mws.phoenix.web.press.NewsForm;
import com.mws.phoenix.web.press.SelectedForm;

/**
 * TableDecorator for the News Items table. Places the Section header
 * at the start of a new section, the summary after a news item and
 * displays "also mentioned" articles.
 * 
 * @author Ed Webb
 */
public class PressTableDecorator extends AbstractArticleDecorator {
    
    /**
     * The previous object used to populate a row in the table
     */
    private Object prevRowObject;

	/**
	 * If this row is the start of a new Sector then add a header row
	 * to display the sector name that spans the entire table
	 */
	public String startRow() {
		super.startRow();
		
		HitSortSection last = null;
		HitSortSection curr = (HitSortSection)this.getCurrentRowObject();
		NewsForm news = (NewsForm)this.getPageContext().findAttribute("newsForm");
		String header = "";
		
		if (null != prevRowObject) {
			last = (HitSortSection)prevRowObject;
		}

		// Only display the section or source header if sorting by section or source
		if ("section".equals(news.getOrderby())) {
			if (last == null || !last.getSortSection().getSortSectionName().equals(curr.getSortSection().getSortSectionName())) {
				header = "<tr class=\"sectionhead\"><th colspan=\"10\" class=\"sectionhead\">" + curr.getSortSection().getSortSectionName() + "</th></tr>"; 
			}
		} else if ("publication".equals(news.getOrderby())) {
		    if (last == null || !last.getHit().getArticle().getSource().getSourceName().equals(curr.getHit().getArticle().getSource().getSourceName())) {
		        header = "<tr class=\"sectionhead\"><th colspan=\"10\" class=\"sectionhead\">" + curr.getHit().getArticle().getSource().getSourceName() + "</th></tr>";
		    }
		} else if ("date".equals(news.getOrderby())) {
		    if (last == null || !last.getHit().getArticle().getArticleDate().equals(curr.getHit().getArticle().getArticleDate())) {
		        header = "<tr class=\"sectionhead\"><th colspan=\"10\" class=\"sectionhead\">" + getArticleDate(curr.getHit().getArticle()) + "</th></tr>";
		    }
		}
        
		prevRowObject = this.getCurrentRowObject();
        
		return header;
	}

	/**
	 * Decorator for headline column
	 */
	public String getHeadline() {
		Login user = (Login)getPageContext().findAttribute("user");
		HitSortSection item = (HitSortSection)this.getCurrentRowObject();
		return getHeadline(item.getHit(), user);
	}

	/**
	 * Decorator for ave column
	 */
	public String getAve() {
		Login user = (Login)getPageContext().findAttribute("user");
		HitSortSection item = (HitSortSection)this.getCurrentRowObject();
		return getAve(item.getHit(), user);
	}
    
    
    /**
     *  Decorator for publication column
     * @return
     */
    public String getSource() {
        HitSortSection item = (HitSortSection)this.getCurrentRowObject();
        return getSource(item.getHit().getArticle());
    }

    /**
	 * Decorator for the Article Date column
	 */
	public String getArticleDate() {
		HitSortSection item = (HitSortSection)this.getCurrentRowObject();
		return getArticleDate(item.getHit().getArticle());		
	}

    private String getSummary(NewsForm news, Hit item) {
        StringBuffer buff = new StringBuffer();
        if ((item.getLeadArticle() == null           // Only display summaries for lead articles
             || !"section".equals(news.getOrderby()))// OR when the sorting is not by section
             && "1".equals(news.getSummary())) {        // and when the client requests them
            String summary = "";
            if (item.getSummary() != null && !item.getSummary().equals("")) {
                summary = item.getSummary();
            } else if (item.getArticle().getSummary() != null && !item.getArticle().getSummary().equals("") ) {
                summary = item.getArticle().getSummary();
            }
            if (!summary.equals("")) {
                buff.append("<tr class=\"" + row + "\">");
                buff.append("<td colspan=\"10\">");
                buff.append("<div class=\"summary\">");
                buff.append(Formatting.htmlEscape(summary));
                buff.append("</div>");
                buff.append("</td>");
                buff.append("</tr>");
            }
        }
        return buff.toString();
    }

    private String getAlsoMentioned(Hit item, Login user) {
        StringBuffer buff = new StringBuffer();
        if (item.getAlsoMentioned().size() > 0) {
            Iterator<Hit> it = item.getAlsoMentioned().iterator();
            buff.append("<tr class=\"" + row + " also\">");
            buff.append("<th>&nbsp;</th><th colspan=\"10\">Also mentioned in:</th>");
            buff.append("</tr>");
            while (it.hasNext()) {
                Hit also = it.next();
                buff.append("<tr class=\"" + row + " also\">");
                buff.append("<td>");
                buff.append(getSource(also.getArticle()));
                buff.append("</td>");
                buff.append("<td>");
                buff.append(getHeadline(also, user));
                buff.append("</td>");
                buff.append("<td>");
                buff.append(getArticleDate(also.getArticle()));
                buff.append("</td>");
                buff.append("<td>");
                buff.append(getAve(also, user));
                buff.append("</td>");
                buff.append("<td></td>");
                if (user.getLevel().compareTo(Login.EDITOR) >= 0) {
                    buff.append("<td>");
                	buff.append(getDelete(also));
                	buff.append("</td>");
                	buff.append("<td>");
                	buff.append(getEdit(also));
                	buff.append("</td>");
                }
                buff.append("<td>");
                buff.append(getSelect(also));
                buff.append("</td>");
                buff.append("</tr>");
            }
        }
        return buff.toString();
    }

    /**
     * @param buff
     * @param also
     */
    private String getEdit(Hit also) {
        StringBuffer buff = new StringBuffer();
        try {
            Map<?, ?> params = RequestUtils.computeParameters(this.getPageContext(),null,null,null,null,null,null,null,false);
            buff.append("<a href=\"");
            buff.append(RequestUtils.computeURL(this.getPageContext(), null, "edit?id=" + also.getHitID(), null, null, params, null, false, true));
            buff.append("\"><img alt=\"Edit\" title=\"Edit\" src=\"image?resource=edit16.gif\"/></a>");
        } catch (MalformedURLException e) {
            buff.append("MalformedURLException");
            e.printStackTrace();
        } catch (JspException e) {
            buff.append("JspException");
            e.printStackTrace();
        }
        return buff.toString();
    }

    /**
     * @param buff
     * @param also
     */
    private String getDelete(Hit also) {
        StringBuffer buff = new StringBuffer();
        try {
            Map<?, ?> params = RequestUtils.computeParameters(this.getPageContext(),null,null,null,null,null,null,null,false);
            buff.append("<a href=\"");
            buff.append(RequestUtils.computeURL(this.getPageContext(), null, "delete?type=also&id=" + also.getHitID(), null, null, params, null, false, true));
            buff.append("\"><img alt=\"Delete\" title=\"Delete\" src=\"image?resource=delete16.gif\"/></a>");
        } catch (MalformedURLException e) {
            buff.append("MalformedURLException");
            e.printStackTrace();
        } catch (JspException e) {
            buff.append("JspException");
            e.printStackTrace();
        }
        return buff.toString();
    }

    private String getSelect(Hit also) {
        SelectedForm selectedForm = (SelectedForm)this.getPageContext().findAttribute("selectedForm");
        Set selected = new TreeSet();
        if (selectedForm != null) {
            selected = selectedForm.getSelectedSet();
        }
        StringBuffer buff = new StringBuffer();
        buff.append("<input type=\"hidden\" name=\"present\" value=\"" + also.getHitID().toString() + "\" />");
        buff.append("<input type=\"checkbox\" name=\"checked\" value=\"" + also.getHitID().toString() + "\"");
        if (selected.contains(also.getHitID().toString())) {
            buff.append(" checked=\"checked\"");
        }
        buff.append(" />");
        return buff.toString();
    }
    
    /**
     * Adds a summary row to the table if summary display is turned on.
     */
    public String finishRow() {
        HitSortSection curr = (HitSortSection)this.getCurrentRowObject();
        NewsForm news = (NewsForm)this.getPageContext().findAttribute("newsForm");
        Login user = (Login)this.getPageContext().findAttribute("user");
        StringBuffer footer = new StringBuffer();
        footer.append(getSummary(news, curr.getHit()));
        footer.append(getAlsoMentioned(curr.getHit(), user));
        footer.append("<tr><td colspan=\"10\" class=\"divider\">&nbsp;</td></tr>");
        super.finishRow();
        return footer.toString();
    }

    /**
     * Release references to the previous row object and the Resource bundle
     */
    public void finish() {
        super.finish();
        prevRowObject = null;
    }
}
