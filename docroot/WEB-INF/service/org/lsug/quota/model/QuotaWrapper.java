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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Quota}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Quota
 * @generated
 */
public class QuotaWrapper implements Quota, ModelWrapper<Quota> {
	public QuotaWrapper(Quota quota) {
		_quota = quota;
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

	/**
	* Returns the primary key of this quota.
	*
	* @return the primary key of this quota
	*/
	@Override
	public long getPrimaryKey() {
		return _quota.getPrimaryKey();
	}

	/**
	* Sets the primary key of this quota.
	*
	* @param primaryKey the primary key of this quota
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_quota.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the quota ID of this quota.
	*
	* @return the quota ID of this quota
	*/
	@Override
	public long getQuotaId() {
		return _quota.getQuotaId();
	}

	/**
	* Sets the quota ID of this quota.
	*
	* @param quotaId the quota ID of this quota
	*/
	@Override
	public void setQuotaId(long quotaId) {
		_quota.setQuotaId(quotaId);
	}

	/**
	* Returns the company ID of this quota.
	*
	* @return the company ID of this quota
	*/
	@Override
	public long getCompanyId() {
		return _quota.getCompanyId();
	}

	/**
	* Sets the company ID of this quota.
	*
	* @param companyId the company ID of this quota
	*/
	@Override
	public void setCompanyId(long companyId) {
		_quota.setCompanyId(companyId);
	}

	/**
	* Returns the fully qualified class name of this quota.
	*
	* @return the fully qualified class name of this quota
	*/
	@Override
	public java.lang.String getClassName() {
		return _quota.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_quota.setClassName(className);
	}

	/**
	* Returns the class name ID of this quota.
	*
	* @return the class name ID of this quota
	*/
	@Override
	public long getClassNameId() {
		return _quota.getClassNameId();
	}

	/**
	* Sets the class name ID of this quota.
	*
	* @param classNameId the class name ID of this quota
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_quota.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this quota.
	*
	* @return the class p k of this quota
	*/
	@Override
	public long getClassPK() {
		return _quota.getClassPK();
	}

	/**
	* Sets the class p k of this quota.
	*
	* @param classPK the class p k of this quota
	*/
	@Override
	public void setClassPK(long classPK) {
		_quota.setClassPK(classPK);
	}

	/**
	* Returns the quota assigned of this quota.
	*
	* @return the quota assigned of this quota
	*/
	@Override
	public long getQuotaAssigned() {
		return _quota.getQuotaAssigned();
	}

	/**
	* Sets the quota assigned of this quota.
	*
	* @param quotaAssigned the quota assigned of this quota
	*/
	@Override
	public void setQuotaAssigned(long quotaAssigned) {
		_quota.setQuotaAssigned(quotaAssigned);
	}

	/**
	* Returns the quota used of this quota.
	*
	* @return the quota used of this quota
	*/
	@Override
	public long getQuotaUsed() {
		return _quota.getQuotaUsed();
	}

	/**
	* Sets the quota used of this quota.
	*
	* @param quotaUsed the quota used of this quota
	*/
	@Override
	public void setQuotaUsed(long quotaUsed) {
		_quota.setQuotaUsed(quotaUsed);
	}

	/**
	* Returns the quota status of this quota.
	*
	* @return the quota status of this quota
	*/
	@Override
	public int getQuotaStatus() {
		return _quota.getQuotaStatus();
	}

	/**
	* Sets the quota status of this quota.
	*
	* @param quotaStatus the quota status of this quota
	*/
	@Override
	public void setQuotaStatus(int quotaStatus) {
		_quota.setQuotaStatus(quotaStatus);
	}

	/**
	* Returns the quota alert of this quota.
	*
	* @return the quota alert of this quota
	*/
	@Override
	public int getQuotaAlert() {
		return _quota.getQuotaAlert();
	}

	/**
	* Sets the quota alert of this quota.
	*
	* @param quotaAlert the quota alert of this quota
	*/
	@Override
	public void setQuotaAlert(int quotaAlert) {
		_quota.setQuotaAlert(quotaAlert);
	}

	@Override
	public boolean isNew() {
		return _quota.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_quota.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _quota.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_quota.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _quota.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _quota.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_quota.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _quota.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_quota.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_quota.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_quota.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new QuotaWrapper((Quota)_quota.clone());
	}

	@Override
	public int compareTo(org.lsug.quota.model.Quota quota) {
		return _quota.compareTo(quota);
	}

	@Override
	public int hashCode() {
		return _quota.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<org.lsug.quota.model.Quota> toCacheModel() {
		return _quota.toCacheModel();
	}

	@Override
	public org.lsug.quota.model.Quota toEscapedModel() {
		return new QuotaWrapper(_quota.toEscapedModel());
	}

	@Override
	public org.lsug.quota.model.Quota toUnescapedModel() {
		return new QuotaWrapper(_quota.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _quota.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _quota.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_quota.persist();
	}

	@Override
	public short getQuotaUsedPercentage() {
		return _quota.getQuotaUsedPercentage();
	}

	@Override
	public boolean hasFreeMB(long size) {
		return _quota.hasFreeMB(size);
	}

	@Override
	public boolean isEnabled() {
		return _quota.isEnabled();
	}

	@Override
	public boolean isExceeded() {
		return _quota.isExceeded();
	}

	@Override
	public boolean isUnlimitedQuota() {
		return _quota.isUnlimitedQuota();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof QuotaWrapper)) {
			return false;
		}

		QuotaWrapper quotaWrapper = (QuotaWrapper)obj;

		if (Validator.equals(_quota, quotaWrapper._quota)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Quota getWrappedQuota() {
		return _quota;
	}

	@Override
	public Quota getWrappedModel() {
		return _quota;
	}

	@Override
	public void resetOriginalValues() {
		_quota.resetOriginalValues();
	}

	private Quota _quota;
}