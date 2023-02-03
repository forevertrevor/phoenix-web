package com.mws.phoenix.db.web;

import javax.swing.Icon;

import com.mws.db.ValueObject;
import com.mws.util.ResourceUtilities;


/**
 * @author Ed Webb
 * @version
 * @since
 */
public class LoginStyle extends ValueObject {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long styleID;
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
    protected Boolean pageNumber = Boolean.FALSE;
    protected Boolean summary = Boolean.FALSE;
   
    // Value Object metadata implementation
    public static final String className = "Style";
    public static final String[] propertyNames = {"Style ID", "Style Name", "Home", "News", "Style Sheet", "Folder", "Email Summary Template", "Email Article Template", "Print Summary Template", "Print Article Template", "Evaluation", "Archive", "Branding ID", "Page Numbering", "Summary"};
    public static final String[] properties = {"styleID", "styleName", "home", "news", "css", "folder", "emailSummary", "emailArticle", "printSummary", "printArticle", "evaluation", "archive", "brandingID", "pageNumber", "summary"};
    public static final Class<?>[] propertyClasses = {Long.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Boolean.class, Boolean.class};
    public static final int[] propertySizes = {0, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 10, 0, 0};
    public static final Icon icon = ResourceUtilities.getIcon(LoginStyle.class, "login-style-16x16.gif");
    public String getObjectName() {
        return className;
    }

    public String[] getPropertyNames() {
        return propertyNames;
    }

    public String[] getProperties() {
        return properties;
    }

    public Class<?>[] getPropertyClasses() {
        return propertyClasses;
    }

    public int[] getPropertySizes() {
        return propertySizes;
    }

    public Long getID() {
        return styleID;
    }
    
    public Icon getIcon() {
    	return icon;
    }

    public String toString() {
        return styleName;
    }
    
    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append(styleID);
        sb.append(" : ");
        sb.append(styleName);
        sb.append(", ");
        sb.append(folder);
        sb.append(", ");
        sb.append(css);
        sb.append(", ");
        sb.append(home);
        sb.append(", ");
        sb.append(news);
        return sb.toString();
    }
    
    /**
     * @return Returns the templateID.
     */
    public Long getStyleID() {
        return styleID;
    }

    /**
     * @param templateID The templateID to set.
     */
    public void setStyleID(Long styleID) {
        this.styleID = styleID;
    }
    /**
     * @return Returns the css.
     */
    public String getCss() {
        return css;
    }
    /**
     * @param css The css to set.
     */
    public void setCss(String css) {
        this.css = css;
    }
    /**
     * @return Returns the folder.
     */
    public String getFolder() {
        return folder;
    }
    /**
     * @param folder The folder to set.
     */
    public void setFolder(String folder) {
        this.folder = folder;
    }
    /**
     * @return Returns the home.
     */
    public String getHome() {
        return home;
    }
    /**
     * @param home The home to set.
     */
    public void setHome(String home) {
        this.home = home;
    }
    /**
     * @return Returns the news.
     */
    public String getNews() {
        return news;
    }
    /**
     * @param news The news to set.
     */
    public void setNews(String news) {
        this.news = news;
    }
    /**
     * @return Returns the styleName.
     */
    public String getStyleName() {
        return styleName;
    }
    /**
     * @param styleName The styleName to set.
     */
    public void setStyleName(String styleName) {
        this.styleName = styleName;
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
    public String getPrintSummary() {
        return printSummary;
    }
    public void setPrintSummary(String printSummary) {
        this.printSummary = printSummary;
    }
    
    public String getPrintArticle() {
		return printArticle;
	}

	public void setPrintArticle(String printArticle) {
		this.printArticle = printArticle;
	}

	public String getEvaluation() {
        return evaluation;
    }
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getArchive() {
        return archive;
    }
    public void setArchive(String archive) {
        this.archive = archive;
    }

    public String getBrandingID() {
        return brandingID;
    }

    public void setBrandingID(String brandingID) {
        this.brandingID = brandingID;
    }

	public Boolean getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Boolean pageNumbering) {
		this.pageNumber = pageNumbering;
	}

	public Boolean getSummary() {
		return summary;
	}

	public void setSummary(Boolean summary) {
		this.summary = summary;
	}

}
