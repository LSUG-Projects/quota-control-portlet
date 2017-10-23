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

package org.lsug.quota.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

/**
 * 
 * @author Juan Gonzalez
 *
 */
@ExtendedObjectClassDefinition(
		category = "other",
		scope = ExtendedObjectClassDefinition.Scope.COMPANY
	)
@Meta.OCD(
		id = "org.lsug.quota.configuration.QuotaConfiguration",
		localization = "content/Language", name = "quota.configuration.name"
	)
public interface QuotaConfiguration {

	@Meta.AD(
		deflt = "Administrator",
		required = false,
		description = "Role to which company quota alert notifications will be sent to"
	)
	public String notificationCompanyRole();
	
	@Meta.AD(
		deflt = "Administrator",
		required = false,
		description = "Role to which guest site quota alert notifications will be sent to"
	)
	public String notificationSiteGuestRole();
	
	@Meta.AD(
		deflt = "Organization Owner",
		required = false,
		description = "Role to which organization site quota alert notifications will be sent to"
	)
	public String notificationSiteOrganizationRole();
	
	@Meta.AD(
		deflt = "Site Owner",
		required = false,
		description = "Role to which regular site quota alert notifications will be sent to"
	)
	public String notificationSiteRegularRole();
}
