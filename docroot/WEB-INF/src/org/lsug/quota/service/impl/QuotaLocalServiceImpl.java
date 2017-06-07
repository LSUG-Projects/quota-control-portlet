/**
 * Copyright (c) 2013-present Liferay Spain User Group All rights reserved.
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

package org.lsug.quota.service.impl;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.service.persistence.PortalPreferencesUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.util.portlet.PortletProps;

import java.util.ArrayList;
import java.util.List;

import org.lsug.quota.NoSuchQuotaException;
import org.lsug.quota.model.Quota;
import org.lsug.quota.portlet.notification.QuotaServerNotificationHandler;
import org.lsug.quota.portlet.notification.QuotaSiteNotificationHandler;
import org.lsug.quota.service.base.QuotaLocalServiceBaseImpl;
import org.lsug.quota.util.Constants;
import org.lsug.quota.util.QuotaUtil;

/**
 *
 * @author LSUG
 * @see org.lsug.quota.service.base.QuotaLocalServiceBaseImpl
 * @see org.lsug.quota.service.QuotaLocalServiceUtil
 */
public class QuotaLocalServiceImpl extends QuotaLocalServiceBaseImpl {

	public Quota addQuota(long companyId, long classNameId, long classPK,
			int quotaAlert, long quotaAssigned, long quotaUsed, int quotaStatus)
			throws SystemException {

		long quotaId = counterLocalService.increment(Quota.class.getName());
		Quota quota = quotaPersistence.create(quotaId);

		quota.setCompanyId(companyId);
		quota.setClassNameId(classNameId);
		quota.setClassPK(classPK);
		quota.setQuotaAlert(quotaAlert);
		quota.setQuotaAssigned(quotaAssigned);
		quota.setQuotaUsed(quotaUsed);
		quota.setQuotaStatus(quotaStatus);

		return quotaLocalService.updateQuota(quota);
	}

	public Quota createDefaultQuota(long companyId, long classNameId,
			long classPK) throws SystemException {

		Quota quota = quotaLocalService.fetchQuotaByClassNameIdClassPK(classNameId, classPK);
		
		if (quota ==  null) {
			int quotaAlert = 0;
			long quotaAssigned = 0;
			long quotaUsed = 0;
			int quotaStatus = 0;
			
			if ( PropsUtil.get("lsug.quota.portlet.default.alert")!=null ) if ( !PropsUtil.get("lsug.quota.portlet.default.alert").equals("") ) quotaAlert=Integer.parseInt( PropsUtil.get("lsug.quota.portlet.default.alert") );
			if ( PropsUtil.get("lsug.quota.portlet.default.assigned")!=null ) if ( !PropsUtil.get("lsug.quota.portlet.default.assigned").equals("") ) quotaAssigned=Integer.parseInt( PropsUtil.get("lsug.quota.portlet.default.assigned") );
			if ( PropsUtil.get("lsug.quota.portlet.default.status")!=null ) if ( !PropsUtil.get("lsug.quota.portlet.default.status").equals("") ) quotaStatus=Integer.parseInt( PropsUtil.get("lsug.quota.portlet.default.status") );		

			quota = quotaLocalService.addQuota(companyId, classNameId, classPK, quotaAlert,
				quotaAssigned, quotaUsed, quotaStatus);
		}
		
		return quota;
	}

	public Quota getQuotaByClassNameIdClassPK(final long classNameId,
			final long classPK) throws NoSuchQuotaException, SystemException {

		return quotaPersistence.findByCN_CP(classNameId, classPK);
	}

	public List<Quota> getQuotaByClassNameId(final long classNameId, int start,
			int end, OrderByComparator orderByComparator)
			throws SystemException {

		return quotaPersistence.findByCN(classNameId, start, end,
				orderByComparator);
	}

	public List<Quota> getQuotaByClassNameIds(final long[] classNameIds, int start,
			int end, OrderByComparator orderByComparator)
			throws SystemException {

		return quotaPersistence.findByCN(classNameIds, start, end,
				orderByComparator);
	}

	public List<Quota> getQuotaByCompanyIdClassNameId(final long companyId,
			final long classNameId, int start, int end,
			OrderByComparator orderByComparator) throws SystemException {

		return quotaPersistence.findByC_CN(companyId, classNameId, start, end,
				orderByComparator);
	}

