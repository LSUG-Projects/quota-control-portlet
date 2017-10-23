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
import java.util.ResourceBundle;

import javax.portlet.Portlet;
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
import org.lsug.quota.service.QuotaLocalService;
import org.lsug.quota.util.PortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
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
			"javax.portlet.display-name=My quota Portlet",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/my-quota/view.jsp",
			"javax.portlet.name=" + PortletKeys.UserQuotaWeb,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class UserQuotaWebPortlet extends MVCPortlet {

	private QuotaLocalService _quotaLocalService;
	
	@Reference(unbind = "-")
	protected void setQuotaLocalService(QuotaLocalService quotaLocalService) {
		_quotaLocalService = quotaLocalService;
	}

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

			Quota siteQuota = _quotaLocalService.fetchQuotaByClassNameIdClassPK(classNameId, groupId);

			if (siteQuota != null && siteQuota.isEnabled()) {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
						"content.Language", resourceRequest.getLocale(), getClass());
				pieDataset.setValue(LanguageUtil.get(resourceBundle, "used-space"), siteQuota.getQuotaUsedPercentage());
				pieDataset.setValue(LanguageUtil.get(resourceBundle, "unused-space"), 100 - siteQuota.getQuotaUsedPercentage());

				sb.append(LanguageUtil.get(resourceBundle, "user-site-current-used-size-diagram-title"));

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
			.getLog(UserQuotaWebPortlet.class);
}
