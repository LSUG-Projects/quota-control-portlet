
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

<%@ include file="/html/common/init.jsp" %>

<c:if test="${not empty searchContainer}">
	<liferay-ui:search-container delta="${paramDelta}" emptyResultsMessage="empty.message" iteratorURL="${portletURL}" searchContainer="${searchContainer}">
	<liferay-ui:search-container-results results="${list}" total="${count}" />
	<liferay-ui:search-container-row className="org.lsug.quota.model.Quota" keyProperty="quotaId"
		escapedModel="<%= true %>" modelVar="quota">

	<liferay-ui:search-container-column-text name="name">
		<%= CompanyLocalServiceUtil.getCompany(quota.getClassPK()).getWebId() %>
	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="quota-enabled" orderable="true" orderableProperty="quotaStatus">
		<c:if test="${quota.enabled}">
			<liferay-ui:message key="yes" />
		</c:if>

		<c:if test="${!quota.enabled}">
			<liferay-ui:message key="no" />
		</c:if>
	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="quota-alert" value="${quota.quotaAlert}"
	orderable="true" orderableProperty="quotaAlert" />

	<liferay-ui:search-container-column-text name="quota-assigned">
		<c:if test="${quota.unlimitedQuota}">
			<liferay-ui:message key="quota-unlimited" />
		</c:if>

		<c:if test="${!quota.unlimitedQuota}">
			<fmt:formatNumber maxFractionDigits="0" value="${quota.quotaAssigned / 1024 / 1024}" var="quotaAssigned" />
			<c:out value="${quotaAssigned}" />
		</c:if>
	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="quota-used" orderable="true" orderableProperty="quotaUsed">
		<fmt:formatNumber maxFractionDigits="3" value="${quota.quotaUsed / 1024 / 1024}" var="quotaUsed" />
		<c:out value="${quotaUsed}"></c:out> MB (<%= quota.getQuotaUsedPercentage() %>%)
	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="actions">

	  	<liferay-ui:icon-menu>

	  		<c:set var="cmd">
	  			<c:choose>
	  				<c:when test="${quota.quotaId eq 0 }"><%= Constants.ADD %></c:when>
	  				<c:otherwise><%= Constants.UPDATE %></c:otherwise>
	  			</c:choose>
	  		</c:set>

	  		<portlet:renderURL var="editServerURL">
				<portlet:param name="mvcPath" value="/html/server-quota/edit.jsp" />
				<portlet:param name="backURL" value="/html/server-quota/view.jsp" />
				<portlet:param name="quotaId" value="${quota.quotaId }" />
				<portlet:param name="classPK" value="${quota.classPK }" />
			</portlet:renderURL>

	  		<liferay-ui:icon
				image="edit"
				url="${editServerURL}"
			/>

	  	</liferay-ui:icon-menu>

	</liferay-ui:search-container-column-text>

	</liferay-ui:search-container-row>
		 <liferay-ui:search-iterator searchContainer="${searchContainer}" paginate="<%= true %>" />
	</liferay-ui:search-container>
</c:if>

<portlet:resourceURL var="serverChart" />

<img src="${serverChart}" />