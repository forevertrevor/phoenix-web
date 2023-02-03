package com.mws.phoenix.web.press;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mws.util.pdf.FileAnnotations;
import com.mws.util.pdf.PDFUtilities;

public class PDFMaker extends Thread {

    private static Map<Long, PDFMaker> users = new HashMap<Long, PDFMaker>();

    public static final int INVALID_ID = 0;
    public static final int ACTIVE = 1;
    public static final int COMPLETED = -1;
    
    private File tempFolder = null;
    private Long userID = null;
    private List<FileAnnotations> articles = null;
    private int count = 0;
    private long completed = 0;
    
    private PDFMaker(File tempFolder, Long userID, List<FileAnnotations> articles) {
        super("PDFMaker for " + userID);
    	this.tempFolder = tempFolder;
        this.userID = userID;
        this.articles = articles;
    }
    
    public static boolean startMaker(File tempFolder, Long userID, List<FileAnnotations> articles) {
        synchronized (users) {
            if (users.containsKey(userID)) {
            	PDFMaker maker = users.get(userID);
            	if (maker.getCompleted() == 0) {
            		return false;
            	} if (System.currentTimeMillis() - maker.getCompleted() > 20000) {
            		// If an existing maker has completed over 20 seconds
            		// ago then it is dropped and the new one started
            		newMaker(tempFolder, userID, articles);
            		return true;
            	} else {
            		return false;
            	}
            } else {
            	newMaker(tempFolder, userID, articles);
                return true;
            }
        }
    }

    private static void newMaker(File tempFolder, Long userID, List<FileAnnotations> articles) {
        PDFMaker maker = new PDFMaker(tempFolder, userID, articles);
        maker.setDaemon(true);
        users.put(userID, maker);
        maker.start();
    }
    
    public static int getStatus(Long userID) {
        synchronized (users) {
            if (!users.containsKey(userID)) {
                return INVALID_ID;
            } else {
                PDFMaker maker = users.get(userID);
                if (maker.isAlive()) {
                    return maker.getCount();
                } else {
                	if (System.currentTimeMillis() - maker.getCompleted() <= 20000) {
                		return COMPLETED;
                	} else {
                		return INVALID_ID;
                	}
                }
            }
        }
    }
    
    public static boolean stopMaker(Long userID) {
        Thread thread = users.get(userID);
        if (thread != null && !thread.isAlive()) {
            users.remove(userID);
            return true;
        } else {
            return false;
        }
    }
    
    public void run() {
        try {
        	PDFUtilities.combineFiles(articles, tempFolder.getAbsolutePath() + "/", userID + "-p.pdf");
            completed = System.currentTimeMillis();
        } catch (Throwable t) {
            t.printStackTrace();
            users.remove(this.userID);
        }
    }
    
    public int getCount() {
    	return ++count;
    }
    public long getCompleted() {
    	return completed;
    }
    
}
