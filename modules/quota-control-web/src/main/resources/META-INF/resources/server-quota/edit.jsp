<%
/**
 * Copyright (c) 2013-present Liferay Spain User Group All rights reserved.
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
String backURL = ParamUtil.getString(request, "backURL");

long quotaId = ParamUtil.getLong(request, "quotaId");
long classPK = ParamUtil.getLong(request, "classPK");

Quota quotaBean = QuotaLocalServiceUtil.getQuota(quotaId);

renderRequest.setAttribute("quota", quotaBean);
%>

<portlet:actionURL name="saveServerQuota" var="editQuotaURL" />

<liferay-util:include page="/common/quota_form.jsp" servletContext="<%= getServletContext() %>">
	<liferay-util:param name="editQuotaURL" value="<%= editQuotaURL %>" />
	<liferay-util:param name="backURL" value="<%= backURL %>" />
	<liferay-util:param name="addUnlimited" value="<%= Boolean.TRUE.toString() %>" />
</liferay-util:include>