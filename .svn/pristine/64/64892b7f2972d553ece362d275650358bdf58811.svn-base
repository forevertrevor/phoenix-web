package com.mws.phoenix.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

/**
 * Converter for converting String dates to Dates
 * 
 * @author Ed Webb
 */
public class DateConverter implements Converter {

    /* (non-Javadoc)
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
     */
    public Object convert(Class type, Object value) {
        if (value instanceof String) {
            DateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = null;
            try {
                date = fmt.parse((String)value);
            } catch (ParseException e) { }
            return date;
        }
        return null;
    }

}
