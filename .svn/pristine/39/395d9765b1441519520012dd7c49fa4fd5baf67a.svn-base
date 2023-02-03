<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<html:html locale="true" xhtml="true">
	<head>
		<title><fmt:message key="fail.title" /></title>
		<link rel="stylesheet" type="text/css" href="/welcome/styles/style.css" />
        <html:base/>
    </head>
	<body>
		<div class="welcome fail">
			<h1><fmt:message key="fail.title" /></h1>
    	    <p><fmt:message key="fail.message" /></p>
			<html:messages id="msg"  header="errors.header" footer="errors.footer" message="false">
				<b class="error">${msg}</b><br />
			</html:messages>
			<html:messages id="msg"  header="messages.header" footer="messages.footer" message="true">
				<b class="error">${msg}</b><br />
			</html:messages>
		</div>
    </body>
</html:html>