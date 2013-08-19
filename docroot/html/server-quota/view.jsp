<%@page import="com.liferay.portal.kernel.util.Constants"%>
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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<portlet:defineObjects />

<c:if test="${not empty searchContainer}">
	<liferay-ui:search-container searchContainer="${searchContainer}" delta="${paramDelta}" emptyResultsMessage="empty.message" iteratorURL="${portletURL}">
	<liferay-ui:search-container-results results="${list}" total="${count}" />
	<liferay-ui:search-container-row className="org.lsug.quota.server.util.ServerVO" 
		escapedModel="<%= true %>" modelVar="serverVO">
	 		
	<liferay-ui:search-container-column-text name="name" value="${serverVO.nameInstance }" />
	
	<%-- TODO --%>
 	<%--liferay-ui:search-container-column-text name="numUser" value="${serverVO.numUser }" /> --%>
 	
 	<liferay-ui:search-container-column-text name="quota-status">
 		<c:if test="${serverVO.quota.quotaStatus eq 1}">
 			<liferay-ui:message key="yes" />
 		</c:if>
 		
 		<c:if test="${serverVO.quota.quotaStatus eq 0}">
 			<liferay-ui:message key="no" />
 		</c:if>
 	</liferay-ui:search-container-column-text>
 	
 	<liferay-ui:search-container-column-text name="quota-alert" value="${serverVO.quota.quotaAlert }" />
 	
 	<liferay-ui:search-container-column-text name="quota-assigned" >
		<c:if test="${serverVO.quota.quotaAssigned eq -1}">
			<liferay-ui:message key="quota-unlimited" />
		</c:if>
		
		<c:if test="${serverVO.quota.quotaAssigned ne -1}">
			<fmt:formatNumber var="quotaAssigned" value="${serverVO.quota.quotaAssigned / 1024 / 1024}" maxFractionDigits="0" />
			<c:out value="${quotaAssigned}" />
		</c:if>
	</liferay-ui:search-container-column-text>
	
	<liferay-ui:search-container-column-text name="quota-used">
		<fmt:formatNumber var="quotaUsed" value="${serverVO.quota.quotaUsed / 1024 / 1024}" maxFractionDigits="3" />
		<c:out value="${quotaUsed}"></c:out>
	</liferay-ui:search-container-column-text>
	
	<liferay-ui:search-container-column-text name="actions">
      	
      	<liferay-ui:icon-menu>
      	
      		<c:set var="cmd">
      			<c:choose>
      				<c:when test="${serverVO.quota.classPK eq 0 }"><%=Constants.ADD%></c:when>
      				<c:otherwise><%=Constants.UPDATE%></c:otherwise>
      			</c:choose>
      		</c:set>
      	
      		<portlet:renderURL var="editServerURL">
				<portlet:param name="<%=Constants.CMD%>" value="${cmd}"/>
				<portlet:param name="backURL" value="/html/server-quota/view.jsp"/>
				<portlet:param name="quotaId" value="${serverVO.quota.quotaId }"/>
				<portlet:param name="classPK" value="${serverVO.quota.classPK }"/>
			</portlet:renderURL>
			
      		<liferay-ui:icon
				image="edit"
				url="${editServerURL}"
			/>
			
      	</liferay-ui:icon-menu> 
		
	</liferay-ui:search-container-column-text> 
			   		
	</liferay-ui:search-container-row>
		 <liferay-ui:search-iterator searchContainer="${searchContainer}" paginate="<%= true %>"/>
	</liferay-ui:search-container>
</c:if>