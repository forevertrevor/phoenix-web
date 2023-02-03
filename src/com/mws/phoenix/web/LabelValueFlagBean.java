package com.mws.phoenix.web;

import org.apache.struts.util.LabelValueBean;

/**
 * This bean extends the struts LabelValueBean but allows you to store
 * a true/false flag against a label. This allows you to create lists of
 * selected/undelected or on/off items. For example if you have a list of
 * payment options but only some were valid you could use this bean to flag
 * which payments would be acceptable.
 * 
 * @author Ed Webb
 *
 */
public class LabelValueFlagBean extends LabelValueBean {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean flag = false;

    /**
     * Zero-argument constructor 
     */
    public LabelValueFlagBean() {
        super("","");
    }

    /**
     * Simple LabelValueBean like constructor
     * 
     * @param label the label of the item
     * @param value the value of the item
     */
    public LabelValueFlagBean(String label, String value) {
        super(label, value);
    }

    /**
     * Complex LabelValueFlagBean constructor
     * 
     * @param label the label of the item
     * @param value the first value of the item
     * @param flag the true/false flag of the item
     */
    public LabelValueFlagBean(String label, String value, boolean flag) {
        super(label, value);
        this.flag = flag;
    }

    /**
     * Returns the true/false flag associated with the label 
     * @return the true/false flag associated with the label
     */
    public boolean isFlag() {
        return this.flag;
    }
    /**
     * Sets the true/false flag associated with the label
     * @param flag the true/false flag associated with the label
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("LabelValueFlagBean[");
        sb.append(super.getLabel());
        sb.append(", ");
        sb.append(super.getValue());
        sb.append(", ");
        sb.append(this.flag);
        sb.append("]");
        return (sb.toString());
    }
}
