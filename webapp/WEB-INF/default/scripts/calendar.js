<%@ page contentType="application/x-javascript" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

//	written	by Tan Ling	Wee	on 2 Dec 2001
//	last updated 28 Mar 2002
//	email :	fuushikaden@yahoo.com
//  Edited by MWS to use style sheets and to be internationalized by Struts
//  Edited by MWS to use the iframe shim to draw the calendar over native controls.

	var txtBox;             // The textbox to write the date back to
	var strFmt;             // The format to write the date back to
	var	fixedX = -1;		// x position (-1 if to appear below control)
	var	fixedY = -1;		// y position (-1 if to appear below control)
	var startAt = 0;		// 0 - sunday ; 1 - monday
	var showWeekNumber = 1;	// 0 - don't show; 1 - show
	var showToday = 1;		// 0 - don't show; 1 - show
    var imgDir = "<html:rewrite href="image?resource="/>"; // directory for images ... e.g. var imgDir="/img/"
    
	// Internationalized Strings
	var gotoString = "<fmt:message key="calendar.currentmonth" />";
	var todayString = "<fmt:message key="calendar.today" />";
	var weekString = "<fmt:message key="calendar.week" />";
	var scrollLeftMessage = "<fmt:message key="calendar.lastmonth" /> <fmt:message key="calendar.scroll"/>";
	var scrollRightMessage = "<fmt:message key="calendar.nextmonth" /> <fmt:message key="calendar.scroll" />";
	var selectMonthMessage = "<fmt:message key="calendar.selectmonth"/>";
	var selectYearMessage = "<fmt:message key="calendar.selectyear" />";
	var selectDateMessage = "<fmt:message key="calendar.selectdate" />";
	var monthName = new Array (
		"<fmt:message key="months.long.jan" />",
		"<fmt:message key="months.long.feb" />",
		"<fmt:message key="months.long.mar" />",
		"<fmt:message key="months.long.apr" />",
		"<fmt:message key="months.long.may" />",
		"<fmt:message key="months.long.jun" />",
		"<fmt:message key="months.long.jul" />",
		"<fmt:message key="months.long.aug" />",
		"<fmt:message key="months.long.sep" />",
		"<fmt:message key="months.long.oct" />",
		"<fmt:message key="months.long.nov" />",
		"<fmt:message key="months.long.dec" />"
	);

	var mthName = new Array (
		"<fmt:message key="months.short.jan" />",
		"<fmt:message key="months.short.feb" />",
		"<fmt:message key="months.short.mar" />",
		"<fmt:message key="months.short.apr" />",
		"<fmt:message key="months.short.may" />",
		"<fmt:message key="months.short.jun" />",
		"<fmt:message key="months.short.jul" />",
		"<fmt:message key="months.short.aug" />",
		"<fmt:message key="months.short.sep" />",
		"<fmt:message key="months.short.oct" />",
		"<fmt:message key="months.short.nov" />",
		"<fmt:message key="months.short.dec" />"
	);

	var dayName = new Array (
		"<fmt:message key='days.short.sun' />",
		"<fmt:message key='days.short.mon' />",
		"<fmt:message key='days.short.tue' />",
		"<fmt:message key='days.short.wed' />",
		"<fmt:message key='days.short.thu' />",
		"<fmt:message key='days.short.fri' />",
		"<fmt:message key='days.short.sat' />"
	);

	var	crossobj, crossMonthObj, crossYearObj, monthSelected, yearSelected, dateSelected, omonthSelected, oyearSelected, odateSelected, monthConstructed, yearConstructed, intervalID1, intervalID2, timeoutID1, timeoutID2, ctlToPlaceValue, ctlNow, dateFormat, nStartingYear;

    var iframeobj;

	var	bPageLoaded=false;

	// Browser check variables
	var	ie=document.all;
	var	dom=document.getElementById;
	var	ns4=document.layers;

	// Current date
	var	today =	new	Date();
	var	dateNow	 = today.getDate();
	var	monthNow = today.getMonth();
	var	yearNow	 = today.getYear();
	if (!ns4 && !ie){
		yearNow += 1900
	};
	// Images
	var	imgsrc = new Array("drop1.gif","drop2.gif","left1.gif","left2.gif","right1.gif","right2.gif");
	var	img	= new Array();

	//----------------------------------------------------------------------------------------------
	// Public Holidays
	var HolidaysCounter = 0
	var Holidays = new Array()
	
	//----------------------------------------------------------------------------------------------
	// Public Holiday Record
	function HolidayRec (d, m, y, desc)
	{
		this.d = d
		this.m = m
		this.y = y
		this.desc = desc
	}

	//----------------------------------------------------------------------------------------------
	// Add a Public Holiday to the calendar
	function addHoliday (d, m, y, desc)
	{
		Holidays[HolidaysCounter++] = new HolidayRec ( d, m, y, desc )
	}

	//----------------------------------------------------------------------------------------------
	if (dom)
	{
		for	(i=0;i<imgsrc.length;i++)
		{
			img[i] = new Image
			img[i].src = imgDir + imgsrc[i]
		}
        if (ie) {
    		document.write ("<iframe id='calendariframe' style='display:none; left: 0px; top: 0px; position: absolute' src='javascript:false' frameborder='0' scrolling='no'></iframe>");
    	}
		document.write ("<div class='calendar' id='calendar' style='position:absolute;visibility:hidden;'>");
		document.write ("<table width='250em'>");
		document.write ("<tr><td>");
		document.write ("<table class='header'><tr><td><span id='caption'></span></td><td><a href='javascript:hideCalendar()'><img src='"+imgDir+"close.gif' alt='<fmt:message key="calendar.close"/>' title='<fmt:message key="calendar.close"/>'></a></td></tr></table>");
		document.write ("</td></tr><tr><td><span id='content'></span></td></tr>");
			
		if (showToday==1)
		{
			document.write ("<tr><td class='today'><span id='lblToday'></span></td></tr>")
		}
			
		document.write ("</table></div><div id='selectMonth' style='position:absolute;visibility:hidden;'></div><div id='selectYear' style='position:absolute;visibility:hidden;'></div>");
	}

	function swapImage(srcImg, destImg){
		if (ie)	{ document.getElementById(srcImg).setAttribute("src",imgDir + destImg) }
	}

	function init()	{
		if (!ns4)
		{
			crossobj=(dom)?document.getElementById("calendar").style : ie? document.all.calendar : document.calendar
            crossobj.zIndex = 100;
            if (ie) {
                crossiframeobj = (dom)?document.getElementById("calendariframe").style : ie? document.all.calendar : document.calendar;
                crossiframeobj.zIndex = 99;
            }
			hideCalendar();

			crossMonthObj=(dom)?document.getElementById("selectMonth").style : ie? document.all.selectMonth	: document.selectMonth
            crossMonthObj.zIndex = 101;
			crossYearObj=(dom)?document.getElementById("selectYear").style : ie? document.all.selectYear : document.selectYear
            crossYearObj.zIndex = 101;

			monthConstructed=false;
			yearConstructed=false;

			if (showToday==1)
			{
				document.getElementById("lblToday").innerHTML =	todayString + " <a onmousemove='window.status=\""+gotoString+"\"' onmouseout='window.status=\"\"' title='"+gotoString+"' href='javascript:monthSelected=monthNow;yearSelected=yearNow;constructCalendar();'>"+dayName[(today.getDay()-startAt==-1)?6:(today.getDay()-startAt)]+", " + dateNow + " " + monthName[monthNow].substring(0,3)	+ "	" +	yearNow	+ "</a>"
			}

			sHTML1="<span class='control' id='spanLeft' onmouseover='swapImage(\"changeLeft\",\"left2.gif\");this.className=\"controlHover\";window.status=\""+scrollLeftMessage+"\"' onclick='javascript:decMonth()' onmouseout='clearInterval(intervalID1);swapImage(\"changeLeft\",\"left1.gif\");this.className=\"control\";window.status=\"\"' onmousedown='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"StartDecMonth()\",500)' onmouseup='clearTimeout(timeoutID1);clearInterval(intervalID1)'>&nbsp;<img id='changeLeft' src='"+imgDir+"left1.gif' />&nbsp;</span>"
			sHTML1+="<span class='control' id='spanRight' onmouseover='swapImage(\"changeRight\",\"right2.gif\");this.className=\"controlHover\";window.status=\""+scrollRightMessage+"\"' onmouseout='clearInterval(intervalID1);swapImage(\"changeRight\",\"right1.gif\");this.className=\"control\";window.status=\"\"' onclick='incMonth()' onmousedown='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"StartIncMonth()\",500)' onmouseup='clearTimeout(timeoutID1);clearInterval(intervalID1)'>&nbsp;<img id='changeRight' src='"+imgDir+"right1.gif' />&nbsp;</span>"
			sHTML1+="<span class='control' id='spanMonth' onmouseover='swapImage(\"changeMonth\",\"drop2.gif\");this.className=\"controlHover\";window.status=\""+selectMonthMessage+"\"' onmouseout='swapImage(\"changeMonth\",\"drop1.gif\");this.className=\"control\";window.status=\"\"' onclick='popUpMonth()'></span>"
			sHTML1+="<span class='control' id='spanYear' onmouseover='swapImage(\"changeYear\",\"drop2.gif\");this.className=\"controlHover\";window.status=\""+selectYearMessage+"\"'	onmouseout='swapImage(\"changeYear\",\"drop1.gif\");this.className=\"control\";window.status=\"\"'	onclick='popUpYear()'></span>"
			
			document.getElementById("caption").innerHTML  =	sHTML1

			bPageLoaded=true

			//Calling a calendar function and passing the name of the text 
			//box which holds the date field. Added by D.T.
			popUpCalendar(this, txtBox , strFmt)
		}
	}

	//----------------------------------------------------------------------------------------------
	// Hide the calendar
	function hideCalendar()	{
		crossobj.visibility="hidden"
		if (ie) {
		    crossiframeobj.visibility="hidden";
		    crossiframeobj.display="none";
		}
		if (crossMonthObj != null){crossMonthObj.visibility="hidden"}
		if (crossYearObj !=	null){crossYearObj.visibility="hidden"}
	}

	//----------------------------------------------------------------------------------------------
	// Pad a single digit number with a leading zero
	function padZero(num) {
		return (num	< 10)? '0' + num : num ;
	}

	//----------------------------------------------------------------------------------------------
	// Construct the date into the requested format
	function constructDate(d, m, y)
	{
		sTmp = dateFormat
		sTmp = sTmp.replace	("dd","<e>")
		sTmp = sTmp.replace	("d","<d>")
		sTmp = sTmp.replace	("<e>",padZero(d))
		sTmp = sTmp.replace	("<d>",d)
		sTmp = sTmp.replace	("mmmm","<p>")
		sTmp = sTmp.replace	("mmm","<o>")
		sTmp = sTmp.replace	("mm","<n>")
		sTmp = sTmp.replace	("m","<m>")
		sTmp = sTmp.replace	("<m>",m+1)
		sTmp = sTmp.replace	("<n>",padZero(m+1))
		sTmp = sTmp.replace	("<o>",mthName[m])
		sTmp = sTmp.replace	("<p>",monthName[m])
		return sTmp.replace ("yyyy",y)
	}

	//----------------------------------------------------------------------------------------------
	// Hide the calendar and write the date back into the control
	function closeCalendar() {
		hideCalendar();
		ctlToPlaceValue.value =	constructDate(dateSelected, monthSelected, yearSelected)
	}

	/*** Month Pulldown	***/

	//----------------------------------------------------------------------------------------------
	// Start a timer to decrement the month on mouse down
	function StartDecMonth()
	{
		intervalID1=setInterval("decMonth()",80)
	}

	//----------------------------------------------------------------------------------------------
	// Start a timer to increment the month on mouse up
	function StartIncMonth()
	{
		intervalID1=setInterval("incMonth()",80)
	}

	//----------------------------------------------------------------------------------------------
	// Increment the month
	function incMonth () {
		monthSelected++
		if (monthSelected>11) {
			monthSelected=0
			yearSelected++
		}
		constructCalendar()
	}

	//----------------------------------------------------------------------------------------------
	// Decrement the month
	function decMonth () {
		monthSelected--
		if (monthSelected<0) {
			monthSelected=11
			yearSelected--
		}
		constructCalendar()
	}

	//----------------------------------------------------------------------------------------------
	// Construct the month drop down
	function constructMonth() {
		popDownYear()
		if (!monthConstructed) {
			sHTML =	""
			for	(i=0; i<12;	i++) {
				sName =	monthName[i];
				if (i==monthSelected){
					sName =	"<b>" +	sName +	"</b>"
				}
				sHTML += "<tr><td class='month' id='m" + i + "' onmouseover='this.className=\"monthHover\"' onmouseout='this.className=\"month\"' onclick='monthConstructed=false;monthSelected=" + i + ";constructCalendar();popDownMonth();event.cancelBubble=true'>&nbsp;" + sName + "&nbsp;</td></tr>"
			}

			document.getElementById("selectMonth").innerHTML = "<table width='70' class='month' onmouseover='clearTimeout(timeoutID1)'	onmouseout='clearTimeout(timeoutID1);timeoutID1=setTimeout(\"popDownMonth()\",100);event.cancelBubble=true'>" +	sHTML +	"</table>"

			monthConstructed=true
		}
	}

	//----------------------------------------------------------------------------------------------
	// Display the month drop-down
	function popUpMonth() {
		constructMonth()
		crossMonthObj.visibility = (dom||ie)? "visible"	: "show"
		crossMonthObj.left = (parseInt(crossobj.left) + 70) + "px";
		crossMonthObj.top =	(parseInt(crossobj.top) + 26) + "px";
	}

	//----------------------------------------------------------------------------------------------
	// Hide the month drop-down
	function popDownMonth()	{
		crossMonthObj.visibility= "hidden"
	}

	/*** Year Pulldown ***/

	//----------------------------------------------------------------------------------------------
	// Increment the year and update the year drop-down list
	function incYear() {
		for	(i = 0; i < 7; i++){
			newYear	= (i + nStartingYear) + 1
			if (newYear == yearSelected)
			{ txtYear =	"&nbsp;<b>"	+ newYear +	"</b>&nbsp;" }
			else
			{ txtYear =	"&nbsp;" + newYear + "&nbsp;" }
			document.getElementById("y" + i).innerHTML = txtYear
		}
		nStartingYear ++;
	}

	//----------------------------------------------------------------------------------------------
	// Decrement the year and update the year drop-down list
	function decYear() {
		for	(i = 0; i < 7; i++){
			newYear	= (i + nStartingYear) - 1
			if (newYear == yearSelected)
			{ txtYear =	"&nbsp;<b>"	+ newYear +	"</b>&nbsp;" }
			else
			{ txtYear =	"&nbsp;" + newYear + "&nbsp;" }
			document.getElementById("y" + i).innerHTML = txtYear
		}
		nStartingYear --;
	}

	//----------------------------------------------------------------------------------------------
	// Select the year
	function selectYear(nYear) {
		yearSelected = parseInt(nYear + nStartingYear);
		yearConstructed = false;
		constructCalendar();
		popDownYear();
	}

	//----------------------------------------------------------------------------------------------
	// Construct the Year drop-down
	function constructYear() {
		popDownMonth();
		sHTML =	"";
		if (!yearConstructed) {
			sHTML =	"<tr><td class='year' onmouseover='this.className=\"yearHover\"' onmouseout='clearInterval(intervalID1);this.className=\"year\"' onmousedown='clearInterval(intervalID1);intervalID1=setInterval(\"decYear()\",30)' onmouseup='clearInterval(intervalID1)'>-</td></tr>"
			j =	0
			nStartingYear =	yearSelected-3
			for	(i=(yearSelected-3); i<=(yearSelected+3); i++) {
				sName =	i;
				if (i==yearSelected){
					sName =	"<b>" +	sName +	"</b>"
				}

				sHTML += "<tr><td class='year' id='y" + j + "' onmouseover='this.className=\"yearHover\"' onmouseout='this.className=\"year\"' onclick='selectYear("+j+");event.cancelBubble=true'>&nbsp;" + sName + "&nbsp;</td></tr>"
				j ++;
			}

			sHTML += "<tr><td class='year' onmouseover='this.className=\"yearHover\"' onmouseout='clearInterval(intervalID2);this.className=\"year\"' onmousedown='clearInterval(intervalID2);intervalID2=setInterval(\"incYear()\",30)' onmouseup='clearInterval(intervalID2)'>+</td></tr>"

			document.getElementById("selectYear").innerHTML	= "<table class='year' onmouseover='clearTimeout(timeoutID2)' onmouseout='clearTimeout(timeoutID2);timeoutID2=setTimeout(\"popDownYear()\",100)'>" + sHTML + "</table>"

			yearConstructed	= true
		}
	}

	//----------------------------------------------------------------------------------------------
	// Hide the year drop-down
	function popDownYear() {
		clearInterval(intervalID1);
		clearTimeout(timeoutID1);
		clearInterval(intervalID2);
		clearTimeout(timeoutID2);
		crossYearObj.visibility = "hidden";
	}

	//----------------------------------------------------------------------------------------------
	// Show the year drop-down
	function popUpYear() {
		var	leftOffset;

		constructYear();
		crossYearObj.visibility	= (dom||ie)? "visible" : "show";
		leftOffset = parseInt(crossobj.left) + document.getElementById("spanYear").offsetLeft;
		if (ie) {
			leftOffset += 6;
		}
		crossYearObj.left =	leftOffset + "px";
		crossYearObj.top = (parseInt(crossobj.top) + 26) + "px";
	}

	/*** calendar ***/

	//----------------------------------------------------------------------------------------------
	// Week number
	function WeekNbr(today) {
		Year = takeYear(today);
		Month = today.getMonth();
		Day = today.getDate();
		now = Date.UTC(Year,Month,Day+1,0,0,0);
		var Firstday = new Date();
		Firstday.setYear(Year);
		Firstday.setMonth(0);
		Firstday.setDate(1);
		then = Date.UTC(Year,0,1,0,0,0);
		var Compensation = Firstday.getDay();
		if (Compensation > 3) Compensation -= 4;
		else Compensation += 3;
		NumberOfWeek =  Math.round((((now-then)/86400000)+Compensation)/7);
		return NumberOfWeek;
	}

	function takeYear(theDate)
	{
		x = theDate.getYear();
		var y = x % 100;
		y += (y < 38) ? 2000 : 1900;
		return y;
	}


	function constructCalendar () {
		var aNumDays = Array (31,0,31,30,31,30,31,31,30,31,30,31)

		var dateMessage
		var	startDate =	new	Date (yearSelected,monthSelected,1)
		var endDate

		if (monthSelected==1)
		{
			endDate	= new Date (yearSelected,monthSelected+1,1);
			endDate	= new Date (endDate	- (24*60*60*1000));
			numDaysInMonth = endDate.getDate()
		}
		else
		{
			numDaysInMonth = aNumDays[monthSelected];
		}

		datePointer	= 0
		dayPointer = startDate.getDay() - startAt
		
		if (dayPointer<0)
		{
			dayPointer = 6
		}

		sHTML =	"<table	class='calendar'><tr>"

		if (showWeekNumber==1) {
			sHTML += "<th class='week'>" + weekString + "</th><th class='divider' rowspan='7'>&nbsp;</th>"
		}

		for	(i=0; i<7; i++)	{
			sHTML += "<th class='dow'>"+ dayName[i]+"</th>"
		}
		sHTML +="</tr><tr>"
		
		if (showWeekNumber==1) {
			sHTML += "<td class='week'>" + WeekNbr(startDate) + "</td>"
		}

		for	( var i=1; i<=dayPointer;i++ ) {
			sHTML += "<td class='dow'>&nbsp;</td>"
		}
	
		for	( datePointer=1; datePointer<=numDaysInMonth; datePointer++ ) {
			dayPointer++;
			sHTML += "<td class='dow'>"
			sClass="date";
			if ((datePointer==odateSelected) &&	(monthSelected==omonthSelected)	&& (yearSelected==oyearSelected)) { 
				sClass+=" selected";
			}

			sHint = ""
			for (k=0;k<HolidaysCounter;k++) {
				if ((parseInt(Holidays[k].d)==datePointer)&&(parseInt(Holidays[k].m)==(monthSelected+1))) {
					if ((parseInt(Holidays[k].y)==0)||((parseInt(Holidays[k].y)==yearSelected)&&(parseInt(Holidays[k].y)!=0))) {
						sClass+=" holiday";
						sHint += sHint == "" ? Holidays[k].desc : "\n"+Holidays[k].desc
					}
				}
			}

			if ((datePointer==dateNow)&&(monthSelected==monthNow)&&(yearSelected==yearNow)){
				sClass+=" today";
			}

			if (dayPointer % 7 == (startAt * -1)+1 || (dayPointer + 1) % 7 == (startAt * -1) + 1) {
				sClass+=" weekend";
			}
			var regexp= /\"/g;
			sHint = sHint.replace(regexp,"&quot;");

			dateMessage = "onmousemove='window.status=\""+selectDateMessage.replace("{0}",constructDate(datePointer,monthSelected,yearSelected))+"\"' onmouseout='window.status=\"\"' "

			sHTML += "<a class='" + sClass + "' "+dateMessage+" title=\"" + sHint + "\" href='javascript:dateSelected="+datePointer+";closeCalendar();'>" + datePointer + "</a>";

			if ((dayPointer+startAt) % 7 == startAt) { 
				sHTML += "</tr><tr>" 
				if ((showWeekNumber==1)&&(datePointer<numDaysInMonth))
				{
					sHTML += "<td class='week'>" + (WeekNbr(new Date(yearSelected,monthSelected,datePointer+1))) + "</td>"
				}
			}
		}

		document.getElementById("content").innerHTML   = sHTML
		document.getElementById("spanMonth").innerHTML = monthName[monthSelected] + "&nbsp;<img id='changeMonth' src='"+imgDir+"drop1.gif' />"
		document.getElementById("spanYear").innerHTML =	yearSelected + "&nbsp;<img id='changeYear' src='"+imgDir+"drop1.gif' />"
	}

	function popUpCalendar(ctl,	ctl2, format) {
		var	leftpos=0
		var	toppos=0

		if (bPageLoaded)
		{
			if ( crossobj.visibility ==	"hidden" ) {
				ctlToPlaceValue	= ctl2
				dateFormat=format;

				formatChar = " "
				aFormat	= dateFormat.split(formatChar)
				if (aFormat.length<3)
				{
					formatChar = "/"
					aFormat	= dateFormat.split(formatChar)
					if (aFormat.length<3)
					{
						formatChar = "."
						aFormat	= dateFormat.split(formatChar)
						if (aFormat.length<3)
						{
							formatChar = "-"
							aFormat	= dateFormat.split(formatChar)
							if (aFormat.length<3)
							{
								// invalid date	format
								formatChar=""
							}
						}
					}
				}

				tokensChanged =	0
				if ( formatChar	!= "" )
				{
					// use user's date
					aData =	ctl2.value.split(formatChar)

					for	(i=0;i<3;i++)
					{
						if ((aFormat[i]=="d") || (aFormat[i]=="dd"))
						{
							dateSelected = parseInt(aData[i], 10)
							tokensChanged ++
						}
						else if	((aFormat[i]=="m") || (aFormat[i]=="mm"))
						{
							monthSelected =	parseInt(aData[i], 10) - 1
							tokensChanged ++
						}
						else if	(aFormat[i]=="yyyy")
						{
							yearSelected = parseInt(aData[i], 10)
							tokensChanged ++
						}
						else if	(aFormat[i]=="mmm")
						{
							for	(j=0; j<12;	j++)
							{
								if (aData[i]==mthName[j])
								{
									monthSelected=j
									tokensChanged ++
								}
							}
						}
						else if	(aFormat[i]=="mmmm")
						{
							for	(j=0; j<12;	j++)
							{
								if (aData[i]==monthName[j])
								{
									monthSelected=j
									tokensChanged ++
								}
							}
						}
					}
				}

				if ((tokensChanged!=3)||isNaN(dateSelected)||isNaN(monthSelected)||isNaN(yearSelected))
				{
					dateSelected = dateNow
					monthSelected =	monthNow
					yearSelected = yearNow
				}

				odateSelected=dateSelected
				omonthSelected=monthSelected
				oyearSelected=yearSelected

				var lp = ctl2;
				var top = 0;
				var left = 0;
				while(lp != null) {
					top += lp.offsetTop;
					left += lp.offsetLeft;
					lp = lp.offsetParent;
				}

				crossobj.left =	fixedX==-1 ? left + "px" :	fixedX
				crossobj.top = fixedY==-1 ?	(top + ctl2.offsetHeight + 2) + "px" :	fixedY
				
				//Setting left and top of the calendar in a popUp window. Added by D.T.

				constructCalendar (1, monthSelected, yearSelected);
				crossobj.visibility=(dom||ie)? "visible" : "show"

				//setting the size of the iframe
                if (ie) {
                    var cal = (dom)?document.getElementById("calendar") : ie? document.all.calendar : document.calendar
                    crossiframeobj.width = cal.offsetWidth;
                    crossiframeobj.height = cal.offsetHeight;
                    crossiframeobj.top = crossobj.top;
                    crossiframeobj.left = crossobj.left;
                    crossiframeobj.visibility = "visible";
                    crossiframeobj.display = "block";
                }

			}
			else
			{
				hideCalendar()
				if (ctlNow!=ctl) {popUpCalendar(ctl, ctl2, format)}
			}
			ctlNow = ctl
		}
	}

	function calendar(dest, format) {
		txtBox = document.getElementById(dest);
		strFmt = format;
		init();
	}
