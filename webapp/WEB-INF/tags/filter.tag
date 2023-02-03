<%@ tag body-content="empty"%>

<%@ attribute name="type" required="true" rtexprvalue="true"%>
<%@ attribute name="index" required="true" rtexprvalue="true"%>
<%@ attribute name="off" required="false" rtexprvalue="true"%>
<%@ attribute name="on" required="false" rtexprvalue="true"%>
<%@ attribute name="restrict" required="false" rtexprvalue="true"%>
<%@ attribute name="styleId" required="false" rtexprvalue="true"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html:xhtml/>
    <c:if test="${empty(restrict) or restrict eq 'on'}">
    	<html:radio property="formFilter[${type}]" value="false" onclick="toggleElements(true, ${index});"/><c:if test="${!empty(off)}">&nbsp;<fmt:message key="${off}"/></c:if>
    </c:if>
    <c:if test="${empty(restrict)}">
    	<br />
  	</c:if>
    <c:if test="${empty(restrict) or restrict eq 'off'}">
        <c:choose>
            <c:when test="${empty(styleId)}">
            	<html:radio property="formFilter[${type}]" value="true" onclick="toggleElements(false, ${index});"/><c:if test="${!empty(on)}">&nbsp;<fmt:message key="${on}"/></c:if>
            </c:when>
            <c:otherwise>
            	<html:radio property="formFilter[${type}]" styleId="${styleId}" value="true" onclick="toggleElements(false, ${index});"/><c:if test="${!empty(on)}">&nbsp;<fmt:message key="${on}"/></c:if>
            </c:otherwise>
        </c:choose>
    </c:if>