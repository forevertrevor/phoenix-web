<%@ page contentType="application/x-javascript" %>

// disableform.js

// Script to enable/disable an arbitrary number of form elements

// Array of arrays containing sets of controls
// The first element in an array is the _name_ of the radio buttons
// Subsequent elements are the _id_ of form elements that are 
// controlled by the radio buttons. If the id is prefixed with a minus 
// sign ( - ) then that control is _always_ disabled.
var sets;

/**
 * Function that enables or disables a set of controls
 * blnDisable - true to disable the controls, false to enable
 * index - the index of the set's ids in the sets global variable
 */
function toggleElements(blnDisabled, index) {
	for (var i = 1; i < sets[index].length; i++) {
		var id = sets[index][i];
		var disable = false;
		if (id.indexOf("-") == 0) {
		    disable = true;
		    id = id.substr(1);
		}
		var element = document.getElementById(id);
		element.className = blnDisabled || disable ? "disabled" : "";
		element.disabled = blnDisabled || disable;
	}
}

/**
 * Function that initialises the control sets to the correct 
 * enabled/disabled status. This must be called before the user
 * is able to toggle radio buttons
 */ 
function initControls(varSets) {
    // Store the control sets
    sets = varSets;
    
    // TODO allow selection of form
    var frm = document.forms[0];

    // Iterate through all the controls on the form
    for (var i = 0; i < frm.elements.length; i++) {
        var element = frm.elements[i];

        // We need to check the control if it is a radio button and checked
        if (element.type == "radio" && element.checked) {
            for (var s = 0; s < sets.length; s++) {
                // If the name of the radio button matches the first element of a set array
                // then we need to enable or disable its dependent controls
                if (sets[s][0] == element.name) {
                    toggleElements(element.value == "false" ? true : false, s);
                }
            }
        }
    }
}

/**
 * Function that clears checks from a group of checkboxes identified by name
 * name - the name of the controls to clear
 */
function clearAll(name) {
    var boxes = document.getElementsByName(name);
    for (i = 0; i < boxes.length; i++) {
        boxes[i].checked = false;
    }
}

/**
 * Function that checks the radio button in a group that has value = true.
 * name = the name of the controls to check
 */ 
function setTrue(name) {
    var boxes = document.getElementsByName(name);
    for (i = 0; i < boxes.length; i++) {
        if (boxes[i].value == false) {
            boxes[i].checked = false;
        } else {
            boxes[i].checked = true;
        }
    }
}        
 
 
/**
 * Function that sets the value of two textboxes or hidden controls to the
 * dates passed to the function
 * propertyFrom - the id of the control that will contain the from date
 * propertyTo - the id of the control that will contain the to date
 * fromDate - the from date
 * toDate - the to date
 */
function setDates(propertyFrom, propertyTo, fromDate, toDate) {
    var fromBox = document.getElementById(propertyFrom);
    fromBox.value = fromDate;
    var toBox = document.getElementById(propertyTo);
    toBox.value = toDate;
}


/**
 * Function that allows only one of a group of checkboxes to be 
 * checked (simulates a radio button).
 * strName - the name of the checkbox control
 * value - the value of the checkbox being checked or unchecked
 * values - an array of values of the other checkboxes in the group
 */
function checkRadio(strId, value, values) {
    objThese = document.getElementsByName(strId);
    for (i = 0; i <objThese.length; i++) {
        if (objThese[i].value != value) {
            for (j = 0; j < values.length; j++) {
                if (objThese[i].value == values[j]) {
                    objThese[i].checked = false;
                }
            }
        }
    }
}
 
 
/**
 * Function that selects or deselects all checkboxes with the same name based
 * on the status of a master checkbox.
 * strName - the name of the checkboxes
 * strMaster - the id of the master checkbox
 */
function toggleCheckboxes(strName, strMaster) {
    objMaster = document.getElementById(strMaster);
    blnChecked = objMaster.checked;
    objBoxes = document.getElementsByName(strName);
    for (i = 0; i < objBoxes.length; i++) {
        objBoxes[i].checked = blnChecked;
    }
}