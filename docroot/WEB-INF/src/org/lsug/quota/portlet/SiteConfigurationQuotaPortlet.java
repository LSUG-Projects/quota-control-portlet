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

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.awt.Color;

import java.io.IOException;
import java.io.OutputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
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
 *  Portlet controller for quotas in site configuration section
 *  @author LSUG
 *
 */
public class SiteConfigurationQuotaPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		Quota quota = null;

		try {
			long groupId = PortalUtil.getScopeGroupId(renderRequest);

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (QuotaUtil.isValidGroupQuota(group)) {
				long classNameId = group.getClassNameId();

				if (group.isStagingGroup()) {
					groupId = group.getLiveGroupId();
				}

				if (classNameId >0) {
					quota = QuotaLocalServiceUtil.getQuotaByClassNameIdClassPK(classNameId, groupId);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
			throw new PortletException(e);
		}

		renderRequest.setAttribute("quota", quota);

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {

		StringBundler sb = new StringBundler(5);

		JFreeChart jFreeChart = null;

		DefaultPieDataset pieDataset = new DefaultPieDataset();

		try {
			long groupId = com.liferay.portal.util.PortalUtil.getScopeGroupId(resourceRequest);

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			long classNameId = 0;

			if (QuotaUtil.isValidGroupQuota(group)) {
				classNameId = group.getClassNameId();

				if (group.isStagingGroup()) {
					groupId = group.getLiveGroupId();
				}
			}

			Quota siteQuota = QuotaLocalServiceUtil.
				getQuotaByClassNameIdClassPK(classNameId, groupId);

			if (siteQuota.isEnabled()) {
				pieDataset.setValue(QuotaUtil.getResource(resourceRequest,"used-space"), siteQuota.getQuotaUsedPercentage());
				pieDataset.setValue(QuotaUtil.getResource(resourceRequest,"unused-space"), 100 - siteQuota.getQuotaUsedPercentage());
			}

			sb.append(QuotaUtil.getResource(resourceRequest, "sites-quota-enabled-sites-used-diagram-title"));

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

		final Quota quota = QuotaLocalServiceUtil.getQuota(quotaId);

		long quotaUsed = quota.getQuotaUsed();

		if (quotaStatus == Constants.QUOTA_ACTIVE && !quota.isEnabled()) {
			quotaUsed = QuotaLocalServiceUtil.calculateSiteUsedQuota(classPK);

			if (quotaUsed > quotaAssignedBytes) {
				SessionErrors.add(actionRequest, "assignedQuotaLessThanCurrent" );
				return;
			}
		}

		quota.setQuotaStatus(quotaStatus);
		quota.setQuotaAssigned(quotaAssignedBytes);
		quota.setQuotaUsed(quotaUsed);
		quota.setQuotaAlert(quotaAlert);

		QuotaLocalServiceUtil.updateQuota(quota);
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
			.getLog(SiteConfigurationQuotaPortlet.class);

}