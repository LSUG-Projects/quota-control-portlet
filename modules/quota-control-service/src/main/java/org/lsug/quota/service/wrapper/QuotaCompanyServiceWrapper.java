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

package org.lsug.quota.service.wrapper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.PortalUtil;

import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 *  DL quota service override for some company services
 *  @author Juan Gonzalez
 *
 */
@Component(
		immediate = true,
		property = {
		},
		service = ServiceWrapper.class
	)
public class QuotaCompanyServiceWrapper
		extends CompanyLocalServiceWrapper {

	private QuotaLocalService _quotaLocalService;
	
	@Reference(unbind = "-")
	protected void setQuotaLocalService(QuotaLocalService quotaLocalService) {
		_quotaLocalService = quotaLocalService;
	}
	
	public QuotaCompanyServiceWrapper() {
		super(null);
	}

	
	@Override
	public Company addCompany(Company company) {
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Add company listener");
		}
		Company companyAdded = super.addCompany(company);

		long classNameId = PortalUtil.getClassNameId(Company.class.getName());
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Creating default company quota, companyId:" + companyAdded.getCompanyId()+", classNameId:"+classNameId);
		}
		
		_quotaLocalService.createDefaultQuota(companyAdded.getCompanyId(), classNameId, companyAdded.getCompanyId());

		return companyAdded;
	}



	@Override
	public Company addCompany(String webId, String virtualHostname, String mx, boolean system, int maxUsers,
			boolean active) throws PortalException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Add company listener");
		}
		Company company = super.addCompany(webId, virtualHostname, mx, system,
				maxUsers, active);

		long classNameId = PortalUtil.getClassNameId(Company.class.getName());
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Creating default company quota, companyId:" + company.getCompanyId()+", classNameId:"+classNameId);
		}
		
		_quotaLocalService.createDefaultQuota(company.getCompanyId(), classNameId, company.getCompanyId());

		return company;
	}

	@Override
	public Company deleteCompany(long companyId) throws PortalException,
			SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Removing company listener, companyId:" + companyId);
		}

		long classNameId = PortalUtil.getClassNameId(Company.class.getName());
		Quota quota = _quotaLocalService.fetchQuotaByClassNameIdClassPK(classNameId, companyId);

		if (quota != null) {
			_quotaLocalService.deleteQuota(quota.getQuotaId());
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Company quota with id="+quota.getQuotaId() + " removed");
			}
		}

		return super.deleteCompany(companyId);
	}

	private static final transient Log LOGGER = LogFactoryUtil
			.getLog(QuotaCompanyServiceWrapper.class);
}
