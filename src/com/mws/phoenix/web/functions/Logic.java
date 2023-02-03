package com.mws.phoenix.web.functions;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;

/**
 * Contains various functions for performing logic comparisons and
 * calculations
 * 
 * @author Ed Webb
 * @version 1.0
 * @since 1.0
 */
public class Logic {
    
    /**
     * Executes a logical & (AND) operation on the supplied parameters
     * 
     * @param op1 the first operand
     * @param op2 the second operand
     * @return the string with replaced special characters
     */
    public static int bitAnd(int op1, int op2 ) {
        return (op1 & op2);
    }

    /**
     * Executes a logical | (OR) operation on the supplied parameters
     * 
     * @param op1 the first operand
     * @param op2 the second operand
     * @return the string with replaced special characters
     */
    public static int bitOr(int op1, int op2 ) {
        return (op1 | op2);
    }

    /**
     * Executes a logical ^ (XOR) operation on the supplied parameters
     * 
     * @param op1 the first operand
     * @param op2 the second operand
     * @return the string with replaced special characters
     */
    public static int bitXor(int op1, int op2 ) {
        return (op1 ^ op2);
    }
    
    /**
     * Executes a logical & (AND) operation on the supplied parameters
     * 
     * @param op1 the first operand
     * @param op2 the second operand
     * @return the string with replaced special characters
     */
    public static long bitAnd(long op1, long op2 ) {
        return (op1 & op2);
    }

    /**
     * Executes a logical | (OR) operation on the supplied parameters
     * 
     * @param op1 the first operand
     * @param op2 the second operand
     * @return the string with replaced special characters
     */
    public static long bitOr(long op1, long op2 ) {
        return (op1 | op2);
    }

    /**
     * Executes a logical ^ (XOR) operation on the supplied parameters
     * 
     * @param op1 the first operand
     * @param op2 the second operand
     * @return the string with replaced special characters
     */
    public static long bitXor(long op1, long op2 ) {
        return (op1 ^ op2);
    }

    /**
     * Executes the BitSet's get(bit) method and returns the result
     * 
     * @param set the BitSet to test
     * @param bit the bit to test 
     * @return true if the bit is set
     */
    public static boolean isSet(BitSet set, int bit) {
        return set.get(bit);
    }

    /**
     * Checks through a comma separated list of values and returns true
     * if the item is in the list
     * 
     * @param list the comma separated list of items
     * @param item the item to check for
     * @return true if the item is contained in the list
     */
    public static boolean contains(String list, String item) {
        if (list != null && item != null && !list.equals("")) {
            String items[] = list.split(",");
            for (int i = 0; i < items.length; i++) {
                if (items[i].trim().equals(item)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Checks a string against a regular expression and returns true 
     * if there is a match
     * @param text the text to check
     * @param regex the regular expression
     * @return true if the text matches the regular expression
     */
    public static boolean matches(String text, String regex) {
        return text.matches(regex);
    }
    
    /**
     * Checks a Collection to see if the object is contained within it
     * 
     */
    public static boolean collectionIncludes(Collection<?> collection, Object item) {
        if (collection.contains(item)) {
            return true;
        } else {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (it.next().toString().equals(item.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean arrayIncludes(Object[] array, Object item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].toString().equals(item.toString())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean strStartsWith(String string, String prefix) {
    	return string.startsWith(prefix);
    }

    public static boolean strEndsWith(String string, String suffix) {
    	return string.endsWith(suffix);
    }

    public static boolean strContains(String string, String seq) {
    	return string.contains(seq);
    }

}
