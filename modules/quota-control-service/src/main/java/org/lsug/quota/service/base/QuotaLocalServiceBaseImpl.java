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

package org.lsug.quota.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.service.persistence.DLFileEntryPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.CompanyPersistence;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.RolePersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupRolePersistence;
import com.liferay.portal.kernel.service.persistence.UserNotificationEventPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalService;
import org.lsug.quota.service.persistence.QuotaPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the quota local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link org.lsug.quota.service.impl.QuotaLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see org.lsug.quota.service.impl.QuotaLocalServiceImpl
 * @see org.lsug.quota.service.QuotaLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class QuotaLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements QuotaLocalService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link org.lsug.quota.service.QuotaLocalServiceUtil} to access the quota local service.
	 */

	/**
	 * Adds the quota to the database. Also notifies the appropriate model listeners.
	 *
	 * @param quota the quota
	 * @return the quota that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Quota addQuota(Quota quota) {
		quota.setNew(true);

		return quotaPersistence.update(quota);
	}

	/**
	 * Creates a new quota with the primary key. Does not add the quota to the database.
	 *
	 * @param quotaId the primary key for the new quota
	 * @return the new quota
	 */
	@Override
	public Quota createQuota(long quotaId) {
		return quotaPersistence.create(quotaId);
	}

	/**
	 * Deletes the quota with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param quotaId the primary key of the quota
	 * @return the quota that was removed
	 * @throws PortalException if a quota with the primary key could not be found
	 * @throws SystemException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Quota deleteQuota(long quotaId)
		throws PortalException, SystemException {
		return quotaPersistence.remove(quotaId);
	}

	/**
	 * Deletes the quota from the database. Also notifies the appropriate model listeners.
	 *
	 * @param quota the quota
	 * @return the quota that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Quota deleteQuota(Quota quota) {
		return quotaPersistence.remove(quota);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(Quota.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return quotaPersistence.findWithDynamicQuery(dynamicQuery);
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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return quotaPersistence.findWithDynamicQuery(dynamicQuery, start, end);
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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return quotaPersistence.findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return quotaPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return quotaPersistence.countWithDynamicQuery(dynamicQuery, projection);
	}

	@Override
	public Quota fetchQuota(long quotaId) {
		return quotaPersistence.fetchByPrimaryKey(quotaId);
	}

	/**
	 * Returns the quota with the primary key.
	 *
	 * @param quotaId the primary key of the quota
	 * @return the quota
	 * @throws PortalException if a quota with the primary key could not be found
	 */
	@Override
	public Quota getQuota(long quotaId) throws PortalException {
		return quotaPersistence.findByPrimaryKey(quotaId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(quotaLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Quota.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("quotaId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(quotaLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(Quota.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("quotaId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(quotaLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Quota.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("quotaId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return quotaLocalService.deleteQuota((Quota)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return quotaPersistence.findByPrimaryKey(primaryKeyObj);
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
	public List<Quota> getQuotas(int start, int end) {
		return quotaPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of quotas.
	 *
	 * @return the number of quotas
	 */
	@Override
	public int getQuotasCount() {
		return quotaPersistence.countAll();
	}

	/**
	 * Updates the quota in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param quota the quota
	 * @return the quota that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Quota updateQuota(Quota quota) {
		return quotaPersistence.update(quota);
	}

	/**
	 * Returns the quota local service.
	 *
	 * @return the quota local service
	 */
	public QuotaLocalService getQuotaLocalService() {
		return quotaLocalService;
	}

	/**
	 * Sets the quota local service.
	 *
	 * @param quotaLocalService the quota local service
	 */
	public void setQuotaLocalService(QuotaLocalService quotaLocalService) {
		this.quotaLocalService = quotaLocalService;
	}

	/**
	 * Returns the quota persistence.
	 *
	 * @return the quota persistence
	 */
	public QuotaPersistence getQuotaPersistence() {
		return quotaPersistence;
	}

	/**
	 * Sets the quota persistence.
	 *
	 * @param quotaPersistence the quota persistence
	 */
	public void setQuotaPersistence(QuotaPersistence quotaPersistence) {
		this.quotaPersistence = quotaPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the company local service.
	 *
	 * @return the company local service
	 */
	public com.liferay.portal.kernel.service.CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	/**
	 * Sets the company local service.
	 *
	 * @param companyLocalService the company local service
	 */
	public void setCompanyLocalService(
		com.liferay.portal.kernel.service.CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	/**
	 * Returns the company persistence.
	 *
	 * @return the company persistence
	 */
	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	/**
	 * Sets the company persistence.
	 *
	 * @param companyPersistence the company persistence
	 */
	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.kernel.service.GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.kernel.service.GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the role local service.
	 *
	 * @return the role local service
	 */
	public com.liferay.portal.kernel.service.RoleLocalService getRoleLocalService() {
		return roleLocalService;
	}

	/**
	 * Sets the role local service.
	 *
	 * @param roleLocalService the role local service
	 */
	public void setRoleLocalService(
		com.liferay.portal.kernel.service.RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	/**
	 * Returns the role persistence.
	 *
	 * @return the role persistence
	 */
	public RolePersistence getRolePersistence() {
		return rolePersistence;
	}

	/**
	 * Sets the role persistence.
	 *
	 * @param rolePersistence the role persistence
	 */
	public void setRolePersistence(RolePersistence rolePersistence) {
		this.rolePersistence = rolePersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user group role local service.
	 *
	 * @return the user group role local service
	 */
	public com.liferay.portal.kernel.service.UserGroupRoleLocalService getUserGroupRoleLocalService() {
		return userGroupRoleLocalService;
	}

	/**
	 * Sets the user group role local service.
	 *
	 * @param userGroupRoleLocalService the user group role local service
	 */
	public void setUserGroupRoleLocalService(
		com.liferay.portal.kernel.service.UserGroupRoleLocalService userGroupRoleLocalService) {
		this.userGroupRoleLocalService = userGroupRoleLocalService;
	}

	/**
	 * Returns the user group role persistence.
	 *
	 * @return the user group role persistence
	 */
	public UserGroupRolePersistence getUserGroupRolePersistence() {
		return userGroupRolePersistence;
	}

	/**
	 * Sets the user group role persistence.
	 *
	 * @param userGroupRolePersistence the user group role persistence
	 */
	public void setUserGroupRolePersistence(
		UserGroupRolePersistence userGroupRolePersistence) {
		this.userGroupRolePersistence = userGroupRolePersistence;
	}

	/**
	 * Returns the user notification event local service.
	 *
	 * @return the user notification event local service
	 */
	public com.liferay.portal.kernel.service.UserNotificationEventLocalService getUserNotificationEventLocalService() {
		return userNotificationEventLocalService;
	}

	/**
	 * Sets the user notification event local service.
	 *
	 * @param userNotificationEventLocalService the user notification event local service
	 */
	public void setUserNotificationEventLocalService(
		com.liferay.portal.kernel.service.UserNotificationEventLocalService userNotificationEventLocalService) {
		this.userNotificationEventLocalService = userNotificationEventLocalService;
	}

	/**
	 * Returns the user notification event persistence.
	 *
	 * @return the user notification event persistence
	 */
	public UserNotificationEventPersistence getUserNotificationEventPersistence() {
		return userNotificationEventPersistence;
	}

	/**
	 * Sets the user notification event persistence.
	 *
	 * @param userNotificationEventPersistence the user notification event persistence
	 */
	public void setUserNotificationEventPersistence(
		UserNotificationEventPersistence userNotificationEventPersistence) {
		this.userNotificationEventPersistence = userNotificationEventPersistence;
	}

	/**
	 * Returns the document library file entry local service.
	 *
	 * @return the document library file entry local service
	 */
	public com.liferay.document.library.kernel.service.DLFileEntryLocalService getDLFileEntryLocalService() {
		return dlFileEntryLocalService;
	}

	/**
	 * Sets the document library file entry local service.
	 *
	 * @param dlFileEntryLocalService the document library file entry local service
	 */
	public void setDLFileEntryLocalService(
		com.liferay.document.library.kernel.service.DLFileEntryLocalService dlFileEntryLocalService) {
		this.dlFileEntryLocalService = dlFileEntryLocalService;
	}

	/**
	 * Returns the document library file entry persistence.
	 *
	 * @return the document library file entry persistence
	 */
	public DLFileEntryPersistence getDLFileEntryPersistence() {
		return dlFileEntryPersistence;
	}

	/**
	 * Sets the document library file entry persistence.
	 *
	 * @param dlFileEntryPersistence the document library file entry persistence
	 */
	public void setDLFileEntryPersistence(
		DLFileEntryPersistence dlFileEntryPersistence) {
		this.dlFileEntryPersistence = dlFileEntryPersistence;
	}

	/**
	 * Returns the document library file version local service.
	 *
	 * @return the document library file version local service
	 */
	public com.liferay.document.library.kernel.service.DLFileVersionLocalService getDLFileVersionLocalService() {
		return dlFileVersionLocalService;
	}

	/**
	 * Sets the document library file version local service.
	 *
	 * @param dlFileVersionLocalService the document library file version local service
	 */
	public void setDLFileVersionLocalService(
		com.liferay.document.library.kernel.service.DLFileVersionLocalService dlFileVersionLocalService) {
		this.dlFileVersionLocalService = dlFileVersionLocalService;
	}

	/**
	 * Returns the document library file version persistence.
	 *
	 * @return the document library file version persistence
	 */
	public DLFileVersionPersistence getDLFileVersionPersistence() {
		return dlFileVersionPersistence;
	}

	/**
	 * Sets the document library file version persistence.
	 *
	 * @param dlFileVersionPersistence the document library file version persistence
	 */
	public void setDLFileVersionPersistence(
		DLFileVersionPersistence dlFileVersionPersistence) {
		this.dlFileVersionPersistence = dlFileVersionPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("org.lsug.quota.model.Quota",
			quotaLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"org.lsug.quota.model.Quota");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return QuotaLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return Quota.class;
	}

	protected String getModelClassName() {
		return Quota.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = quotaPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = QuotaLocalService.class)
	protected QuotaLocalService quotaLocalService;
	@BeanReference(type = QuotaPersistence.class)
	protected QuotaPersistence quotaPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.CompanyLocalService.class)
	protected com.liferay.portal.kernel.service.CompanyLocalService companyLocalService;
	@ServiceReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.GroupLocalService.class)
	protected com.liferay.portal.kernel.service.GroupLocalService groupLocalService;
	@ServiceReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.RoleLocalService.class)
	protected com.liferay.portal.kernel.service.RoleLocalService roleLocalService;
	@ServiceReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserGroupRoleLocalService.class)
	protected com.liferay.portal.kernel.service.UserGroupRoleLocalService userGroupRoleLocalService;
	@ServiceReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserNotificationEventLocalService.class)
	protected com.liferay.portal.kernel.service.UserNotificationEventLocalService userNotificationEventLocalService;
	@ServiceReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLFileEntryLocalService.class)
	protected com.liferay.document.library.kernel.service.DLFileEntryLocalService dlFileEntryLocalService;
	@ServiceReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLFileVersionLocalService.class)
	protected com.liferay.document.library.kernel.service.DLFileVersionLocalService dlFileVersionLocalService;
	@ServiceReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}