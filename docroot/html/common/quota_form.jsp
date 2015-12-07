<%
/**
 * Copyright (c) 2013-Present Liferay Spain User Group All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ include file="/html/common/init.jsp" %>

<%
Quota quota = (Quota) renderRequest.getAttribute("quota");
String editQuotaURL = ParamUtil.getString(request, "editQuotaURL");
String backURL = ParamUtil.getString(request, "backURL", null);
String tabs2 = ParamUtil.getString(request, "tabs2", "");
boolean addUnlimited = ParamUtil.getBoolean(request, "addUnlimited", false);
%>

<aui:form action="<%= editQuotaURL %>" method="post" name="fm">
	<c:if test="<%= backURL != null %>">
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	</c:if>

	<c:if test="<%= tabs2 != null %>">
		<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	</c:if>

	<aui:input name="quotaId" type="hidden" value="<%= quota.getQuotaId() %>" />
	<aui:input name="classPK" type="hidden" value="<%= quota.getClassPK() %>" />

	<aui:input label="enable-quota" name="quotaStatus" type="checkbox" value="<%= quota.isEnabled() %>" />

	<c:if test="${addUnlimited}">
		<aui:input label="quota-unlimited" name="quotaUnlimited" type="checkbox" value="<%= quota.isUnlimitedQuota() %>" />
	</c:if>

	<aui:input label="quota-alert" name="quotaAlert" value="<%= String.valueOf(quota.getQuotaAlert()) %>">
		<aui:validator name="digits" />
	</aui:input>

	<% long quotaAssignedMB = (quota.getQuotaAssigned() / 1024) / 1024; %>

	<aui:input label="quota-assigned" name="quotaAssigned" value="<%= String.valueOf(quotaAssignedMB) %>">
		<aui:validator name="digits" />
	</aui:input>

	<aui:button-row cssClass="button-row">
		<aui:button type="submit" value="update" />
	</aui:button-row>
</aui:form>