package com.mws.phoenix.web.press;

/**
 * @author EdW
 * @version 
 * @since
 */
public class SortSectionRelevance {
    private String sortSectionID;
    private String sortSectionName;
    private String relevance;
    
    public SortSectionRelevance() {
        sortSectionID = "0";
        sortSectionName = "";
        relevance = "0";
    }
    
    public SortSectionRelevance(Long sortSectionID, String sortSectionName, Long relevance) {
        this.sortSectionID = sortSectionID.toString();
        this.sortSectionName = sortSectionName;
        this.relevance = relevance.toString();
    }

    public String getSortSectionID() {
        return sortSectionID;
    }
    public void setSortSectionID(String sortSectionID) {
        this.sortSectionID = sortSectionID;
    }
    public String getSortSectionName() {
        return sortSectionName;
    }
    public void setSortSectionName(String sortSectionName) {
        this.sortSectionName = sortSectionName;
    }
    public String getRelevance() {
        return relevance;
    }
    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }
}
