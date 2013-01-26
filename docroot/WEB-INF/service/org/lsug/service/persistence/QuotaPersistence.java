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

package org.lsug.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import org.lsug.model.Quota;

/**
 * The persistence interface for the quota service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see QuotaPersistenceImpl
 * @see QuotaUtil
 * @generated
 */
public interface QuotaPersistence extends BasePersistence<Quota> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link QuotaUtil} to access the quota persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the quota in the entity cache if it is enabled.
	*
	* @param quota the quota
	*/
	public void cacheResult(org.lsug.model.Quota quota);

	/**
	* Caches the quotas in the entity cache if it is enabled.
	*
	* @param quotas the quotas
	*/
	public void cacheResult(java.util.List<org.lsug.model.Quota> quotas);

	/**
	* Creates a new quota with the primary key. Does not add the quota to the database.
	*
	* @param qoutaId the primary key for the new quota
	* @return the new quota
	*/
	public org.lsug.model.Quota create(long qoutaId);

	/**
	* Removes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param qoutaId the primary key of the quota
	* @return the quota that was removed
	* @throws org.lsug.NoSuchQuotaException if a quota with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.lsug.model.Quota remove(long qoutaId)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.NoSuchQuotaException;

	public org.lsug.model.Quota updateImpl(org.lsug.model.Quota quota,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the quota with the primary key or throws a {@link org.lsug.NoSuchQuotaException} if it could not be found.
	*
	* @param qoutaId the primary key of the quota
	* @return the quota
	* @throws org.lsug.NoSuchQuotaException if a quota with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.lsug.model.Quota findByPrimaryKey(long qoutaId)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.NoSuchQuotaException;

	/**
	* Returns the quota with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param qoutaId the primary key of the quota
	* @return the quota, or <code>null</code> if a quota with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.lsug.model.Quota fetchByPrimaryKey(long qoutaId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the quotas.
	*
	* @return the quotas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<org.lsug.model.Quota> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the quotas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @return the range of quotas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<org.lsug.model.Quota> findAll(int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the quotas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of quotas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<org.lsug.model.Quota> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the quotas from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of quotas.
	*
	* @return the number of quotas
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}