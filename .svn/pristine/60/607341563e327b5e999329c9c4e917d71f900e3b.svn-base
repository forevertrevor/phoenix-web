package com.mws.phoenix.web.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class for including static content from an absolute URL
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class Including {
    
    public static String include(String strURL) {
        String encoding = null;
        try {
            StringBuffer out = new StringBuffer(); 
            URL url = new URL(strURL); 
            URLConnection con = url.openConnection(); 
            con.connect(); 
            encoding = con.getContentEncoding(); 
            BufferedReader in = null; 
            if (encoding == null) { 
                in = new BufferedReader(new InputStreamReader(url.openStream())); 
            } else { 
                in = new BufferedReader(new InputStreamReader(url.openStream(), encoding)); 
            } 
            char[] buf = new char[4 * 1024]; // 4Kchar buffer 
            int charsRead; 
            while ((charsRead = in.read(buf)) != -1) { 
                out.append(buf,0, charsRead); 
            } 
            return out.toString();
        } catch (MalformedURLException e) {
            return "Unable to include content from " + strURL + " as it is not a valid URL";
        } catch (UnsupportedEncodingException e) {
            return "Unable to include content from " + strURL + " as its encoding '" + encoding + "' is unsupported";
        } catch (IOException e) {
            return "Unable to include content from " + strURL + " as there has been a communication problem";
        } catch (Exception e) {
            return "Unable to include content from " + strURL + " as there has been an unexpected " + e.getMessage();
        }
    }
}
