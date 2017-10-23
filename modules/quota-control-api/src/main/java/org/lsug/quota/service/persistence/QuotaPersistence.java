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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.lsug.quota.exception.NoSuchQuotaException;
import org.lsug.quota.model.Quota;

/**
 * The persistence interface for the quota service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see org.lsug.quota.service.persistence.impl.QuotaPersistenceImpl
 * @see QuotaUtil
 * @generated
 */
@ProviderType
public interface QuotaPersistence extends BasePersistence<Quota> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link QuotaUtil} to access the quota persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the quota where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchQuotaException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching quota
	* @throws NoSuchQuotaException if a matching quota could not be found
	*/
	public Quota findByCN_CP(long classNameId, long classPK)
		throws NoSuchQuotaException;

	/**
	* Returns the quota where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public Quota fetchByCN_CP(long classNameId, long classPK);

	/**
	* Returns the quota where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public Quota fetchByCN_CP(long classNameId, long classPK,
		boolean retrieveFromCache);

	/**
	* Removes the quota where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the quota that was removed
	*/
	public Quota removeByCN_CP(long classNameId, long classPK)
		throws NoSuchQuotaException;

	/**
	* Returns the number of quotas where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching quotas
	*/
	public int countByCN_CP(long classNameId, long classPK);

	/**
	* Returns all the quotas where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching quotas
	*/
	public java.util.List<Quota> findByCN(long classNameId);

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
	public java.util.List<Quota> findByCN(long classNameId, int start, int end);

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
	public java.util.List<Quota> findByCN(long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator);

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
	public java.util.List<Quota> findByCN(long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first quota in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching quota
	* @throws NoSuchQuotaException if a matching quota could not be found
	*/
	public Quota findByCN_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator)
		throws NoSuchQuotaException;

	/**
	* Returns the first quota in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public Quota fetchByCN_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator);

	/**
	* Returns the last quota in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching quota
	* @throws NoSuchQuotaException if a matching quota could not be found
	*/
	public Quota findByCN_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator)
		throws NoSuchQuotaException;

	/**
	* Returns the last quota in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public Quota fetchByCN_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator);

	/**
	* Returns the quotas before and after the current quota in the ordered set where classNameId = &#63;.
	*
	* @param quotaId the primary key of the current quota
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next quota
	* @throws NoSuchQuotaException if a quota with the primary key could not be found
	*/
	public Quota[] findByCN_PrevAndNext(long quotaId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator)
		throws NoSuchQuotaException;

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
	public java.util.List<Quota> findByCN(long[] classNameIds);

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
	public java.util.List<Quota> findByCN(long[] classNameIds, int start,
		int end);

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
	public java.util.List<Quota> findByCN(long[] classNameIds, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator);

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
	public java.util.List<Quota> findByCN(long[] classNameIds, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the quotas where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public void removeByCN(long classNameId);

	/**
	* Returns the number of quotas where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching quotas
	*/
	public int countByCN(long classNameId);

	/**
	* Returns the number of quotas where classNameId = any &#63;.
	*
	* @param classNameIds the class name IDs
	* @return the number of matching quotas
	*/
	public int countByCN(long[] classNameIds);

	/**
	* Returns all the quotas where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching quotas
	*/
	public java.util.List<Quota> findByC_CN(long companyId, long classNameId);

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
	public java.util.List<Quota> findByC_CN(long companyId, long classNameId,
		int start, int end);

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
	public java.util.List<Quota> findByC_CN(long companyId, long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator);

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
	public java.util.List<Quota> findByC_CN(long companyId, long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching quota
	* @throws NoSuchQuotaException if a matching quota could not be found
	*/
	public Quota findByC_CN_First(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator)
		throws NoSuchQuotaException;

	/**
	* Returns the first quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public Quota fetchByC_CN_First(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator);

	/**
	* Returns the last quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching quota
	* @throws NoSuchQuotaException if a matching quota could not be found
	*/
	public Quota findByC_CN_Last(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator)
		throws NoSuchQuotaException;

	/**
	* Returns the last quota in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching quota, or <code>null</code> if a matching quota could not be found
	*/
	public Quota fetchByC_CN_Last(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator);

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
	public Quota[] findByC_CN_PrevAndNext(long quotaId, long companyId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator)
		throws NoSuchQuotaException;

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
	public java.util.List<Quota> findByC_CN(long companyId, long[] classNameIds);

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
	public java.util.List<Quota> findByC_CN(long companyId,
		long[] classNameIds, int start, int end);

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
	public java.util.List<Quota> findByC_CN(long companyId,
		long[] classNameIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator);

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
	public java.util.List<Quota> findByC_CN(long companyId,
		long[] classNameIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the quotas where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	*/
	public void removeByC_CN(long companyId, long classNameId);

	/**
	* Returns the number of quotas where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching quotas
	*/
	public int countByC_CN(long companyId, long classNameId);

	/**
	* Returns the number of quotas where companyId = &#63; and classNameId = any &#63;.
	*
	* @param companyId the company ID
	* @param classNameIds the class name IDs
	* @return the number of matching quotas
	*/
	public int countByC_CN(long companyId, long[] classNameIds);

	/**
	* Caches the quota in the entity cache if it is enabled.
	*
	* @param quota the quota
	*/
	public void cacheResult(Quota quota);

	/**
	* Caches the quotas in the entity cache if it is enabled.
	*
	* @param quotas the quotas
	*/
	public void cacheResult(java.util.List<Quota> quotas);

	/**
	* Creates a new quota with the primary key. Does not add the quota to the database.
	*
	* @param quotaId the primary key for the new quota
	* @return the new quota
	*/
	public Quota create(long quotaId);

	/**
	* Removes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param quotaId the primary key of the quota
	* @return the quota that was removed
	* @throws NoSuchQuotaException if a quota with the primary key could not be found
	*/
	public Quota remove(long quotaId) throws NoSuchQuotaException;

	public Quota updateImpl(Quota quota);

	/**
	* Returns the quota with the primary key or throws a {@link NoSuchQuotaException} if it could not be found.
	*
	* @param quotaId the primary key of the quota
	* @return the quota
	* @throws NoSuchQuotaException if a quota with the primary key could not be found
	*/
	public Quota findByPrimaryKey(long quotaId) throws NoSuchQuotaException;

	/**
	* Returns the quota with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param quotaId the primary key of the quota
	* @return the quota, or <code>null</code> if a quota with the primary key could not be found
	*/
	public Quota fetchByPrimaryKey(long quotaId);

	@Override
	public java.util.Map<java.io.Serializable, Quota> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the quotas.
	*
	* @return the quotas
	*/
	public java.util.List<Quota> findAll();

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
	public java.util.List<Quota> findAll(int start, int end);

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
	public java.util.List<Quota> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator);

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
	public java.util.List<Quota> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Quota> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the quotas from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of quotas.
	*
	* @return the number of quotas
	*/
	public int countAll();
}