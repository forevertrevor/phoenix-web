<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="info">
	<h2><fmt:message key="remind.welcome"/></h2>
	<html:messages name="mediagen.remind.messages" id="msg" header="remind.messages.header" footer="messages.footer">
		<b class="message">${msg}</b>
	</html:messages>
	<html:messages name="mediagen.remind.errors" id="msg" header="remind.errors.header" footer="errors.footer">
		<b class="error">${msg}</b>
	</html:messages>
	<script type="text/javascript">var remindFormValidate = "mediagen.remind."</script>
	<div class="validate" id="mediagen.remind.validate">
	</div>
	<html:form action="/remind" method="post" focus="username">
		<table class="form">
			<tr><th class="caption"><fmt:message key="prompt.username" /></th><td><html:text property="username" size="25" /></td></tr>
			<tr><th class="caption"><fmt:message key="prompt.email" /></th><td><html:text property="email" size="25" /></td></tr>
			<tr><th>&nbsp;</th><td><html:submit><fmt:message key="prompt.remind"/></html:submit></td></tr>
		</table>
	</html:form>
</div>