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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalInstances;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Gonzalez
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.display-name=Server Quota Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/server-quota/view.jsp",
		"javax.portlet.name=" + PortletKeys.ServerQuotaWeb,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ServerQuotaWebPortlet extends MVCPortlet {
	
	private QuotaLocalService _quotaLocalService;
	private CompanyLocalService _companyLocalService;
	private GroupLocalService _groupLocalService;
	
	@Reference(unbind = "-")
	protected void setQuotaLocalService(QuotaLocalService quotaLocalService) {
		_quotaLocalService = quotaLocalService;
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(CompanyLocalService companyLocalService) {
		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		long[] companyIds = PortalInstances.getCompanyIds();
		
		for (long companyId : companyIds) {
			try {
				doRun(companyId);
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
	}

	@Deactivate
	protected void deactivate(Map<String, Object> properties) {
	
	}
	
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

		final Quota quota = _quotaLocalService.getQuota(quotaId);

		long quotaUsed = quota.getQuotaUsed();

		if (quotaStatus == Constants.QUOTA_ACTIVE && !quota.isEnabled()) {
			quotaUsed = _quotaLocalService.calculateServerUsedQuota(classPK);
		}

		quota.setQuotaStatus(quotaStatus);
		quota.setQuotaAssigned(quotaAssigned);
		quota.setQuotaUsed(quotaUsed);
		quota.setQuotaAlert(quotaAlert);
		quota.setNew(false);
		_quotaLocalService.updateQuota(quota);
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
			
			OrderByComparator<Quota> orderByComparator = QuotaUtil.getQuotaOrderByComparator(orderByCol, orderByType);
			DefaultPieDataset pieDataset = new DefaultPieDataset();

			try {
				List<Quota> listCompanyQuota = _quotaLocalService.
						getQuotaByClassNameId(classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
								orderByComparator);

				for (Quota quota : listCompanyQuota) {
					if (quota.isEnabled()) {
						pieDataset.setValue(_companyLocalService.getCompany(quota.getClassPK()).getWebId(), quota.getQuotaUsed());
					}
				}
			} catch (Exception e) {
				LOGGER.error(e);
				throw new PortletException(e);
			}

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					"content.Language", resourceRequest.getLocale(), getClass());
			
			sb.append(LanguageUtil.get(
					resourceBundle, "server-current-used-size-diagram-title"));

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
		final OrderByComparator<Quota> orderByComparator = QuotaUtil
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
		long listQuotasCount = _quotaLocalService.countByClassNameId(classNameId);

		List<Quota> listCompanyQuota = _quotaLocalService.
				getQuotaByClassNameId(classNameId, searchContainer.getStart(), searchContainer.getEnd(),
						orderByComparator);

		renderRequest.setAttribute("searchContainer", searchContainer);
		renderRequest.setAttribute("list", listCompanyQuota);
		renderRequest.setAttribute("count", listQuotasCount);
	}
	
	protected void doRun(long companyId) throws Exception {

		long classNameId = PortalUtil.getClassNameId(Company.class);

		Quota quota = _quotaLocalService.fetchQuotaByClassNameIdClassPK(classNameId, companyId);

		if (quota == null) {
			LOGGER.info("Adding quota for company " + companyId);
			_quotaLocalService.createDefaultQuota(companyId, classNameId, companyId);
		}

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

		int start=0;		
		int end=0;
		int pages=0;
		
		long[] siteClassNameIds = { PortalUtil.getClassNameId(Group.class), PortalUtil.getClassNameId(Organization.class.getName()) };

		int siteCount = _groupLocalService.searchCount(companyId, siteClassNameIds, null, groupParams);

		int siteQuotaCount = _quotaLocalService.countByCompanyIdClassNameIds(companyId, siteClassNameIds);

		if (siteCount != siteQuotaCount) {
			long sitesQuotaAdded = 0;

			pages = siteCount / INTERVAL;
			
			LOGGER.info("Detected missing sites quotas. Adding new site quotas...");

			for (int i = 0; i <= pages; i++) {				
				start = (i * INTERVAL);
				end = start + INTERVAL;

				List<Group> groups = _groupLocalService.search(companyId,
							siteClassNameIds, null, groupParams, start, end);
	
				for (Group group : groups) {
					if (!QuotaUtil.isValidGroupQuota(group)) {
						continue;
					}
	
					classNameId = group.getClassNameId();
	
					long classPK = group.getGroupId();
	
					quota = _quotaLocalService
							.fetchQuotaByClassNameIdClassPK(classNameId, classPK);
	
					if (quota == null) {
						long quotaUsed = _quotaLocalService.calculateSiteUsedQuota(classPK);
						_quotaLocalService.addQuota(companyId, classNameId, classPK, 0, 0, quotaUsed, Constants.QUOTA_INACTIVE);
						sitesQuotaAdded++;
					}
					
					start++;
				}
			}

			LOGGER.info("Added "+sitesQuotaAdded+ " sites quotas");
		}

		classNameId = PortalUtil.getClassNameId(User.class);
		long[] userClassNameIds = { classNameId };

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

		int userCount = _groupLocalService.searchCount(companyId, userClassNameIds, null, userParams);

		int userQuotaCount = _quotaLocalService.countByCompanyIdClassNameId(companyId, classNameId);

		if (userCount != userQuotaCount) {
			long usersQuotaAdded = 0;

			LOGGER.info("Detected missing user quotas. Adding new user quotas...");

			pages = userCount / INTERVAL;
			
			for (int i = 0; i <= pages; i++) {
				start = (i * INTERVAL);		
				end = start + INTERVAL;
				
				List<Group> userGroups = _groupLocalService.search(companyId,
					userClassNameIds, null, groupParams, start, end);

				for (Group userGroup : userGroups) {
					long classPK = userGroup.getGroupId();
	
					quota = _quotaLocalService
							.fetchQuotaByClassNameIdClassPK(classNameId, classPK);
	
					if (quota == null) {
						long quotaUsed = _quotaLocalService.calculateSiteUsedQuota(classPK);
						_quotaLocalService.addQuota(companyId, classNameId, classPK, 0, 0, quotaUsed, Constants.QUOTA_INACTIVE);
						usersQuotaAdded++;
					}
					start++;
				}
			}

			LOGGER.info("Added "+usersQuotaAdded+ " users quotas");
		}
	}

	private static final int INTERVAL = 100;
	
	private static final transient Log LOGGER = LogFactoryUtil
			.getLog(ServerQuotaWebPortlet.class);
		
}