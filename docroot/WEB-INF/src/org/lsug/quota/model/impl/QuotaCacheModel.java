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

package org.lsug.quota.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import org.lsug.quota.model.Quota;

import java.io.Serializable;

/**
 * The cache model class for representing Quota in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Quota
 * @generated
 */
public class QuotaCacheModel implements CacheModel<Quota>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{quotaId=");
		sb.append(quotaId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", quotaAssigned=");
		sb.append(quotaAssigned);
		sb.append(", quotaUsed=");
		sb.append(quotaUsed);
		sb.append(", quotaStatus=");
		sb.append(quotaStatus);
		sb.append(", quotaAlert=");
		sb.append(quotaAlert);
		sb.append("}");

		return sb.toString();
	}

	public Quota toEntityModel() {
		QuotaImpl quotaImpl = new QuotaImpl();

		quotaImpl.setQuotaId(quotaId);
		quotaImpl.setClassNameId(classNameId);
		quotaImpl.setClassPK(classPK);
		quotaImpl.setQuotaAssigned(quotaAssigned);
		quotaImpl.setQuotaUsed(quotaUsed);
		quotaImpl.setQuotaStatus(quotaStatus);
		quotaImpl.setQuotaAlert(quotaAlert);

		quotaImpl.resetOriginalValues();

		return quotaImpl;
	}

	public long quotaId;
	public long classNameId;
	public long classPK;
	public long quotaAssigned;
	public long quotaUsed;
	public int quotaStatus;
	public int quotaAlert;
}