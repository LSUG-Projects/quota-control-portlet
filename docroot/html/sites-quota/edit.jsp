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

<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="org.lsug.quota.service.QuotaLocalServiceUtil"%>

<%@ include file="/html/sites-quota/init.jsp"%>

<%
final String tabs2 = ParamUtil.getString(request, "tabs2", "sites");
final long quotaId = ParamUtil.getLong(request, "quotaId");
final Quota quota = QuotaLocalServiceUtil.getQuota(quotaId);
%>

<portlet:actionURL var="editURL">
	<portlet:param name="<%=ActionRequest.ACTION_NAME%>" value="updateQuota" />
	<portlet:param name="quotaId" value="<%=String.valueOf(quotaId)%>" />
	<portlet:param name="classPK" value="<%=String.valueOf(quota.getClassPK())%>" />
</portlet:actionURL>

<aui:form action="<%=editURL%>" method="post" name="fm">
	
	<%-- Campo oculto para saber en que pestaña estabamos y volver alli --%>
	<input type="hidden" value="<%=tabs2 %>" name="<portlet:namespace/>tabs2"/>

	<aui:column cssClass="edit-column">
		<aui:input label="quota-status" name="quotaStatus" type="checkbox" 
			value="<%=quota.getQuotaStatus() == 0 ? Boolean.FALSE : Boolean.TRUE%>" />				
	</aui:column>	
	
	<aui:column cssClass="edit-column">
		<aui:input label="quota-alert" name="quotaAlert" value="<%=quota.getQuotaAlert()%>">
			<%-- Validar que solo se puedan introducir digitos --%>	
			<aui:validator name="digits"/>	
		</aui:input>			
	</aui:column>
	
	<aui:column cssClass="edit-column">
		<aui:input label="quota-assigned" name="quotaAssigned" value="<%=(quota.getQuotaAssigned() / 1024) / 1024%>">
			<%-- Validar que solo se puedan introducir digitos --%>	
			<aui:validator name="digits"/>	
		</aui:input>
	</aui:column>
		
	<aui:button-row cssClass="button-row">
		<aui:button type="submit" value="update" />
	</aui:button-row>
	
</aui:form>
