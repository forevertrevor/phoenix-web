package com.mws.phoenix.web.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class LoginForm extends ValidatorForm {

    /**
	 * The version ID 
	 */
	private static final long serialVersionUID = 1L;
	
private String username = "";
  private String password = "";

  /* (non-Javadoc)
   * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    username = "";
    password = "";
  }

  public void setUsername(String username) {
    this.username = username;
  }
  public String getUsername() {
    return username;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  public String getPassword() {
    return password;
  }
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errs = super.validate(mapping, request);
        request.setAttribute(LoginAction.LOGIN_ERRORS, errs);
        return errs;
    }
}