<%@ tag body-content="empty"%>

<%@ attribute name="filter" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="fromDate" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="toDate" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="exclude" type="java.lang.String" required="false" rtexprvalue="true"%>
<%@ attribute name="include" type="java.lang.String" required="false" rtexprvalue="true"%>

<%-- exclude and include are comma separated lists of items that should be
included or excluded. You can specify either a list to include or exclude.
If both attributes are specified only the include list is used. Valid values
for list items are: any, today, yesterday, week, month, year and custom --%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mws" %>
<%@ taglib uri="/WEB-INF/tags/dates.tld" prefix="dt"%>
<%@ taglib uri="/WEB-INF/tags/logic.tld" prefix="lg"%>

<c:choose>
    <c:when test="${!empty(include)}">
        <c:set var="inc" value="inc"/>
    </c:when>
    <c:when test="${!empty(exclude)}">
        <c:set var="inc" value="exc"/>
    </c:when>
    <c:otherwise>
        <c:set var="inc" value="all"/>
    </c:otherwise>
</c:choose>

<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'any')) or (inc eq 'exc' and !lg:contains(exclude, 'any'))}">
			<html:radio property="formFilter[${filter}]" value="false" onclick="setDates('${fromDate}', '${toDate}', '','');"/>&nbsp;<fmt:message key="prompt.any"/>
            <br />
</c:if>
<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'today')) or (inc eq 'exc' and !lg:contains(exclude, 'today'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" onclick="setDates('${fromDate}', '${toDate}', '${dt:getDate()}', '${dt:getDate()}');"/> <fmt:message key="date.today"/> (${dt:getDate()})
            </span>
            <br />
</c:if>
<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'yesterday')) or (inc eq 'exc' and !lg:contains(exclude, 'yesterday'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" onclick="setDates('${fromDate}', '${toDate}', '${dt:getRelativeDate('DATE',-1)}', '${dt:getRelativeDate('DATE',-1)}');"/> <fmt:message key="date.yesterday"/> (${dt:getRelativeDate('DATE',-1)})
            </span>
            <br />
</c:if>
<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'week')) or (inc eq 'exc' and !lg:contains(exclude, 'week'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" onclick="setDates('${fromDate}', '${toDate}', '${dt:getFirstDate('DAY_OF_WEEK')}', '${dt:getLastDate('DAY_OF_WEEK')}');"/> <fmt:message key="date.this.week"/> (${dt:getFirstDate('DAY_OF_WEEK')} - ${dt:getLastDate('DAY_OF_WEEK')})
            </span>
            <br />
</c:if>
<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'last-week')) or (inc eq 'exc' and !lg:contains(exclude, 'last-week'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" onclick="setDates('${fromDate}', '${toDate}', '${dt:getFirstRelativeDate('DAY_OF_WEEK', -1)}', '${dt:getLastRelativeDate('DAY_OF_WEEK', -1)}');"/> <fmt:message key="date.last.week"/> (${dt:getFirstRelativeDate('DAY_OF_WEEK', -1)} - ${dt:getLastRelativeDate('DAY_OF_WEEK', -1)})
            </span>
            <br />
</c:if>
<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'month')) or (inc eq 'exc' and !lg:contains(exclude, 'month'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" onclick="setDates('${fromDate}', '${toDate}', '${dt:getFirstDate('DAY_OF_MONTH')}', '${dt:getLastDate('DAY_OF_MONTH')}');"/> <fmt:message key="date.this.month"/> (${dt:getFirstDate('DAY_OF_MONTH')} - ${dt:getLastDate('DAY_OF_MONTH')})
            </span>
            <br />
</c:if>
<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'last-month')) or (inc eq 'exc' and !lg:contains(exclude, 'last-month'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" onclick="setDates('${fromDate}', '${toDate}', '${dt:getFirstRelativeDate('DAY_OF_MONTH', -1)}', '${dt:getLastRelativeDate('DAY_OF_MONTH', -1)}');"/> <fmt:message key="date.last.month"/> (${dt:getFirstRelativeDate('DAY_OF_MONTH', -1)} - ${dt:getLastRelativeDate('DAY_OF_MONTH', -1)})
            </span>
            <br />
</c:if>
<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'quarter')) or (inc eq 'exc' and !lg:contains(exclude, 'quarter'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" onclick="setDates('${fromDate}', '${toDate}', '${dt:getFirstDate('DAY_OF_QUARTER')}', '${dt:getLastDate('DAY_OF_QUARTER')}');"/> <fmt:message key="date.this.quarter"/> (${dt:getFirstDate('DAY_OF_QUARTER')} - ${dt:getLastDate('DAY_OF_QUARTER')})
            </span>
            <br />
</c:if>
<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'last-quarter')) or (inc eq 'exc' and !lg:contains(exclude, 'last-quarter'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" onclick="setDates('${fromDate}', '${toDate}', '${dt:getFirstRelativeDate('DAY_OF_QUARTER', -1)}', '${dt:getLastRelativeDate('DAY_OF_QUARTER', -1)}');"/> <fmt:message key="date.last.quarter"/> (${dt:getFirstRelativeDate('DAY_OF_QUARTER', -1)} - ${dt:getLastRelativeDate('DAY_OF_QUARTER', -1)})
            </span>
            <br />
</c:if>
<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'year')) or (inc eq 'exc' and !lg:contains(exclude, 'year'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" onclick="setDates('${fromDate}', '${toDate}', '${dt:getFirstDate('DAY_OF_YEAR')}', '${dt:getLastDate('DAY_OF_YEAR')}');"/> <fmt:message key="date.this.year"/> (${dt:getFirstDate('DAY_OF_YEAR')} - ${dt:getLastDate('DAY_OF_YEAR')})
            </span>
            <br />
</c:if>
<c:if test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'last-year')) or (inc eq 'exc' and !lg:contains(exclude, 'last-year'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" onclick="setDates('${fromDate}', '${toDate}', '${dt:getFirstRelativeDate('DAY_OF_YEAR', -1)}', '${dt:getLastRelativeDate('DAY_OF_YEAR', -1)}');"/> <fmt:message key="date.last.year"/> (${dt:getFirstRelativeDate('DAY_OF_YEAR', -1)} - ${dt:getLastRelativeDate('DAY_OF_YEAR', -1)})
            </span>
            <br />
</c:if>
<c:choose>
    <c:when test="${inc eq 'all' or (inc eq 'inc' and lg:contains(include, 'custom')) or (inc eq 'exc' and !lg:contains(exclude, 'custom'))}">
            <span>
                <html:radio property="formFilter[${filter}]" value="true" /><fmt:message key="date.custom"/>
                <mws:dateRange from="${fromDate}" to="${toDate}" format="dd-mmm-yyyy"/><br />
            </span>
    </c:when>
    <c:otherwise>
            <html:hidden styleId="${fromDate}" property="${fromDate}"/>
            <html:hidden styleId="${toDate}" property="${toDate}"/>
    </c:otherwise>
</c:choose>