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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lsug.quota.NoSuchQuotaException;
import org.lsug.quota.QuotaExceededException;
import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalServiceUtil;
import org.lsug.quota.util.comparator.QuotaAssignedComparator;
import org.lsug.quota.util.comparator.QuotaUsedComparator;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

public class QuotaUtil {

	public static void decreaseQuota(long groupId, long userId, long size) throws PortalException, SystemException,
			NoSuchQuotaException, QuotaExceededException {

		final Group group = GroupLocalServiceUtil.getGroup(groupId);
		final Company company = CompanyLocalServiceUtil.getCompany(group.getCompanyId());

		QuotaLocalServiceUtil.decrementQuota(PortalUtil.getClassNameId(Group.class), group.getGroupId(), size);

		QuotaLocalServiceUtil.decrementQuota(PortalUtil.getClassNameId(Company.class), company.getCompanyId(), size);
	}

	public static void increaseQuota(long groupId, long userId, long size) throws PortalException, SystemException {

		final Group group = GroupLocalServiceUtil.getGroup(groupId);
		final Company company = CompanyLocalServiceUtil.getCompany(group.getCompanyId());

		QuotaLocalServiceUtil.incrementQuota(PortalUtil.getClassNameId(Group.class), group.getGroupId(), size);

		QuotaLocalServiceUtil.incrementQuota(PortalUtil.getClassNameId(Company.class), company.getCompanyId(), size);
	}

	public static boolean hasQuota(long groupId, long userId, long size) throws PortalException, SystemException {

		final Quota groupQuota = getGroupQuota(groupId);
		final Quota companyQuota = getCompanyQuotaByGroupId(groupId);

		return groupQuota.hasFreeMB(size) && (isUnlimitedQuota(companyQuota) || companyQuota.hasFreeMB(size));
	}

	public static boolean checkAlerts(long groupId, long userId) throws PortalException, SystemException {

		final Quota groupQuota = getGroupQuota(groupId);
		final Quota companyQuota = getCompanyQuotaByGroupId(groupId);

		return groupQuota.isExceeded() || companyQuota.isExceeded();
	}

	public static Quota getCompanyQuota(long companyId) throws PortalException, SystemException {

		try {
			final Quota quota = QuotaLocalServiceUtil.getQuotaByClassNameIdClassPK(
					PortalUtil.getClassNameId(Company.class), companyId);

			return quota;
		} catch (NoSuchQuotaException nsqe) {
			return null;
		}
	}

