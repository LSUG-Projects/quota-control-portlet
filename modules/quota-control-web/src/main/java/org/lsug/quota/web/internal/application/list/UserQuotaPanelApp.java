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

package org.lsug.quota.web.internal.application.list;

import org.lsug.quota.util.PortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;

/**
 * 
 * @author Juan Gonzalez
 *
 */
@Component(immediate = true, property = { "panel.app.order:Integer=100",
		"panel.category.key=" + PanelCategoryKeys.USER_MY_ACCOUNT }, 
		service = PanelApp.class)
public class UserQuotaPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return PortletKeys.UserQuotaWeb;
	}

	@Override
	@Reference(target = "(javax.portlet.name=" + PortletKeys.UserQuotaWeb + ")", unbind = "-")
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}
}
