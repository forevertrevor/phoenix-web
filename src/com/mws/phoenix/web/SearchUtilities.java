package com.mws.phoenix.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.db.ValueObject;

public class SearchUtilities {

    public static void searchFor(int type, Searchable form, Class clazz, List field, List comp, List value, String orderBy) {
        List found = form.getFound(type);
        String[] term = form.getSearch(type).split(";");

        List list = null;
        String t = "";
        try {
            found.clear();
            for (int i = 0; i < term.length; i++) {
                t = term[i].trim();
                t = t.replaceAll("\\*", "%");
                t = t.replaceAll("\\?", "_");
                if (t.indexOf("%") < 0 && t.indexOf("_") < 0) {
                    t = "%" + t + "%";
                }
                value.set(0, t);
                list = DataStore.store().getObjects(clazz, (String[]) field.toArray(new String[field.size()]), (String[]) comp.toArray(new String[comp.size()]), value.toArray(), orderBy);
                found.addAll(list);
            }
        } catch (DataStoreException e) {
            //TODO catch block
            e.printStackTrace();
        }
    }
    
    public static void removeUnchecked(int type, Searchable form) {
        List selected = form.getSelected(type);
        String[] unchecked = form.getUnchecked(type);
        List found = form.getFound(type);
        String[] filter = form.getFilterList(type);
        
        //Remove the unchecked items from the selected list and
        //add them to the found list
        Iterator it = selected.iterator();
        while (it.hasNext()) {
            ValueObject vo = (ValueObject)it.next();
            for (int i = 0; i < unchecked.length; i++) {
                if (vo.getID().toString().equals(unchecked[i])) {
                    found.add(vo);
                    it.remove();
                    break;
                }
            }
        }

        //remove the unchecked ids from the filter list
        Set combined = new HashSet(Arrays.asList(filter));
        combined.removeAll(Arrays.asList(unchecked));
        form.setFilterList(type, (String[])combined.toArray(new String[combined.size()]));
    }

    public static void addChecked(int type, Searchable form) {
        List found = form.getFound(type);
        String[] checked = form.getChecked(type);
        List selected = form.getSelected(type);
        String[] filter = form.getFilterList(type);

        Iterator it = found.iterator();
        while (it.hasNext()) {
            ValueObject vo = (ValueObject)it.next();
            for (int i = 0; i < checked.length; i++) {
                if (vo.getID().toString().equals(checked[i])) {
                    selected.add(vo);
                    it.remove();
                    break;
                }
            }
        }

        Set combined = new HashSet(Arrays.asList(filter));
        combined.addAll(Arrays.asList(checked));
        form.setFilterList(type, (String[])combined.toArray(new String[combined.size()]));
    }

}
