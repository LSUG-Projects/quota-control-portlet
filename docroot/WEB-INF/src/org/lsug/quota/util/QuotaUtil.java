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

package org.lsug.quota.util;

import com.liferay.portal.kernel.exception.PortalException;

import org.lsug.quota.QuotaExceededException;

public class QuotaUtil {

	public static void checkQuotaExceeded(long groupId, long userId, long size) 
		throws PortalException {

		throw new QuotaExceededException();		
	}
}
