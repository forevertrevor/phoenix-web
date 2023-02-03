package com.mws.phoenix.web.functions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains various formatting functions for displaying
 * data in a browser.
 * 
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class Formatting {
    
    private static final Pattern content = Pattern.compile("\\[([\\S].?)](.*?)\\[(/.*?)]"); 
    private static final Pattern empty = Pattern.compile("\\[([\\S].*?\\s/)]"); 
    private static final String replaceContent = "<$1>$2<$3>";
    private static final String replaceEmpty = "<$1>";

    /**
     * replaces HTML special characters with the correct entity
     * 
     * replaces "simple" html tags (those without attributes) marked up
     * with [ and ] as opening and closing marks with < and > closing
     * marks.
     * 
     * e.g.
     * 
     *  [b]This is bold[/b] [i] [br /] [a href="http://www.nastysite.com"]Click Here[/a]
     *  
     *  becomes:
     * 
     *  <b>This is bold</b> [i] <br /> [a href="http://www.nastysite.com"]Click Here[/a]
     *  
     * @param string the text to be escaped
     * @return the string with replaced special characters
     */
    public static String htmlEscape(String string) {
        string = string.replaceAll("&", "&amp;");
        string = string.replaceAll("\"", "&quot;");
        string = string.replaceAll("<", "&lt;");
        string = string.replaceAll(">", "&gt;");
        Matcher match = null;
        match = content.matcher(string);
        string = match.replaceAll(replaceContent); 
        match = empty.matcher(string);
        string = match.replaceAll(replaceEmpty); 
        return string;
    }

}
