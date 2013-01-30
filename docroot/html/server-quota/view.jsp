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

<portlet:defineObjects />

<c:if test="${not empty searchContainer}">
	<liferay-ui:search-container searchContainer="${searchContainer}" delta="${paramDelta}" emptyResultsMessage="empty.message" iteratorURL="${portletURL}">
	<liferay-ui:search-container-results results="${list}" total="${count}" />
	<liferay-ui:search-container-row className="org.lsug.quota.server.util.ServerVO" 
		escapedModel="<%= true %>" modelVar="serverVO">
	 		
	<liferay-ui:search-container-column-text name="name" value="${serverVO.nameInstance }" />
	
	<%-- TODO --%>
 	<%--liferay-ui:search-container-column-text name="numUser" value="${serverVO.numUser }" /> --%>
 	
 	<liferay-ui:search-container-column-text name="quota-status" value="${serverVO.quota.quotaStatus }" />
 	
 	<liferay-ui:search-container-column-text name="quota-alert" value="${serverVO.quota.quotaAlert }" />
 	
 	<liferay-ui:search-container-column-text name="quota-assigned" value="${serverVO.quota.quotaAssigned }" />
 	
 	<liferay-ui:search-container-column-text name="quota-used" value="${serverVO.quota.quotaUsed }" />
	
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