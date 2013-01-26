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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import org.lsug.model.Quota;

import java.util.List;

/**
 * The persistence utility for the quota service. This utility wraps {@link QuotaPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see QuotaPersistence
 * @see QuotaPersistenceImpl
 * @generated
 */
public class QuotaUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Quota quota) {
		getPersistence().clearCache(quota);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Quota> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Quota> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Quota> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Quota update(Quota quota, boolean merge)
		throws SystemException {
		return getPersistence().update(quota, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Quota update(Quota quota, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(quota, merge, serviceContext);
	}

	/**
	* Caches the quota in the entity cache if it is enabled.
	*
	* @param quota the quota
	*/
	public static void cacheResult(org.lsug.model.Quota quota) {
		getPersistence().cacheResult(quota);
	}

	/**
	* Caches the quotas in the entity cache if it is enabled.
	*
	* @param quotas the quotas
	*/
	public static void cacheResult(java.util.List<org.lsug.model.Quota> quotas) {
		getPersistence().cacheResult(quotas);
	}

	/**
	* Creates a new quota with the primary key. Does not add the quota to the database.
	*
	* @param qoutaId the primary key for the new quota
	* @return the new quota
	*/
	public static org.lsug.model.Quota create(long qoutaId) {
		return getPersistence().create(qoutaId);
	}

	/**
	* Removes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param qoutaId the primary key of the quota
	* @return the quota that was removed
	* @throws org.lsug.NoSuchQuotaException if a quota with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static org.lsug.model.Quota remove(long qoutaId)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.NoSuchQuotaException {
		return getPersistence().remove(qoutaId);
	}

	public static org.lsug.model.Quota updateImpl(org.lsug.model.Quota quota,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(quota, merge);
	}

	/**
	* Returns the quota with the primary key or throws a {@link org.lsug.NoSuchQuotaException} if it could not be found.
	*
	* @param qoutaId the primary key of the quota
	* @return the quota
	* @throws org.lsug.NoSuchQuotaException if a quota with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static org.lsug.model.Quota findByPrimaryKey(long qoutaId)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.NoSuchQuotaException {
		return getPersistence().findByPrimaryKey(qoutaId);
	}

	/**
	* Returns the quota with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param qoutaId the primary key of the quota
	* @return the quota, or <code>null</code> if a quota with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static org.lsug.model.Quota fetchByPrimaryKey(long qoutaId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(qoutaId);
	}

	/**
	* Returns all the quotas.
	*
	* @return the quotas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<org.lsug.model.Quota> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<org.lsug.model.Quota> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<org.lsug.model.Quota> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the quotas from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of quotas.
	*
	* @return the number of quotas
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static QuotaPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (QuotaPersistence)PortletBeanLocatorUtil.locate(org.lsug.service.ClpSerializer.getServletContextName(),
					QuotaPersistence.class.getName());

			ReferenceRegistry.registerReference(QuotaUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(QuotaPersistence persistence) {
	}

	private static QuotaPersistence _persistence;
}