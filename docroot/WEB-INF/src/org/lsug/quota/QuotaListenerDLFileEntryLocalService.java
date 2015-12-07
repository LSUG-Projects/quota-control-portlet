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

package org.lsug.quota;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceWrapper;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.lsug.quota.service.QuotaLocalServiceUtil;

/**
 *  DL quota service override for some DLFileEntry services
 *  @author LSUG
 *
 */
public class QuotaListenerDLFileEntryLocalService
		extends DLFileEntryLocalServiceWrapper {

	public QuotaListenerDLFileEntryLocalService(
			DLFileEntryLocalService dlFileEntryLocalService) {

		super(dlFileEntryLocalService);
	}

	public DLFileEntry addFileEntry(long userId, long groupId,
			long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			long fileEntryTypeId, Map<String, Fields> fieldsMap, File file,
			InputStream is, long size,
			ServiceContext serviceContext) throws PortalException,
			SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Adding file entry quota listener, groupId:" +groupId+ ",userId:"+userId+",size:" + size);
		}
		
		if (!QuotaLocalServiceUtil.hasQuota(groupId, userId, size)) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Quota exceeded, throwing error");
			}
			if (isStackTraceFromEditor()) {
				throw new FileSizeException("quota-exceeded-error");
			}else {
				throw new QuotaExceededException();
			}
		}

		DLFileEntry dlFileEntry = super.addFileEntry(userId, groupId,
				repositoryId, folderId, sourceFileName, mimeType, title,
				description, changeLog, fileEntryTypeId, fieldsMap, file, is,
				size, serviceContext);

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Decreasing quota, groupId:" + groupId + ",userId:" + userId + ", size:"+dlFileEntry.getSize());
		}
		
		QuotaLocalServiceUtil.decreaseQuota(groupId, userId, dlFileEntry.getSize());

		return dlFileEntry;
	}

	public DLFileEntry deleteFileEntry(DLFileEntry dlFileEntry)
			throws PortalException, SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Deleting file entry quota listener, dlFileEntry:" +dlFileEntry.toString());
		}
		
		List<DLFileVersion> versions =DLFileVersionLocalServiceUtil.
				getFileVersions(dlFileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);
		
		long totalSize = 0;
		
		for (DLFileVersion dlFileVersion : versions) {
			totalSize += dlFileVersion.getSize();
		}
		
		DLFileEntry retVal = super.deleteDLFileEntry(dlFileEntry);

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Increasing quota, groupId:"+dlFileEntry.getGroupId()+ ", userId:"+dlFileEntry.getUserId() + ", size:"+ totalSize);
		}
		
		QuotaLocalServiceUtil.increaseQuota(dlFileEntry.getGroupId(),
				dlFileEntry.getUserId(), totalSize);

		return retVal;
	}

	@Override
	public DLFileEntry deleteFileVersion(long userId, long fileEntryId,
			String version) throws PortalException, SystemException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Deleting file version quota listener, userId:"+userId+",fileEntryId:" +fileEntryId +",fileVersion:" +version);
		}
		
		DLFileVersion dlFileVersion = DLFileVersionLocalServiceUtil.getFileVersion(fileEntryId, version);

		DLFileEntry dlFileEntry = super.deleteFileVersion(userId, fileEntryId, version);

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Increasing quota, groupId:"+dlFileVersion.getGroupId()+ ", userId:"+userId + ", size:"+dlFileVersion.getSize());
		}
		
		QuotaLocalServiceUtil.increaseQuota(dlFileVersion.getGroupId(), userId, dlFileVersion.getSize());

		return dlFileEntry;
	}

	
	@Override
	public void revertFileEntry(long userId, long fileEntryId, String version,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Reverting file entry listener, userId:"+userId+",fileEntryId:" +fileEntryId +",version:" +version);
		}
		
		DLFileVersion dlFileVersion = DLFileVersionLocalServiceUtil.getFileVersion(fileEntryId, version);
				
		if (!QuotaLocalServiceUtil.hasQuota(dlFileVersion.getGroupId(), userId, dlFileVersion.getSize())) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Quota exceeded, throwing error");
			}
			if (isStackTraceFromEditor()) {
				throw new FileSizeException("quota-exceeded-error");
			}else {
				throw new QuotaExceededException();
			}
		}
		
		super.revertFileEntry(userId, fileEntryId, version, serviceContext);
		
		dlFileVersion = DLFileVersionLocalServiceUtil.getLatestFileVersion(userId, fileEntryId);
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Decreasing quota, groupId:" + dlFileVersion.getGroupId() + ",userId:" + userId + ", size:"+dlFileVersion.getSize());
		}
		
		QuotaLocalServiceUtil.decreaseQuota(dlFileVersion.getGroupId(), userId, dlFileVersion.getSize());
	}

	public DLFileEntry updateFileEntry(long userId, long fileEntryId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, boolean majorVersion,
			long fileEntryTypeId, Map<String, Fields> fieldsMap, File file,
			InputStream is, long size, ServiceContext serviceContext)
			throws PortalException, SystemException {

		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(fileEntryId);
		long groupId = fileEntry.getGroupId();

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Updating file entry listener, groupId:"+groupId+", userId:"+userId+",fileEntryId:" +fileEntryId +",size:" +size);
		}
		
		//QuotaLocalServiceUtil.increaseQuota(groupId, userId, size);

		if (!QuotaLocalServiceUtil.hasQuota(groupId, userId, size)) {
			if (LOGGER.isDebugEnabled()){
				LOGGER.debug("Quota exceeded, throwing error");
			}
			
			if (isStackTraceFromEditor()) {
				throw new FileSizeException("quota-exceeded-error");
			}else {
				throw new QuotaExceededException();
			}
		}
		
		String versionBefore = fileEntry.getVersion();
		long sizeBefore = fileEntry.getSize();
		
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Version before:" + versionBefore + ", size before:"+ sizeBefore);
		}
		
		DLFileEntry dlFileEntry = super.updateFileEntry(userId, fileEntryId,
				sourceFileName, mimeType, title, description, changeLog,
				majorVersion, fileEntryTypeId, fieldsMap, file, is, size,
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
		
			QuotaLocalServiceUtil.decreaseQuota(groupId, userId, dlFileEntry.getSize());
		}

		return dlFileEntry;
	}

	private boolean isStackTraceFromEditor() {

		Thread currentThread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

		for (StackTraceElement stackTraceElement : stackTraceElements) {
			String className = stackTraceElement.getClassName();

			if (className.endsWith("DocumentCommandReceiver")) {
				return true;
			}
		}

		return false;
	}
	
	private static final transient Log LOGGER = LogFactoryUtil
			.getLog(QuotaListenerDLFileEntryLocalService.class);
}