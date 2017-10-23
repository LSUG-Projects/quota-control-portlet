/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package org.lsug.quota.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the Quota service. Represents a row in the &quot;LSUGQUOTA_Quota&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see QuotaModel
 * @see org.lsug.quota.model.impl.QuotaImpl
 * @see org.lsug.quota.model.impl.QuotaModelImpl
 * @generated
 */
@ImplementationClassName("org.lsug.quota.model.impl.QuotaImpl")
@ProviderType
public interface Quota extends QuotaModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link org.lsug.quota.model.impl.QuotaImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Quota, Long> QUOTA_ID_ACCESSOR = new Accessor<Quota, Long>() {
			@Override
			public Long get(Quota quota) {
				return quota.getQuotaId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Quota> getTypeClass() {
				return Quota.class;
			}
		};

	public short getQuotaUsedPercentage();

	public boolean hasFreeMB(long size);

	public boolean isEnabled();

	public boolean isExceeded();

	public boolean isUnlimitedQuota();
}