	public static OrderByComparator getQuotaOrderByComparator(String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("quotaUsed")) {
			orderByComparator = new QuotaUsedComparator(orderByAsc);
		} else if (orderByCol.equals("quotaAssigned")) {
			orderByComparator = new QuotaAssignedComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static List<Quota> getSitesQuotas(long companyId, int start, int end, OrderByComparator orderByComparator)
			throws PortalException, SystemException {

		final long[] classNameIds = { ClassNameLocalServiceUtil.getClassNameId(Group.class.getName()) };

		return getGroupsQuotas(companyId, start, end, orderByComparator, classNameIds);

	}

	public static int getSitesQuotasCount(long companyId) throws PortalException, SystemException {

		final long[] classNameIds = { ClassNameLocalServiceUtil.getClassNameId(Group.class.getName()) };

		return GroupLocalServiceUtil.searchCount(companyId, classNameIds, null, null, null);
	}

	public static List<Quota> getSitesUsersQuotas(long companyId, int start, int end,
			OrderByComparator orderByComparator) throws PortalException, SystemException {

		final long[] classNameIds = { ClassNameLocalServiceUtil.getClassNameId(User.class.getName()) };

		return getGroupsQuotas(companyId, start, end, orderByComparator, classNameIds);

	}

	public static int getSitesUsersQuotasCount(long companyId) throws SystemException {

		final long[] classNameIds = { ClassNameLocalServiceUtil.getClassNameId(User.class.getName()) };

		return GroupLocalServiceUtil.searchCount(companyId, classNameIds, null, null, null);
	}

	/**
	 * Calculate Group quotas
	 * 
	 * @param companyId
	 * @param start
	 * @param end
	 * @param orderByComparator
	 * @param classNameIds
	 * @return
	 * @throws SystemException
	 * @throws PortalException
	 * @throws NoSuchQuotaException
	 */
	@SuppressWarnings("unchecked")
	private static List<Quota> getGroupsQuotas(long companyId, int start, int end, OrderByComparator orderByComparator,
			final long[] classNameIds) throws SystemException, PortalException, NoSuchQuotaException {
		List<Quota> result = new ArrayList<Quota>();

		final List<Group> groups = GroupLocalServiceUtil.search(companyId, classNameIds, null, null, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			try {
				result.add(getGroupQuota(group.getGroupId()));
			} catch (NoSuchQuotaException e) {
				final long classNameId = PortalUtil.getClassNameId(Group.class);
				final long classPK = group.getGroupId();
				final int quotaAlert = 0;
				final long quotaAssigned = 0;
				final long quotaUsed = 0;
				final int quotaStatus = 0;

				final Quota quota = QuotaLocalServiceUtil.addQuota(classNameId, classPK, quotaAlert, quotaAssigned,
						quotaUsed, quotaStatus);

				result.add(quota);
			}
		}

		if (Validator.isNotNull(orderByComparator)) {
			Collections.sort(result, orderByComparator);
		}

		final int resutlsSize = result.size();

		if (start == QueryUtil.ALL_POS) {
			start = 0;
		}

		if (end == QueryUtil.ALL_POS) {
			end = resutlsSize;
		}

		if (resutlsSize < start) {
			return null;
		}

		if (resutlsSize < end) {
			return result.subList(start, resutlsSize);
		} else {
			return result.subList(start, end);
		}
	}

	/**
	 * Calculate current used quota from an instance
	 * 
	 * @param companyId
	 * @param quotaUsed
	 * @return
	 * @throws SystemException
	 */
	public static long calculateServerUsedQuota(final long companyId) throws SystemException {
		long usedQuota = 0;

		final long[] classNameIds = { ClassNameLocalServiceUtil.getClassNameId(Group.class.getName()),
				ClassNameLocalServiceUtil.getClassNameId(User.class.getName()) };

		try {
			final List<Quota> quotas = getGroupsQuotas(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null,
					classNameIds);

			for (Quota quota : quotas) {
				usedQuota += quota.getQuotaUsed();
			}

			return usedQuota;
		} catch (NoSuchQuotaException e) {
			return usedQuota;
		} catch (PortalException e) {
			return usedQuota;
		} catch (SystemException e) {
			return usedQuota;
		}
	}

	/**
	 * Calculate current used quota from a site
	 * 
	 * @param groupId
	 * @param quotaUsed
	 * @return
	 * @throws SystemException
	 */
	public static long calculateSiteUsedQuota(final long groupId) throws SystemException {
		long quota = 0;

		final List<DLFileEntry> fileEntries = DLFileEntryLocalServiceUtil.getGroupFileEntries(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (DLFileEntry fileEntry : fileEntries) {
			quota += fileEntry.getSize();
		}

		return quota;
	}

	private static Quota getCompanyQuotaByGroupId(long groupId) throws PortalException, SystemException {

		final Group group = GroupLocalServiceUtil.getGroup(groupId);
		final Company company = CompanyLocalServiceUtil.getCompany(group.getCompanyId());

		return QuotaLocalServiceUtil.getQuotaByClassNameIdClassPK(PortalUtil.getClassNameId(Company.class),
				company.getCompanyId());
	}

	private static Quota getGroupQuota(long groupId) throws PortalException, SystemException {

		final Group group = GroupLocalServiceUtil.getGroup(groupId);

		return QuotaLocalServiceUtil.getQuotaByClassNameIdClassPK(PortalUtil.getClassNameId(Group.class),
				group.getGroupId());
	}

	private static boolean isUnlimitedQuota(Quota quota) {
		return quota.getQuotaAssigned() == Constants.UNLIMITED_QUOTA;
	}

}
