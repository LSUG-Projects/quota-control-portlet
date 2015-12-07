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

package org.lsug.quota;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyLocalServiceWrapper;
import com.liferay.portal.util.PortalUtil;

import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalServiceUtil;

/**
 *  DL quota service override for some company services
 *  @author LSUG
 *
 */
public class QuotaListenerCompanyLocalService
		extends CompanyLocalServiceWrapper {

	public QuotaListenerCompanyLocalService(
			CompanyLocalService companyLocalService) {
		super(companyLocalService);
	}

	@Override
	public Company addCompany(String webId, String virtualHostname, String mx,
			String shardName, boolean system, int maxUsers, boolean active)
			throws PortalException, SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Add company listener");
		}
		Company company = super.addCompany(webId, virtualHostname, mx, shardName, system,
				maxUsers, active);

		long classNameId = PortalUtil.getClassNameId(Company.class.getName());
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Creating default company quota, companyId:" + company.getCompanyId()+", classNameId:"+classNameId);
		}
		
		QuotaLocalServiceUtil.createDefaultQuota(company.getCompanyId(), classNameId, company.getCompanyId());

		return company;
	}

	@Override
	public Company deleteCompany(long companyId) throws PortalException,
			SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Removing company listener, companyId:" + companyId);
		}

		long classNameId = PortalUtil.getClassNameId(Company.class.getName());
		Quota quota = QuotaLocalServiceUtil.fetchQuotaByClassNameIdClassPK(classNameId, companyId);

		if (quota != null) {
			QuotaLocalServiceUtil.deleteQuota(quota.getQuotaId());
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Company quota with id="+quota.getQuotaId() + " removed");
			}
		}

		return super.deleteCompany(companyId);
	}

	private static final transient Log LOGGER = LogFactoryUtil
			.getLog(QuotaListenerCompanyLocalService.class);
}