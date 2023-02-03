<%@ page contentType="application/x-javascript" %>
<!-- Begin

std = (document.getElementById) ? true : false;

function popUp(evt, tipid) {
    if (std) {
        pop = document.getElementById(tipid).style;
    } else {
        pop = document.eval("document." + tipid);
    }
    
    if (document.all) {
        pop.pixelTop = parseInt(evt.y) + 10;
        pop.pixelLeft = parseInt(evt.x) + 25;
    } else if (std) {
        pop.top = (parseInt(evt.pageY) + 10) + "px";
        pop.left = (parseInt(evt.pageX) + 25) + "px";
    } else {
        pop.top = parseInt(evt.pageY) + 10;
        pop.left = parseInt(evt.pageX) + 25;
    }
    pop.visibility = "visible";
}

function popDown(tipid) {
    if (std) {
        pop = document.getElementById(tipid).style;
    } else {
        pop = document.eval("document." + tipid);
    }

    pop.visibility = "hidden";    
}    
-->