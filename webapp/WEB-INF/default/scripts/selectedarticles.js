<%@ page contentType="application/x-javascript" %>

// selectedarticles.js
// JavaScript module to determine which articles have been selected in
// the fraBody Frame

function createList(name) {

	list = "";
	articles = parent.fraBody.document.articles;
	if (null == articles) {
		alert("please return to the news page before selecting " + name);
	} else {
		for (i = 0; i < articles.elements.length ; i++) {
			chkSel = articles.elements[i];
			if(chkSel.checked) {
				if (list != "") {
    				list = list + ",";
	    		}
    			list = list + chkSel.value; 
			}
		}
		document.toolbarForm.list.value = list;
		document.toolbarForm.dispatch.value = name;
		document.toolbarForm.submit();
	}
}
