<%@ tag body-content="empty"%>

<%@ attribute name="from" required="true" rtexprvalue="true"%>
<%@ attribute name="to" required="true" rtexprvalue="true"%>
<%@ attribute name="format" required="true" rtexprvalue="true"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html:xhtml/>
    <fmt:message key="date.between"/>
    <html:text styleId="${from}" property="${from}"/><input id="${from}Button" class="button" type="button" name="cmdPubDateFrom" value="..." onClick="calendar('${from}', '${format}');" />
    <fmt:message key="date.and"/>
    <html:text styleId="${to}" property="${to}"/><input id="${to}Button" class="button" type="button" name="cmdPubDateTo" value="..." onClick="calendar('${to}', '${format}');" />
