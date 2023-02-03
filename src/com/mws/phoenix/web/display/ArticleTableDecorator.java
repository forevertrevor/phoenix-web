package com.mws.phoenix.web.display;

import java.util.Iterator;

import com.mws.phoenix.db.despatch.Hit;
import com.mws.phoenix.db.web.Login;



/**
 * TableDecorator for the Article List table. 
 * 
 * @author Ed Webb
 */
public class ArticleTableDecorator extends AbstractArticleDecorator {
    
	/**
	 * Decorator for headline column
	 */
	public String getHeadline() {
		Login user = (Login)getPageContext().findAttribute("user");
		Hit item = (Hit)this.getCurrentRowObject();
		return getHeadline(item, user);
	}

    /**
     *  Decorator for publication column
     * @return
     */
    public String getSource() {
        Hit item = (Hit)this.getCurrentRowObject();
        return getSource(item.getArticle());
    }
    
    /**
	 * Decorator for the Article Date column
	 */
	public String getArticleDate() {

		Hit item = (Hit)this.getCurrentRowObject();

		return getArticleDate(item.getArticle());		
	}

    /**
     * Adds a summary row to the table if summary display is turned on.
     */
    public String finishRow() {
        StringBuffer footer = new StringBuffer();
    	Hit item = (Hit)this.getCurrentRowObject();
    	Iterator<Hit> alsos = item.getAlsoMentioned().iterator();
        while (alsos.hasNext()) {
        	Hit also = alsos.next();
        	footer.append("<tr class=\"" + row + " also\">");
        	footer.append("<td>");
        	footer.append(getSource(also.getArticle()));
        	footer.append("</td>");
        	footer.append("<td>");
        	footer.append(also.getArticle().getHeadline());
        	footer.append("</td>");
        	footer.append("<td>");
        	footer.append(getArticleDate(also.getArticle()));
        	footer.append("</td>");
        	footer.append("</tr>");
        }
        super.finishRow();
        return footer.toString();
    }

}
