<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/wf.tld" prefix="wf" %>
<tiles:insert page="/WEB-INF/af/main_layout.jsp" flush="true">

<tiles:put name="body" type="string" >
<%
	String idParameter = request.getParameter("id");
	long id = 0;
	if (idParameter != null) {
		id = Long.parseLong(idParameter);
	}
	String actorIdParameter = request.getParameter("actorId");
	long actorId = 0;
	if (actorIdParameter != null) {
		actorId = Long.parseLong(actorIdParameter);
	}
	boolean terminator = "true".equals(request.getParameter("terminator"));
%>
	<script type="text/javascript" src="<html:rewrite page="/substitution.js" />"></script>
	<wf:updateSubstitutionForm identifiableId="<%= id %>" terminator="<%= terminator %>" actorId="<%= actorId %>" />
</tiles:put>
<tiles:put name="messages" value="../common/messages.jsp" />
</tiles:insert>