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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;

import java.util.Map;

import org.lsug.quota.configuration.QuotaConfiguration;
import org.lsug.quota.model.Quota;
import org.lsug.quota.util.comparator.QuotaAssignedComparator;
import org.lsug.quota.util.comparator.QuotaEnabledComparator;
import org.lsug.quota.util.comparator.QuotaUsedComparator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

/**
 * Utils class for multiple operations
 * @author Juan Gonzalez
 *
 */
@Component(configurationPid = "org.lsug.quota.configuration.QuotaConfiguration")
public class QuotaUtil {

	public static final String[] validClassNames = {User.class.getName(), Group.class.getName(), Organization.class.getName()};

	private static volatile QuotaConfiguration quotaConfiguration;

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
	     quotaConfiguration = ConfigurableUtil.createConfigurable(QuotaConfiguration.class, properties);
	}

	public static final QuotaConfiguration getQuotaConfiguration() {
		return quotaConfiguration;
	}

	public static OrderByComparator<Quota> getQuotaOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Quota> orderByComparator = null;

		if (orderByCol.equals("quotaUsed")) {
			orderByComparator = new QuotaUsedComparator(orderByAsc);
		} else if (orderByCol.equals("quotaAssigned")) {
			orderByComparator = new QuotaAssignedComparator(orderByAsc);
		} else if (orderByCol.equals("quotaStatus")) {
			orderByComparator = new QuotaEnabledComparator(orderByAsc);
		}

		return orderByComparator;
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
