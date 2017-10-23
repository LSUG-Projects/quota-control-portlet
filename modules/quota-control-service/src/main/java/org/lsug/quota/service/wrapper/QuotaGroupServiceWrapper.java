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

package org.lsug.quota.service.wrapper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;
import java.util.Map;

import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalService;
import org.lsug.quota.util.QuotaUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 *  DL quota service override for some Group services
 *  @author Juan Gonzalez
 *
 */
@Component(
		immediate = true,
		property = {
		},
		service = ServiceWrapper.class
	)
public class QuotaGroupServiceWrapper extends GroupLocalServiceWrapper {

	private QuotaLocalService _quotaLocalService;
	
	@Reference(unbind = "-")
	protected void setQuotaLocalService(QuotaLocalService quotaLocalService) {
		_quotaLocalService = quotaLocalService;
	}
	
	public QuotaGroupServiceWrapper() {
		super(null);
	}

	
	
	@Override
	public Group addGroup(long userId, long parentGroupId, String className, long classPK, long liveGroupId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap, int type, boolean manualMembership,
			int membershipRestriction, String friendlyURL, boolean site, boolean inheritContent, boolean active,
			ServiceContext serviceContext) throws PortalException {
		
		// We have to add this deprecated method, as the original service doesn't call the method using the reference
		Group group = super.addGroup(userId, parentGroupId, className, classPK, liveGroupId,
				nameMap, descriptionMap, type, manualMembership, membershipRestriction, friendlyURL, site, inheritContent, active, serviceContext);
		
		if (Validator.isNull(className)) {
			className = Group.class.getName();
		}

		if (QuotaUtil.isValidGroupQuota(group)) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Creating group default quota, className:"+className + ",groupId:"+group.getGroupId());
			}
			long classNameId = PortalUtil.getClassNameId(className);
			_quotaLocalService.createDefaultQuota(group.getCompanyId(), classNameId, group.getGroupId());
		}
		
		return group;
	}


	@Override
	public Group addGroup(long userId, long parentGroupId, String className, long classPK, long liveGroupId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap, int type, boolean manualMembership,
			int membershipRestriction, String friendlyURL, boolean site, boolean active, ServiceContext serviceContext)
			throws PortalException {
		
		// We have to add this deprecated method, as the original service doesn't call the method using the reference
		Group group = super.addGroup(userId, parentGroupId, className, classPK, liveGroupId, nameMap,
				descriptionMap, type, manualMembership, membershipRestriction, friendlyURL, site, active, serviceContext);
		
		if (Validator.isNull(className)) {
			className = Group.class.getName();
		}

		if (QuotaUtil.isValidGroupQuota(group)) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Creating group default quota, className:"+className + ",groupId:"+group.getGroupId());
			}
			long classNameId = PortalUtil.getClassNameId(className);
			_quotaLocalService.createDefaultQuota(group.getCompanyId(), classNameId, group.getGroupId());
		}
		
		return group;
	}

	@Override
	public Group addGroup(Group group) throws SystemException {
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Adding group listener, group:"+group.toString());
		}
		
		Group gro = super.addGroup(group);

		String className = gro.getClassName();

		if (Validator.isNull(className)) {
			className = Group.class.getName();
		}

		if (QuotaUtil.isValidGroupQuota(gro)) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Creating group default quota, className:"+className + ",groupId:"+gro.getGroupId());
			}
			long classNameId = PortalUtil.getClassNameId(className);
			_quotaLocalService.createDefaultQuota(gro.getCompanyId(), classNameId, gro.getGroupId());
		}

		return gro;
	}

	@Override
	public Group addGroup(long userId, long parentGroupId, String className,
			long classPK, long liveGroupId, String name, String description,
			int type, boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean site, boolean active,
			ServiceContext serviceContext) throws PortalException,
			SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Adding group listener, className:"+className);
		}
		
		Group group = super.addGroup(userId, parentGroupId, className, classPK, liveGroupId,
				name, description, type, manualMembership, membershipRestriction,
				friendlyURL, site, active, serviceContext);

		if (Validator.isNull(className)) {
			className = Group.class.getName();
		}

		if (QuotaUtil.isValidGroupQuota(group)) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Creating group default quota, className:"+className + ",groupId:"+group.getGroupId());
			}
			long classNameId = PortalUtil.getClassNameId(className);
			_quotaLocalService.createDefaultQuota(group.getCompanyId(), classNameId, group.getGroupId());
		}

		return group;
	}

	
	@Override
	public Group deleteGroup(Group group) throws PortalException,
			SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Deleting group listener, group:"+group);		
		}
		
		Group gro = super.deleteGroup(group);
		
		String className = gro.getClassName();
		long classNameId = gro.getClassNameId();

		if (Validator.isNull(className)) {
			className = Group.class.getName();
		}

		if (QuotaUtil.isValidGroupQuota(group)) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Deleting group quota, className:"+className + ",groupId:"+group.getGroupId());
			}
			Quota quota = _quotaLocalService.fetchQuotaByClassNameIdClassPK(classNameId, group.getGroupId());
			if (quota != null) {
				_quotaLocalService.deleteQuota(quota.getQuotaId());
			}
		}

		return group;
	}


	@Override
	public Group deleteGroup(long groupId) throws PortalException,
			SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Deleting group listener, groupId:"+groupId);
		}
		
		Group group = super.deleteGroup(groupId);

		String className = group.getClassName();
		long classNameId = group.getClassNameId();

		if (Validator.isNull(className)) {
			className = Group.class.getName();
		}

		if (QuotaUtil.isValidGroupQuota(group)) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Deleting group quota, className:"+className + ",groupId:"+group.getGroupId());
			}
			Quota quota = _quotaLocalService.fetchQuotaByClassNameIdClassPK(classNameId, group.getGroupId());
			if (quota != null) {
				_quotaLocalService.deleteQuota(quota.getQuotaId());
			}
		}

		return group;
	}

	private static final transient Log LOGGER = LogFactoryUtil
			.getLog(QuotaGroupServiceWrapper.class);
	
}
