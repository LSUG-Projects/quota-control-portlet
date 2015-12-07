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

<% long classNameId = PortalUtil.getClassNameId(User.class);

Quota quota = QuotaLocalServiceUtil.fetchQuotaByClassNameIdClassPK(classNameId, themeDisplay.getUser().getGroupId());
%>

<c:if test="<%= quota != null && quota.isEnabled() %>">

	<portlet:resourceURL var="userOwnQuotaURL" />

	<img src="<%= userOwnQuotaURL %>" />

</c:if>
<c:if test="<%= quota == null || !quota.isEnabled() %>">
	<liferay-ui:message key="user-site-no-quota-enabled" />
</c:if>