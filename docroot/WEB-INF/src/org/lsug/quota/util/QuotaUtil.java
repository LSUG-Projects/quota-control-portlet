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
import org.lsug.quota.util.comparator.QuotaUsedComparator;
import org.lsug.quota.util.comparator.QuotaAssignedComparator;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		final Quota companyQuota = getCompanyQuotaByGroupId(groupId);

		return groupQuota.hasFreeMB(size) && companyQuota.hasFreeMB(size);
	}

	public static boolean checkAlerts(long groupId, long userId)
		throws PortalException, SystemException {

		Quota groupQuota = getGroupQuota(groupId);
		Quota companyQuota = getCompanyQuotaByGroupId(groupId);

		return groupQuota.isExceeded() || companyQuota.isExceeded();
	}

	public static Quota getCompanyQuota(long companyId)
		throws PortalException, SystemException {

		try {
			Quota quota =
				QuotaLocalServiceUtil.getQuotaByClassNameIdClassPK(
					PortalUtil.getClassNameId(Company.class), companyId);

			return quota;
		}
		catch (NoSuchQuotaException nsqe) {
			return null;
		}
	}

	public static OrderByComparator getQuotaOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("quotaUsed")) {
			orderByComparator = new QuotaUsedComparator(orderByAsc);
		}
		else if (orderByCol.equals("quotaAssigned")) {
			orderByComparator = new QuotaAssignedComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static List<Quota> getSitesQuotas(
		long companyId, int start, int end, OrderByComparator orderByComparator)
		throws PortalException, SystemException {

		List<Quota> result = new ArrayList();

		List<Group> groups =
			GroupLocalServiceUtil.getCompanyGroups(
				companyId, 0, GroupLocalServiceUtil.getGroupsCount());

		for (Group group : groups) {
			if (group.isSite() && !group.isControlPanel()) {
				try {
					result.add(getGroupQuota(group.getGroupId()));
				}
				catch (NoSuchQuotaException nsqe) {
					result.add(QuotaLocalServiceUtil.addQuota(
						PortalUtil.getClassNameId(Group.class),
						group.getGroupId(), 0, (long) 0, (long) 0, 0));
				}
			}
		}

		Collections.sort(result, orderByComparator);
		if (result.size() < start) {
			return null;
		}
		if (result.size() < end) {
			return result.subList(start, result.size());
		}
		else {
			return result.subList(start, end);
		}

	}

	public static int getSitesQuotasCount(long companyId)
		throws PortalException, SystemException {

		int result = 0;

		List<Group> groups =
			GroupLocalServiceUtil.getCompanyGroups(
				companyId, 0, GroupLocalServiceUtil.getGroupsCount());

		for (Group group : groups) {
			if (group.isSite() && !group.isControlPanel()) {
				result++;
			}
		}

		return result;

	}

	private static Quota getCompanyQuotaByGroupId(long groupId)
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