	public List<Quota> getQuotaByCompanyIdClassNameIds(final long companyId,
			final long[] classNameIds, int start, int end,
			OrderByComparator orderByComparator) throws SystemException {

		return quotaPersistence.findByC_CN(companyId, classNameIds, start, end,
				orderByComparator);
	}

	public Quota fetchQuotaByClassNameIdClassPK(final long classNameId,
			final long classPK) throws SystemException {

		return quotaPersistence.fetchByCN_CP(classNameId, classPK);
	}

	public Quota updateQuota(final long classNameId, final long classPK,
			final long fileSize) throws PortalException, SystemException {

		Quota quota = quotaPersistence.findByCN_CP(classNameId, classPK);

		quota.setQuotaUsed(quota.getQuotaUsed() + fileSize);

		return quotaLocalService.updateQuota(quota);
	}

	public Quota updateQuota(long quotaId, long classNameId, long classPK,
			int quotaAlert, long quotaAssigned, long quotaUsed, int quotaStatus)
			throws PortalException, SystemException {

		Quota quota = quotaPersistence.fetchByPrimaryKey(quotaId);

		quota.setClassNameId(classNameId);
		quota.setClassPK(classPK);
		quota.setQuotaAlert(quotaAlert);
		quota.setQuotaAssigned(quotaAssigned);
		quota.setQuotaUsed(quotaUsed);
		quota.setQuotaStatus(quotaStatus);

		return quotaPersistence.update(quota);
	}

	public Quota decrementQuota(final long classNameId, final long classPK,
			final long fileSize) throws PortalException, SystemException {

		if (fileSize < 0)
			throw new IllegalArgumentException(
					"Cannot decrement a quota by a negative decrement");

		return quotaLocalService.updateQuota(classNameId, classPK, fileSize);
	}

	public Quota incrementQuota(final long classNameId, final long classPK,
			final long fileSize) throws PortalException, SystemException {

		if (fileSize < 0)
			throw new IllegalArgumentException(
					"Cannot increment a quota by a negative increment.");

		return quotaLocalService.updateQuota(classNameId, classPK, -fileSize);
	}

	public int countByCompanyIdClassNameId(final long companyId,
			final long classNameId) throws SystemException {
		return quotaPersistence.countByC_CN(companyId, classNameId);
	}

	public int countByCompanyIdClassNameIds(final long companyId,
			final long[] classNameIds) throws SystemException {
		return quotaPersistence.countByC_CN(companyId, classNameIds);
	}

	public int countByClassNameId(final long classNameId)
			throws SystemException {
		return quotaPersistence.countByCN(classNameId);
	}

	public int countByClassNameIds(final long[] classNameIds)
			throws SystemException {
		return quotaPersistence.countByCN(classNameIds);
	}

	public void decreaseQuota(long groupId, long userId, long size)
			throws PortalException, SystemException {

		final Group group = groupLocalService.getGroup(groupId);
		final Company company = companyLocalService.getCompany(group
				.getCompanyId());

		if (!QuotaUtil.isValidGroupQuota(group)) {
			return;
		}

		String className = group.getClassName();

		long classNameId = PortalUtil.getClassNameId(className);

		quotaLocalService.decrementQuota(classNameId, groupId, size);

		quotaLocalService.decrementQuota(
				PortalUtil.getClassNameId(Company.class),
				company.getCompanyId(), size);

		quotaLocalService.checkAlerts(groupId, userId);
	}

	public Quota deleteQuota(long quotaId) throws PortalException, SystemException{
		Quota quota = super.deleteQuota(quotaId);

		//Update server used quota		
		quotaLocalService.calculateServerUsedQuota(quota.getCompanyId());

		return quota;
	}

	public void increaseQuota(long groupId, long userId, long size)
			throws PortalException, SystemException {

		final Group group = groupLocalService.getGroup(groupId);
		final Company company = companyLocalService.getCompany(group
				.getCompanyId());

		if (!QuotaUtil.isValidGroupQuota(group)) {
			return;
		}

		String className = group.getClassName();

		long classNameId = PortalUtil.getClassNameId(className);

		quotaLocalService.incrementQuota(classNameId, groupId, size);

		quotaLocalService.incrementQuota(
				PortalUtil.getClassNameId(Company.class),
				company.getCompanyId(), size);

		// quotaLocalService.checkAlerts(groupId, userId);

	}

