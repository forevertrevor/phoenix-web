package com.mws.phoenix.web.display;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;

import com.mws.phoenix.db.web.LoginArchive;

/**
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class ArchiveTableDecorator extends TableDecorator {
    
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
        display = false;
        return "";
    }

    public String getLastSent() {
        return getFormattedDate(((LoginArchive)this.getCurrentRowObject()).getLastSent());
    }
    
    public String getFormattedDate(Date date) {
        if (date == null) {
            return "n/a";
        }
        if (display) {
            return dateTime.format(date);
        } else {
            return sortformat.format(date); 
        }
    }

    public String getCreated() {
        return getFormattedDate(((LoginArchive)this.getCurrentRowObject()).getCreated());
    }
}
