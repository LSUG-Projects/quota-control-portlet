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

package org.lsug.quota.portlet;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.awt.Color;

import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
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
import org.lsug.quota.service.QuotaLocalServiceUtil;
import org.lsug.quota.util.Constants;
import org.lsug.quota.util.QuotaUtil;

/**
 *  Portlet controller for server quota
 *  @author LSUG
 *
 */
public class ServerQuotaPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

			try {
				listServerQuotas(renderRequest, renderResponse);
			} catch (SystemException e) {
				LOGGER.error(e);
				throw new PortletException(e);
			}

			super.doView(renderRequest, renderResponse);
	}

	public void saveServerQuota(final ActionRequest req,
			final ActionResponse res) throws PortalException, SystemException {

		final long quotaId = ParamUtil.getLong(req, "quotaId");
		final long classPK = ParamUtil.getLong(req, "classPK");
		final int quotaStatus = ParamUtil.getBoolean(req, "quotaStatus",
				Boolean.FALSE) == Boolean.FALSE ? org.lsug.quota.util.Constants.QUOTA_INACTIVE
				: org.lsug.quota.util.Constants.QUOTA_ACTIVE;
		final boolean quotaUnlimited = ParamUtil.getBoolean(req,
				"quotaUnlimited", Boolean.FALSE);

		long quotaAssigned = ParamUtil.getLong(req, "quotaAssigned");

		if (quotaUnlimited) {
			quotaAssigned = Constants.UNLIMITED_QUOTA;
		} else {
			quotaAssigned = quotaAssigned * 1024 * 1024;
		}

		final int quotaAlert = ParamUtil.getInteger(req, "quotaAlert");

		// Update quota

		final Quota quota = QuotaLocalServiceUtil.getQuota(quotaId);

		long quotaUsed = quota.getQuotaUsed();

		if (quotaStatus == Constants.QUOTA_ACTIVE && !quota.isEnabled()) {
			quotaUsed = QuotaLocalServiceUtil.calculateServerUsedQuota(classPK);
		}

		quota.setQuotaStatus(quotaStatus);
		quota.setQuotaAssigned(quotaAssigned);
		quota.setQuotaUsed(quotaUsed);
		quota.setQuotaAlert(quotaAlert);
		quota.setNew(false);
		QuotaLocalServiceUtil.updateQuota(quota);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {

		String type = ParamUtil.getString(resourceRequest, "type", "currentSize");

		StringBundler sb = new StringBundler(5);

		JFreeChart jFreeChart = null;

		if (type.equals("currentSize")) {
			long classNameId = PortalUtil.getClassNameId(Company.class.getName());

			String orderByCol ="quotaUsed";
			String orderByType = "desc";
			OrderByComparator orderByComparator = QuotaUtil .getQuotaOrderByComparator(orderByCol, orderByType);
			DefaultPieDataset pieDataset = new DefaultPieDataset();

			try {
				List<Quota> listCompanyQuota = QuotaLocalServiceUtil.
						getQuotaByClassNameId(classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
								orderByComparator);

				for (Quota quota : listCompanyQuota) {
					if (quota.isEnabled()) {
						pieDataset.setValue(CompanyLocalServiceUtil.getCompany(quota.getClassPK()).getWebId(), quota.getQuotaUsed());
					}
				}
			} catch (Exception e) {
				LOGGER.error(e);
				throw new PortletException(e);
			}

			sb.append(QuotaUtil.getResource(resourceRequest, "server-current-used-size-diagram-title"));

			jFreeChart = getCurrentSizeJFreeChart(sb.toString(), pieDataset);
		}

		OutputStream outputStream = null;

		resourceResponse.setContentType(ContentTypes.IMAGE_PNG);

		try {
			outputStream = resourceResponse.getPortletOutputStream();
			ChartUtilities.writeChartAsPNG(outputStream, jFreeChart, 400, 200);
		}finally {
			if (outputStream!= null) {
				outputStream.close();
			}
		}
	}

	protected JFreeChart getCurrentSizeJFreeChart(String title, PieDataset pieDataset) {
		JFreeChart jFreeChart = ChartFactory.createPieChart3D(title, pieDataset, true, false, null);

		jFreeChart.setBackgroundPaint(Color.white);

		return jFreeChart;
	}

	private void listServerQuotas(RenderRequest renderRequest,
			RenderResponse renderResponse) throws SystemException {

		int cur = ParamUtil.getInteger(renderRequest, "cur", 0);
		int paramDelta = ParamUtil.getInteger(renderRequest, "delta",
				SearchContainer.DEFAULT_DELTA);
		PortletURL portletURL = renderResponse.createRenderURL();

		// OrderByComparator

		final String orderByCol = ParamUtil.getString(renderRequest,
								"orderByCol", "quotaUsed");
		final String orderByType = ParamUtil.getString(renderRequest,
								"orderByType", "desc");
		final OrderByComparator orderByComparator = QuotaUtil
								.getQuotaOrderByComparator(orderByCol, orderByType);

		SearchContainer<Quota> searchContainer = new SearchContainer<Quota>(
				renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
				cur, paramDelta, portletURL, null, null);
		searchContainer.setDelta(paramDelta);
		searchContainer.setDeltaConfigurable(false);
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setOrderByComparator(orderByComparator);

		long classNameId = PortalUtil.getClassNameId(Company.class.getName());
		long listQuotasCount = QuotaLocalServiceUtil.countByClassNameId(classNameId);

		List<Quota> listCompanyQuota = QuotaLocalServiceUtil.
				getQuotaByClassNameId(classNameId, searchContainer.getStart(), searchContainer.getEnd(),
						orderByComparator);

		renderRequest.setAttribute("searchContainer", searchContainer);
		renderRequest.setAttribute("list", listCompanyQuota);
		renderRequest.setAttribute("count", listQuotasCount);
	}

	/**
	 * Log.
	 */
	private static final transient Log LOGGER = LogFactoryUtil
			.getLog(ServerQuotaPortlet.class);

}