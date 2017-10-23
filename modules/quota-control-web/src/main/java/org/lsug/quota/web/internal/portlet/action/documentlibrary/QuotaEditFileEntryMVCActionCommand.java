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

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletResponse;

import org.lsug.quota.exception.QuotaExceededException;
import org.lsug.quota.util.PortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

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
			"mvc.command.name=/document_library/edit_file_entry",
			"service.ranking:Integer=100"
		},
		service = MVCActionCommand.class
	)
public class QuotaEditFileEntryMVCActionCommand extends BaseMVCActionCommand {

	
		@Override
		protected void doProcessAction(
				ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			PortletConfig portletConfig = getPortletConfig(actionRequest);

			try {
				mvcActionCommand.processAction(actionRequest, actionResponse);
			}
			catch (Exception e) {
				handleUploadException(
					portletConfig, actionRequest, actionResponse, cmd, e);
			}
		}

		protected void handleUploadException(
				PortletConfig portletConfig, ActionRequest actionRequest,
				ActionResponse actionResponse, String cmd, Exception e)
			throws Exception {

			Throwable errorThrowable = null;
			
			if (e instanceof QuotaExceededException) {
				errorThrowable = e;
			} else if (e.getCause() instanceof QuotaExceededException) {
				errorThrowable = e.getCause();
			}

			if (errorThrowable != null) {
				
				if (!cmd.equals(Constants.ADD_DYNAMIC)) {
					SessionErrors.add(actionRequest, errorThrowable.getClass());
					return;
				}

				HttpServletResponse response = _portal.getHttpServletResponse(
					actionResponse);

				response.setContentType(ContentTypes.TEXT_HTML);
				response.setStatus(HttpServletResponse.SC_OK);

				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
						"content.Language", themeDisplay.getLocale(), getClass());

				String errorMessage = ResourceBundleUtil.getString(
						resourceBundle, "quota-exceeded-error");

				int errorType = PortletKeys.SC_QUOTA_EXCEEDED_ERROR_TYPE;

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("message", errorMessage);
				jsonObject.put("status", errorType);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
				
				SessionErrors.add(actionRequest, errorThrowable.getClass());
				
			}
			else {
				throw e;		
			}
		}

		@Reference
		private Portal _portal;
		
	    @Reference(target = "(component.name=com.liferay.document.library.web.internal.portlet.action.EditFileEntryMVCActionCommand)")
	    protected MVCActionCommand mvcActionCommand;


}
