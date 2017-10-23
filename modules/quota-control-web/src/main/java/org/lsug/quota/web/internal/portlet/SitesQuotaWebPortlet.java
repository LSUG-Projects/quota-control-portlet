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

package org.lsug.quota.web.internal.portlet;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalService;
import org.lsug.quota.util.Constants;
import org.lsug.quota.util.PortletKeys;
import org.lsug.quota.util.QuotaUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * 
 * @author Juan Gonzalez
 *
 */
@Component(
		immediate = true,
		property = {
			"javax.portlet.display-name=Sites quota Portlet",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/sites-quota/view.jsp",
			"javax.portlet.name=" + PortletKeys.SitesQuotaWeb,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class SitesQuotaWebPortlet extends MVCPortlet {

	private QuotaLocalService _quotaLocalService;
	private GroupLocalService _groupLocalService;
	
	@Reference(unbind = "-")
	protected void setQuotaLocalService(QuotaLocalService quotaLocalService) {
		_quotaLocalService = quotaLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		// Parametro para identificar la pestaña en la que estamos

		final String tabs2 = ParamUtil.getString(renderRequest, "tabs2",
				"sites");

		final int cur = ParamUtil.getInteger(renderRequest, "cur", 0);
		final int paramDelta = ParamUtil.getInteger(renderRequest, "delta",
				SearchContainer.DEFAULT_DELTA);

		// Url del searchContainer

		final PortletURL portletURL = renderResponse.createRenderURL();
		portletURL.setParameter("tabs2", tabs2);

		// OrderByComparator

		final String orderByCol = ParamUtil.getString(renderRequest,
				"orderByCol", "quotaUsed");
		final String orderByType = ParamUtil.getString(renderRequest,
				"orderByType", "desc");
		final OrderByComparator<Quota> orderByComparator = QuotaUtil
				.getQuotaOrderByComparator(orderByCol, orderByType);

		// Crear searchContainer

		SearchContainer<Quota> searchContainer = new SearchContainer<Quota>(
				renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
				cur, paramDelta, portletURL, null, null);
		searchContainer.setDelta(paramDelta);
		searchContainer.setDeltaConfigurable(false);
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);

		try {

			// Identificador instancia de Liferay

			final long companyId = PortalUtil.getCompanyId(renderRequest);

			int total = 0;
			List<Quota> results = null;

			long[] classNameIds = null;

			// Si la pestaña es sites obtenemos los sitios web de una instancia

			if (tabs2.equalsIgnoreCase("sites")) {
				classNameIds = new long[] {
						PortalUtil.getClassNameId(Group.class.getName()),
						PortalUtil.getClassNameId(Organization.class.getName())};

			} else if (tabs2.equalsIgnoreCase("user-sites")) {
				classNameIds = new long[] {
						PortalUtil.getClassNameId(User.class.getName())};
			}

			results = _quotaLocalService.
					getQuotaByCompanyIdClassNameIds(companyId, classNameIds,
							searchContainer.getStart(),
							searchContainer.getEnd(), orderByComparator);
			total = _quotaLocalService.countByCompanyIdClassNameIds(companyId, classNameIds);

			searchContainer.setResults(results);
			searchContainer.setTotal(total);

		} catch (SystemException e) {
			LOGGER.error(e);
			throw new PortletException(e);
		}

		renderRequest.setAttribute("tabs2", tabs2);
		renderRequest.setAttribute("searchContainer", searchContainer);

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(5);

		String type = ParamUtil.getString(resourceRequest, "type", "sites");

		JFreeChart jFreeChart = null;

		DefaultPieDataset pieDataset = new DefaultPieDataset();

		try {
			long[] classNameIds = null;

			if ("sites".equals(type)) {
				classNameIds = new long[] {
						PortalUtil.getClassNameId(Group.class.getName()),
						PortalUtil.getClassNameId(Organization.class.getName())};
			}else {
				classNameIds = new long[] {
						PortalUtil.getClassNameId(User.class.getName())};
			}

			// OrderByComparator

			String orderByCol = "quotaUsed";
			String orderByType = "desc";
			OrderByComparator<Quota> orderByComparator = QuotaUtil
					.getQuotaOrderByComparator(orderByCol, orderByType);

			List<Quota> siteQuotas = _quotaLocalService.
				getQuotaByCompanyIdClassNameIds(themeDisplay.getCompanyId(), classNameIds,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						orderByComparator);

			for (Quota siteQuota : siteQuotas) {
				if (siteQuota.isEnabled()) {
					Group group = _groupLocalService.getGroup(siteQuota.getClassPK());
					pieDataset.setValue(group.getDescriptiveName(themeDisplay.getLocale()), siteQuota.getQuotaUsed());
				}
			}

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					"content.Language", resourceRequest.getLocale(), getClass());
			
			sb.append(LanguageUtil.get(resourceBundle, "sites-quota-enabled-sites-used-diagram-title"));

			jFreeChart = getCurrentSizeJFreeChart(sb.toString(), pieDataset);

			resourceResponse.setContentType(ContentTypes.IMAGE_PNG);

			OutputStream outputStream = null;

			try {
				outputStream = resourceResponse.getPortletOutputStream();
				ChartUtilities.writeChartAsPNG(outputStream, jFreeChart, 400, 200);
			}finally {
				if (outputStream!= null) {
					outputStream.close();
				}
			}

		} catch (Exception e) {
			LOGGER.error(e);
			throw new PortletException(e);
		}
	}

	public void updateQuota(ActionRequest actionRequest,
			ActionResponse actionResponse) throws PortalException, SystemException {

		// Parametro para identificar la pestaña en la que estamos

		final String tabs2 = ParamUtil.getString(actionRequest, "tabs2",
				"sites");
		actionResponse.setRenderParameter("tabs2", tabs2);

		// Identificador quota

		final long quotaId = ParamUtil.getLong(actionRequest, "quotaId");
		//final long classNameId = PortalUtil.getClassNameId(Group.class);
		final long classPK = ParamUtil.getLong(actionRequest, "classPK");

		// Cuota activa para indicar si una cuota esta activa o no

		final int quotaStatus = ParamUtil.getBoolean(actionRequest,
				"quotaStatus", Boolean.FALSE) == Boolean.FALSE ? Constants.QUOTA_INACTIVE
				: Constants.QUOTA_ACTIVE;

		// Numero a partir del cual se envia un correo para enviar un aviso del
		// espacio utilizado y disponible

		final int quotaAlert = ParamUtil.getInteger(actionRequest,
				"quotaAlert", 0);

		// Tamaño asignado a un sitio

		final long quotaAssigned = ParamUtil.getLong(actionRequest,
				"quotaAssigned");

		// Pasar los megas a bytes

		final long quotaAssignedBytes = quotaAssigned * 1024 * 1024;

		// Update quota

		final Quota quota = _quotaLocalService.getQuota(quotaId);

		long quotaUsed = quota.getQuotaUsed();

		if (quotaStatus == Constants.QUOTA_ACTIVE && !quota.isEnabled()) {
			quotaUsed = _quotaLocalService.calculateSiteUsedQuota(classPK);

			if (quotaUsed > quotaAssignedBytes) {
				SessionErrors.add(actionRequest, "assignedQuotaLessThanCurrent" );
				return;
			}
		}

		quota.setQuotaStatus(quotaStatus);
		quota.setQuotaAssigned(quotaAssignedBytes);
		quota.setQuotaUsed(quotaUsed);
		quota.setQuotaAlert(quotaAlert);

		_quotaLocalService.updateQuota(quota);
	}

	protected JFreeChart getCurrentSizeJFreeChart(String title, PieDataset pieDataset) {
		JFreeChart jFreeChart = ChartFactory.createPieChart3D(title, pieDataset, true, false, null);

		jFreeChart.setBackgroundPaint(Color.white);

		return jFreeChart;
	}

	/**
	 * Log.
	 */
	private static final transient Log LOGGER = LogFactoryUtil
			.getLog(SitesQuotaWebPortlet.class);
}
