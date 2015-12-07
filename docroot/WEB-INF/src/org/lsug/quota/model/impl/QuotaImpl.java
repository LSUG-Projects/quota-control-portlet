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

package org.lsug.quota.model.impl;

import org.lsug.quota.util.Constants;

/**
 * The extended model implementation for the Quota service. Represents a row in
 * the &quot;LSUGQUOTA_Quota&quot; database table, with each column mapped to a
 * property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class.
 * Whenever methods are added, rerun ServiceBuilder to copy their definitions
 * into the {@link org.lsug.quota.model.Quota} interface.
 * </p>
 *
 * @author LSUG
 */
public class QuotaImpl extends QuotaBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a quota
	 * model instance should use the {@link org.lsug.quota.model.Quota}
	 * interface instead.
	 */
	public QuotaImpl() {
	}

	public short getQuotaUsedPercentage() {
		if (isEnabled() && this.getQuotaAssigned() > 0 ) {
			return (short) ((this.getQuotaUsed() * 100) / (this
					.getQuotaAssigned()));
		} else {
			return 0;
		}
	}

	public boolean hasFreeMB(long size) {
		return this.getQuotaAssigned() - this.getQuotaUsed() >= size;
	}

	public boolean isEnabled() {
		return getQuotaStatus() == Constants.QUOTA_ACTIVE;
	}

	public boolean isExceeded() {
		return this.getQuotaAlert() > 0 && (this.getQuotaUsedPercentage() >= this.getQuotaAlert());
	}

	public boolean isUnlimitedQuota() {
		return getQuotaAssigned() == Constants.UNLIMITED_QUOTA;
	}
}