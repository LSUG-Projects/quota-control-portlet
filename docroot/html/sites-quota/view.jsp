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

<liferay-portlet:renderURL var="portletURL">
	<portlet:param name="tabs2" value="${tabs2}" />
</liferay-portlet:renderURL>

<liferay-ui:error key="assignedQuotaLessThanCurrent" message="assigned-quota-less-than-current" />

<liferay-ui:tabs names="sites,user-sites" param="tabs2" url="${portletURL}" />

<liferay-ui:search-container searchContainer="${searchContainer}">

	<liferay-ui:search-container-results results="${searchContainer.getResults()}" total="${searchContainer.getTotal()}" />

	<liferay-ui:search-container-row className="org.lsug.quota.model.Quota" keyProperty="quotaId"
	modelVar="quota" escapedModel="<%= true %>">

	<liferay-ui:search-container-column-text name="site-name">
		<a target="_blank" href="/group/control_panel?doAsGroupId=${quota.getClassPK()}&p_p_id=156">
			<%= GroupLocalServiceUtil.getGroup(quota.getClassPK()).getDescriptiveName(locale) %>
		</a>
	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="quota-enabled">
		<c:if test="${quota.enabled}">
			<liferay-ui:message key="yes" />
		</c:if>

		<c:if test="${!quota.enabled}">
			<liferay-ui:message key="no" />
		</c:if>
	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="quota-alert"
		value="${quota.getQuotaAlert()}" />

	<liferay-ui:search-container-column-text name="quota-assigned" orderable="true"
		orderableProperty="quotaAssigned">

		<fmt:formatNumber maxFractionDigits="0" value="${quota.getQuotaAssigned() / 1024 / 1024}" var="quotaAssigned" />
		<c:out value="${quotaAssigned}"></c:out> MB

	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="quota-used" orderable="true"
		orderableProperty="quotaUsed">

		<fmt:formatNumber maxFractionDigits="3" value="${quota.getQuotaUsed() / 1024 / 1024}" var="quotaUsed" />
		<c:out value="${quotaUsed}"></c:out> MB (<%= quota.getQuotaUsedPercentage() %>%)

	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="edit">

		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/html/sites-quota/edit.jsp" />
			<portlet:param name="quotaId" value="${quota.getQuotaId()}" />
			<portlet:param name="tabs2" value="${tabs2}" />
		</portlet:renderURL>

		<liferay-ui:icon image="edit" url="${editURL}" />

	</liferay-ui:search-container-column-text>

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />

</liferay-ui:search-container>

<% String tabs2 = (String) renderRequest.getAttribute("tabs2"); %>
<c:if test='<%= tabs2.equals(\"sites\") %>'>

	<portlet:resourceURL var="siteQuotaURL">
		<portlet:param name="type" value="sites"/>
	</portlet:resourceURL>

	<img src="<%= siteQuotaURL %>" />
</c:if>

<c:if test ='<%= tabs2.equals(\"user-sites\") %>'>

	<portlet:resourceURL var="userSiteQuotaURL">
		<portlet:param name="type" value="users"/>
	</portlet:resourceURL>

	<img src="<%= userSiteQuotaURL %>" />
</c:if>