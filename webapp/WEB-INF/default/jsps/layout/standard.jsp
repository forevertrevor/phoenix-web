<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tags/login.tld" prefix="login" %>
<html>
<head>
	<title><tiles:getAsString name="title"/></title>
	<link rel="stylesheet" type="text/css" href='<html:rewrite page="/action/style?property=css"/>' />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
    <div class="internal">
        <tiles:get name="menu" flush="false"/>
	    <tiles:get name="toolbar" flush="false"/>
    	<tiles:get name="info"  flush="false"/>
    	<tiles:get name="footer"  flush="false"/>
    </div>
</body>
</html>
