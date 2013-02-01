<%@page import="org.lsug.quota.service.QuotaLocalServiceUtil"%>
<%
	/**
	 * Copyright (c) 2013 Liferay Spain User Group All rights reserved.
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

<%@ include file="/html/sites-quota/init.jsp"%>

<%
	long quotaId = ParamUtil.getLong(request, "quotaId");
	Quota quota = QuotaLocalServiceUtil.getQuota(quotaId);
%>

<portlet:actionURL var="editURL">
	<portlet:param name="<%=ActionRequest.ACTION_NAME%>" value="updateQuota" />
	<portlet:param name="quotaId" value="<%=String.valueOf(quotaId)%>" />
	<portlet:param name="classPK" value="<%=String.valueOf(quota.getClassPK())%>" />
</portlet:actionURL>

<aui:form action="<%=editURL%>" method="post" name="fm">

	<aui:column cssClass="edit-column">
		<aui:input label="quota-status" name="quotaStatus"
			value="<%=quota.getQuotaStatus()%>" />
	</aui:column>
	<aui:column cssClass="edit-column">
		<aui:input label="quota-alert" name="quotaAlert"
			value="<%=quota.getQuotaAlert()%>" />
	</aui:column>
	<aui:column cssClass="edit-column">
		<aui:input label="quota-assigned" name="quotaAssigned"
			value="<%=quota.getQuotaAssigned()%>" />
	</aui:column>
	<aui:column cssClass="edit-column">
		<aui:input label="quota-used" name="quotaUsed"
			value="<%=quota.getQuotaUsed()%>" />
	</aui:column>
	<aui:button-row cssClass="button-row">
		<aui:button type="submit" value="update" />
	</aui:button-row>
</aui:form>