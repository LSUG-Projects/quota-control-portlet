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
String orderByCol = ParamUtil.getString(request, "orderByCol", "quotaUsed");
String orderByType = ParamUtil.getString(request, "orderByType", "desc");

Quota companyQuota = QuotaUtil.getCompanyQuota(companyId);
%>
<liferay-ui:message key="sites-quota-description" />
<br/><br/>
niON | niOFF
<c:if test="<%= companyQuota != null %>">
	<liferay-ui:message key="used-space" />
	<%= companyQuota.getQuotaUsed() %>
	<liferay-ui:message key="assigned-space" />
	<%= companyQuota.getQuotaAssigned() %>
</c:if>

<liferay-ui:search-container delta="5" orderByCol="<%=orderByCol%>"
	orderByType="<%=orderByType%>">
	<liferay-ui:search-container-results>
		<%
			results = null;
			total = 0;
			pageContext.setAttribute("results", results);
			pageContext.setAttribute("total", total);
		%>
	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="org.lsug.quota.model.Quota" keyProperty="quotaId" modelVar="quota">
		<liferay-ui:search-container-column-text name="quotaAlert" value="<%= String.valueOf(quota.getQuotaAlert()) %>" />
		<liferay-ui:search-container-column-text name="quotaAssigned" value="<%= String.valueOf(quota.getQuotaAssigned()) %>" />
		<liferay-ui:search-container-column-text name="quotaUsed" value="<%= String.valueOf(quota.getQuotaUsed()) %>" />
	</liferay-ui:search-container-row>
</liferay-ui:search-container>
