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

package org.lsug.quota.portlet;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalServiceUtil;
import org.lsug.quota.util.QuotaUtil;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Group;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class SitesQuotaPortlet extends MVCPortlet {
	
	/**
	 * Log.
	 */
	private static final transient Log LOGGER = LogFactoryUtil.getLog(SitesQuotaPortlet.class);
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		
		// Parametro para identificar la pestaña en la que estamos
		final String tabs2 = ParamUtil.getString(renderRequest, "tabs2", "sites");
		
		final int cur = ParamUtil.getInteger(renderRequest, "cur", 0);
		final int paramDelta = ParamUtil.getInteger(renderRequest, "delta", SearchContainer.DEFAULT_DELTA);
			
		// Url del searchContainer
		final PortletURL portletURL = renderResponse.createRenderURL();
		portletURL.setParameter("tabs2", tabs2);		
			
		// OrderByComparator
		final String orderByCol = ParamUtil.getString(renderRequest, "orderByCol", "quotaUsed");
		final String orderByType = ParamUtil.getString(renderRequest, "orderByType", "desc");
		final OrderByComparator orderByComparator = QuotaUtil.getQuotaOrderByComparator(orderByCol, orderByType);
		
		// Crear searchContainer
		SearchContainer<Quota> searchContainer = new SearchContainer<Quota>(
				renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
				cur, paramDelta, portletURL, null, null);
		searchContainer.setDelta(paramDelta);
		searchContainer.setDeltaConfigurable(false);
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
		
		try{			
		
			// Identificador instancia de Liferay
			final long companyId = PortalUtil.getCompanyId(renderRequest);
			
			// Si la pestaña es sites obtenemos los sitios web de una instancia
			if (tabs2.equalsIgnoreCase("sites")) {
				final List<Quota> results = QuotaUtil.getSitesQuotas(companyId, searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);
				final int total = QuotaUtil.getSitesQuotasCount(companyId);

				searchContainer.setResults(results);
				searchContainer.setTotal(total);
				
			} else if (tabs2.equalsIgnoreCase("sites-users")){
				
				// Obtenemos los sitios de usuario de una instancia				
				final List<Quota> results = QuotaUtil.getSitesUsersQuotas(companyId, searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);
				final int total = QuotaUtil.getSitesUsersQuotasCount(companyId);

				searchContainer.setResults(results);
				searchContainer.setTotal(total);
			}		
	
		} catch (SystemException e) {
			LOGGER.error(e);
			throw new PortletException(e);
		} catch (PortalException e) {
			LOGGER.error(e);
			throw new PortletException(e);
		}
		
		
		renderRequest.setAttribute("tabs2", tabs2);
		renderRequest.setAttribute("searchContainer", searchContainer);
		
		super.doView(renderRequest, renderResponse);
	}

	public Quota updateQuota(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		// Identificador quota
		final long quotaId = ParamUtil.getLong(actionRequest, "quotaId");
		final long classNameId = PortalUtil.getClassNameId(Group.class);
		final long classPK = ParamUtil.getLong(actionRequest, "classPK");
		// Cuota activa para indicar si una cuota esta activa o no
		final int quotaStatus = ParamUtil.getBoolean(actionRequest, "quotaStatus", Boolean.FALSE) == Boolean.FALSE ? 0
				: 1;
		// Numero a partir del cual se envia un correo para enviar un aviso del espacio utilizado y disponible
		final int quotaAlert = ParamUtil.getBoolean(actionRequest, "quotaAlert", Boolean.FALSE) == Boolean.FALSE ? 0
				: 1;
		// Tamaño asignado a un sitio
		final long quotaAssigned = ParamUtil.getLong(actionRequest, "quotaAssigned");
		// Pasar los megas a bytes
		final long quotaAssignedBytes = quotaAssigned * 1024 * 1024;

		return QuotaLocalServiceUtil.updateQuota(quotaId, classNameId, classPK, quotaAlert, quotaAssignedBytes,
				quotaStatus);
	}

}
