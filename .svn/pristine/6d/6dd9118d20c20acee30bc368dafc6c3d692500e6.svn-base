package com.mws.phoenix.web.display;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.displaytag.decorator.TableDecorator;

import com.mws.phoenix.db.brief.SortSection;
import com.mws.phoenix.db.source.SourceCategory;
import com.mws.phoenix.db.source.SourceType;
import com.mws.phoenix.db.web.AlertBrief;
import com.mws.phoenix.db.web.LoginAlert;

/**
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class AlertsTableDecorator extends TableDecorator {
    
    private SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dateTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
    private String row = "even";

	/**
	 * A flag to tell the decorator methods when they're being sorted and when they're being
	 * displayed. Set to true by startRow and false by finishRow
	 */
	private boolean display = false;

	/**
	 * A date formatter that allows sorting by date
	 */
	private Format sortformat = new SimpleDateFormat("yyyyMMddHHmmss");

	
	
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

    public String finishRow() {
        LoginAlert curr = (LoginAlert)this.getCurrentRowObject();
        StringBuffer footer = new StringBuffer();
        footer.append("<tr class=\"" + row + "\"><th>Times:</th><td colspan=\"10\">");
        footer.append(getTimes(curr.getUploadTime(), curr.getTimes(), curr.getNoContent()));
        footer.append("</td></tr>");
        footer.append("<tr class=\"" + row + "\"><th>Content:</th><td colspan=\"10\">");
        footer.append(getContent(curr.getBriefs()));
        footer.append("</td></tr>");
        footer.append("<tr class=\"" + row + "\"><th>Source Types:</th><td colspan=\"10\">");
        footer.append(getSources(curr.getSourceTypes(), curr.getSourceCategories()));
        footer.append("</td></tr>");
        footer.append("<tr class=\"" + row + "\"><th>Recipients:</th><td colspan=\"10\">");
        footer.append(getEmails(curr.getEmails()));
        footer.append("</td></tr>");
        footer.append("<tr><td colspan=\"10\" class=\"divider\">&nbsp;</td></tr>");
        display = false;
        return footer.toString();
    }

    public String getLastSent() {
        return getLastSent(((LoginAlert)this.getCurrentRowObject()).getLastSent());
    }
    
    public String getTimes() {
    	LoginAlert alert = (LoginAlert)this.getCurrentRowObject();
    	return getTimes(alert.getUploadTime(), alert.getTimes(), alert.getNoContent());
    }

    public String getContent() {
        return getContent(((LoginAlert)this.getCurrentRowObject()).getBriefs());
    }

    public String getEmails() {
        return getEmails(((LoginAlert)this.getCurrentRowObject()).getEmails());
    }
    
    public String getHtml() {
        return getHtml(((LoginAlert)this.getCurrentRowObject()).getHtml());
    }
    
    public String getSources() {
    	return getSources(((LoginAlert)this.getCurrentRowObject()).getSourceTypes(), ((LoginAlert)this.getCurrentRowObject()).getSourceCategories());
    }
    
    public String getTimes(Boolean upload, Set<Time> times, Boolean noContent) {
        StringBuffer sb = new StringBuffer();
        if (upload.booleanValue()) {
            sb.append("On upload of new content. ");
        }
        if (!upload.booleanValue() || noContent.booleanValue()) {
           	sb.append("At these times: ");
            Iterator<Time> it = times.iterator();
            while(it.hasNext()) {
                sb.append(time.format(it.next()));
                if (it.hasNext()) {
                    sb.append(", ");
                }
            }
            if (noContent.booleanValue()) {
            	sb.append(" a no content alert will be sent.");
            }
        }
        return sb.toString();
    }
    
    public String getContent(Set<AlertBrief> briefs) {
        StringBuffer sb = new StringBuffer();
        if (briefs == null || briefs.isEmpty()) {
            sb.append("All Articles");
        } else {
            Iterator<AlertBrief> it = briefs.iterator();
            while(it.hasNext()) {
                AlertBrief ab = it.next();
                sb.append(ab.getBrief());
                sb.append(" [");
                if (ab.getSortSections().isEmpty()) {
                    sb.append("All Sections");
                } else {
                    Iterator<SortSection> sec = ab.getSortSections().iterator();
                    while(sec.hasNext()) {
                        sb.append(sec.next());
                        if (sec.hasNext()) {
                            sb.append(", ");
                        }
                    }
                }
                sb.append("]");
                if (it.hasNext()) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
    
    public String getSources(Set<SourceType> sourceTypes, Set<SourceCategory> pubTypes) {
    	if (sourceTypes.isEmpty() && pubTypes.isEmpty()) {
    		return "All Sources";
    	}
    	StringBuffer sb = new StringBuffer();
    	Iterator<SourceType> it;
    	it = sourceTypes.iterator();
    	while (it.hasNext()) {
    		sb.append(it.next());
   			if (it.hasNext()) {
   				sb.append(", ");
   			}
    	}
    	
    	Iterator<SourceCategory> xit = pubTypes.iterator();
    	if (xit.hasNext()) {
        	if (!sourceTypes.isEmpty()) {
        		sb.append(", ");
        	}
        	sb.append("Category:");
       		while (xit.hasNext()) {
       			sb.append(xit.next());
       			if (xit.hasNext()) {
       				sb.append(", ");
       			}
        	}
    	}
    	return sb.toString();
    }

    public String getEmails(Set<String> emails) {
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = emails.iterator();
        while(it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public String getHtml(Boolean html) {
        if (html.booleanValue()) {
            return "HTML";
        } else {
            return "Plain Text";
        }
    }
    
    public String getLastSent(Date date) {
        if (date == null) {
            return "n/a";
        }
        if (display) {
            return dateTime.format(date);
        } else {
            return sortformat.format(date); 
        }
    }
}
