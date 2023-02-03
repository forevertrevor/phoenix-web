<%@ tag body-content="empty"%>

<%@ attribute name="type" required="true" rtexprvalue="true"%>
<%@ attribute name="checked" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="unchecked" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="label" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="value" type="java.lang.String" required="true" rtexprvalue="true"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

				<table>
					<tr>
						<th><fmt:message key="search.search"/></th>
						<td><html:text styleId="searchText${type}" property="search[${type}]" size="25"/></td>
						<td><html:submit styleId="searchSubmit${type}" property="query"><fmt:message key="search.search"/></html:submit></td>
					</tr>
					<tr>
						<th><fmt:message key="search.found"/></th>
						<td>
							<html:select styleId="addSelect${type}" property="${checked}" multiple="multiple" size="10" style="vertical-align: top; width: 20em;">
								<html:optionsCollection property="found[${type}]" label="${label}" value="${value}"/>
							</html:select>
						</td>
						<td><html:submit styleId="addSubmit${type}" property="add"><fmt:message key="search.add"/></html:submit></td>
					</tr>
					<tr>
						<th><fmt:message key="search.selected"/></th>
						<td>
							<html:select styleId="removeSelect${type}" property="${unchecked}" multiple="multiple" size="10" style="vertical-align: top; width: 20em;">
								<html:optionsCollection property="selected[${type}]" label="${label}" value="${value}"/>
							</html:select>
						</td>
						<td><html:submit styleId="removeSubmit${type}" property="remove"><fmt:message key="search.remove"/></html:submit></td>
					</tr>
				</table>
