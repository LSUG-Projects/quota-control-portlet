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

package org.lsug.quota.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;

import org.lsug.quota.model.Quota;

/**
 * Comparator for quotaAssigned field
 * @author Juan Gonzalez
 *
 */
public class QuotaAssignedComparator extends OrderByComparator<Quota> {

	public static String ORDER_BY_ASC = "quotaAssigned ASC";

	public static String ORDER_BY_DESC = "quotaAssigned DESC";

	public static String[] ORDER_BY_FIELDS = { "quotaAssigned" };

	public QuotaAssignedComparator() {

		this(false);
	}

	public QuotaAssignedComparator(boolean ascending) {

		_ascending = ascending;
	}

	public int compare(Quota obj1, Quota obj2) {

		Quota entry1 = (Quota)obj1;
		Quota entry2 = (Quota)obj2;

		int value = 0;

		if (entry1.getQuotaAssigned() < entry2.getQuotaAssigned()) {
			value = -1;
		} else if (entry1.getQuotaAssigned() > entry2.getQuotaAssigned()) {
			value = 1;
		}

		if (_ascending) {
			return value;
		} else {
			return -value;
		}
	}

	public String getOrderBy() {

		if (_ascending) {
			return ORDER_BY_ASC;
		} else {
			return ORDER_BY_DESC;
		}
	}

	public String[] getOrderByFields() {

		return ORDER_BY_FIELDS;
	}

	public boolean isAscending() {

		return _ascending;
	}

	private boolean _ascending;
}