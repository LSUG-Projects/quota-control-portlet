package org.lsug.quota;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.lsug.quota.util.QuotaUtil;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceWrapper;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

public class QuotaListenerDLFileEntryLocalService extends DLFileEntryLocalServiceWrapper {

	private final static Log LOGGER = LogFactoryUtil.getLog(QuotaListenerDLFileEntryLocalService.class);

	public QuotaListenerDLFileEntryLocalService(DLFileEntryLocalService dlFileEntryLocalService) {

		super(dlFileEntryLocalService);
	}

	public DLFileEntry addFileEntry(long userId, long groupId, long repositoryId, long folderId, String sourceFileName,
			String mimeType, String title, String description, String changeLog, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, File file, InputStream is, long size, ServiceContext serviceContext)
			throws PortalException, SystemException {	

		DLFileEntry dlFileEntry = super.addFileEntry(userId, groupId, repositoryId, folderId, sourceFileName, mimeType,
				title, description, changeLog, fileEntryTypeId, fieldsMap, file, is, size, serviceContext);
		
		try {
			if (QuotaUtil.hasQuota(groupId, userId, size)){
				QuotaUtil.decreaseQuota(groupId, userId, size);
			}			
		} catch (NoSuchQuotaException e) {
			LOGGER.error("Not found a Quota with groupId: " + groupId + " and userId : "+userId);
		}
	
		return dlFileEntry;
	}

	protected void deleteFileEntry(DLFileEntry dlFileEntry) throws PortalException, SystemException {

		super.deleteDLFileEntry(dlFileEntry);

		try {
			QuotaUtil.increaseQuota(dlFileEntry.getGroupId(), dlFileEntry.getUserId(), dlFileEntry.getSize());
		} catch (NoSuchQuotaException e) {
			LOGGER.error("Not found a Quota with groupId: " + dlFileEntry.getGroupId() + " and userId : "+dlFileEntry.getUserId());
		}
	}

	public void deleteFileEntry(long fileEntryId) throws PortalException, SystemException {

		DLFileEntry dlFileEntry = super.getFileEntry(fileEntryId);

		super.deleteDLFileEntry(fileEntryId);

		// Update consumed quota here
		try {
			QuotaUtil.increaseQuota(dlFileEntry.getGroupId(), dlFileEntry.getUserId(), dlFileEntry.getSize());
		} catch (NoSuchQuotaException e) {
			LOGGER.error("Not found a Quota with groupId: " + dlFileEntry.getGroupId() + " and userId : "+dlFileEntry.getUserId());
		}
	}

	public void deleteFileEntry(long userId, long fileEntryId) throws PortalException, SystemException {

		DLFileEntry dlFileEntry = super.getFileEntry(fileEntryId);

		super.deleteFileEntry(userId, fileEntryId);

		try {
			QuotaUtil.increaseQuota(dlFileEntry.getGroupId(), dlFileEntry.getUserId(), dlFileEntry.getSize());
		} catch (NoSuchQuotaException e) {
			LOGGER.error("Not found a Quota with groupId: " + dlFileEntry.getGroupId() + " and userId : "+dlFileEntry.getUserId());
		}
	}

	public DLFileEntry updateFileEntry(long userId, long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, boolean majorVersion, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, File file, InputStream is, long size, ServiceContext serviceContext)
			throws PortalException, SystemException {

		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(fileEntryId);
		long groupId = fileEntry.getGroupId();
		try {			
			if (QuotaUtil.hasQuota(groupId, userId, size)) {
				QuotaUtil.increaseQuota(groupId, userId, size);
			}			
		} catch (NoSuchQuotaException e) {
			LOGGER.error("Not found a Quota with groupId: " + groupId + " and userId : "+userId);
		}
	
		DLFileEntry dlFileEntry = super.updateFileEntry(userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, fileEntryTypeId, fieldsMap, file, is, size, serviceContext);

		try {
			QuotaUtil.decreaseQuota(groupId, userId, dlFileEntry.getSize());
		} catch (NoSuchQuotaException e1) {
			LOGGER.error("Not found a Quota with groupId: " + groupId + " and userId : "+userId);
		}
		
		return dlFileEntry;		
	}

}