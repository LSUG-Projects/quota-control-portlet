package org.lsug;

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

}