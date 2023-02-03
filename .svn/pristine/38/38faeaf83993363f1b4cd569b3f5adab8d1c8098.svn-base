package com.mws.phoenix.web.functions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class Dates {

    private static Map<String, Integer> datePart = new HashMap<String, Integer>();
    
    static {
        datePart.put("ERA", new Integer(0));
        datePart.put("YEAR", new Integer(1));
        datePart.put("MONTH", new Integer(2));
        datePart.put("WEEK_OF_YEAR", new Integer(3));
        datePart.put("WEEK_OF_MONTH", new Integer(4));
        datePart.put("DATE", new Integer(5));
        datePart.put("DAY_OF_MONTH", new Integer(5));
        datePart.put("DAY_OF_QUARTER", new Integer(5));
        datePart.put("DAY_OF_YEAR", new Integer(6));
        datePart.put("DAY_OF_WEEK", new Integer(7));
        datePart.put("DAY_OF_WEEK_IN_MONTH", new Integer(8));
        datePart.put("AM_PM", new Integer(9));
        datePart.put("HOUR", new Integer(10));
        datePart.put("HOUR_OF_DAY", new Integer(11));
        datePart.put("MINUTE", new Integer(12));
        datePart.put("SECOND", new Integer(13));
        datePart.put("MILLISECOND", new Integer(14));
    }
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    
    public static String getDate() {
        return getDate(sdf);
    }
    public static String getDate(String format) {
        return getDate(new SimpleDateFormat(format));
    }
    public static String getDate(SimpleDateFormat f) {
        return f.format(new Date());
    }

    public static String getRelativeDate(String field, int value) {
        return getRelativeDate(sdf, field, value);
    }
    public static String getRelativeDate(String format, String field, int value) {
        return getRelativeDate(new SimpleDateFormat(format), field, value);
    }
    public static String getRelativeDate(SimpleDateFormat f, String field, int value) {
        Date newDate = calcRelativeDate(new Date(), field, value);
        return f.format(newDate);
    }    

    public static String getFirstDate(String field) {
        return getFirstDate(sdf, field);
    }
    public static String getFirstDate(String format, String field) {
        return getFirstDate(new SimpleDateFormat(format), field);
    }
    public static String getFirstDate(SimpleDateFormat f, String field) {
        Date newDate = calcFirstDate(new Date(), field);
        return f.format(newDate);
    }
    
    public static String getLastDate(String field) {
        return getLastDate(sdf, field);
    }
    
    public static String getLastDate(String format, String field) {
        return getLastDate(new SimpleDateFormat(format), field);
    }
    public static String getLastDate(SimpleDateFormat f, String field) {
        Date newDate = calcLastDate(new Date(), field);
        return f.format(newDate);
    }
    
    public static String getFirstRelativeDate(String field, int value) {
    	return getFirstRelativeDate(sdf, field, value);
    }
    public static String getFirstRelativeDate(String format, String field, int value) {
    	return getFirstRelativeDate(new SimpleDateFormat(format), field, value);
    }
    public static String getFirstRelativeDate(SimpleDateFormat f, String field, int value) {
    	Date newDate = calcRelativeDate(new Date(), field, value);
    	newDate = calcFirstDate(newDate, field);
    	return f.format(newDate);
    }
    
    public static String getLastRelativeDate(String field, int value) {
    	return getLastRelativeDate(sdf, field, value);
    }
    public static String getLastRelativeDate(String format, String field, int value) {
    	return getLastRelativeDate(new SimpleDateFormat(format), field, value);
    }
    public static String getLastRelativeDate(SimpleDateFormat f, String field, int value) {
    	Date newDate = calcRelativeDate(new Date(), field, value);
    	newDate = calcLastDate(newDate, field);
    	return f.format(newDate);
    }

    /**
     * Returns the correct time period code to calculate the last day of the time period
     * @param field the DAY_OF_Xxx code
     * @return the corresponding field code for the calculation
     */
    private static int getLast(int field) {
        switch(field) {
            case 5: return 2;
            case 6: return 1;
            case 7: return 3;
            default: return field;
        }
    }
    
    private static Date calcRelativeDate(Date date, String field, int value) {
        int fieldcode = getLast((datePart.get(field)).intValue());
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        if (field.equals("DAY_OF_QUARTER")) {
        	value *= 3; 
        }
        if (field.equals("DATE")) {
        	fieldcode = Calendar.DATE;
        }
       	cal.add(fieldcode, value);
        return cal.getTime();
    }
    
    private static Date calcFirstDate(Date date, String field) {
        int fieldcode = (datePart.get(field)).intValue();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        if (field.equals("DAY_OF_QUARTER")) {
        	int value = cal.get(Calendar.MONTH);
        	value = value % 3;
        	cal.add(Calendar.MONTH, -value);
        }
        int value = cal.get(fieldcode);
        cal.add(fieldcode, -value+1);
        return cal.getTime();
    }
    
    private static Date calcLastDate(Date date, String field) {
        if (field.equals("DATE")) {
        	return date;
        } else {
        	int fieldcode = (datePart.get(field)).intValue();
        	Calendar cal = new GregorianCalendar();
        	cal.setTime(date);
            if (field.equals("DAY_OF_QUARTER")) {
            	int value = cal.get(Calendar.MONTH);
            	value = value % 3;
            	cal.add(Calendar.MONTH, 2 - value);
            }
        	cal.add(getLast(fieldcode), 1);
        	cal.add(fieldcode, -cal.get(fieldcode));
        	return cal.getTime();
        }
    }
}

