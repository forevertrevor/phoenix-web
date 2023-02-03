<%@ tag body-content="empty"%>

<%@ attribute name="type" required="true" rtexprvalue="true"%>
<%@ attribute name="checked" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="label" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="value" type="java.lang.String" required="true" rtexprvalue="true"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

				<script type="text/javascript">
					function toggle(name) {
						obj = document.getElementById("toggle_${checked}");
						objs = name //document.getElementsByName(name);
						for (i = 0; i < objs.length; i++) {
							objs[i].checked = obj.checked;
						}
					}
				</script>
				<table>
					<tr>
						<th><fmt:message key="search.search"/></th>
						<td><html:text styleId="searchText${type}" property="search[${type}]" size="25"/></td>
						<td><html:submit styleId="searchSubmit${type}" property="query"><fmt:message key="search.search"/></html:submit></td>
					</tr>
					<tr>
						<th><fmt:message key="search.found"/></th>
						<td>
							<c:if test="${!empty reportForm.found[type]}">
								<input type="checkbox" id="toggle_${checked}" name="toggle_${checked}" value="" onclick="toggle(${checked});"/><b>Check All</b><br />
							</c:if>
							<c:forEach var="item" items="${reportForm.found[type]}">
								<c:set var="itemValue"><bean:write name="item" property="${value}"/></c:set>
								<html:multibox property="${checked}" value="${itemValue}"/><bean:write name="item" property="${label}"/><br />
							</c:forEach>
						</td>
						<td></td>
					</tr>
				</table>
