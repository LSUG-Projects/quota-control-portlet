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

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<liferay-portlet:renderURL var="portletURL">
	<portlet:param name="tabs2" value="${tabs2}" />
</liferay-portlet:renderURL>

<liferay-ui:tabs names="sites,sites-users" param="tabs2" url="${portletURL}"/>
			
<liferay-ui:search-container searchContainer="${searchContainer}">
	
	<liferay-ui:search-container-results results="${searchContainer.getResults()}" total="${searchContainer.getTotal()}" />
	
	<liferay-ui:search-container-row className="org.lsug.quota.model.Quota" keyProperty="quotaId" modelVar="quota">
			
	<liferay-ui:search-container-column-text name="site">

		<%= GroupLocalServiceUtil.getGroup(quota.getClassPK()).getDescriptiveName(locale) %>

	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="quota-status">
 		<c:if test="${quota.getQuotaStatus() eq 1}">
 			<liferay-ui:message key="yes" />
 		</c:if>
 		
 		<c:if test="${quota.getQuotaStatus() eq 0}">
 			<liferay-ui:message key="no" />
 		</c:if>
 	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="quota-alert"
		value="${quota.getQuotaAlert()}" />

	<liferay-ui:search-container-column-text name="quota-assigned" orderable="true" 
		orderableProperty="quotaAssigned">
		
		<fmt:formatNumber var="quotaAssigned" value="${quota.getQuotaAssigned() / 1024 / 1024}" maxFractionDigits="0" />
		<c:out value="${quotaAssigned}"></c:out>

	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="quota-used" orderable="true"
		orderableProperty="quotaUsed">
			
		<fmt:formatNumber var="quotaUsed" value="${quota.getQuotaUsed() / 1024 / 1024}" maxFractionDigits="3" />
		<c:out value="${quotaUsed}"></c:out>
			
	</liferay-ui:search-container-column-text>

	<liferay-ui:search-container-column-text name="edit">
				
		<portlet:renderURL var="editURL">
			<portlet:param name="jspPage" value="/html/sites-quota/edit.jsp" />
			<portlet:param name="quotaId" value="${quota.getQuotaId()}" />
			<portlet:param name="tabs2" value="${tabs2}" />
		</portlet:renderURL>

		<liferay-ui:icon image="edit" url="${editURL}" />
				
	</liferay-ui:search-container-column-text>
			
	</liferay-ui:search-container-row>
		
	<liferay-ui:search-iterator />
			
</liferay-ui:search-container>

