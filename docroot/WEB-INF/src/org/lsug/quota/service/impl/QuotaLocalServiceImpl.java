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

import org.lsug.quota.NoSuchQuotaException;
import org.lsug.quota.model.Quota;
import org.lsug.quota.service.base.QuotaLocalServiceBaseImpl;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the quota local service. <p> All custom service methods
 * should be put in this class. Whenever methods are added, rerun ServiceBuilder
 * to copy their definitions into the
 * {@link org.lsug.quota.service.QuotaLocalService} interface. <p> This is a
 * local service. Methods of this service will not have security checks based on
 * the propagated JAAS credentials because this service can only be accessed
 * from within the same VM. </p>
 * 
=======
import org.lsug.quota.service.base.QuotaLocalServiceBaseImpl;

/**
 * The implementation of the quota local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link org.lsug.quota.service.QuotaLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
>>>>>>> 05ae214d56dfa801e384416c8f6721ea1045fafa
 * @author Brian Wing Shun Chan
 * @see org.lsug.quota.service.base.QuotaLocalServiceBaseImpl
 * @see org.lsug.quota.service.QuotaLocalServiceUtil
 */
public class QuotaLocalServiceImpl extends QuotaLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS: Never reference this interface directly. Always use
	 * {@link org.lsug.quota.service.QuotaLocalServiceUtil} to access the quota
	 * local service.
	 */

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
