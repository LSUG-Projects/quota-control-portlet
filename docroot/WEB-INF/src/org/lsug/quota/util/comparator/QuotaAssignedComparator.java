
package org.lsug.quota.util.comparator;

import org.lsug.quota.model.Quota;

import com.liferay.portal.kernel.util.OrderByComparator;

public class QuotaAssignedComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "quotaAssigned ASC";
	public static String ORDER_BY_DESC = "quotaAssigned DESC";
	public static String[] ORDER_BY_FIELDS = {
		"quotaAssigned"
	};

	public QuotaAssignedComparator() {

		this(false);
	}

	public QuotaAssignedComparator(boolean ascending) {

		_ascending = ascending;
	}

	public int compare(Object obj1, Object obj2) {

		Quota entry1 = (Quota) obj1;
		Quota entry2 = (Quota) obj2;

		int value = 0;

		if (entry1.getQuotaAssigned() < entry2.getQuotaAssigned()) {
			value = -1;
		}
		else if (entry1.getQuotaAssigned() > entry2.getQuotaAssigned()) {
			value = 1;
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	public String getOrderBy() {

		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
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
