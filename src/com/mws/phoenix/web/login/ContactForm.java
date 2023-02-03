package com.mws.phoenix.web.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author EdW
 * @version 
 * @since
 */
public class ContactForm extends ValidatorForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String title = "";
    private String firstName = "";
    private String lastName = "";
    private String jobTitle = "";
    private String company = "";
    private String address1 = "";
    private String address2 = "";
    private String address3 = "";
    private String address4 = "";
    private String county = "";
    private String postcode = "";
    private String telephone = "";
    private String fax = "";
    private String email = "";
    private String message = "";
    
    /**
     * @return Returns the address1.
     */
    public String getAddress1() {
        return address1;
    }
    /**
     * @param address1 The address1 to set.
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    /**
     * @return Returns the address2.
     */
    public String getAddress2() {
        return address2;
    }
    /**
     * @param address2 The address2 to set.
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    /**
     * @return Returns the address3.
     */
    public String getAddress3() {
        return address3;
    }
    /**
     * @param address3 The address3 to set.
     */
    public void setAddress3(String address3) {
        this.address3 = address3;
    }
    /**
     * @return Returns the address4.
     */
    public String getAddress4() {
        return address4;
    }
    /**
     * @param address4 The address4 to set.
     */
    public void setAddress4(String address4) {
        this.address4 = address4;
    }
    /**
     * @return Returns the company.
     */
    public String getCompany() {
        return company;
    }
    /**
     * @param company The company to set.
     */
    public void setCompany(String company) {
        this.company = company;
    }
    /**
     * @return Returns the county.
     */
    public String getCounty() {
        return county;
    }
    /**
     * @param county The county to set.
     */
    public void setCounty(String county) {
        this.county = county;
    }
    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return Returns the fax.
     */
    public String getFax() {
        return fax;
    }
    /**
     * @param fax The fax to set.
     */
    public void setFax(String fax) {
        this.fax = fax;
    }
    /**
     * @return Returns the firstName.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName The firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return Returns the jobTitle.
     */
    public String getJobTitle() {
        return jobTitle;
    }
    /**
     * @param jobTitle The jobTitle to set.
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    /**
     * @return Returns the lastName.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName The lastName to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return Returns the postcode.
     */
    public String getPostcode() {
        return postcode;
    }
    /**
     * @param postcode The postcode to set.
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    /**
     * @return Returns the telephone.
     */
    public String getTelephone() {
        return telephone;
    }
    /**
     * @param telephone The telephone to set.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errs = super.validate(mapping, request);
        request.setAttribute(ContactAction.CONTACT_ERRORS, errs);
        return errs;
    }
}

