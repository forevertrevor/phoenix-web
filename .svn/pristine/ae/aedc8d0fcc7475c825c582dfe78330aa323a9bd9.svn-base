<%@ tag body-content="empty"%>

<%@ attribute name="selected" required="true" rtexprvalue="true"%>
<%@ attribute name="loginid" required="true" rtexprvalue="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html:xhtml/>

        <div class="menubar">
            <span <c:if test="${selected eq 'user'}">class="selected"</c:if>>
                <html:link href="admin-user?loginID=${loginid}"><fmt:message key="menubar.user"/></html:link>
            </span>
            <span <c:if test="${selected eq 'alert'}">class="selected"</c:if>>
                <html:link href="admin-alert?alertID=${loginid}"><fmt:message key="menubar.alerts"/></html:link>
            </span>
            <span <c:if test="${selected eq 'finance'}">class="selected"</c:if>>
                <html:link href="admin-finance?financeID=${loginid}"><fmt:message key="menubar.finance"/></html:link>
            </span>
        
            <c:if test="${user.level eq globals.USER_INTERNAL}">
                <span <c:if test="${selected eq 'archive'}">class="selected"</c:if>>
                    <html:link href="admin-archive?archiveID=${loginid}"><fmt:message key="menubar.archive"/></html:link>
                </span>
                <span <c:if test="${selected eq 'press'}">class="selected"</c:if>>
                    <html:link href="admin-user-press?loginID=${loginid}"><fmt:message key="menubar.userbrief"/></html:link>
                </span>
                <%-- 
                <span <c:if test="${selected eq 'eval'}">class="selected"</c:if>>
                    <html:link href="admin-user-eval?loginID=${loginid}"><fmt:message key="menubar.usereval"/></html:link>
                </span>
                --%>
            </c:if>
        </div>
