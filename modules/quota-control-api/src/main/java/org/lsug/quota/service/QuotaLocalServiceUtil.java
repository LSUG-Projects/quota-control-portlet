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

package org.lsug.quota.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for Quota. This utility wraps
 * {@link org.lsug.quota.service.impl.QuotaLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see QuotaLocalService
 * @see org.lsug.quota.service.base.QuotaLocalServiceBaseImpl
 * @see org.lsug.quota.service.impl.QuotaLocalServiceImpl
 * @generated
 */
@ProviderType
public class QuotaLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link org.lsug.quota.service.impl.QuotaLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static boolean hasQuota(long groupId, long userId, long size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasQuota(groupId, userId, size);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countByClassNameId(classNameId);
	}

	public static int countByClassNameIds(long[] classNameIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countByClassNameIds(classNameIds);
	}

	public static int countByCompanyIdClassNameId(long companyId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countByCompanyIdClassNameId(companyId, classNameId);
	}

	public static int countByCompanyIdClassNameIds(long companyId,
		long[] classNameIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countByCompanyIdClassNameIds(companyId, classNameIds);
	}

	/**
	* Returns the number of quotas.
	*
	* @return the number of quotas
	*/
	public static int getQuotasCount() {
		return getService().getQuotasCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.lsug.quota.model.impl.QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.lsug.quota.model.impl.QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static java.util.List<org.lsug.quota.model.Quota> getQuotaByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<org.lsug.quota.model.Quota> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getQuotaByClassNameId(classNameId, start, end,
			orderByComparator);
	}

	public static java.util.List<org.lsug.quota.model.Quota> getQuotaByClassNameIds(
		long[] classNameIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<org.lsug.quota.model.Quota> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getQuotaByClassNameIds(classNameIds, start, end,
			orderByComparator);
	}

	public static java.util.List<org.lsug.quota.model.Quota> getQuotaByCompanyIdClassNameId(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<org.lsug.quota.model.Quota> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getQuotaByCompanyIdClassNameId(companyId, classNameId,
			start, end, orderByComparator);
	}

	public static java.util.List<org.lsug.quota.model.Quota> getQuotaByCompanyIdClassNameIds(
		long companyId, long[] classNameIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<org.lsug.quota.model.Quota> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getQuotaByCompanyIdClassNameIds(companyId, classNameIds,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the quotas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.lsug.quota.model.impl.QuotaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of quotas
	* @param end the upper bound of the range of quotas (not inclusive)
	* @return the range of quotas
	*/
	public static java.util.List<org.lsug.quota.model.Quota> getQuotas(
		int start, int end) {
		return getService().getQuotas(start, end);
	}

	public static long calculateServerUsedQuota(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().calculateServerUsedQuota(companyId);
	}

	/**
	* Calculate current used quota from a site
	*
	* @param groupId
	* @param quotaUsed
	* @return
	* @throws SystemException
	*/
	public static long calculateSiteUsedQuota(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().calculateSiteUsedQuota(groupId);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static org.lsug.quota.model.Quota addQuota(long companyId,
		long classNameId, long classPK, int quotaAlert, long quotaAssigned,
		long quotaUsed, int quotaStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addQuota(companyId, classNameId, classPK, quotaAlert,
			quotaAssigned, quotaUsed, quotaStatus);
	}

	/**
	* Adds the quota to the database. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was added
	*/
	public static org.lsug.quota.model.Quota addQuota(
		org.lsug.quota.model.Quota quota) {
		return getService().addQuota(quota);
	}

	public static org.lsug.quota.model.Quota createDefaultQuota(
		long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().createDefaultQuota(companyId, classNameId, classPK);
	}

	/**
	* Creates a new quota with the primary key. Does not add the quota to the database.
	*
	* @param quotaId the primary key for the new quota
	* @return the new quota
	*/
	public static org.lsug.quota.model.Quota createQuota(long quotaId) {
		return getService().createQuota(quotaId);
	}

	public static org.lsug.quota.model.Quota decrementQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().decrementQuota(classNameId, classPK, fileSize);
	}

	/**
	* Deletes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param quotaId the primary key of the quota
	* @return the quota that was removed
	* @throws PortalException if a quota with the primary key could not be found
	* @throws SystemException
	*/
	public static org.lsug.quota.model.Quota deleteQuota(long quotaId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteQuota(quotaId);
	}

	/**
	* Deletes the quota from the database. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was removed
	*/
	public static org.lsug.quota.model.Quota deleteQuota(
		org.lsug.quota.model.Quota quota) {
		return getService().deleteQuota(quota);
	}

	public static org.lsug.quota.model.Quota fetchQuota(long quotaId) {
		return getService().fetchQuota(quotaId);
	}

	public static org.lsug.quota.model.Quota fetchQuotaByClassNameIdClassPK(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchQuotaByClassNameIdClassPK(classNameId, classPK);
	}

	/**
	* Returns the quota with the primary key.
	*
	* @param quotaId the primary key of the quota
	* @return the quota
	* @throws PortalException if a quota with the primary key could not be found
	*/
	public static org.lsug.quota.model.Quota getQuota(long quotaId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getQuota(quotaId);
	}

	public static org.lsug.quota.model.Quota getQuotaByClassNameIdClassPK(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.quota.exception.NoSuchQuotaException {
		return getService().getQuotaByClassNameIdClassPK(classNameId, classPK);
	}

	public static org.lsug.quota.model.Quota incrementQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().incrementQuota(classNameId, classPK, fileSize);
	}

	public static org.lsug.quota.model.Quota updateQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateQuota(classNameId, classPK, fileSize);
	}

	public static org.lsug.quota.model.Quota updateQuota(long quotaId,
		long classNameId, long classPK, int quotaAlert, long quotaAssigned,
		long quotaUsed, int quotaStatus)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateQuota(quotaId, classNameId, classPK, quotaAlert,
			quotaAssigned, quotaUsed, quotaStatus);
	}

	/**
	* Updates the quota in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was updated
	*/
	public static org.lsug.quota.model.Quota updateQuota(
		org.lsug.quota.model.Quota quota) {
		return getService().updateQuota(quota);
	}

	public static void checkAlerts(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkAlerts(groupId, userId);
	}

	public static void decreaseQuota(long groupId, long userId, long size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().decreaseQuota(groupId, userId, size);
	}

	public static void increaseQuota(long groupId, long userId, long size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().increaseQuota(groupId, userId, size);
	}

	public static QuotaLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<QuotaLocalService, QuotaLocalService> _serviceTracker =
		ServiceTrackerFactory.open(QuotaLocalService.class);
}