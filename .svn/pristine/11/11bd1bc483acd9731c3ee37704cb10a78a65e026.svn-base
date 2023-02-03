package com.mws.phoenix.web.display;

import java.net.MalformedURLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.util.RequestUtils;
import org.displaytag.decorator.TableDecorator;

import com.mws.phoenix.db.article.Article;
import com.mws.phoenix.db.article.rss.RSSItem;
import com.mws.phoenix.db.article.cutting.Cutting;
import com.mws.phoenix.db.article.webpage.Webpage;
import com.mws.phoenix.db.despatch.Hit;
import com.mws.phoenix.db.source.Source;
import com.mws.phoenix.db.source.publication.Publication;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.db.web.WebFolderItem;
import com.mws.phoenix.web.WebUtilities;
import com.mws.phoenix.web.functions.Formatting;
import com.mws.phoenix.web.press.NewsForm;

/**
 * TableDecorator for the Folder Items table. Places the Section header
 * at the start of a new section, the summary after a news item and
 * displays "also mentioned" articles.
 * 
 * @author Ed Webb
 */
public class FolderItemTableDecorator extends TableDecorator {
    
	public static final int COLUMNS = 6;
	
    private String row = "even";
    
	/**
	 * A date formatter that allows sorting by date
	 */
	private Format sortformat;

	/**
	 * A flag to tell the decorator methods when they're being sorted and when they're being
	 * displayed. Set to true by startRow and false by finishRow
	 */
	private boolean display = false;

	/**
	 * Retrieve the Resource bundle for i18n
	 */
	public void init(PageContext context, Object decorated) {
		super.init(context, decorated);
		sortformat = new SimpleDateFormat("yyyyMMddHHmmss");
	}

	/**
	 * If this row is the start of a new Sector then add a header row
	 * to display the sector name that spans the entire table
	 */
	public String startRow() {
		display = true;
		
		//Update the row count
		if (row.equals("odd")) {
		    row = "even";
		} else {
		    row = "odd";
		}
		
		return "";
	}

	/**
	 * Decorator for headline column
	 */
	public String getHeadline() {
		Login user = (Login)getPageContext().findAttribute("user");
		WebFolderItem item = (WebFolderItem)this.getCurrentRowObject();
		return getHeadline(item.getHit(), user);
	}
    
    /**
     * @param item
     * @return
     */
    public String getHeadline(Hit item, Login user) {
        StringBuffer headline = new StringBuffer();
		if (WebUtilities.isArticleExpired(item, user) 
            || user.getLevel().compareTo(Login.SUMMARY) <= 0        
		    || !display) {
			headline.append(Formatting.htmlEscape(item.getArticle().getHeadline()));

        } else {
            try {
                headline.append("<a target=\"_blank\" href=\"");
                if (item.getArticle() instanceof Webpage) {
                    Webpage web = (Webpage)item.getArticle();
                    headline.append(web.getUrl());
                } else if (item.getArticle() instanceof RSSItem) {
                	RSSItem rss = (RSSItem)item.getArticle();
                	headline.append(rss.getLink());
                } else {
                    Map<?, ?> params = RequestUtils.computeParameters(this.getPageContext(),null,null,null,null,null,null,null,false);
                    headline.append(RequestUtils.computeURL(this.getPageContext(), null, "/clip?id=" + item.getHitID(), null, null, params, null, false, true));
                }
                headline.append("\">");
                headline.append(Formatting.htmlEscape(item.getArticle().getHeadline()));
                headline.append("</a>");
            } catch (MalformedURLException e) {
                headline.append("MalformedURLException");
                e.printStackTrace();
            } catch (JspException e) {
                headline.append("JspException");
                e.printStackTrace();
            }
		}
		return headline.toString();
    }
    
    /**
     *  Decorator for publication column
     * @return
     */
    public String getSource() {
        WebFolderItem item = (WebFolderItem)this.getCurrentRowObject();
        return getSource(item.getHit());
    }
    
	/**
     * @param sourcestring
     * @param item
     * @return
     */
    public String getSource(Hit item) {
        StringBuffer buff = new StringBuffer();
        Article art = item.getArticle();
        Source source = art.getSource();
        
        String type = source.getSourceType().getSourceTypeName().toLowerCase();
        buff.append("<img src=\"image?resource=");
        buff.append(type);
        buff.append("16.gif\"/>&nbsp;");
        buff.append(Formatting.htmlEscape(source.getSourceName()));
        
        if (source instanceof Publication) {
            Cutting cut = (Cutting)art;
            if(cut.getSection() != null && !cut.getSection().getSectionName().equals("Main")) {
                buff.append(" (");
                buff.append(Formatting.htmlEscape(cut.getSection().getSectionName()));
                buff.append(")");
            }
            if(!cut.getPage().equals("")) {
                buff.append(" p.");
                buff.append(cut.getPage());
            }
            if(cut.getEdition() != null && !cut.getEdition().getEditionName().equals("First")) {
                buff.append(" ");
                buff.append(Formatting.htmlEscape(cut.getEdition().getEditionName()));
            }
        }

        return buff.toString();
    }

    /**
	 * Decorator for the Article Date column
	 */
	public String getArticleDate() {

		WebFolderItem item = (WebFolderItem)this.getCurrentRowObject();

		return getArticleDate(item.getHit());		
	}

    /**
     * @param item
     * @return
     */
    public String getArticleDate(Hit item) {
        String date = "";
		Article a = item.getArticle();
		Format fmt = a.getSource().getFrequency().getFormat();
		if (display) {
			date = fmt.format(a.getArticleDate());
		} else {
			date = sortformat.format(a.getArticleDate());
		}

		return date;
    }

    private String getSummary(Hit item) {
        StringBuffer buff = new StringBuffer();
        String summary = "";
        if (item.getSummary() != null && !item.getSummary().equals("")) {
            summary = item.getSummary();
        } else if (item.getArticle().getSummary() != null && !item.getArticle().getSummary().equals("") ) {
            summary = item.getArticle().getSummary();
        }
        if (!summary.equals("")) {
            buff.append("<tr class=\"" + row + "\">");
            buff.append("<td colspan=\"" + COLUMNS + "\">");
            buff.append("<div class=\"summary\">");
            buff.append(Formatting.htmlEscape(summary));
            buff.append("</div>");
            buff.append("</td>");
            buff.append("</tr>");
        }
        return buff.toString();
    }

    /**
     * Adds a summary row to the table if summary display is turned on.
     */
    public String finishRow() {
        WebFolderItem curr = (WebFolderItem)this.getCurrentRowObject();
        StringBuffer footer = new StringBuffer();
        
        NewsForm news = (NewsForm)this.getPageContext().findAttribute("folderForm");
        if ("1".equals(news.getSummary())) { 
        	String summary = getSummary(curr.getHit());
        	if ((summary == null || summary.trim().equals("")) && curr.getHit().getLeadArticle() != null) {
        		summary = getSummary(curr.getHit().getLeadArticle());
        	}
        	footer.append(summary);
        }
        footer.append("<tr><td colspan=\"" + COLUMNS + "\" class=\"divider\">&nbsp;</td></tr>");
        display = false;
        return footer.toString();
    }

    /**
     * Release references to the previous row object and the Resource bundle
     */
    public void finish() {
        super.finish();
    }
}
