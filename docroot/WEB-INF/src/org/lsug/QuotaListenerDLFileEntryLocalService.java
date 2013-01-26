package org.lsug;

import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceWrapper;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;

public class QuotaListenerDLFileEntryLocalService 
	extends DLFileEntryLocalServiceWrapper {
	
	public QuotaListenerDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {
		
		super(dlFileEntryLocalService);
	}

}