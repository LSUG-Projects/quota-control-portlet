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

<%@ include file="/html/common/init.jsp" %>

<% Quota quota = (Quota) renderRequest.getAttribute("quota"); %>

<liferay-ui:error key="assignedQuotaLessThanCurrent" message="assigned-quota-less-than-current" />

<c:if test="<%= quota != null %>">
	<portlet:actionURL var="editURL">
		<portlet:param name="<%= ActionRequest.ACTION_NAME %>" value="updateQuota" />
		<portlet:param name="quotaId" value="<%= String.valueOf(quota.getQuotaId()) %>" />
		<portlet:param name="classPK" value="<%= String.valueOf(quota.getClassPK()) %>" />
	</portlet:actionURL>

	<liferay-util:include page="/html/common/quota_form.jsp" servletContext="<%= getServletContext() %>">
		<liferay-util:param name="editQuotaURL" value="<%= editURL %>" />
	</liferay-util:include>

	<portlet:resourceURL var="siteConfigQuotaURL" />

	<img src="<%= siteConfigQuotaURL %>" />

</c:if>