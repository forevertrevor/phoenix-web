package com.mws.phoenix.web;

import java.util.List;

public interface Searchable {
    /**
     * @param index the index of the serchable criterion
     * @return an array of ids of the checked items
     */
    public String[] getChecked(int index);
    
    /**
     * @param index the index of the serchable criterion
     * @return an array of ids of the unchecked items
     */
    public String[] getUnchecked(int index);
    
    /**
     * @param index the index of the searchable criterion
     * @return an array of names of the selected items
     */
    public String[] getFilterList(int index);
    
    public void setFilterList(int index, String[] list);
    
    /**
     * @param index the index of the searchable criterion
     * @return a List of the found items (ValueObjects)
     */
    public List getFound(int index);
    
    /**
     * @param index the index of the searchable criterion
     * @return a List of the selected items (ValueObjects)
     */
    public List getSelected(int index);
    
    /**
     * @param index the index of the searchable criterion
     * @return the search string entered by the user
     */
    public String getSearch(int index);
}
