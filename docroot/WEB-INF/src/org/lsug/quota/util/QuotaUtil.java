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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.servlet.jsp.PageContext;

import org.lsug.quota.util.comparator.QuotaAssignedComparator;
import org.lsug.quota.util.comparator.QuotaEnabledComparator;
import org.lsug.quota.util.comparator.QuotaUsedComparator;

/**
 * Utils class for multiple operations
 * @author LSUG
 *
 */
public class QuotaUtil {

	public static final String[] validClassNames = {User.class.getName(), Group.class.getName(), Organization.class.getName()};

	public static OrderByComparator getQuotaOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("quotaUsed")) {
			orderByComparator = new QuotaUsedComparator(orderByAsc);
		} else if (orderByCol.equals("quotaAssigned")) {
			orderByComparator = new QuotaAssignedComparator(orderByAsc);
		} else if (orderByCol.equals("quotaStatus")) {
			orderByComparator = new QuotaEnabledComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static String getResource(Locale locale, String key, Object... arguments) {
		String text = ResourceBundle.getBundle("content.Language", locale).getString(key);
		return MessageFormat.format(text, arguments);
	}

	public static String getResource(PageContext pageContext, String key) {
		return LanguageUtil.get(pageContext, key);
	}

	public static String getResource(PortletRequest portletRequest, String key) {
		PortletConfig portletConfig = (PortletConfig)portletRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		return LanguageUtil.get(portletConfig, portletRequest.getLocale(), key);
	}

	public static String getResource(PortletRequest portletRequest, String key, Object... arguments) {
		PortletConfig portletConfig = (PortletConfig)portletRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		return LanguageUtil.format(portletConfig, portletRequest.getLocale(), key, arguments);
	}

	public static boolean isValidGroupQuota(Group group) {

		String className = group.getClassName();

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Checking group validity for quota, group:" + group.toString() + ",className:" + className + ",is site:"+group.isSite()+",is staging group:"+group.isStagingGroup());
		}
				
		if (!ArrayUtil.contains(validClassNames, className)) {
			return false;
		}

		if (!group.isSite() && !User.class.getName().equals(className)) {
			return false;
		}

		if (group.isStagingGroup()) {
			return false;
		}

		return true;
	}

	private static final transient Log LOGGER = LogFactoryUtil
			.getLog(QuotaUtil.class);
	
}