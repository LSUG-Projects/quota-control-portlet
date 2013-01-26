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

package org.lsug.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class QuotaClp extends BaseModelImpl<Quota> implements Quota {
	public QuotaClp() {
	}

	public Class<?> getModelClass() {
		return Quota.class;
	}

	public String getModelClassName() {
		return Quota.class.getName();
	}

	public long getPrimaryKey() {
		return _qoutaId;
	}

	public void setPrimaryKey(long primaryKey) {
		setQoutaId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_qoutaId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("qoutaId", getQoutaId());
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
		Long qoutaId = (Long)attributes.get("qoutaId");

		if (qoutaId != null) {
			setQoutaId(qoutaId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		String classPK = (String)attributes.get("classPK");

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

	public long getQoutaId() {
		return _qoutaId;
	}

	public void setQoutaId(long qoutaId) {
		_qoutaId = qoutaId;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public String getClassPK() {
		return _classPK;
	}

	public void setClassPK(String classPK) {
		_classPK = classPK;
	}

	public long getQuotaAssigned() {
		return _quotaAssigned;
	}

	public void setQuotaAssigned(long quotaAssigned) {
		_quotaAssigned = quotaAssigned;
	}

	public long getQuotaUsed() {
		return _quotaUsed;
	}

	public void setQuotaUsed(long quotaUsed) {
		_quotaUsed = quotaUsed;
	}

	public int getQuotaStatus() {
		return _quotaStatus;
	}

	public void setQuotaStatus(int quotaStatus) {
		_quotaStatus = quotaStatus;
	}

	public int getQuotaAlert() {
		return _quotaAlert;
	}

	public void setQuotaAlert(int quotaAlert) {
		_quotaAlert = quotaAlert;
	}

	public BaseModel<?> getQuotaRemoteModel() {
		return _quotaRemoteModel;
	}

	public void setQuotaRemoteModel(BaseModel<?> quotaRemoteModel) {
		_quotaRemoteModel = quotaRemoteModel;
	}

	@Override
	public Quota toEscapedModel() {
		return (Quota)Proxy.newProxyInstance(Quota.class.getClassLoader(),
			new Class[] { Quota.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		QuotaClp clone = new QuotaClp();

		clone.setQoutaId(getQoutaId());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setQuotaAssigned(getQuotaAssigned());
		clone.setQuotaUsed(getQuotaUsed());
		clone.setQuotaStatus(getQuotaStatus());
		clone.setQuotaAlert(getQuotaAlert());

		return clone;
	}

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
		if (obj == null) {
			return false;
		}

		QuotaClp quota = null;

		try {
			quota = (QuotaClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = quota.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{qoutaId=");
		sb.append(getQoutaId());
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

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("org.lsug.model.Quota");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>qoutaId</column-name><column-value><![CDATA[");
		sb.append(getQoutaId());
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

	private long _qoutaId;
	private long _classNameId;
	private String _classPK;
	private long _quotaAssigned;
	private long _quotaUsed;
	private int _quotaStatus;
	private int _quotaAlert;
	private BaseModel<?> _quotaRemoteModel;
}