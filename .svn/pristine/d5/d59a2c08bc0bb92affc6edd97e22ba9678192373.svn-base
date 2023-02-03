<%@ tag body-content="empty"%>

<%@ attribute name="property" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="optionBean" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="optionProperty" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="optionLabel" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="optionValue" type="java.lang.String" required="true" rtexprvalue="true"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

	<html:select styleId="${property}" property="${property}" multiple="multiple" size="10" style="vertical-align: top; width: 20em;">
		<html:optionsCollection name="${optionBean}" property="${optionProperty}" label="${optionLabel}" value="${optionValue}"/>
	</html:select>
