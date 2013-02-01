
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
	String orderByCol =
		ParamUtil.getString(request, "orderByCol", "quotaUsed");
	String orderByType =
		ParamUtil.getString(request, "orderByType", "desc");

	Quota companyQuota = QuotaUtil.getCompanyQuota(companyId);
	OrderByComparator orderByComparator =
		QuotaUtil.getQuotaOrderByComparator(orderByCol, orderByType);
%>

<liferay-ui:message key="sites-quota-description" />

<c:if test="<%=companyQuota != null%>">
	<liferay-ui:message key="used-space" />
	<%=companyQuota.getQuotaUsed()%>

	<liferay-ui:message key="assigned-space" />
	<%=companyQuota.getQuotaAssigned()%>
</c:if>

<liferay-ui:search-container delta="5" orderByCol="<%=orderByCol%>"
	orderByType="<%=orderByType%>">
	<liferay-ui:search-container-results>
		<%
			results =
						QuotaUtil.getSitesQuotas(
							companyId, searchContainer.getStart(),
							searchContainer.getEnd(), orderByComparator);
					total = QuotaUtil.getSitesQuotasCount(companyId);
					pageContext.setAttribute("results", results);
					pageContext.setAttribute("total", total);
		%>
	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="org.lsug.quota.model.Quota"
		keyProperty="quotaId" modelVar="quota">
		<liferay-ui:search-container-column-text name="site">
			<%=GroupLocalServiceUtil.getGroup(
							quota.getClassPK()).getDescriptiveName(locale)%>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="quota-status"
			value="<%=String.valueOf(quota.getQuotaStatus())%>" />
		<liferay-ui:search-container-column-text name="quota-alert"
			value="<%=String.valueOf(quota.getQuotaAlert())%>" />
		<liferay-ui:search-container-column-text name="quota-assigned"
			value="<%=String.valueOf(quota.getQuotaAssigned())%>" orderable="<%=true%>"
			orderableProperty="quotaAssigned" />
		<liferay-ui:search-container-column-text name="quota-used"
			value="<%=String.valueOf(quota.getQuotaUsed())%>" orderable="<%=true%>"
			orderableProperty="quotaUsed" />
		<liferay-ui:search-container-column-text name="edit">
			<portlet:renderURL var="editURL">
				<portlet:param name="jspPage" value="/html/sites-quota/edit.jsp" />
				<portlet:param name="quotaId"
					value="<%=String.valueOf(quota.getQuotaId())%>" />
			</portlet:renderURL>

			<liferay-ui:icon image="edit" url="<%=editURL%>" />
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>