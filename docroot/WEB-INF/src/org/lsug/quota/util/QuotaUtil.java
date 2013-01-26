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

package org.lsug.quota.util;

import org.lsug.quota.NoSuchQuotaException;
import org.lsug.quota.QuotaExceededException;
import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalServiceUtil;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;

public class QuotaUtil {

	public static void decreaseQuota(long groupId, long userId, long size)
		throws PortalException, SystemException, NoSuchQuotaException,
		QuotaExceededException {

		final Group group = GroupLocalServiceUtil.getGroup(groupId);
		final Company company =
			CompanyLocalServiceUtil.getCompany(group.getCompanyId());

		QuotaLocalServiceUtil.decrementQuota(
			PortalUtil.getClassNameId(Group.class), group.getClassPK(), size);

		QuotaLocalServiceUtil.decrementQuota(
			PortalUtil.getClassNameId(Company.class), company.getCompanyId(),
			size);
	}

	public static void increaseQuota(long groupId, long userId, long size)
		throws PortalException, SystemException {

		final Group group = GroupLocalServiceUtil.getGroup(groupId);
		final Company company =
			CompanyLocalServiceUtil.getCompany(group.getCompanyId());

		QuotaLocalServiceUtil.decrementQuota(
			PortalUtil.getClassNameId(Group.class), group.getClassPK(), size);

		QuotaLocalServiceUtil.decrementQuota(
			PortalUtil.getClassNameId(Company.class), company.getCompanyId(),
			size);
	}

	public static boolean hasQuota(long groupId, long userId, long size)
		throws PortalException, SystemException {

		final Quota groupQuota = getGroupQuota(groupId);
		final Quota companyQuota = getCompanyQuota(groupId);

		return groupQuota.hasFreeMB(size) && companyQuota.hasFreeMB(size);
	}

	public static boolean checkAlerts(long groupId, long userId)
		throws PortalException, SystemException {

		Quota groupQuota = getGroupQuota(groupId);
		Quota companyQuota = getCompanyQuota(groupId);

		return groupQuota.isExceeded() || companyQuota.isExceeded();
	}

	private static Quota getCompanyQuota(long groupId)
		throws PortalException, SystemException {

		final Group group = GroupLocalServiceUtil.getGroup(groupId);
		final Company company =
			CompanyLocalServiceUtil.getCompany(group.getCompanyId());

		return QuotaLocalServiceUtil.getQuotaByClassNameIdClassPK(
			PortalUtil.getClassNameId(Company.class), company.getCompanyId());
	}

	private static Quota getGroupQuota(long groupId)
		throws PortalException, SystemException {

		final Group group = GroupLocalServiceUtil.getGroup(groupId);

		return QuotaLocalServiceUtil.getQuotaByClassNameIdClassPK(
			PortalUtil.getClassNameId(Group.class), group.getClassPK());
	}

}