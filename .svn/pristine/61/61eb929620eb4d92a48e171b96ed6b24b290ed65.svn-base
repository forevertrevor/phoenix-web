package com.mws.phoenix.web.exceptions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;


/**
 * Creates an ActionMessage from the Exception's message and arguments and 
 * places it in the request's ActionErrors collection.
 * 
 * @author Ed Webb
 */
public class ParameterisableExceptionHandler extends ExceptionHandler {

    /* (non-Javadoc)
     * @see org.apache.struts.action.ExceptionHandler#execute(java.lang.Exception, org.apache.struts.config.ExceptionConfig, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward execute(Exception e, ExceptionConfig config, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ActionErrors errs;

        ParameterisableException pe = (ParameterisableException)e;
        
        if (null == request.getAttribute(Globals.ERROR_KEY)) {
            errs = new ActionErrors();
        } else {
            errs = (ActionErrors)request.getAttribute(Globals.ERROR_KEY);
        }

        errs.add(Globals.ERROR_KEY, new ActionMessage(pe.getMessage(), pe.getArguments()));
        request.setAttribute(Globals.ERROR_KEY, errs);

        return new ActionForward(config.getPath());
    }

}