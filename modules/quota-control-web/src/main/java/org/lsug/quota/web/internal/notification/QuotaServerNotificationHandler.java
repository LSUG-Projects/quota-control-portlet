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

package org.lsug.quota.web.internal.notification;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.ResourceBundle;

import org.lsug.quota.util.PortletKeys;
import org.osgi.service.component.annotations.Component;

/**
 *  Notifications for server quota
 *  @author Juan Gonzalez
 *
 */
@Component(
		  immediate = true,
		  property = {
				  "javax.portlet.name=" + PortletKeys.ServerQuotaWeb
				  },
		  service = UserNotificationHandler.class
		)
public class QuotaServerNotificationHandler extends BaseUserNotificationHandler {

	public QuotaServerNotificationHandler() {
		setPortletId(PortletKeys.ServerQuotaWeb);
	}

	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext) throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		long userId = jsonObject.getLong("userId");
		String webId = CompanyLocalServiceUtil.getCompany(classPK).getWebId();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", serviceContext.getLocale(), getClass());

		String title = LanguageUtil.format(
				resourceBundle, "quota-from-instance-x-above-the-alert", HtmlUtil.escape(webId));

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$TITLE$]"},
			new String[] {
				title
			});
	}

	protected String getBodyTemplate() throws Exception {
		StringBundler sb = new StringBundler(5);
		sb.append("<div class=\"title\">[$TITLE$]</div>");
		return sb.toString();
	}

}
