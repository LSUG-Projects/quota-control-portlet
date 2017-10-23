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


<%@ include file="/common/init.jsp" %>

<%
final String tabs2 = ParamUtil.getString(request, "tabs2", "sites");

final long quotaId = ParamUtil.getLong(request, "quotaId");
final Quota quota = QuotaLocalServiceUtil.getQuota(quotaId);

renderRequest.setAttribute("quota", quota);
%>

<portlet:actionURL var="editURL">
	<portlet:param name="<%= ActionRequest.ACTION_NAME %>" value="updateQuota" />
	<portlet:param name="quotaId" value="<%= String.valueOf(quotaId) %>" />
	<portlet:param name="classPK" value="<%= String.valueOf(quota.getClassPK()) %>" />
</portlet:actionURL>

<liferay-util:include page="/common/quota_form.jsp" servletContext="<%= getServletContext() %>">
	<liferay-util:param name="editQuotaURL" value="<%= editURL %>" />
	<liferay-util:param name="tabs2" value="<%= tabs2 %>" />
</liferay-util:include>