	public boolean hasQuota(long groupId, long userId, long size)
			throws PortalException, SystemException {

		Group group = groupLocalService.getGroup(groupId);

		String className = group.getClassName();

		if (!QuotaUtil.isValidGroupQuota(group)) {
			return true;
		}

		long siteClassNameId = PortalUtil.getClassNameId(className);

		long companyClassNameId = PortalUtil.getClassNameId(Company.class);
		final Quota groupQuota = quotaLocalService
				.getQuotaByClassNameIdClassPK(siteClassNameId, groupId);

		Company company = companyLocalService.getCompany(group.getCompanyId());
		final Quota companyQuota = quotaLocalService
				.getQuotaByClassNameIdClassPK(companyClassNameId,
						company.getCompanyId());

		boolean groupFreeMB = Boolean.FALSE;
		boolean companyFreeMB = Boolean.FALSE;

		if (groupQuota.getQuotaStatus() == Constants.QUOTA_INACTIVE) {
			groupFreeMB = Boolean.TRUE;
		} else {
			groupFreeMB = groupQuota.hasFreeMB(size);
		}

		if (companyQuota.getQuotaStatus() == Constants.QUOTA_INACTIVE) {
			companyFreeMB = Boolean.TRUE;
		} else {
			companyFreeMB = companyQuota.isUnlimitedQuota() ||
					 companyQuota.hasFreeMB(size);
		}

		return groupFreeMB && companyFreeMB;
	}

	public void checkAlerts(long groupId, long userId) throws PortalException,
			SystemException {

		Group group = groupLocalService.getGroup(groupId);

		if (!QuotaUtil.isValidGroupQuota(group)) {
			return;
		}

		String className = group.getClassName();
		long siteClassNameId = PortalUtil.getClassNameId(className);

		long companyClassNameId = PortalUtil.getClassNameId(Company.class);
		final Quota groupQuota = quotaLocalService
				.getQuotaByClassNameIdClassPK(siteClassNameId, groupId);
		Company company = companyLocalService.getCompany(group.getCompanyId());
		final Quota companyQuota = quotaLocalService
				.getQuotaByClassNameIdClassPK(companyClassNameId,
						company.getCompanyId());

		if (companyQuota.isExceeded()) {
			String companyRole = PortletProps.get("lsug.quota.notification.company.role");

			Role administratorRole = roleLocalService.getRole(
					company.getCompanyId(), companyRole);
			long[] users = userLocalService.getRoleUserIds(administratorRole
					.getRoleId());

			for (long user : users) {
				JSONObject notificationEventJSONObject = JSONFactoryUtil
						.createJSONObject();

				notificationEventJSONObject.put("classPK",
						company.getCompanyId());
				notificationEventJSONObject.put("userId", user);

				if (UserNotificationManagerUtil.isDeliver(user,
						QuotaServerNotificationHandler.PORTLET_ID, 0,
						QuotaServerNotificationHandler.SERVER_QUOTA_EXCEEDED,
						UserNotificationDeliveryConstants.TYPE_EMAIL)) {

					NotificationEvent notificationEvent = NotificationEventFactoryUtil
							.createNotificationEvent(
									System.currentTimeMillis(),
									QuotaServerNotificationHandler.PORTLET_ID,
									notificationEventJSONObject);

					notificationEvent.setDeliveryRequired(0);

					userNotificationEventLocalService.addUserNotificationEvent(user, notificationEvent);
				}
			}
		}

		if (groupQuota.isExceeded()) {
			long[] users = null;

			if (group.isUser()) {
				users = new long[1];
				users[0] = group.getCreatorUserId();
			} else {

				if (group.isGuest()) {
					//In case of default site, send to configured roles
					String guestSiteRole = PortletProps.get("lsug.quota.notification.site.guest.role");
					Role administratorRole = roleLocalService.getRole(
							company.getCompanyId(), guestSiteRole);
					users = userLocalService.getRoleUserIds(administratorRole
							.getRoleId());
				}
				else if (group.isOrganization()) {

					String organizationSiteRole = PortletProps.get("lsug.quota.notification.site.organization.role");
					Role role = roleLocalService.getRole(
							company.getCompanyId(), organizationSiteRole);

					List<UserGroupRole> userGroupRoles = userGroupRoleLocalService
							.getUserGroupRolesByGroupAndRole(groupId,
									role.getRoleId());

					users = new long[userGroupRoles.size()];

					int i = 0;

					for (UserGroupRole userGroupRole : userGroupRoles) {
						users[i] = userGroupRole.getUserId();
						i++;
					}
				}
				else {
					String regularSiteRole = PortletProps.get("lsug.quota.notification.site.regular.role");
					Role role = roleLocalService.getRole(
							company.getCompanyId(), regularSiteRole);

					List<UserGroupRole> userGroupRoles = userGroupRoleLocalService
							.getUserGroupRolesByGroupAndRole(groupId,
									role.getRoleId());

					users = new long[userGroupRoles.size()];

					int i = 0;

					for (UserGroupRole userGroupRole : userGroupRoles) {
						users[i] = userGroupRole.getUserId();
						i++;
					}
				}
			}

			for (long user : users) {
				JSONObject notificationEventJSONObject = JSONFactoryUtil
						.createJSONObject();

				notificationEventJSONObject.put("classPK", group.getGroupId());
				notificationEventJSONObject.put("userId", user);
				notificationEventJSONObject.put("type", group.isUser() ? "user"
						: "site");

				String portletId = QuotaSiteNotificationHandler.PORTLET_ID;
				int type = QuotaSiteNotificationHandler.SITE_QUOTA_EXCEEDED;

				if (UserNotificationManagerUtil.isDeliver(user, portletId, 0,
						type, UserNotificationDeliveryConstants.TYPE_EMAIL)) {

					NotificationEvent notificationEvent = NotificationEventFactoryUtil
							.createNotificationEvent(
									System.currentTimeMillis(), portletId,
									notificationEventJSONObject);

					notificationEvent.setDeliveryRequired(0);

					userNotificationEventLocalService
							.addUserNotificationEvent(user, notificationEvent);
				}
			}
		}
	}

