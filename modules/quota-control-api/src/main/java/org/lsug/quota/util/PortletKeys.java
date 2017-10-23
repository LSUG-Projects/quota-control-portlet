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
package org.lsug.quota.util;

import com.liferay.portal.kernel.servlet.ServletResponseConstants;

/**
 * 
 * @author Juan Gonzalez
 *
 */
public class PortletKeys {

	public static final String ServerQuotaWeb = "org_lsug_quota_web_portlet_ServerQuotaWebPortlet";
	public static final String SitesQuotaWeb = "org_lsug_quota_web_portlet_SitesQuotaWebPortlet";
	public static final String UserQuotaWeb = "org_lsug_quota_web_portlet_UserQuotaWebPortlet";
	public static final String SiteConfigurationQuotaWeb = "org_lsug_quota_web_portlet_SiteConfigurationQuotaWebPortlet";
	public static final int QUOTA_EXCEEDED_NOTIFICATION_TYPE=1234;
	//TODO Error should be CUSTOM_FILE_ERROR (499) 
	//BUT CODE RIGHT NOW ONLY SHOWS CUSTOM ERROR MESSAGE ON ANTIVIRUS EXCEPTION
	//See https://issues.liferay.com/browse/LPS-75260
	public static final int SC_QUOTA_EXCEEDED_ERROR_TYPE=ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
	
}
