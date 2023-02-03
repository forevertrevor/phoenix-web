package com.mws.phoenix.web.press;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Contains data about a news item
 */

public class ItemForm extends ActionForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	// Clipping related data
	private String id = "0";
    private String headline = "";
    private String specificSummary = "";
    private String articleDate = "";
    private String source = "";
    private String type = "";
    private boolean withdrawn = false;
    private List<SortSectionRelevance> sortSections = new ArrayList<SortSectionRelevance>();
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        id = "0";
        headline = "";
        specificSummary = "";
        articleDate = "";
        source = "";
        type = "";
        withdrawn = false;
        //sortSections.clear();
    }
    
    public String getArticleDate() {
        return articleDate;
    }
    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }
    public String getHeadline() {
        return headline;
    }
    public void setHeadline(String headline) {
        this.headline = headline;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getSpecificSummary() {
        return specificSummary;
    }
    public void setSpecificSummary(String summary) {
        this.specificSummary = summary;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public boolean isWithdrawn() {
		return withdrawn;
	}

	public void setWithdrawn(boolean withdrawn) {
		this.withdrawn = withdrawn;
	}

	public List<SortSectionRelevance> getSortSections() {
        return sortSections;
    }
    public SortSectionRelevance getSortSection(int index) {
        while (sortSections.size() <= index) {
            sortSections.add(new SortSectionRelevance());
        }
        return sortSections.get(index);
    }
    
    public void setSortSections(List<SortSectionRelevance> sortSections) {
        this.sortSections = sortSections;
    }
    
    public void clearSortSections() {
        sortSections.clear();
    }
    public void addSortSection(Long sortSectionID, String sortSectionName, Long sortSectionRelevance) {
        this.sortSections.add(new SortSectionRelevance(sortSectionID, sortSectionName, sortSectionRelevance));
    }
}
