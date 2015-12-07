/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link QuotaLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see QuotaLocalService
 * @generated
 */
public class QuotaLocalServiceWrapper implements QuotaLocalService,
	ServiceWrapper<QuotaLocalService> {
	public QuotaLocalServiceWrapper(QuotaLocalService quotaLocalService) {
		_quotaLocalService = quotaLocalService;
	}

	/**
	* Adds the quota to the database. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public org.lsug.quota.model.Quota addQuota(org.lsug.quota.model.Quota quota)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.addQuota(quota);
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

	/**
	* Deletes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param quotaId the primary key of the quota
	* @return the quota that was removed
	* @throws PortalException if a quota with the primary key could not be found
	* @throws SystemException if a system exception occurred
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public org.lsug.quota.model.Quota deleteQuota(
		org.lsug.quota.model.Quota quota)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.deleteQuota(quota);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _quotaLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public org.lsug.quota.model.Quota fetchQuota(long quotaId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.fetchQuota(quotaId);
	}

	/**
	* Returns the quota with the primary key.
	*
	* @param quotaId the primary key of the quota
	* @return the quota
	* @throws PortalException if a quota with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public org.lsug.quota.model.Quota getQuota(long quotaId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuota(quotaId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getPersistedModel(primaryKeyObj);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<org.lsug.quota.model.Quota> getQuotas(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuotas(start, end);
	}

	/**
	* Returns the number of quotas.
	*
	* @return the number of quotas
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getQuotasCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuotasCount();
	}

	/**
	* Updates the quota in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public org.lsug.quota.model.Quota updateQuota(
		org.lsug.quota.model.Quota quota)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.updateQuota(quota);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _quotaLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_quotaLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _quotaLocalService.invokeMethod(name, parameterTypes, arguments);
	}

	@Override
	public org.lsug.quota.model.Quota addQuota(long companyId,
		long classNameId, long classPK, int quotaAlert, long quotaAssigned,
		long quotaUsed, int quotaStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.addQuota(companyId, classNameId, classPK,
			quotaAlert, quotaAssigned, quotaUsed, quotaStatus);
	}

	@Override
	public org.lsug.quota.model.Quota createDefaultQuota(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.createDefaultQuota(companyId, classNameId,
			classPK);
	}

	@Override
	public org.lsug.quota.model.Quota getQuotaByClassNameIdClassPK(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.quota.NoSuchQuotaException {
		return _quotaLocalService.getQuotaByClassNameIdClassPK(classNameId,
			classPK);
	}

	@Override
	public java.util.List<org.lsug.quota.model.Quota> getQuotaByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuotaByClassNameId(classNameId, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<org.lsug.quota.model.Quota> getQuotaByClassNameIds(
		long[] classNameIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuotaByClassNameIds(classNameIds, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<org.lsug.quota.model.Quota> getQuotaByCompanyIdClassNameId(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuotaByCompanyIdClassNameId(companyId,
			classNameId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<org.lsug.quota.model.Quota> getQuotaByCompanyIdClassNameIds(
		long companyId, long[] classNameIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.getQuotaByCompanyIdClassNameIds(companyId,
			classNameIds, start, end, orderByComparator);
	}

	@Override
	public org.lsug.quota.model.Quota fetchQuotaByClassNameIdClassPK(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.fetchQuotaByClassNameIdClassPK(classNameId,
			classPK);
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

	@Override
	public org.lsug.quota.model.Quota decrementQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.decrementQuota(classNameId, classPK, fileSize);
	}

	@Override
	public org.lsug.quota.model.Quota incrementQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.incrementQuota(classNameId, classPK, fileSize);
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
	public boolean hasQuota(long groupId, long userId, long size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _quotaLocalService.hasQuota(groupId, userId, size);
	}

	@Override
	public void checkAlerts(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_quotaLocalService.checkAlerts(groupId, userId);
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
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public QuotaLocalService getWrappedQuotaLocalService() {
		return _quotaLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedQuotaLocalService(QuotaLocalService quotaLocalService) {
		_quotaLocalService = quotaLocalService;
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