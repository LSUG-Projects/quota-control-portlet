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

package org.lsug.quota.event;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.persistence.GroupFinderUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.LinkedHashMap;
import java.util.List;

import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalServiceUtil;
import org.lsug.quota.util.Constants;
import org.lsug.quota.util.QuotaUtil;

/**
 *  Operations for doing on webapp startup
 *  @author LSUG
 *
 */
public class QuotaStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {

		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void doRun(long companyId) throws Exception {

		long classNameId = PortalUtil.getClassNameId(Company.class);

		Quota quota = QuotaLocalServiceUtil.fetchQuotaByClassNameIdClassPK(classNameId, companyId);

		if (quota == null) {
			LOGGER.info("Adding quota for company " + companyId);
			QuotaLocalServiceUtil.createDefaultQuota(companyId, classNameId, companyId);
		}

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

		int start=0;		
		int end=0;
		int pages=0;
		
		long[] siteClassNameIds = { PortalUtil.getClassNameId(Group.class), PortalUtil.getClassNameId(Organization.class.getName()) };

		int siteCount = GroupLocalServiceUtil.searchCount(companyId, siteClassNameIds, null, groupParams);

		int siteQuotaCount = QuotaLocalServiceUtil.countByCompanyIdClassNameIds(companyId, siteClassNameIds);

		if (siteCount != siteQuotaCount) {
			long sitesQuotaAdded = 0;

			pages = siteCount / INTERVAL;
			
			LOGGER.info("Detected missing sites quotas. Adding new site quotas...");

			for (int i = 0; i <= pages; i++) {				
				start = (i * INTERVAL);
				end = start + INTERVAL;

				List<Group> groups = GroupLocalServiceUtil.search(companyId,
							siteClassNameIds, null, groupParams, start, end);
	
				for (Group group : groups) {
					if (!QuotaUtil.isValidGroupQuota(group)) {
						continue;
					}
	
					classNameId = group.getClassNameId();
	
					long classPK = group.getGroupId();
	
					quota = QuotaLocalServiceUtil
							.fetchQuotaByClassNameIdClassPK(classNameId, classPK);
	
					if (quota == null) {
						long quotaUsed = QuotaLocalServiceUtil.calculateSiteUsedQuota(classPK);
						QuotaLocalServiceUtil.addQuota(companyId, classNameId, classPK, 0, 0, quotaUsed, Constants.QUOTA_INACTIVE);
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

		int userCount = GroupLocalServiceUtil.searchCount(companyId, userClassNameIds, null, userParams);

		int userQuotaCount = QuotaLocalServiceUtil.countByCompanyIdClassNameId(companyId, classNameId);

		if (userCount != userQuotaCount) {
			long usersQuotaAdded = 0;

			LOGGER.info("Detected missing user quotas. Adding new user quotas...");

			pages = userCount / INTERVAL;
			
			for (int i = 0; i <= pages; i++) {
				start = (i * INTERVAL);		
				end = start + INTERVAL;
				
				List<Group> userGroups = GroupLocalServiceUtil.search(companyId,
					userClassNameIds, null, groupParams, start, end);

				for (Group userGroup : userGroups) {
					long classPK = userGroup.getGroupId();
	
					quota = QuotaLocalServiceUtil
							.fetchQuotaByClassNameIdClassPK(classNameId, classPK);
	
					if (quota == null) {
						long quotaUsed = QuotaLocalServiceUtil.calculateSiteUsedQuota(classPK);
						QuotaLocalServiceUtil.addQuota(companyId, classNameId, classPK, 0, 0, quotaUsed, Constants.QUOTA_INACTIVE);
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
			.getLog(QuotaStartupAction.class);

}