package com.mws.phoenix.web.press;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.mws.phoenix.db.source.SourceCategory;
import com.mws.phoenix.db.source.SourceType;
import com.mws.phoenix.db.web.Login;
import com.mws.phoenix.web.WebUtilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class NewsForm extends ValidatorForm {

    /**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean firstReset = true;
	private String summary = "1";
	private String acct = "0";
	private String datetype = "0";
	private String datefrom = "";
	private String dateto = "";
	private String orderby = "0";
	private boolean orderdesc = false;
	private String sector = "0";
	private String search = "";
    private String action = "";
    private boolean fullText = false;
    private String[] sourcetype = new String[] {"0"};
    private String[] sourcecategory = new String[] {"0"};
    private String sourceTypeString = null;
    private String sourceCategoryString = null;
    
    
    private String pageSize = "25";

	public NewsForm() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
		datefrom = fmt.format(new Date(System.currentTimeMillis()));
		dateto = datefrom;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    if (firstReset) {
            //Set the default values based on the user
	        Login user = (Login)request.getSession().getAttribute("user");
            if (user.getDefaultBrief() != null) {
                acct = user.getDefaultBrief().toString();
            }
            if (user.getDefaultSection() != null) {
                sector = user.getDefaultSection().toString();
            }
            firstReset = false;
        }
        //Do not reset this form as it is stored in Session scope
	    // Except for fullText which is a checkbox
	    fullText = false;
	    sourcetype = new String[0];
	    sourcecategory = new String[0];
	    sourceTypeString = null;
	    sourceCategoryString = null;
	    
	}

	public String getSummary() {
		return this.summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcct() {
		return this.acct;
	}
	public void setAcct(String acct) {
		this.acct = acct;
	}

	public String getSearch() {
		return this.search;
	}
	public void setSearch(String search) {
		this.search = search;
	}

	public String getSector() {
	    return this.sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDatetype() {
		return this.datetype;
	}
	public void setDatetype(String datetype) {
		this.datetype = datetype;
	}

	public String getDatefrom() {
		return this.datefrom;
	}
	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}

	public String getDateto() {
		return this.dateto;
	}
	public void setDateto(String dateto) {
		this.dateto = dateto;
	}

	public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderby() {
		return this.orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

    public String getAction() {
        return this.action;
    }
    public void setAction(String action) {
        this.action = action;
    }

	/**
	 * @return
	 */
	public boolean isOrderdesc() {
		return orderdesc;
	}

	/**
	 * @param b
	 */
	public void setOrderdesc(boolean b) {
		orderdesc = b;
	}

    public boolean isFullText() {
		return fullText;
	}

	public void setFullText(boolean fullText) {
		this.fullText = fullText;
	}
	
	public String[] getSourcecategory() {
		return sourcecategory;
	}

	public void setSourcecategory(String[] sourcecategory) {
		this.sourcecategory = sourcecategory;
	}
	
	public String getSourceCategoryFilter() {
		if (sourceCategoryString == null) {
			sourceCategoryString = buildSourceCategoryFilter();
		}
		return sourceCategoryString;
	}

	/**
	 * Builds a descriptive string of the source types selected.
	 * It also cleans the arrays so that
	 * a. if All is selected then sourcetype contains only a "0" element.
	 * b. if Publication is selected then pubtype is emptied.
	 * @return
	 */
	private String buildSourceCategoryFilter() {
		if ((sourcecategory.length == 0) || (sourcecategory.length > 0 && sourcecategory[0].equals("0"))) {
			sourcecategory = new String[] {"0"};
			return "All Source Categories";
		} else {
			StringBuffer sb = new StringBuffer();
			boolean first = true;
			if (sourcecategory.length > 0) {
				List<SourceCategory> sourceCats = WebUtilities.getInstance().getSourceCategories();
				Iterator<SourceCategory> it = sourceCats.iterator();
				while (it.hasNext()) {
					SourceCategory st = it.next();
					for (int i = 0; i < sourcecategory.length; i++) {
						if (st.getCategoryID().toString().equals(sourcecategory[i])) {
							if (!first) {
								sb.append(", ");
							} else {
								first = false;
							}
							sb.append(st.getCategoryName());
						}
					}
				}
			}
			return sb.toString();
		}
	}
	
	
	/**
	 * Builds a descriptive string of the source types selected.
	 * It also cleans the arrays so that
	 * a. if All is selected then sourcetype contains only a "0" element.
	 * b. if Publication is selected then pubtype is emptied.
	 * @return
	 */
	private String buildSourceTypeFilter() {
		if ((sourcetype.length == 0) || (sourcetype.length > 0 && sourcetype[0].equals("0"))) {
			sourcetype = new String[] {"0"};
			return "All Source Types";
		} else {
			StringBuffer sb = new StringBuffer();
			boolean first = true;
			if (sourcetype.length > 0) {
				List<SourceType> sourceTypes = WebUtilities.getInstance().getSourceTypes();
				Iterator<SourceType> it = sourceTypes.iterator();
				while (it.hasNext()) {
					SourceType st = it.next();
					for (int i = 0; i < sourcetype.length; i++) {
						if (st.getSourceTypeID().toString().equals(sourcetype[i])) {
							if (!first) {
								sb.append(", ");
							} else {
								first = false;
							}
							sb.append(st.getSourceTypeName());
						}
					}
				}
			}
			return sb.toString();
		}
	}

	public String[] getSourcetype() {
		return sourcetype;
	}

	public void setSourcetype(String[] sourcetype) {
		this.sourcetype = sourcetype;
	}
	
	public String getSourceTypeFilter() {
		if (sourceTypeString == null) {
			sourceTypeString = buildSourceTypeFilter();
		}
		return sourceTypeString;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        buildSourceTypeFilter();
		ActionErrors errs = super.validate(mapping, request);
        request.setAttribute(NewsAction.NEWS_ERRORS, errs);
        return errs;
    }
}
