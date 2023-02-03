package com.mws.phoenix.web.press;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

import com.mws.db.DataStore;
import com.mws.db.DataStoreException;
import com.mws.phoenix.db.brief.Brief;
import com.mws.phoenix.db.brief.SortSection;
import com.mws.phoenix.db.despatch.Hit;
import com.mws.phoenix.db.despatch.HitSortSection;
import com.mws.phoenix.db.web.Login;

/**
 *
 *
 * @author Ed Webb
 */
public class ToolForm extends ValidatorActionForm {

	/**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private String list = "";
	private String dispatch = "";
	
    private String fromName = "";
    private String toName = "";
    private String fromAddress = "";
    private String toAddress = "";
    private String company = "";
    private String subject = "";
    private String message = "";  
    private String maxItems = "20";
    private String maxSize = "5";
    private boolean combinedPDF = true;
    
    private boolean newsForm = false;
    private String brief = "0";
    private String section = "0";
	private String datetype = "0";
	private String datefrom = "";
	private String dateto = "";
	private String search = "";
    
    private boolean selectForm = false;
    private String select = "";
    
	private List<Hit> hits = new ArrayList<Hit>();
    private List<HitSortSection> hss = new ArrayList<HitSortSection>();
    
    private String action = "";

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        list = "";
        dispatch = "";
        fromName = "";
        toName = "";
        fromAddress = "";
        toAddress = "";
        company = "";
        subject = "";
        message = "";
        action = "";
        maxSize = "5";
        maxItems = "20";
        action = "";
        combinedPDF = true;

        newsForm = false;
        brief = "0";
        section = "0";
        datetype = "0";
        datefrom = "";
        dateto = "";
        search = "";

        selectForm = false;
        select = "";
    }

    /**
     * @param request
     * @param selectedForm
     * @param user
     */
    void buildHits(HttpServletRequest request) {
        HttpSession session = request.getSession();
    	SelectedForm selectedForm = (SelectedForm)session.getAttribute("selectedForm");
        Login user = (Login)session.getAttribute("user");
        String query = null;
        boolean nonSelected = false;
       	String bookmark = (String)session.getAttribute("bookmark");
        String mode = "/news";
        if (bookmark == null) {
        	mode = "/news";
        } else if (bookmark.startsWith("/action/news")) {
       		mode = "/news";
       	} else if (bookmark.startsWith("/action/archive")) {
       		mode = "/archive";
       	} else if (bookmark.startsWith("/action/folder")) {
       		mode = "/folder";
       	}
        
        if (newsForm) {
           	System.out.println("Using new NewsForm");
        	NewsForm form = new NewsForm();
            form.setAcct(brief);
            form.setSector(section);
            form.setDatetype(datetype);
            form.setDatefrom(datefrom);
            form.setDateto(dateto);
            form.setSearch(search);
            System.out.println(section);
            query = ItemsHelper.buildQuery(user, form, "/news", "hit");
            nonSelected = true;
        } else if (selectForm) {
        	System.out.println("Using new SelectForm");
        	SelectedForm form = new SelectedForm();
        	form.setSelected(select.split(","));
        	session.setAttribute("selectedForm", form);
        	query = buildSelectQuery(form, user);
        } else if (selectedForm == null || selectedForm.getSelectedSet().size() == 0) {
           	System.out.println("Using existing NewsForm");
           	// Select all articles based on NewsForm
           	NewsForm form = null;
           	if (mode.equals("/news")) {
           		form = (NewsForm)session.getAttribute("newsForm");
           	} else if (mode.equals("/archive")) {
           		form = (NewsForm)session.getAttribute("archiveForm");
           	} else if (mode.equals("/folder")) {
           		form = (NewsForm)session.getAttribute("folderForm");
           	} else {
           		// Default to news form
           		// FIXME what if the home is not news?
           		form = (NewsForm)session.getAttribute("newsForm");
           	}
            query = ItemsHelper.buildQuery(user, form, mode, "hit");
            nonSelected = true;
        } else {
            // Select all articles based on SelectedForm
            query = buildSelectQuery(selectedForm, user);
        }
        if (query != null && !query.equals("")) {
            try {
                List<Hit> temp = DataStore.store().getObjects(query);
                temp = ItemsHelper.sortHits(temp, mode, session, request);
                createLists(temp, nonSelected, mode);
            } catch (DataStoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

	private String buildSelectQuery(SelectedForm selectedForm, Login user) {
		String[] selected = selectedForm.getSelected();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < selected.length; i++) {
		    if(i > 0) {
		        sb.append(",");
		    }
		    sb.append(selected[i]);
		}
		if (sb.length() > 0) {
		    return "select distinct hit from Login as login inner join login.briefAccess as brief, Hit hit where login.loginID = " + user.getLoginID() + " and hit.hitID in (" + sb.toString() + ") and hit.brief.briefID = brief.briefID";
		} else {
			return "";
		}
	}
    
    /**
     * Create a list of all relevant HitSortSections (from the Hit itself
     * and from its lead hit (if one exists)), sorts them into the display
     * order then creates a correctly ordered List of Hit objects.
     * 
     * @param hits the List of selected Hits
     */
    private void createLists(List<Hit> data, boolean nonSelected, String mode) {
        Iterator<Hit> it = data.iterator();
        hss.clear();
        hits.clear();

        //Set temp = new HashSet();
        if (mode.equals("/folder")) {
    	   Brief b = new Brief();
    	   b.setBriefName("Selected Articles");
    	   b.setBriefCode("FOLDER");
    	   b.setBriefID(new Long(0));
    	   SortSection ss = new SortSection();
    	   ss.setSortSectionName(" ");
    	   ss.setSortSectionOrder(new Long(0));
    	   ss.setSortSectionID(new Long(0));
    	   ss.setBrief(b);
           while (it.hasNext()) {
               Hit hit = it.next();
               hit.setBrief(b);
               HitSortSection fake = new HitSortSection();
               fake.setHit(hit);
               fake.setSortSection(ss);
               fake.setRelevance(new Long(5));
               fake.setHitSortSectionID(new Long(0));
               hss.add(fake);
           }
        } else {
        
        	while (it.hasNext()) {
            	Hit hit = it.next();
            	if (hit.getLeadArticle() != null) {
                	Iterator<HitSortSection> leads = hit.getLeadArticle().getHitSortSections().iterator();
                	while (leads.hasNext()) {
                    	HitSortSection lead = leads.next();
                    	HitSortSection fake = new HitSortSection();
                    	fake.setSortSection(lead.getSortSection());
                    	fake.setHit(hit);
                    	String summary = lead.getHit().getSummary();
                    	if (summary == null || summary.equals("") ) {
                        	summary = lead.getHit().getArticle().getSummary();
                    	}
                    	hit.setSummary(summary);
                    	fake.setRelevance(lead.getRelevance());
                    	fake.setHitSortSectionID(lead.getHitSortSectionID());
                    	hss.add(fake);
                	}
            	} 
            	hss.addAll(hit.getHitSortSections());
        	}
        	Collections.sort(hss);
        }
        
        Set<Hit> temp = new LinkedHashSet<Hit>();
        Iterator<HitSortSection> xit = hss.iterator();
        while (xit.hasNext()) {
            HitSortSection hs = xit.next();
            Hit hit = hs.getHit();
            if (data.contains(hit)) {
                temp.add(hit);
            }
            if (hit.getAlsoMentioned()!= null) {
                Iterator<Hit> alsos = hit.getAlsoMentioned().iterator();
                while (alsos.hasNext()) {
                    Hit also = alsos.next();
                    if (data.contains(also) || nonSelected) {
                        temp.add(also);
                    }
                }
            }
        }
        hits.addAll(temp);
    }
    
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		if (!"".equals(this.action)) {
			ActionErrors errs = super.validate(mapping, request);
            request.setAttribute(ToolAction.TOOL_ERRORS, errs);
			return errs;
		} else {
			return null;
		}
	}
    
	public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    
    public List<Hit> getHits() {
        return hits;
    }
    
    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }
    
