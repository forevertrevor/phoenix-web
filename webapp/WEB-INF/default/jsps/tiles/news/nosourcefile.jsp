<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<html>
<head>
	<title><fmt:message key="nosourcefile.title"/></title>
	<link rel="stylesheet" type="text/css" href='<html:rewrite href="/action/style?property=css"/>' />
</head>
<body>
    <div class="internal display plain">
	
		<h2><fmt:message key="nosourcefile.heading"/></h2>
		<p><fmt:message key="nosourcefile.paragraph"/></p>
		<html:messages id="msg" message="false" header="errors.header" footer="errors.footer">
			<b class="error">${msg}</b>
		</html:messages>
		<html:messages id="msg" message="true" header="messages.header" footer="messages.footer">
			<b class="message">${msg}</b>
		</html:messages>
		<div class="validate" name="validate">
		</div>
	</div>
</body>
</html>