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

import org.lsug.quota.util.PortletKeys;
import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;

/**
 * 
 * @author Juan Gonzalez
 *
 */
@Component(
		  immediate = true,
		  property = {
				  "javax.portlet.name=" + PortletKeys.ServerQuotaWeb			  
				  },
		  service = UserNotificationDefinition.class
		)
public class QuotaServerNotificationDefinition extends UserNotificationDefinition {

	public QuotaServerNotificationDefinition() {

		super(PortletKeys.ServerQuotaWeb, 
				0,
				PortletKeys.QUOTA_EXCEEDED_NOTIFICATION_TYPE,
				"quota-notification-server-description");

	    addUserNotificationDeliveryType(
	      new UserNotificationDeliveryType(
	        "email", UserNotificationDeliveryConstants.TYPE_EMAIL, true, true));
	    addUserNotificationDeliveryType(
	      new UserNotificationDeliveryType(
	        "website", UserNotificationDeliveryConstants.TYPE_WEBSITE, true, true));
	}

}
