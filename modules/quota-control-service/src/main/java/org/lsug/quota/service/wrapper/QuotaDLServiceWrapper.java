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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceWrapper;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.lsug.quota.exception.QuotaExceededException;
import org.lsug.quota.service.QuotaLocalService;
import org.lsug.quota.service.QuotaLocalServiceUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Gonzalez
 */
@Component(
	immediate = true,
	property = {
	},
	service = ServiceWrapper.class
)
public class QuotaDLServiceWrapper extends DLFileEntryLocalServiceWrapper {

	private QuotaLocalService _quotaLocalService;
	private DLFileVersionLocalService _dlFileVersionLocalService;
	private DLFileEntryLocalService _dlFileEntryLocalService;
	
	@Reference(unbind = "-")
	protected void setQuotaLocalService(QuotaLocalService quotaLocalService) {
		_quotaLocalService = quotaLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileVersionLocalService(DLFileVersionLocalService dlFileVersionLocalService) {
		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(DLFileEntryLocalService dlFileEntryLocalService) {
		_dlFileEntryLocalService = dlFileEntryLocalService;;
	}
	
	public QuotaDLServiceWrapper() {
		super(null);
	}

	
	@Override
	public DLFileEntry addFileEntry(long userId, long groupId, long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog, long fileEntryTypeId,
			Map<String, DDMFormValues> ddmFormValuesMap, File file, InputStream is, long size,
			ServiceContext serviceContext) throws PortalException {
		

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Adding file entry quota listener, groupId:" +groupId+ ",userId:"+userId+",size:" + size);
		}
		
		if (!_quotaLocalService.hasQuota(groupId, userId, size)) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Quota exceeded, throwing error");
			}
			throw new QuotaExceededException();
		}

		DLFileEntry dlFileEntry = super.addFileEntry(userId, groupId,
				repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, fileEntryTypeId, ddmFormValuesMap, file, is,
				size, serviceContext);

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Decreasing quota, groupId:" + groupId + ",userId:" + userId + ", size:"+dlFileEntry.getSize());
		}
		
		_quotaLocalService.decreaseQuota(groupId, userId, dlFileEntry.getSize());

		return dlFileEntry;
		
	}

	@Override
	public DLFileEntry deleteFileEntry(DLFileEntry dlFileEntry)
			throws PortalException, SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Deleting file entry quota listener, dlFileEntry:" +dlFileEntry.toString());
		}
		
		List<DLFileVersion> versions =_dlFileVersionLocalService.
				getFileVersions(dlFileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);
		
		long totalSize = 0;
		
		for (DLFileVersion dlFileVersion : versions) {
			totalSize += dlFileVersion.getSize();
		}
		
		DLFileEntry retVal = super.deleteDLFileEntry(dlFileEntry);

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Increasing quota, groupId:"+dlFileEntry.getGroupId()+ ", userId:"+dlFileEntry.getUserId() + ", size:"+ totalSize);
		}
		
		_quotaLocalService.increaseQuota(dlFileEntry.getGroupId(),
				dlFileEntry.getUserId(), totalSize);

		return retVal;
	}

	@Override
	public DLFileEntry deleteFileVersion(long userId, long fileEntryId,
			String version) throws PortalException, SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Deleting file version quota listener, userId:"+userId+",fileEntryId:" +fileEntryId +",fileVersion:" +version);
		}
		
		DLFileVersion dlFileVersion = _dlFileVersionLocalService.getFileVersion(fileEntryId, version);

		DLFileEntry dlFileEntry = super.deleteFileVersion(userId, fileEntryId, version);

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Increasing quota, groupId:"+dlFileVersion.getGroupId()+ ", userId:"+userId + ", size:"+dlFileVersion.getSize());
		}
		
		_quotaLocalService.increaseQuota(dlFileVersion.getGroupId(), userId, dlFileVersion.getSize());

		return dlFileEntry;
	}

	
	@Override
	public void revertFileEntry(long userId, long fileEntryId, String version,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Reverting file entry listener, userId:"+userId+",fileEntryId:" +fileEntryId +",version:" +version);
		}
		
		DLFileVersion dlFileVersion = _dlFileVersionLocalService.getFileVersion(fileEntryId, version);
				
		if (!QuotaLocalServiceUtil.hasQuota(dlFileVersion.getGroupId(), userId, dlFileVersion.getSize())) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Quota exceeded, throwing error");
			}
			throw new QuotaExceededException();
		}
		
		super.revertFileEntry(userId, fileEntryId, version, serviceContext);
		
		dlFileVersion = _dlFileVersionLocalService.getLatestFileVersion(userId, fileEntryId);
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Decreasing quota, groupId:" + dlFileVersion.getGroupId() + ",userId:" + userId + ", size:"+dlFileVersion.getSize());
		}
		
		_quotaLocalService.decreaseQuota(dlFileVersion.getGroupId(), userId, dlFileVersion.getSize());
	}

	
	@Override
	public DLFileEntry updateFileEntry(long userId, long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, boolean majorVersion, long fileEntryTypeId,
			Map<String, DDMFormValues> ddmFormValuesMap, File file, InputStream is, long size,
			ServiceContext serviceContext) throws PortalException {

		DLFileEntry fileEntry = _dlFileEntryLocalService.getFileEntry(fileEntryId);
		long groupId = fileEntry.getGroupId();

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Updating file entry listener, groupId:"+groupId+", userId:"+userId+",fileEntryId:" +fileEntryId +",size:" +size);
		}
		
		//QuotaLocalServiceUtil.increaseQuota(groupId, userId, size);

		if (!_quotaLocalService.hasQuota(groupId, userId, size)) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Quota exceeded, throwing error");
			}
			
			throw new QuotaExceededException();
		}
		
		String versionBefore = fileEntry.getVersion();
		long sizeBefore = fileEntry.getSize();
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Version before:" + versionBefore + ", size before:"+ sizeBefore);
		}
		
		DLFileEntry dlFileEntry = super.updateFileEntry(userId, fileEntryId,
				sourceFileName, mimeType, title, description, changeLog,
				majorVersion, fileEntryTypeId, ddmFormValuesMap, file, is, size,
				serviceContext);

		String versionAfter = dlFileEntry.getVersion();
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Version after:" + versionAfter + ", size after:"+dlFileEntry.getSize());
		}
		
		//In case of editing file with the same file name, there is no new version		
		if (!StringUtil.equalsIgnoreCase(versionBefore ,versionAfter)
			|| (sizeBefore != dlFileEntry.getSize())) {
			
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Decreasing quota, groupId:"+groupId+ ", userId:"+userId + ", size:"+dlFileEntry.getSize());
			}
		
			_quotaLocalService.decreaseQuota(groupId, userId, dlFileEntry.getSize());
		}

		return dlFileEntry;
	}

	private static final transient Log LOGGER = LogFactoryUtil
			.getLog(QuotaDLServiceWrapper.class);
}