	public long calculateServerUsedQuota(final long companyId)
			throws SystemException {
		long usedQuota = 0;

		String[] validClassNames = QuotaUtil.validClassNames;

		long[] classNameIds = new long[validClassNames.length];

		int i = 0;

		for (String className : validClassNames) {
			if (Validator.isNotNull(className)) {
				classNameIds[i] = PortalUtil.getClassNameId(className);
				i++;
			}
		}

		int count =  quotaLocalService.countByCompanyIdClassNameIds(companyId, classNameIds);
		int pages = count / INTERVAL;
		
		for (int j = 0; j <= pages; j++) {
			int start = (j * INTERVAL);		
			int end = start + INTERVAL;
			
			List<Quota> quotas = quotaLocalService.getQuotaByCompanyIdClassNameIds(
					companyId, classNameIds, start, end, null);
			List<Quota> quotaList = new ArrayList<Quota>(quotas);

			for (Quota quota : quotaList) {
				usedQuota += quota.getQuotaUsed();
				start++;
			}
		}

		return usedQuota;
	}

	/**
	 * Calculate current used quota from a site
	 *
	 * @param groupId
	 * @param quotaUsed
	 * @return
	 * @throws SystemException
	 */
	public long calculateSiteUsedQuota(final long groupId)
			throws SystemException {
		long quota = 0;

		int count = dlFileEntryLocalService.getGroupFileEntriesCount(groupId);
		int pages = count / INTERVAL;
		
		for (int i = 0; i <= pages; i++) {
				int start = (i * INTERVAL);		
				int end = start + INTERVAL;
				List<DLFileEntry> fileEntries = dlFileEntryLocalService
						.getGroupFileEntries(groupId, start, end);
				
				for (DLFileEntry fileEntry : fileEntries) {
					
					long fileEntryId = fileEntry.getFileEntryId();
						
					List<DLFileVersion> DLFileVersions = dlFileVersionLocalService.getFileVersions(fileEntryId, WorkflowConstants.STATUS_ANY);

					for (DLFileVersion dlFileVersion : DLFileVersions) {
						quota += dlFileVersion.getSize();
					}
					
					start++;
				}
		}		

		return quota;
	}

	private static final int INTERVAL = 100;
}