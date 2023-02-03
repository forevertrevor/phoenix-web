<%@ page contentType="application/x-javascript" %>

// forms.js
// JavaScript module for form elements

sectors = new Array();

function toggleCheck(strCheck) {
	chkThis = document.getElementById(strCheck);
	chkThis.checked = !chkThis.checked;
}

function toggleRadio(strRadio) {
	optThis = document.getElementById(strRadio);
	if (!optThis.checked) {
		optThis.checked = true;
	}
}

function selectAll(strId) {
	intId = 0;
	objThis = document.getElementById(strId + intId);
	blnCheck = objThis.checked;
	while (objThis != null)	{
		intId++;
		objThis = document.getElementById(strId + intId);
		if (objThis != null) {
			objThis.checked = blnCheck;
		}
	}
}

function copyText(strFrom, strTo) {
	var txtFrom = document.getElementById(strFrom);
	var txtTo = document.getElementById(strTo);
	txtTo.value = txtFrom.value;
}


    