    public List<HitSortSection> getHitSortSections() {
        return hss;
    }
    public void setHitSortSections(List<HitSortSection> hss) {
        this.hss = hss;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getDispatch() {
        return dispatch;
    }
    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }
    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getFromName() {
        return fromName;
    }
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
    public String getList() {
        return list;
    }
    public void setList(String list) {
        this.list = list;
    }
    public String getMaxItems() {
        return maxItems;
    }
    public void setMaxItems(String maxItems) {
        this.maxItems = maxItems;
    }
    public String getMaxSize() {
        return maxSize;
    }
    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getToAddress() {
        return toAddress;
    }
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    public String getToName() {
        return toName;
    }
    public void setToName(String toName) {
        this.toName = toName;
    }

    public boolean isCombinedPDF() {
        return combinedPDF;
    }
    public void setCombinedPDF(boolean combinedPDF) {
        this.combinedPDF = combinedPDF;
    }
    
    public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}

	public String getDateto() {
		return dateto;
	}

	public void setDateto(String dateto) {
		this.dateto = dateto;
	}

	public String getDatetype() {
		return datetype;
	}

	public void setDatetype(String datetype) {
		this.datetype = datetype;
	}

	public boolean isNewsForm() {
		return newsForm;
	}

	public void setNewsForm(boolean newsForm) {
		this.newsForm = newsForm;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public boolean isSelectForm() {
		return selectForm;
	}

	public void setSelectForm(boolean selectForm) {
		this.selectForm = selectForm;
	}
}
