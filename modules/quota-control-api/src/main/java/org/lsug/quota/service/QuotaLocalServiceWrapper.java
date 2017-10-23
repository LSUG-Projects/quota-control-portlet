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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link QuotaLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see QuotaLocalService
 * @generated
 */
@ProviderType
public class QuotaLocalServiceWrapper implements QuotaLocalService,
	ServiceWrapper<QuotaLocalService> {
	public QuotaLocalServiceWrapper(QuotaLocalService quotaLocalService) {
		_quotaLocalService = quotaLocalService;
	}

	@Override
	public boolean hasQuota(long groupId, long userId, long size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.hasQuota(groupId, userId, size);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _quotaLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _quotaLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _quotaLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _quotaLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _quotaLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.countByClassNameId(classNameId);
	}

	@Override
	public int countByClassNameIds(long[] classNameIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.countByClassNameIds(classNameIds);
	}

	@Override
	public int countByCompanyIdClassNameId(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.countByCompanyIdClassNameId(companyId,
			classNameId);
	}

	@Override
	public int countByCompanyIdClassNameIds(long companyId, long[] classNameIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.countByCompanyIdClassNameIds(companyId,
			classNameIds);
	}

	/**
	* Returns the number of quotas.
	*
	* @return the number of quotas
	*/
	@Override
	public int getQuotasCount() {
		return _quotaLocalService.getQuotasCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _quotaLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _quotaLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _quotaLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _quotaLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<org.lsug.quota.model.Quota> getQuotaByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<org.lsug.quota.model.Quota> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuotaByClassNameId(classNameId, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<org.lsug.quota.model.Quota> getQuotaByClassNameIds(
		long[] classNameIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<org.lsug.quota.model.Quota> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuotaByClassNameIds(classNameIds, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<org.lsug.quota.model.Quota> getQuotaByCompanyIdClassNameId(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<org.lsug.quota.model.Quota> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuotaByCompanyIdClassNameId(companyId,
			classNameId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<org.lsug.quota.model.Quota> getQuotaByCompanyIdClassNameIds(
		long companyId, long[] classNameIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<org.lsug.quota.model.Quota> orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuotaByCompanyIdClassNameIds(companyId,
			classNameIds, start, end, orderByComparator);
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
	@Override
	public java.util.List<org.lsug.quota.model.Quota> getQuotas(int start,
		int end) {
		return _quotaLocalService.getQuotas(start, end);
	}

	@Override
	public long calculateServerUsedQuota(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.calculateServerUsedQuota(companyId);
	}

	/**
	* Calculate current used quota from a site
	*
	* @param groupId
	* @param quotaUsed
	* @return
	* @throws SystemException
	*/
	@Override
	public long calculateSiteUsedQuota(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.calculateSiteUsedQuota(groupId);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _quotaLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _quotaLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public org.lsug.quota.model.Quota addQuota(long companyId,
		long classNameId, long classPK, int quotaAlert, long quotaAssigned,
		long quotaUsed, int quotaStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.addQuota(companyId, classNameId, classPK,
			quotaAlert, quotaAssigned, quotaUsed, quotaStatus);
	}

	/**
	* Adds the quota to the database. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was added
	*/
	@Override
	public org.lsug.quota.model.Quota addQuota(org.lsug.quota.model.Quota quota) {
		return _quotaLocalService.addQuota(quota);
	}

	@Override
	public org.lsug.quota.model.Quota createDefaultQuota(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.createDefaultQuota(companyId, classNameId,
			classPK);
	}

	/**
	* Creates a new quota with the primary key. Does not add the quota to the database.
	*
	* @param quotaId the primary key for the new quota
	* @return the new quota
	*/
	@Override
	public org.lsug.quota.model.Quota createQuota(long quotaId) {
		return _quotaLocalService.createQuota(quotaId);
	}

	@Override
	public org.lsug.quota.model.Quota decrementQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.decrementQuota(classNameId, classPK, fileSize);
	}

	/**
	* Deletes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param quotaId the primary key of the quota
	* @return the quota that was removed
	* @throws PortalException if a quota with the primary key could not be found
	* @throws SystemException
	*/
	@Override
	public org.lsug.quota.model.Quota deleteQuota(long quotaId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.deleteQuota(quotaId);
	}

	/**
	* Deletes the quota from the database. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was removed
	*/
	@Override
	public org.lsug.quota.model.Quota deleteQuota(
		org.lsug.quota.model.Quota quota) {
		return _quotaLocalService.deleteQuota(quota);
	}

	@Override
	public org.lsug.quota.model.Quota fetchQuota(long quotaId) {
		return _quotaLocalService.fetchQuota(quotaId);
	}

	@Override
	public org.lsug.quota.model.Quota fetchQuotaByClassNameIdClassPK(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.fetchQuotaByClassNameIdClassPK(classNameId,
			classPK);
	}

	/**
	* Returns the quota with the primary key.
	*
	* @param quotaId the primary key of the quota
	* @return the quota
	* @throws PortalException if a quota with the primary key could not be found
	*/
	@Override
	public org.lsug.quota.model.Quota getQuota(long quotaId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _quotaLocalService.getQuota(quotaId);
	}

	@Override
	public org.lsug.quota.model.Quota getQuotaByClassNameIdClassPK(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.quota.exception.NoSuchQuotaException {
		return _quotaLocalService.getQuotaByClassNameIdClassPK(classNameId,
			classPK);
	}

	@Override
	public org.lsug.quota.model.Quota incrementQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.incrementQuota(classNameId, classPK, fileSize);
	}

	@Override
	public org.lsug.quota.model.Quota updateQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.updateQuota(classNameId, classPK, fileSize);
	}

	@Override
	public org.lsug.quota.model.Quota updateQuota(long quotaId,
		long classNameId, long classPK, int quotaAlert, long quotaAssigned,
		long quotaUsed, int quotaStatus)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.updateQuota(quotaId, classNameId, classPK,
			quotaAlert, quotaAssigned, quotaUsed, quotaStatus);
	}

	/**
	* Updates the quota in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was updated
	*/
	@Override
	public org.lsug.quota.model.Quota updateQuota(
		org.lsug.quota.model.Quota quota) {
		return _quotaLocalService.updateQuota(quota);
	}

	@Override
	public void checkAlerts(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_quotaLocalService.checkAlerts(groupId, userId);
	}

	@Override
	public void decreaseQuota(long groupId, long userId, long size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_quotaLocalService.decreaseQuota(groupId, userId, size);
	}

	@Override
	public void increaseQuota(long groupId, long userId, long size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_quotaLocalService.increaseQuota(groupId, userId, size);
	}

	@Override
	public QuotaLocalService getWrappedService() {
		return _quotaLocalService;
	}

	@Override
	public void setWrappedService(QuotaLocalService quotaLocalService) {
		_quotaLocalService = quotaLocalService;
	}

	private QuotaLocalService _quotaLocalService;
}