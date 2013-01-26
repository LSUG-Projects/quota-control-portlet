/**
 * Copyright (c) 2013 Liferay Spain User Group All rights reserved.
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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceWrapper;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.lsug.quota.util.QuotaUtil;

public class QuotaListenerDLFileEntryLocalService 
	extends DLFileEntryLocalServiceWrapper {
	
	public QuotaListenerDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {
		
		super(dlFileEntryLocalService);
	}
	
	public DLFileEntry addFileEntry(
			long userId, long groupId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, File file, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {
		
		// Add quota control here

		System.out.println("Controlando quota excedida - file size: " + size);
		
		QuotaUtil.checkQuotaExceeded(groupId, userId, size);

		DLFileEntry dlFileEntry = super.addFileEntry(
			userId, groupId, repositoryId, folderId, sourceFileName, mimeType, 
			title, description, changeLog, fileEntryTypeId, fieldsMap, file, is,
			size, serviceContext);
		
		// Update consumed quota here

		System.out.println("Actualizando quota - file size: " + size);
		
		return dlFileEntry;
	}

	protected void deleteFileEntry(DLFileEntry dlFileEntry)
			throws PortalException, SystemException {
		
		super.deleteDLFileEntry(dlFileEntry);
		
		// Update consumed quota here
		
		System.out.println("Actualizando quota - file size: " + 
			dlFileEntry.getSize());
	}
	
	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = super.getFileEntry(fileEntryId);

		long size = dlFileEntry.getSize();

		super.deleteDLFileEntry(fileEntryId);

		// Update consumed quota here

		System.out.println("Actualizando quota - file size: " + size);
	}

	public void deleteFileEntry(long userId, long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = super.getFileEntry(fileEntryId);

		long size = dlFileEntry.getSize();

		super.deleteFileEntry(userId, fileEntryId);
		
		// Update consumed quota here

		System.out.println("Actualizando quota - file size: " + size);
	}
	
	public DLFileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName, 
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, long fileEntryTypeId, 
			Map<String, Fields>fieldsMap, File file, InputStream is, long size, 
			ServiceContext serviceContext)
		throws PortalException, SystemException {
		
		// Add quota control here

		System.out.println("Controlando quota excedida - file size: " + size);

		DLFileEntry dlFileEntry = super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description, 
			changeLog, majorVersion, fileEntryTypeId, fieldsMap, file, is, size,
			serviceContext);
		
		// Update consumed quota here

		System.out.println("Actualizando quota - file size: " + size);
		
		return dlFileEntry;
	}

}