<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html:xhtml/>
<script language="javascript" src="/phoenix/welcome/scripts/validate.js" type="text/javascript"></script>
<html:javascript formName="loginForm" cdata="false" staticJavascript="false" />
<div class="box">
	<h2>Login</h2>
	<html:messages name="mediagen.login.messages" id="msg" header="login.messages.header" footer="messages.footer">
		<b class="message">${msg}</b>
	</html:messages>
	<html:messages name="mediagen.login.errors" id="msg" header="login.errors.header" footer="errors.footer">
		<b class="error">${msg}</b>
	</html:messages>
	<script type="text/javascript">var loginFormValidate = "mediagen.login."</script>
	<div class="validate" id="mediagen.login.validate">
	</div>
    <html:form action="/login" method="post" focus="username" onsubmit="return validateLoginForm(this);">
	    <fmt:message key="login.username" /><br />
		   <html:text property="username" size="24" /><br />
    	<fmt:message key="login.password" /><br />
	    <html:password property="password" size="24" redisplay="false" /><br /><br />
        <html:submit><fmt:message key="prompt.login"/></html:submit><br />
	</html:form>
	<html:link href="/action/welcome?page=remind" styleClass="nolink"><fmt:message key="remind.welcome" /></html:link>
</div>
