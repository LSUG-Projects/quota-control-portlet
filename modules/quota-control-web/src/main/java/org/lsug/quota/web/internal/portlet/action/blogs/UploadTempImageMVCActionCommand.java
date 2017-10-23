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

package org.lsug.quota.web.internal.portlet.action.blogs;

import com.liferay.blogs.web.constants.BlogsPortletKeys;
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
	immediate = true,
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"mvc.command.name=/blogs/upload_temp_image",
		"service.ranking:Integer=100"
	}
)
public class UploadTempImageMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	protected void activate(BundleContext bundleContext, Map<String, Object> properties) {
	
		_serviceTrackerFileEntryHandler = new ServiceTracker<>(
				bundleContext,
				"com.liferay.blogs.web.internal.upload.TempImageBlogsUploadFileEntryHandler", 
				new ServiceTrackerCustomizer<Object, ServiceTracker<?, ?>>() {

					private UploadFileEntryHandler _localUploadFileEntryHandler;

					@Override
					public ServiceTracker<?, ?> addingService(ServiceReference<Object> reference) {
						Object service = bundleContext.getService(reference);
						_localUploadFileEntryHandler = (UploadFileEntryHandler) service;
		
						ServiceTracker<?,?> serviceTrackerFileResponseHandler = 
								new ServiceTracker<>(bundleContext, 
										"com.liferay.blogs.web.internal.upload.ImageBlogsUploadResponseHandler",																			
									new ServiceTrackerCustomizer<Object, ServiceRegistration<?>>() {
										@Override
										public ServiceRegistration<?> addingService(
												ServiceReference<Object> reference) {

											Object service = bundleContext.getService(reference);
											_imageBlogsUploadResponseHandler = (UploadResponseHandler) service;
											_tempImageBlogsUploadFileEntryHandler = _localUploadFileEntryHandler;
											return bundleContext.registerService(
													MVCActionCommand.class,
													UploadTempImageMVCActionCommand.this,
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
											_tempImageBlogsUploadFileEntryHandler = null;
											_imageBlogsUploadResponseHandler = null;
											_localUploadFileEntryHandler = null;
											 bundleContext.ungetService(reference);
										}
									});

						serviceTrackerFileResponseHandler.open();
						return serviceTrackerFileResponseHandler;
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
			_tempImageBlogsUploadFileEntryHandler,
			_imageBlogsUploadResponseHandler, actionRequest, actionResponse);
	}
	
	private UploadFileEntryHandler _tempImageBlogsUploadFileEntryHandler;	
	private UploadResponseHandler _imageBlogsUploadResponseHandler;
	
	private ServiceTracker<Object, ServiceTracker<?, ?>> _serviceTrackerFileEntryHandler;

	@Reference
	private QuotaDefaultUploadHandler _quotaDefaultUploadHandler;

}