/**
 * Copyright (c) 2013 Liferay Spain User Group All rights reserved.
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

package org.lsug.quota.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Group;
import com.liferay.util.bridges.mvc.MVCPortlet;
import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalServiceUtil;

public class SitesQuotaPortlet extends MVCPortlet {

	public Quota updateQuota(
		ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long quotaId = ParamUtil.getLong(actionRequest, "quotaId");
		long classNameId = PortalUtil.getClassNameId(Group.class);
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		int quotaAlert = ParamUtil.getInteger(actionRequest, "quotaAlert");
		long quotaAssigned = ParamUtil.getLong(actionRequest, "quotaAssigned");
		long quotaUsed = ParamUtil.getLong(actionRequest, "quotaUsed");
		int quotaStatus = ParamUtil.getInteger(actionRequest, "quotaStatus");

		return QuotaLocalServiceUtil.updateQuota(
			quotaId, classNameId, classPK, quotaAlert, quotaAssigned,
			quotaUsed, quotaStatus);
	}

}
