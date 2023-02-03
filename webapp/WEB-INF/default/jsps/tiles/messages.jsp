<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html:xhtml/>
		

<%--
		<%@ page import="java.util.Enumeration" %>
		<%
			Enumeration enum = request.getAttributeNames();
			while(enum.hasMoreElements()) {
				String key = (String)enum.nextElement();
				out.println("<hr /><i>" + key + "</i><br />");
				Object obj = request.getAttribute(key);
				if (null == obj) {
					out.println("null");
				} else {
					out.println(obj.toString());
				}
			}
		%>
--%>
		<html:messages id="msg" message="true" header="info.header" footer="info.footer">
			<b class="message">${msg}</b>
		</html:messages>
		<html:messages id="msg" message="false" header="errors.header" footer="errors.footer">
			<b class="error">${msg}</b>
		</html:messages>
		<div class="validate" name="validate">
		</div>