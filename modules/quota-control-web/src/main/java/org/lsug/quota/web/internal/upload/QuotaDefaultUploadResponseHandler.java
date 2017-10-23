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

package org.lsug.quota.web.internal.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadResponseHandler;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import org.lsug.quota.util.PortletKeys;
import org.osgi.service.component.annotations.Component;

/**
 * 
 * @author Juan Gonzalez
 *
 */
@Component (immediate=true, service=QuotaDefaultUploadResponseHandler.class)
public class QuotaDefaultUploadResponseHandler implements UploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException pe)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("success", Boolean.FALSE);

		String errorMessage = StringPool.BLANK;
		int errorType = PortletKeys.SC_QUOTA_EXCEEDED_ERROR_TYPE;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)portletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", themeDisplay.getLocale(), getClass());

		errorMessage = ResourceBundleUtil.getString(
					resourceBundle, "quota-exceeded-error");

		JSONObject errorJSONObject = JSONFactoryUtil.createJSONObject();

		errorJSONObject.put("errorType", errorType);
		errorJSONObject.put("message", errorMessage);

		jsonObject.put("error", errorJSONObject);

		return jsonObject;
	}

	@Override
	public JSONObject onSuccess(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry)
		throws PortalException {

		//no-op Not used
		return null;
	}

}