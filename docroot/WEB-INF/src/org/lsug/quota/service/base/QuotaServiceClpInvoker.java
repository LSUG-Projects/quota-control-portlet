/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package org.lsug.quota.service.base;

import org.lsug.quota.service.QuotaServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class QuotaServiceClpInvoker {
	public QuotaServiceClpInvoker() {
		_methodName20 = "getBeanIdentifier";

		_methodParameterTypes20 = new String[] {  };

		_methodName21 = "setBeanIdentifier";

		_methodParameterTypes21 = new String[] { "java.lang.String" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName20.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes20, parameterTypes)) {
			return QuotaServiceUtil.getBeanIdentifier();
		}

		if (_methodName21.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes21, parameterTypes)) {
			QuotaServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName20;
	private String[] _methodParameterTypes20;
	private String _methodName21;
	private String[] _methodParameterTypes21;
}