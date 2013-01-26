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

package org.lsug.quota.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the quota local service. This utility wraps {@link org.lsug.quota.service.impl.QuotaLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see QuotaLocalService
 * @see org.lsug.quota.service.base.QuotaLocalServiceBaseImpl
 * @see org.lsug.quota.service.impl.QuotaLocalServiceImpl
 * @generated
 */
public class QuotaLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link org.lsug.quota.service.impl.QuotaLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the quota to the database. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was added
	* @throws SystemException if a system exception occurred
	*/
	public static org.lsug.quota.model.Quota addQuota(
		org.lsug.quota.model.Quota quota)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addQuota(quota);
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

	/**
	* Deletes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param quotaId the primary key of the quota
	* @return the quota that was removed
	* @throws PortalException if a quota with the primary key could not be found
	* @throws SystemException if a system exception occurred
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
	* @throws SystemException if a system exception occurred
	*/
	public static org.lsug.quota.model.Quota deleteQuota(
		org.lsug.quota.model.Quota quota)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteQuota(quota);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static org.lsug.quota.model.Quota fetchQuota(long quotaId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchQuota(quotaId);
	}

	/**
	* Returns the quota with the primary key.
	*
	* @param quotaId the primary key of the quota
	* @return the quota
	* @throws PortalException if a quota with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static org.lsug.quota.model.Quota getQuota(long quotaId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getQuota(quotaId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<org.lsug.quota.model.Quota> getQuotas(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getQuotas(start, end);
	}

	/**
	* Returns the number of quotas.
	*
	* @return the number of quotas
	* @throws SystemException if a system exception occurred
	*/
	public static int getQuotasCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getQuotasCount();
	}

	/**
	* Updates the quota in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static org.lsug.quota.model.Quota updateQuota(
		org.lsug.quota.model.Quota quota)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateQuota(quota);
	}

	/**
	* Updates the quota in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @param merge whether to merge the quota with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the quota that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static org.lsug.quota.model.Quota updateQuota(
		org.lsug.quota.model.Quota quota, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateQuota(quota, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static org.lsug.quota.model.Quota getQuotaByClassNameIdClassPK(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.quota.NoSuchQuotaException {
		return getService().getQuotaByClassNameIdClassPK(classNameId, classPK);
	}

	public static org.lsug.quota.model.Quota updateQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.quota.NoSuchQuotaException {
		return getService().updateQuota(classNameId, classPK, fileSize);
	}

	public static org.lsug.quota.model.Quota updateQuota(long quotaId,
		long classNameId, long classPK, int quotaAlert, long quotaAssigned,
		long quotaUsed)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.quota.NoSuchQuotaException {
		return getService()
				   .updateQuota(quotaId, classNameId, classPK, quotaAlert,
			quotaAssigned, quotaUsed);
	}

	public static org.lsug.quota.model.Quota decrementQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.quota.NoSuchQuotaException {
		return getService().decrementQuota(classNameId, classPK, fileSize);
	}

	public static org.lsug.quota.model.Quota incrementQuota(long classNameId,
		long classPK, long fileSize)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.lsug.quota.NoSuchQuotaException {
		return getService().incrementQuota(classNameId, classPK, fileSize);
	}

	public static void clearService() {
		_service = null;
	}

	public static QuotaLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					QuotaLocalService.class.getName());

			if (invokableLocalService instanceof QuotaLocalService) {
				_service = (QuotaLocalService)invokableLocalService;
			}
			else {
				_service = new QuotaLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(QuotaLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(QuotaLocalService service) {
	}

	private static QuotaLocalService _service;
}