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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.lsug.quota.exception.NoSuchQuotaException;
import org.lsug.quota.model.Quota;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for Quota. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see QuotaLocalServiceUtil
 * @see org.lsug.quota.service.base.QuotaLocalServiceBaseImpl
 * @see org.lsug.quota.service.impl.QuotaLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface QuotaLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link QuotaLocalServiceUtil} to access the quota local service. Add custom service methods to {@link org.lsug.quota.service.impl.QuotaLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasQuota(long groupId, long userId, long size)
		throws PortalException, SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public int countByClassNameId(long classNameId) throws SystemException;

	public int countByClassNameIds(long[] classNameIds)
		throws SystemException;

	public int countByCompanyIdClassNameId(long companyId, long classNameId)
		throws SystemException;

	public int countByCompanyIdClassNameIds(long companyId, long[] classNameIds)
		throws SystemException;

	/**
	* Returns the number of quotas.
	*
	* @return the number of quotas
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getQuotasCount();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Quota> getQuotaByClassNameId(long classNameId, int start,
		int end, OrderByComparator<Quota> orderByComparator)
		throws SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Quota> getQuotaByClassNameIds(long[] classNameIds, int start,
		int end, OrderByComparator<Quota> orderByComparator)
		throws SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Quota> getQuotaByCompanyIdClassNameId(long companyId,
		long classNameId, int start, int end,
		OrderByComparator<Quota> orderByComparator) throws SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Quota> getQuotaByCompanyIdClassNameIds(long companyId,
		long[] classNameIds, int start, int end,
		OrderByComparator<Quota> orderByComparator) throws SystemException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Quota> getQuotas(int start, int end);

	public long calculateServerUsedQuota(long companyId)
		throws SystemException;

	/**
	* Calculate current used quota from a site
	*
	* @param groupId
	* @param quotaUsed
	* @return
	* @throws SystemException
	*/
	public long calculateSiteUsedQuota(long groupId) throws SystemException;

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public Quota addQuota(long companyId, long classNameId, long classPK,
		int quotaAlert, long quotaAssigned, long quotaUsed, int quotaStatus)
		throws SystemException;

	/**
	* Adds the quota to the database. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public Quota addQuota(Quota quota);

	public Quota createDefaultQuota(long companyId, long classNameId,
		long classPK) throws SystemException;

	/**
	* Creates a new quota with the primary key. Does not add the quota to the database.
	*
	* @param quotaId the primary key for the new quota
	* @return the new quota
	*/
	public Quota createQuota(long quotaId);

	public Quota decrementQuota(long classNameId, long classPK, long fileSize)
		throws PortalException, SystemException;

	/**
	* Deletes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param quotaId the primary key of the quota
	* @return the quota that was removed
	* @throws PortalException if a quota with the primary key could not be found
	* @throws SystemException
	*/
	@Indexable(type = IndexableType.DELETE)
	public Quota deleteQuota(long quotaId)
		throws PortalException, SystemException;

	/**
	* Deletes the quota from the database. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public Quota deleteQuota(Quota quota);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Quota fetchQuota(long quotaId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Quota fetchQuotaByClassNameIdClassPK(long classNameId, long classPK)
		throws SystemException;

	/**
	* Returns the quota with the primary key.
	*
	* @param quotaId the primary key of the quota
	* @return the quota
	* @throws PortalException if a quota with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Quota getQuota(long quotaId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Quota getQuotaByClassNameIdClassPK(long classNameId, long classPK)
		throws SystemException, NoSuchQuotaException;

	public Quota incrementQuota(long classNameId, long classPK, long fileSize)
		throws PortalException, SystemException;

	public Quota updateQuota(long classNameId, long classPK, long fileSize)
		throws PortalException, SystemException;

	public Quota updateQuota(long quotaId, long classNameId, long classPK,
		int quotaAlert, long quotaAssigned, long quotaUsed, int quotaStatus)
		throws PortalException, SystemException;

	/**
	* Updates the quota in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param quota the quota
	* @return the quota that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public Quota updateQuota(Quota quota);

	public void checkAlerts(long groupId, long userId)
		throws PortalException, SystemException;

	public void decreaseQuota(long groupId, long userId, long size)
		throws PortalException, SystemException;

	public void increaseQuota(long groupId, long userId, long size)
		throws PortalException, SystemException;
}