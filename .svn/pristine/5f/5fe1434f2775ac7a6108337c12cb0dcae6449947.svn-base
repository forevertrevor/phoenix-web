package com.mws.phoenix.web;

import org.apache.struts.util.LabelValueBean;

/**
 * This bean extends the struts LabelValueBean but allows you to store
 * two values against one label. This allows you to create lists of
 * items in a two level heirarchy. For example if you have a list of
 * accounts and each account has a number of transactions you can store
 * the name of the transaction, the transaction id and also the account id.
 * This then allows you to easily access all of an accounts transactions 
 * or an individual transaction. 
 * 
 * @author Ed Webb
 *
 */
public class LabelValueValueBean extends LabelValueBean {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String value2 = null;

    /**
     * Zero-argument constructor 
     */
    public LabelValueValueBean() {
        super("","");
    }

    /**
     * Simple LabelValueBean like constructor
     * 
     * @param label the label of the item
     * @param value the value of the item
     */
    public LabelValueValueBean(String label, String value) {
        super(label, value);
    }

    /**
     * Complex LabelValueValueBean constructor
     * 
     * @param label the label of the item
     * @param value1 the first value of the item
     * @param value2 the second value of the item
     */
    public LabelValueValueBean(String label, String value1, String value2) {
        super(label, value1);
        this.value2 = value2;
    }

    /**
     * Returns the second value associated with the label 
     * @return the second value associated with this label
     */
    public String getValue2() {
        return this.value2;
    }
    /**
     * Sets the second value associated with this label
     * @param value2 the second value associated with this label
     */
    public void setValue2(String value2) {
        this.value2 = value2;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("LabelValueValueBean[");
        sb.append(super.getLabel());
        sb.append(", ");
        sb.append(super.getValue());
        sb.append(", ");
        sb.append(this.value2);
        sb.append("]");
        return (sb.toString());
    }
}
