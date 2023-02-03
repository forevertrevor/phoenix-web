package com.mws.phoenix.web.display;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.jsp.JspException;

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
import com.mws.phoenix.web.WebUtilities;
import com.mws.phoenix.web.functions.Formatting;

public abstract class AbstractArticleDecorator extends TableDecorator {

	/**
	 * A flag to tell the decorator methods when they're being sorted and when they're being
	 * displayed. Set to true by startRow and false by finishRow
	 */
	protected boolean display = false;
	
	/**
	 * A date formatter that allows sorting by date
	 */
	protected Format sortformat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * A flag to remember whether we're on an even or odd row
	 */
	protected String row = "even";

	/**
	 * Turns on the display flag and updates the row flag
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
     * @param article the article to return the date of
     * @return a nicely formatted article date
     */
    public String getArticleDate(Article article) {
        String date = "";
		Format fmt = article.getSource().getFrequency().getFormat();
		if (display) {
			date = fmt.format(article.getArticleDate());
		} else {
			date = sortformat.format(article.getArticleDate());
		}

		return date;
    }

	/**
     * @param article the article to return the source of
     * @return a nicely formatted source name
     */
    public String getSource(Article article) {
        StringBuffer buff = new StringBuffer();
        Source source = article.getSource();
        DecimalFormat df = new DecimalFormat("#,##0");
        
        // Create the icon for the source
        String type = source.getSourceType().getSourceTypeName().toLowerCase();
        buff.append("<img src=\"image?resource=");
        buff.append(type);
        buff.append("16.gif\"/>&nbsp;");
        
        // Write the source name
        buff.append(Formatting.htmlEscape(source.getSourceName()));
        
        // Write the Section, Edition and Page Number of a Cutting
        if (source instanceof Publication) {
            Cutting cut = (Cutting)article;
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
            if (cut.getPublication().getCirculation() != null && cut.getPublication().getCirculation() > 0) {
            	buff.append(" (circ. ");
            	buff.append(df.format(cut.getPublication().getCirculation()));
            	buff.append(")");
            }
        }

        return buff.toString();
    }

    /**
     * @param item
     * @return
     */
    public String getHeadline(Hit item, Login user) {
        StringBuffer headline = new StringBuffer();
		if (WebUtilities.isArticleExpired(item, user) 
			|| !WebUtilities.displayNlaItem(item,user)
			|| user.getLevel().compareTo(Login.SUMMARY) <= 0        
		    || !display) {
			if (item.getArticle().getWithdrawn().booleanValue()) {
				headline.append("<b>(w)</b> ");
			}
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
                    Map<?,?> params = RequestUtils.computeParameters(this.getPageContext(),null,null,null,null,null,null,null,false);
                    headline.append(RequestUtils.computeURL(this.getPageContext(), null, "clip?id=" + item.getHitID(), null, null, params, null, false, true));
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
    
    public String getAve(Hit item, Login user) {
    	NumberFormat nf = new DecimalFormat("#,##0.00");
    	return nf.format(item.getArticle().getAve());
    }
    
   
    
    /**
     * Turns off the display flag.
     */
    public String finishRow() {
        display = false;
        return "";
    }
}
