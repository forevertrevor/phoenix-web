package com.mws.phoenix.web.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;


/**
 * Holds information about a Login Style
 * 
 * @author Ed Webb
 */
public class StyleForm extends ValidatorForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected String styleID;
    protected String styleName;
    protected String home;
    protected String news;
    protected String css;
    protected String folder;
    protected String emailSummary;
    protected String emailArticle;
    protected String printSummary;
    protected String printArticle;
    protected String evaluation;
    protected String archive;
    protected String brandingID;
    protected boolean pageNumber;
    protected boolean summary;
    
    private String action = "";

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        action = "";
        styleID = "0";
        styleName = "";
        folder = "default/";
        home = "/action/news";
        news = "default.news";
        evaluation = "default.home";
        archive = "default.archive";
        css = "style.css";
        emailSummary = "emailsummary.xsl";
        emailArticle = "emailarticle.xsl";
        printSummary = "printsummary.xsl";
        printArticle = "printarticle.xsl";
        brandingID = "M1";
        pageNumber = false;
        summary = false;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getEmailArticle() {
        return emailArticle;
    }

    public void setEmailArticle(String emailArticle) {
        this.emailArticle = emailArticle;
    }

    public String getEmailSummary() {
        return emailSummary;
    }

    public void setEmailSummary(String emailSummary) {
        this.emailSummary = emailSummary;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getPrintSummary() {
        return printSummary;
    }

    public void setPrintSummary(String printSummary) {
        this.printSummary = printSummary;
    }

    public String getStyleID() {
        return styleID;
    }

    public void setStyleID(String styleID) {
        this.styleID = styleID;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (this.getAction().equals("")) {
            // Form has not yet been sent to client
            return null;
        } else {
            ActionErrors errs = super.validate(mapping, request);
            request.setAttribute(StyleAction.STYLE_ERRORS, errs);
            return errs;
        }
    }

    public String getBrandingID() {
        return brandingID;
    }

    public void setBrandingID(String brandingID) {
        this.brandingID = brandingID;
    }

	public boolean isPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(boolean pageNumber) {
		this.pageNumber = pageNumber;
	}

	public boolean isSummary() {
		return summary;
	}

	public void setSummary(boolean summary) {
		this.summary = summary;
	}

	public String getPrintArticle() {
		return printArticle;
	}

	public void setPrintArticle(String printArticle) {
		this.printArticle = printArticle;
	}

}
