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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.awt.Color;

import java.io.IOException;
import java.io.OutputStream;

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
import org.lsug.quota.util.QuotaUtil;

/**
 *  Portlet controller for user's quota.
 *  @author LSUG
 *
 */
public class UserQuotaPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(5);

		JFreeChart jFreeChart = null;

		DefaultPieDataset pieDataset = new DefaultPieDataset();

		try {
			long groupId = themeDisplay.getUser().getGroupId();

			long classNameId = PortalUtil.getClassNameId(User.class);

			Quota siteQuota = QuotaLocalServiceUtil.fetchQuotaByClassNameIdClassPK(classNameId, groupId);

			if (siteQuota != null && siteQuota.isEnabled()) {
				pieDataset.setValue(QuotaUtil.getResource(resourceRequest,"used-space"), siteQuota.getQuotaUsedPercentage());
				pieDataset.setValue(QuotaUtil.getResource(resourceRequest,"unused-space"), 100 - siteQuota.getQuotaUsedPercentage());

				sb.append(QuotaUtil.getResource(resourceRequest, "user-site-current-used-size-diagram-title"));

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
			}
		} catch (Exception e) {
			LOGGER.error(e);
			throw new PortletException(e);
		}
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
			.getLog(UserQuotaPortlet.class);

}