<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="ru.runa.common.web.form.IdForm" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/wf.tld" prefix="wf" %>

<tiles:insert page="/WEB-INF/af/main_layout.jsp" flush="true">
	<tiles:put name="body" type="string">
<%
	long botStationId = Long.parseLong(request.getParameter("botStationId"));
	String saveActionUrl = "save_bot_station.do?id=" + botStationId;
	String createActionUrl = "add_bot.do?botStationId=" + botStationId;
%>
        <wf:botStationTag botStationId="<%= botStationId %>"/>
        <table width="100%">
            <tr>
                <td align="left"><wf:saveBotStationLink href="<%= saveActionUrl %>" /></td>
            </tr>
        </table>
        <wf:botStationStatusTag botStationId="<%= botStationId %>"/>
        <wf:deployBot botStationId="<%= botStationId %>"/>
        <wf:botListTag botStationId="<%= botStationId %>">
			<table width="100%">
                <tr>
                    <td align="left"><wf:createBotLink href="<%= createActionUrl %>"/></td>
                </tr>
            </table>
        </wf:botListTag>
    </tiles:put>
    <tiles:put name="messages" value="../common/messages.jsp"/>
</tiles:insert>