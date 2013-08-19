<%@page import="com.liferay.portal.kernel.bean.BeanParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
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

<%@ include file="/html/server-quota/init.jsp" %>

<%
Quota quota = (Quota) request.getAttribute( "quota" );

String backURL = ParamUtil.getString(request, "backURL");

long quotaId = BeanParamUtil.getLong(quota, request, "quotaId");

long classPK = BeanParamUtil.getLong(quota, request, "classPK");
%>

<portlet:actionURL var="editQuotaURL" name="saveServerQuota" />

<liferay-ui:header
	backURL="<%= backURL %>"
/>

<%-- TODO: show error messages --%>
<%-- <liferay-ui:error key="" message="" /> --%>

<aui:form action="<%= editQuotaURL %>" method="post" name="fm" >
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= quotaId == 0 ? Constants.ADD : Constants.UPDATE %>"/>
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="quotaId" type="hidden" value="<%= quotaId %>" />
	<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
	
	<aui:input label="quota-status" name="quotaStatus" type="checkbox" 
		value="<%=quota.getQuotaStatus() == 0 ? Boolean.FALSE : Boolean.TRUE%>" />
		
	<aui:input label="quota-unlimited" name="quotaUnlimited" type="checkbox" 
		value="<%=quota.getQuotaAssigned() == -1 ? Boolean.TRUE : Boolean.FALSE%>" />			

	<aui:input label="quota-alert" name="quotaAlert" value="<%=quota.getQuotaAlert()%>">
		<%-- Validar que solo se puedan introducir digitos --%>	
		<aui:validator name="digits"/>	
	</aui:input>			

	<aui:input label="quota-assigned" name="quotaAssigned" value="<%=(quota.getQuotaAssigned() / 1024) / 1024%>">
		<%-- Validar que solo se puedan introducir digitos --%>	
		<aui:validator name="digits"/>	
	</aui:input>

	<aui:button-row cssClass="button-row">
		<aui:button type="submit" value="update" />
	</aui:button-row>
</aui:form>
