/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package org.lsug.quota.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import org.lsug.quota.service.ClpSerializer;
import org.lsug.quota.service.QuotaLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class QuotaClp extends BaseModelImpl<Quota> implements Quota {
	public QuotaClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return Quota.class;
	}

	@Override
	public String getModelClassName() {
		return Quota.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _quotaId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setQuotaId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _quotaId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("quotaId", getQuotaId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("quotaAssigned", getQuotaAssigned());
		attributes.put("quotaUsed", getQuotaUsed());
		attributes.put("quotaStatus", getQuotaStatus());
		attributes.put("quotaAlert", getQuotaAlert());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long quotaId = (Long)attributes.get("quotaId");

		if (quotaId != null) {
			setQuotaId(quotaId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long quotaAssigned = (Long)attributes.get("quotaAssigned");

		if (quotaAssigned != null) {
			setQuotaAssigned(quotaAssigned);
		}

		Long quotaUsed = (Long)attributes.get("quotaUsed");

		if (quotaUsed != null) {
			setQuotaUsed(quotaUsed);
		}

		Integer quotaStatus = (Integer)attributes.get("quotaStatus");

		if (quotaStatus != null) {
			setQuotaStatus(quotaStatus);
		}

		Integer quotaAlert = (Integer)attributes.get("quotaAlert");

		if (quotaAlert != null) {
			setQuotaAlert(quotaAlert);
		}
	}

	@Override
	public long getQuotaId() {
		return _quotaId;
	}

	@Override
	public void setQuotaId(long quotaId) {
		_quotaId = quotaId;

		if (_quotaRemoteModel != null) {
			try {
				Class<?> clazz = _quotaRemoteModel.getClass();

				Method method = clazz.getMethod("setQuotaId", long.class);

				method.invoke(_quotaRemoteModel, quotaId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;

		if (_quotaRemoteModel != null) {
			try {
				Class<?> clazz = _quotaRemoteModel.getClass();

				Method method = clazz.getMethod("setCompanyId", long.class);

				method.invoke(_quotaRemoteModel, companyId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;

		if (_quotaRemoteModel != null) {
			try {
				Class<?> clazz = _quotaRemoteModel.getClass();

				Method method = clazz.getMethod("setClassNameId", long.class);

				method.invoke(_quotaRemoteModel, classNameId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_classPK = classPK;

		if (_quotaRemoteModel != null) {
			try {
				Class<?> clazz = _quotaRemoteModel.getClass();

				Method method = clazz.getMethod("setClassPK", long.class);

				method.invoke(_quotaRemoteModel, classPK);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getQuotaAssigned() {
		return _quotaAssigned;
	}

	@Override
	public void setQuotaAssigned(long quotaAssigned) {
		_quotaAssigned = quotaAssigned;

		if (_quotaRemoteModel != null) {
			try {
				Class<?> clazz = _quotaRemoteModel.getClass();

				Method method = clazz.getMethod("setQuotaAssigned", long.class);

				method.invoke(_quotaRemoteModel, quotaAssigned);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getQuotaUsed() {
		return _quotaUsed;
	}

	@Override
	public void setQuotaUsed(long quotaUsed) {
		_quotaUsed = quotaUsed;

		if (_quotaRemoteModel != null) {
			try {
				Class<?> clazz = _quotaRemoteModel.getClass();

				Method method = clazz.getMethod("setQuotaUsed", long.class);

				method.invoke(_quotaRemoteModel, quotaUsed);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getQuotaStatus() {
		return _quotaStatus;
	}

	@Override
	public void setQuotaStatus(int quotaStatus) {
		_quotaStatus = quotaStatus;

		if (_quotaRemoteModel != null) {
			try {
				Class<?> clazz = _quotaRemoteModel.getClass();

				Method method = clazz.getMethod("setQuotaStatus", int.class);

				method.invoke(_quotaRemoteModel, quotaStatus);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getQuotaAlert() {
		return _quotaAlert;
	}

	@Override
	public void setQuotaAlert(int quotaAlert) {
		_quotaAlert = quotaAlert;

		if (_quotaRemoteModel != null) {
			try {
				Class<?> clazz = _quotaRemoteModel.getClass();

				Method method = clazz.getMethod("setQuotaAlert", int.class);

				method.invoke(_quotaRemoteModel, quotaAlert);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public short getQuotaUsedPercentage() {
		try {
			String methodName = "getQuotaUsedPercentage";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Short returnObj = (Short)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean isEnabled() {
		try {
			String methodName = "isEnabled";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Boolean returnObj = (Boolean)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean isUnlimitedQuota() {
		try {
			String methodName = "isUnlimitedQuota";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Boolean returnObj = (Boolean)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean isExceeded() {
		try {
			String methodName = "isExceeded";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Boolean returnObj = (Boolean)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean hasFreeMB(long size) {
		try {
			String methodName = "hasFreeMB";

			Class<?>[] parameterTypes = new Class<?>[] { long.class };

			Object[] parameterValues = new Object[] { size };

			Boolean returnObj = (Boolean)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public BaseModel<?> getQuotaRemoteModel() {
		return _quotaRemoteModel;
	}

	public void setQuotaRemoteModel(BaseModel<?> quotaRemoteModel) {
		_quotaRemoteModel = quotaRemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName,
		Class<?>[] parameterTypes, Object[] parameterValues)
		throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _quotaRemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName,
				remoteParameterTypes);

		Object returnValue = method.invoke(_quotaRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			QuotaLocalServiceUtil.addQuota(this);
		}
		else {
			QuotaLocalServiceUtil.updateQuota(this);
		}
	}

	@Override
	public Quota toEscapedModel() {
		return (Quota)ProxyUtil.newProxyInstance(Quota.class.getClassLoader(),
			new Class[] { Quota.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		QuotaClp clone = new QuotaClp();

		clone.setQuotaId(getQuotaId());
		clone.setCompanyId(getCompanyId());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setQuotaAssigned(getQuotaAssigned());
		clone.setQuotaUsed(getQuotaUsed());
		clone.setQuotaStatus(getQuotaStatus());
		clone.setQuotaAlert(getQuotaAlert());

		return clone;
	}

	@Override
	public int compareTo(Quota quota) {
		long primaryKey = quota.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof QuotaClp)) {
			return false;
		}

		QuotaClp quota = (QuotaClp)obj;

		long primaryKey = quota.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	public Class<?> getClpSerializerClass() {
		return _clpSerializerClass;
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{quotaId=");
		sb.append(getQuotaId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", quotaAssigned=");
		sb.append(getQuotaAssigned());
		sb.append(", quotaUsed=");
		sb.append(getQuotaUsed());
		sb.append(", quotaStatus=");
		sb.append(getQuotaStatus());
		sb.append(", quotaAlert=");
		sb.append(getQuotaAlert());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(28);

		sb.append("<model><model-name>");
		sb.append("org.lsug.quota.model.Quota");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>quotaId</column-name><column-value><![CDATA[");
		sb.append(getQuotaId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>quotaAssigned</column-name><column-value><![CDATA[");
		sb.append(getQuotaAssigned());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>quotaUsed</column-name><column-value><![CDATA[");
		sb.append(getQuotaUsed());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>quotaStatus</column-name><column-value><![CDATA[");
		sb.append(getQuotaStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>quotaAlert</column-name><column-value><![CDATA[");
		sb.append(getQuotaAlert());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _quotaId;
	private long _companyId;
	private long _classNameId;
	private long _classPK;
	private long _quotaAssigned;
	private long _quotaUsed;
	private int _quotaStatus;
	private int _quotaAlert;
	private BaseModel<?> _quotaRemoteModel;
	private Class<?> _clpSerializerClass = org.lsug.quota.service.ClpSerializer.class;
}