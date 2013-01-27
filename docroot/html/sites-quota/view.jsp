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

<liferay-ui:message key="sites-quota-title" />

ON | OFF
<liferay-ui:message key="total-quota" />
<%=QuotaUtil.getTotalQuota(companyId)%>
<liferay-ui:message key="used-space" />
<%=QuotaUtil.getUsedSpace(companyId)%>
<liferay-ui:message key="allocated-space" />
<%=QuotaUtil.getAllocatedSpace(companyId)%>

<liferay-ui:search-container delta="5" orderByCol="<%=orderByCol%>"
	orderByType="<%=orderByType%>" iteratorURL="<%=iteratorURL%>">
	<liferay-ui:search-container-results>
		<%
			results = QuotaServiceUtil.findQuotas(classNameId);
			total = QuotaServiceUtil.countQuotas(classNameId);
			pageContext.setAttribute("results", results);
			pageContext.setAttribute("total", total);
		%>
	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="org.lsug.model.Quota" keyProperty="quotaId" modelVar="quota">
		<liferay-ui:search-container-column-text name="quotaAlert" value="<%= quota.getAlert() %>" />
		<liferay-ui:search-container-column-text name="quotaAssigned" value="<%= quota.getQuotaAssigned() %>" />
		<liferay-ui:search-container-column-text name="quotaUsed" value="<%= quota.getQuotaUsed() %>" />
	</liferay-ui:search-container-row>
</liferay-ui:search-container>
