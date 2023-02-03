package com.mws.phoenix.web.admin;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.brief.Brief;
import com.mws.phoenix.db.web.Login;

public class UserForm extends ValidatorForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;

	protected List<Brief> briefs = new ArrayList<Brief>();
    
    protected String loginID = "0";
    protected String loginName = "";
    protected String password = "";
    protected String userName = "";
    protected String fax = "";
    protected String email = "";
    protected String level = "0";
    protected String maxLogins = "0";
    protected String curLogins = "0";
    protected String retype = "";
    protected String groupID = "";
    protected String styleID = "";
    protected String nlaUserID = "";
    protected String nlaPassword = "";
    protected String defaultBrief = "";
    protected String defaultSection = "";
    protected String timeout = "15";
    protected boolean attachment = false;
    protected boolean restrictNI = false;
    protected String[] controls;
    protected String[] client;
    protected String[] brief;
    
    protected String[] all;
    protected String[] broadcastCompany;
    protected String[] productionCompany;
    protected String[] genre;

    // Search and Select variables
    //private String[] search = new String[Evaluation.FIELD_COUNT]; 
    //private List[] selected = new ArrayList[Evaluation.FIELD_COUNT]; 
    //private List[] found = new ArrayList[Evaluation.FIELD_COUNT];
    //private String[][] checked = new String[Evaluation.FIELD_COUNT][];
    //private String[][] unchecked = new String[Evaluation.FIELD_COUNT][];
    
    protected String action = "";
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        briefs = new ArrayList<Brief>();
        loginID = "0";
        loginName = "";
        password = "";
        userName = "";
        fax = "";
        email = "";
        level = "0";
        maxLogins = "0";
        curLogins = "0";
        retype = "";
        action = "";
        controls = new String[0];
        brief = new String[0];
        client = new String[0];
        all = new String[0];
        broadcastCompany = new String[0];
        productionCompany = new String[0];
        genre = new String[0];

        //found[Evaluation.PROGRAMME] = new ArrayList();
        //selected[Evaluation.PROGRAMME] = new ArrayList();
        groupID = "";
        styleID = "";
        nlaUserID = "";
        nlaPassword = "";
        defaultBrief = "";
        defaultSection = "";
        timeout = "15";
        attachment = false;
        restrictNI = false;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (action.equals("")) {
            // Form has not yet been sent to client
            return null;
        } else {
            // Form has been returned from client
            ActionErrors errs = super.validate(mapping, request);

            if (!loginName.equals("") && loginID.equals("")) {
                try {
                    Login login = (Login)DataStore.store().getObject(Login.class, "loginName", loginName);
                    if (login != null) {
                        errs.add("", new ActionMessage("errors.unique", "Login Name", "Login", loginName));
                    }
                } catch (DataStoreException e) {
                    e.printStackTrace();
                }
            }
            
            request.setAttribute(UserAction.USER_ERRORS, errs);
            return errs;
        }
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
     * @return Returns the level.
     */
    public String getLevel() {
        return level;
    }
    /**
     * @param level The level to set.
     */
    public void setLevel(String level) {
        this.level = level;
    }
    /**
     * @return Returns the loginID.
     */
    public String getLoginID() {
        return loginID;
    }
    /**
     * @param loginID The loginID to set.
     */
    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }
    /**
     * @return Returns the loginName.
     */
    public String getLoginName() {
        return loginName;
    }
    /**
     * @param loginName The loginName to set.
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    /**
     * @return Returns the maxLogins.
     */
    public String getMaxLogins() {
        return maxLogins;
    }
    /**
     * @param maxLogins The maxLogins to set.
     */
    public void setMaxLogins(String maxLogins) {
        this.maxLogins = maxLogins;
    }
    /**
     * @return Returns the maxLogins.
     */
    public String getCurLogins() {
        return curLogins;
    }
    /**
     * @param maxLogins The maxLogins to set.
     */
    public void setCurLogins(String curLogins) {
        this.curLogins = curLogins;
    }
    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return Returns the retype.
     */
    public String getRetype() {
        return retype;
    }
    /**
     * @param retype The retype to set.
     */
    public void setRetype(String retype) {
        this.retype = retype;
    }
    /**
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public String[] getBrief() {
        return brief;
    }

    public void setBrief(String[] brief) {
        this.brief = brief;
    }

    public String[] getClient() {
        return client;
    }

    public void setClient(String[] client) {
        this.client = client;
    }

    public String[] getAll() {
        return all;
    }

    public void setAll(String[] all) {
        this.all = all;
    }

    public String[] getBroadcastCompany() {
        return broadcastCompany;
    }

    public void setBroadcastCompany(String[] broadcastCompany) {
        this.broadcastCompany = broadcastCompany;
    }

    public String[] getGenre() {
        return genre;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public String[] getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String[] productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    // Search Properties
    /*public List getFound(int index) {
        return found[index];
    }
    public void setFound(int index, List found) {
        this.found[index] = found;
    }
    public List[] getFound() {
        return found;
    }
    
    public String getSearch(int index) {
        return search[index];
    }
    public void setSearch(int index, String search) {
        this.search[index] = search;
    }
    public List getSelected(int index) {
        return selected[index];
    }
    public void setSelected(int index, List selected) {
        this.selected[index] = selected;
    }
    public String[] getChecked(int index) {
        return checked[index];
    }
    public void setChecked(int index, String[] checked) {
        this.checked[index] = checked;
    }
    public String[] getUnchecked(int index) {
        return unchecked[index];
    }
    public void setUnchecked(int index, String[] unchecked) {
        this.unchecked[index] = unchecked;
    }
    public String[] getProgrammeChecked() {
        return this.checked[Evaluation.PROGRAMME];
    }
    public void setProgrammeChecked(String[] programme) {
        this.checked[Evaluation.PROGRAMME] = programme;
    }
    public String[] getProgrammeUnchecked() {
        return this.unchecked[Evaluation.PROGRAMME];
    }
    public void setProgrammeUnchecked(String[] programme) {
        this.unchecked[Evaluation.PROGRAMME] = programme;
    }
    */
    public String getDefaultBrief() {
        return defaultBrief;
    }

    public void setDefaultBrief(String defaultBrief) {
        this.defaultBrief = defaultBrief;
    }

    public String getDefaultSection() {
        return defaultSection;
    }

    public void setDefaultSection(String defaultSection) {
        this.defaultSection = defaultSection;
    }

    public String getNlaUserID() {
        return nlaUserID;
    }

    public void setNlaUserID(String nlaUserID) {
        this.nlaUserID = nlaUserID;
    }

    public String getNlaPassword() {
        return nlaPassword;
    }

    public void setNlaPassword(String nlaPassword) {
        this.nlaPassword = nlaPassword;
    }

    public String getStyleID() {
        return styleID;
    }

    public void setStyleID(String styleID) {
        this.styleID = styleID;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String[] getControlArray() {
        return controls;
    }
    
    public BitSet getControls() {
        BitSet result = new BitSet();
        for (int i = 0; i < controls.length; i++) {
            result.set(Integer.parseInt(controls[i]));
        }
        return result;
    }

    public void setControlArray(String[] controls) {
        this.controls = controls;
    }

    public void setControls(BitSet controls) {
        this.controls = new String[controls.cardinality()];
        for (int i = controls.nextSetBit(0), j = 0; i >= 0; i = controls.nextSetBit(i+1), j++) {
            this.controls[j] = Integer.toString(i);
        }
    }

    public List<Brief> getBriefList() {
        return briefs;
    }

    public void setBriefList(List<Brief> briefs) {
        this.briefs = briefs;
    }

	public boolean isAttachment() {
		return attachment;
	}

	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}

	public boolean isRestrictNI() {
		return restrictNI;
	}

	public void setRestrictNI(boolean restrictNI) {
		this.restrictNI = restrictNI;
	}
}
