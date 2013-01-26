package org.lsug.quota;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
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

		if (!QuotaUtil.hasQuota(groupId, userId, size))
			throw new QuotaExceededException();

		DLFileEntry dlFileEntry = super.addFileEntry(
			userId, groupId, repositoryId, folderId, sourceFileName, mimeType, 
			title, description, changeLog, fileEntryTypeId, fieldsMap, file, is,
			size, serviceContext);

		QuotaUtil.decreaseQuota(groupId, userId, size);
		
		return dlFileEntry;
	}

	protected void deleteFileEntry(DLFileEntry dlFileEntry)
			throws PortalException, SystemException {
		
		super.deleteDLFileEntry(dlFileEntry);
		
		QuotaUtil.increaseQuota(
			dlFileEntry.getGroupId(), dlFileEntry.getUserId(), 
			dlFileEntry.getSize());
	}
	
	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = super.getFileEntry(fileEntryId);

		super.deleteDLFileEntry(fileEntryId);

		// Update consumed quota here

		QuotaUtil.increaseQuota(
			dlFileEntry.getGroupId(), dlFileEntry.getUserId(), 
			dlFileEntry.getSize());
	}

	public void deleteFileEntry(long userId, long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = super.getFileEntry(fileEntryId);

		super.deleteFileEntry(userId, fileEntryId);

		QuotaUtil.increaseQuota(
			dlFileEntry.getGroupId(), dlFileEntry.getUserId(),
			dlFileEntry.getSize());
	}

	public DLFileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, File file, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			fileEntryId);
		long groupId = fileEntry.getGroupId();
		QuotaUtil.increaseQuota(groupId, userId, size);

		if (!QuotaUtil.hasQuota(groupId, userId, size))
			throw new QuotaExceededException();

		DLFileEntry dlFileEntry =
			super.updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, fileEntryTypeId,
				fieldsMap, file, is, size, serviceContext);

		QuotaUtil.decreaseQuota(groupId, userId, dlFileEntry.getSize());

		return dlFileEntry;
	}

}