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

package org.lsug.quota.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.lsug.quota.model.Quota;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the quota service. This utility wraps {@link org.lsug.quota.service.persistence.impl.QuotaPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see QuotaPersistence
 * @see org.lsug.quota.service.persistence.impl.QuotaPersistenceImpl
 * @generated
 */
@ProviderType
public class QuotaUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Quota quota) {
		getPersistence().clearCache(quota);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Quota> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Quota> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Quota> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator<Quota> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Quota update(Quota quota) {
		return getPersistence().update(quota);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Quota update(Quota quota, ServiceContext serviceContext) {
		return getPersistence().update(quota, serviceContext);
	}

	/**
	* Returns the quota where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchQuotaException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching quota
	* @throws NoSuchQuotaException if a matching quota could not be found
	*/
	public static Quota findByCN_CP(long classNameId, long classPK)
		throws org.lsug.quota.exception.NoSuchQuotaException {
		return getPersistence().findByCN_CP(classNameId, classPK);
	}

	/**
	* Returns the quota where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public static Quota fetchByCN_CP(long classNameId, long classPK) {
		return getPersistence().fetchByCN_CP(classNameId, classPK);
	}

	/**
	* Returns the quota where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public static Quota fetchByCN_CP(long classNameId, long classPK,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByCN_CP(classNameId, classPK, retrieveFromCache);
	}

	/**
	* Removes the quota where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the quota that was removed
	*/
	public static Quota removeByCN_CP(long classNameId, long classPK)
		throws org.lsug.quota.exception.NoSuchQuotaException {
		return getPersistence().removeByCN_CP(classNameId, classPK);
	}

	/**
	* Returns the number of quotas where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching quotas
	*/
	public static int countByCN_CP(long classNameId, long classPK) {
		return getPersistence().countByCN_CP(classNameId, classPK);
	}

	/**
	* Returns all the quotas where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching quotas
	*/
	public static List<Quota> findByCN(long classNameId) {
		return getPersistence().findByCN(classNameId);
	}

	/**
	* Returns a range of all the quotas where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @return the range of matching quotas
	*/
	public static List<Quota> findByCN(long classNameId, int start, int end) {
		return getPersistence().findByCN(classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the quotas where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching quotas
	*/
	public static List<Quota> findByCN(long classNameId, int start, int end,
		OrderByComparator<Quota> orderByComparator) {
		return getPersistence()
				   .findByCN(classNameId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the quotas where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching quotas
	*/
	public static List<Quota> findByCN(long classNameId, int start, int end,
		OrderByComparator<Quota> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByCN(classNameId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first quota in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching quota
	* @throws NoSuchQuotaException if a matching quota could not be found
	*/
	public static Quota findByCN_First(long classNameId,
		OrderByComparator<Quota> orderByComparator)
		throws org.lsug.quota.exception.NoSuchQuotaException {
		return getPersistence().findByCN_First(classNameId, orderByComparator);
	}

	/**
	* Returns the first quota in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public static Quota fetchByCN_First(long classNameId,
		OrderByComparator<Quota> orderByComparator) {
		return getPersistence().fetchByCN_First(classNameId, orderByComparator);
	}

	/**
	* Returns the last quota in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching quota
	* @throws NoSuchQuotaException if a matching quota could not be found
	*/
	public static Quota findByCN_Last(long classNameId,
		OrderByComparator<Quota> orderByComparator)
		throws org.lsug.quota.exception.NoSuchQuotaException {
		return getPersistence().findByCN_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the last quota in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public static Quota fetchByCN_Last(long classNameId,
		OrderByComparator<Quota> orderByComparator) {
		return getPersistence().fetchByCN_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the quotas before and after the current quota in the ordered set where classNameId = &#63;.
	*
	* @param quotaId the primary key of the current quota
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next quota
	* @throws NoSuchQuotaException if a quota with the primary key could not be found
	*/
	public static Quota[] findByCN_PrevAndNext(long quotaId, long classNameId,
		OrderByComparator<Quota> orderByComparator)
		throws org.lsug.quota.exception.NoSuchQuotaException {
		return getPersistence()
				   .findByCN_PrevAndNext(quotaId, classNameId, orderByComparator);
	}

	/**
	* Returns all the quotas where classNameId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameIds the class name IDs
	* @return the matching quotas
	*/
	public static List<Quota> findByCN(long[] classNameIds) {
		return getPersistence().findByCN(classNameIds);
	}

	/**
	* Returns a range of all the quotas where classNameId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameIds the class name IDs
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @return the range of matching quotas
	*/
	public static List<Quota> findByCN(long[] classNameIds, int start, int end) {
		return getPersistence().findByCN(classNameIds, start, end);
	}

	/**
	* Returns an ordered range of all the quotas where classNameId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameIds the class name IDs
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching quotas
	*/
	public static List<Quota> findByCN(long[] classNameIds, int start, int end,
		OrderByComparator<Quota> orderByComparator) {
		return getPersistence()
				   .findByCN(classNameIds, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the quotas where classNameId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching quotas
	*/
	public static List<Quota> findByCN(long[] classNameIds, int start, int end,
		OrderByComparator<Quota> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByCN(classNameIds, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Removes all the quotas where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public static void removeByCN(long classNameId) {
		getPersistence().removeByCN(classNameId);
	}

	/**
	* Returns the number of quotas where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching quotas
	*/
	public static int countByCN(long classNameId) {
		return getPersistence().countByCN(classNameId);
	}

	/**
	* Returns the number of quotas where classNameId = any &#63;.
	*
	* @param classNameIds the class name IDs
	* @return the number of matching quotas
	*/
	public static int countByCN(long[] classNameIds) {
		return getPersistence().countByCN(classNameIds);
	}

	/**
	* Returns all the quotas where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching quotas
	*/
	public static List<Quota> findByC_CN(long companyId, long classNameId) {
		return getPersistence().findByC_CN(companyId, classNameId);
	}

	/**
	* Returns a range of all the quotas where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @return the range of matching quotas
	*/
	public static List<Quota> findByC_CN(long companyId, long classNameId,
		int start, int end) {
		return getPersistence().findByC_CN(companyId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the quotas where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching quotas
	*/
	public static List<Quota> findByC_CN(long companyId, long classNameId,
		int start, int end, OrderByComparator<Quota> orderByComparator) {
		return getPersistence()
				   .findByC_CN(companyId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the quotas where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching quotas
	*/
	public static List<Quota> findByC_CN(long companyId, long classNameId,
		int start, int end, OrderByComparator<Quota> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_CN(companyId, classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching quota
	* @throws NoSuchQuotaException if a matching quota could not be found
	*/
	public static Quota findByC_CN_First(long companyId, long classNameId,
		OrderByComparator<Quota> orderByComparator)
		throws org.lsug.quota.exception.NoSuchQuotaException {
		return getPersistence()
				   .findByC_CN_First(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the first quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public static Quota fetchByC_CN_First(long companyId, long classNameId,
		OrderByComparator<Quota> orderByComparator) {
		return getPersistence()
				   .fetchByC_CN_First(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the last quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching quota
	* @throws NoSuchQuotaException if a matching quota could not be found
	*/
	public static Quota findByC_CN_Last(long companyId, long classNameId,
		OrderByComparator<Quota> orderByComparator)
		throws org.lsug.quota.exception.NoSuchQuotaException {
		return getPersistence()
				   .findByC_CN_Last(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the last quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public static Quota fetchByC_CN_Last(long companyId, long classNameId,
		OrderByComparator<Quota> orderByComparator) {
		return getPersistence()
				   .fetchByC_CN_Last(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the quotas before and after the current quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param quotaId the primary key of the current quota
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next quota
	* @throws NoSuchQuotaException if a quota with the primary key could not be found
	*/
	public static Quota[] findByC_CN_PrevAndNext(long quotaId, long companyId,
		long classNameId, OrderByComparator<Quota> orderByComparator)
		throws org.lsug.quota.exception.NoSuchQuotaException {
		return getPersistence()
				   .findByC_CN_PrevAndNext(quotaId, companyId, classNameId,
			orderByComparator);
	}

	/**
	* Returns all the quotas where companyId = &#63; and classNameId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameIds the class name IDs
	* @return the matching quotas
	*/
	public static List<Quota> findByC_CN(long companyId, long[] classNameIds) {
		return getPersistence().findByC_CN(companyId, classNameIds);
	}

	/**
	* Returns a range of all the quotas where companyId = &#63; and classNameId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameIds the class name IDs
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @return the range of matching quotas
	*/
	public static List<Quota> findByC_CN(long companyId, long[] classNameIds,
		int start, int end) {
		return getPersistence().findByC_CN(companyId, classNameIds, start, end);
	}

	/**
	* Returns an ordered range of all the quotas where companyId = &#63; and classNameId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameIds the class name IDs
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching quotas
	*/
	public static List<Quota> findByC_CN(long companyId, long[] classNameIds,
		int start, int end, OrderByComparator<Quota> orderByComparator) {
		return getPersistence()
				   .findByC_CN(companyId, classNameIds, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the quotas where companyId = &#63; and classNameId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching quotas
	*/
	public static List<Quota> findByC_CN(long companyId, long[] classNameIds,
		int start, int end, OrderByComparator<Quota> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_CN(companyId, classNameIds, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the quotas where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	*/
	public static void removeByC_CN(long companyId, long classNameId) {
		getPersistence().removeByC_CN(companyId, classNameId);
	}

	/**
	* Returns the number of quotas where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching quotas
	*/
	public static int countByC_CN(long companyId, long classNameId) {
		return getPersistence().countByC_CN(companyId, classNameId);
	}

	/**
	* Returns the number of quotas where companyId = &#63; and classNameId = any &#63;.
	*
	* @param companyId the company ID
	* @param classNameIds the class name IDs
	* @return the number of matching quotas
	*/
	public static int countByC_CN(long companyId, long[] classNameIds) {
		return getPersistence().countByC_CN(companyId, classNameIds);
	}

	/**
	* Caches the quota in the entity cache if it is enabled.
	*
	* @param quota the quota
	*/
	public static void cacheResult(Quota quota) {
		getPersistence().cacheResult(quota);
	}

	/**
	* Caches the quotas in the entity cache if it is enabled.
	*
	* @param quotas the quotas
	*/
	public static void cacheResult(List<Quota> quotas) {
		getPersistence().cacheResult(quotas);
	}

	/**
	* Creates a new quota with the primary key. Does not add the quota to the database.
	*
	* @param quotaId the primary key for the new quota
	* @return the new quota
	*/
	public static Quota create(long quotaId) {
		return getPersistence().create(quotaId);
	}

	/**
	* Removes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param quotaId the primary key of the quota
	* @return the quota that was removed
	* @throws NoSuchQuotaException if a quota with the primary key could not be found
	*/
	public static Quota remove(long quotaId)
		throws org.lsug.quota.exception.NoSuchQuotaException {
		return getPersistence().remove(quotaId);
	}

	public static Quota updateImpl(Quota quota) {
		return getPersistence().updateImpl(quota);
	}

	/**
	* Returns the quota with the primary key or throws a {@link NoSuchQuotaException} if it could not be found.
	*
	* @param quotaId the primary key of the quota
	* @return the quota
	* @throws NoSuchQuotaException if a quota with the primary key could not be found
	*/
	public static Quota findByPrimaryKey(long quotaId)
		throws org.lsug.quota.exception.NoSuchQuotaException {
		return getPersistence().findByPrimaryKey(quotaId);
	}

	/**
	* Returns the quota with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param quotaId the primary key of the quota
	* @return the quota, or <code>null</code> if a quota with the primary key could not be found
	*/
	public static Quota fetchByPrimaryKey(long quotaId) {
		return getPersistence().fetchByPrimaryKey(quotaId);
	}

	public static java.util.Map<java.io.Serializable, Quota> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the quotas.
	*
	* @return the quotas
	*/
	public static List<Quota> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the quotas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @return the range of quotas
	*/
	public static List<Quota> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the quotas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of quotas
	*/
	public static List<Quota> findAll(int start, int end,
		OrderByComparator<Quota> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the quotas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of quotas
	*/
	public static List<Quota> findAll(int start, int end,
		OrderByComparator<Quota> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the quotas from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of quotas.
	*
	* @return the number of quotas
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static QuotaPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<QuotaPersistence, QuotaPersistence> _serviceTracker =
		ServiceTrackerFactory.open(QuotaPersistence.class);
}