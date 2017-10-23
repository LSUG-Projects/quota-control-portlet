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

package org.lsug.quota.web.internal.portlet.action.documentlibrary;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.upload.UploadFileEntryHandler;
import com.liferay.upload.UploadResponseHandler;

import java.util.Hashtable;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.lsug.quota.web.internal.upload.QuotaDefaultUploadHandler;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * 
 * @author Juan Gonzalez
 *
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/upload_file_entry",
		"service.ranking:Integer=100"
	}
)
public class QuotaUploadFileEntryMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	protected void activate(BundleContext bundleContext, Map<String, Object> properties) {
	
		_serviceTrackerFileEntryHandler = new ServiceTracker<>(
				bundleContext,
				"com.liferay.document.library.web.internal.upload.DLUploadFileEntryHandler", 
				new ServiceTrackerCustomizer<Object, ServiceTracker<?, ?>>() {

					private UploadFileEntryHandler _localUploadFileEntryHandler;

					@Override
					public ServiceTracker<?, ?> addingService(ServiceReference<Object> reference) {
						Object service = bundleContext.getService(reference);
						_localUploadFileEntryHandler = (UploadFileEntryHandler) service;
		
						ServiceTracker<?,?> _serviceTrackerFileResponseHandler = 
								new ServiceTracker<>(bundleContext, 
										"com.liferay.document.library.web.internal.upload.DLUploadResponseHandler",																			
									new ServiceTrackerCustomizer<Object, ServiceRegistration<?>>() {
										@Override
										public ServiceRegistration<?> addingService(
												ServiceReference<Object> reference) {

											Object service = bundleContext.getService(reference);
											_dlUploadResponseHandler = (UploadResponseHandler) service;
											_dlLUploadFileEntryHandler = _localUploadFileEntryHandler;
											return bundleContext.registerService(
													MVCActionCommand.class,
													QuotaUploadFileEntryMVCActionCommand.this,
													new Hashtable<>(properties));
										}
	
										@Override
										public void modifiedService(ServiceReference<Object> reference,
												ServiceRegistration<?> service) {
										}
	
										@Override
										public void removedService(ServiceReference<Object> reference,
												ServiceRegistration<?> service) {

											 service.unregister();											
											 _dlLUploadFileEntryHandler = null;
											_dlUploadResponseHandler = null;
											_localUploadFileEntryHandler = null;
											 bundleContext.ungetService(reference);
										}
									});

						_serviceTrackerFileResponseHandler.open();
						return _serviceTrackerFileResponseHandler;
					}

					@Override
					public void modifiedService(ServiceReference<Object> reference,
							ServiceTracker<?, ?> service) {
						
					}

					@Override
					public void removedService(ServiceReference<Object> reference,
							ServiceTracker<?,?> service) {

						bundleContext.ungetService(reference);
						service.close();
						
					}
				});

		_serviceTrackerFileEntryHandler.open();
	}
	
	@Deactivate
	protected void deactivate() {
		_serviceTrackerFileEntryHandler.close();
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_quotaDefaultUploadHandler.upload(
				_dlLUploadFileEntryHandler, _dlUploadResponseHandler, actionRequest,
			actionResponse);
	}

	
	private UploadFileEntryHandler _dlLUploadFileEntryHandler;
	private UploadResponseHandler _dlUploadResponseHandler;

	private ServiceTracker<?, ?> _serviceTrackerFileEntryHandler;
	
	@Reference
	private QuotaDefaultUploadHandler _quotaDefaultUploadHandler;

}