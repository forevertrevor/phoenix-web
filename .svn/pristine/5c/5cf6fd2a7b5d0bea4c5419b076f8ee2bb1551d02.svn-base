package com.mws.phoenix.web.functions;

import java.util.List;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;

/**
 * 
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class Query {
    
    public static List<?> executeQuery(String query) {
        try {
            return DataStore.store().getObjects(query);
        } catch (DataStoreException e) {
            return null;
        }
    }
    
}