package com.mws.phoenix.web.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorUtil;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.FieldChecks;
import org.apache.struts.validator.Resources;

/**
 * Custom validator methods for the web application
 * 
 * @author EdW
 */
public class Validate {

    /**
     * 
     * @param bean
     * @param va
     * @param field
     * @param errors
     * @param request
     * @return
     */
    public static boolean validateEmails(Object bean, ValidatorAction va, Field field, ActionErrors errors, HttpServletRequest request) {

        // Get the delimiter for the email addresses
        String delim = field.getVarValue("delim");

        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtil.getValueAsString(bean, field.getProperty());
        }

        if (GenericValidator.isBlankOrNull(value)) {
            errors.add(field.getKey(), Resources.getActionError(request, va, field));
            return false;
        } else {
            StringTokenizer tok = new StringTokenizer(value, delim);
            while (tok.hasMoreTokens()) {
                String token = tok.nextToken();
                if (!FieldChecks.validateEmail(token, va, field, errors, request)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return <code>true</code> if the specified object is a String or a <code>null</code> value.
     * 
     * @param o Object to be tested
     * @return The string value
     */
    protected static boolean isString(Object o) {
        return (o == null) ? true : String.class.isInstance(o);
    }

    public static boolean validateRetype(Object bean, ValidatorAction va, Field field, ActionErrors errors, HttpServletRequest request) {

        String retype = field.getVarValue("retype");

        String value1 = null;
        String value2 = null;
        if (isString(bean)) {
            value1 = (String) bean;
            value2 = (String) bean;
        } else {
            value1 = ValidatorUtil.getValueAsString(bean, field.getProperty());
            value2 = ValidatorUtil.getValueAsString(bean, retype);
        }

        if (!GenericValidator.isBlankOrNull(value1)) {
            try {
                if (!value1.equals(value2)) {
                    errors.add(field.getKey(), Resources.getActionError(request, va, field));
                    return false;
                }
            } catch (Exception e) {
                errors.add(field.getKey(), Resources.getActionError(request, va, field));
                return false;
            }
        }
        return true;
    }

    public static boolean validateDate(Object bean, ValidatorAction va, Field field, ActionErrors errors, HttpServletRequest request) {

        String pattern = field.getVarValue("datePatternStrict");
        if (null == pattern || pattern.equals("")) {
            pattern = field.getVarValue("datePattern");
            if (null == pattern || pattern.equals("")) {
                errors.add(field.getKey(), Resources.getActionError(request, va, field));
                return false;
            }
        }
        
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtil.getValueAsString(bean, field.getProperty());
        }

        if (!GenericValidator.isBlankOrNull(value)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                sdf.setLenient(false);
                try {
                    sdf.parse(value);
                } catch (ParseException e) {
                    errors.add(field.getKey(), Resources.getActionError(request, va, field));
                    return false;
                }
            } catch (Exception e) {
                errors.add(field.getKey(), Resources.getActionError(request, va, field));
                return false;
            }
        }
        return true;
    }
    
}