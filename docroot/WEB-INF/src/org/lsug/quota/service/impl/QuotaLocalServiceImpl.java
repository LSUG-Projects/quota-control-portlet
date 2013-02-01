/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package org.lsug.quota.service.impl;

import com.liferay.portal.kernel.exception.SystemException;

import org.lsug.quota.NoSuchQuotaException;
import org.lsug.quota.model.Quota;
import org.lsug.quota.service.base.QuotaLocalServiceBaseImpl;

/**
 * The implementation of the quota local service. <p> All custom service methods
 * should be put in this class. Whenever methods are added, rerun ServiceBuilder
 * to copy their definitions into the
 * {@link org.lsug.quota.service.QuotaLocalService} interface. <p> This is a
 * local service. Methods of this service will not have security checks based on
 * the propagated JAAS credentials because this service can only be accessed
 * from within the same VM. </p>
 * 
 * @author Brian Wing Shun Chan
 * @see org.lsug.quota.service.base.QuotaLocalServiceBaseImpl
 * @see org.lsug.quota.service.QuotaLocalServiceUtil
 */
public class QuotaLocalServiceImpl extends QuotaLocalServiceBaseImpl {

	public Quota addQuota(
		long classNameId, long classPK, int quotaAlert, long quotaAssigned,
		long quotaUsed, int quotaStatus)
		throws NoSuchQuotaException, SystemException {

		long quotaId = counterLocalService.increment(Quota.class.getName());
		Quota quota = quotaPersistence.create(quotaId);

		quota.setClassNameId(classNameId);
		quota.setClassPK(classPK);
		quota.setQuotaAlert(quotaAlert);
		quota.setQuotaAssigned(quotaAssigned);
		quota.setQuotaUsed(quotaUsed);
		quota.setQuotaStatus(quotaStatus);

		return quotaPersistence.update(quota, false);
	}

	public Quota getQuotaByClassNameIdClassPK(
		final long classNameId, final long classPK)
		throws NoSuchQuotaException, SystemException {

		return getQuotaPersistence().findByClassNameIdClassPK(
			classNameId, classPK);
	}

	public Quota updateQuota(
		final long classNameId, final long classPK, final long fileSize)
		throws NoSuchQuotaException, SystemException {

		Quota quota = getQuotaByClassNameIdClassPK(classNameId, classPK);

		quota.setQuotaUsed(quota.getQuotaUsed() + fileSize);

		return updateQuota(quota);
	}

	public Quota updateQuota(
		long quotaId, long classNameId, long classPK, int quotaAlert,
		long quotaAssigned, long quotaUsed, int quotaStatus)
		throws NoSuchQuotaException, SystemException {

		Quota quota = quotaPersistence.fetchByPrimaryKey(quotaId);

		quota.setClassNameId(classNameId);
		quota.setClassPK(classPK);
		quota.setQuotaAlert(quotaAlert);
		quota.setQuotaAssigned(quotaAssigned);
		quota.setQuotaUsed(quotaUsed);
		quota.setQuotaStatus(quotaStatus);

		return quotaPersistence.update(quota, false);
	}

	public Quota decrementQuota(
		final long classNameId, final long classPK, final long fileSize)
		throws NoSuchQuotaException, SystemException {

		if (fileSize >= 0)
			throw new IllegalArgumentException(
				"Cannot increment a quota by a negative increment.");

		return updateQuota(classNameId, classPK, -fileSize);
	}

	public Quota incrementQuota(
		final long classNameId, final long classPK, final long fileSize)
		throws NoSuchQuotaException, SystemException {

		if (fileSize < 0)
			throw new IllegalArgumentException(
				"Cannot decrement a quota by a positive decrement");

		return updateQuota(classNameId, classPK, fileSize);
	}

}
