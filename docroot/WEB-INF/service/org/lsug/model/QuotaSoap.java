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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link org.lsug.service.http.QuotaServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       org.lsug.service.http.QuotaServiceSoap
 * @generated
 */
public class QuotaSoap implements Serializable {
	public static QuotaSoap toSoapModel(Quota model) {
		QuotaSoap soapModel = new QuotaSoap();

		soapModel.setQoutaId(model.getQoutaId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setQuotaAssigned(model.getQuotaAssigned());
		soapModel.setQuotaUsed(model.getQuotaUsed());
		soapModel.setQuotaStatus(model.getQuotaStatus());
		soapModel.setQuotaAlert(model.getQuotaAlert());

		return soapModel;
	}

	public static QuotaSoap[] toSoapModels(Quota[] models) {
		QuotaSoap[] soapModels = new QuotaSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static QuotaSoap[][] toSoapModels(Quota[][] models) {
		QuotaSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new QuotaSoap[models.length][models[0].length];
		}
		else {
			soapModels = new QuotaSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static QuotaSoap[] toSoapModels(List<Quota> models) {
		List<QuotaSoap> soapModels = new ArrayList<QuotaSoap>(models.size());

		for (Quota model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new QuotaSoap[soapModels.size()]);
	}

	public QuotaSoap() {
	}

	public long getPrimaryKey() {
		return _qoutaId;
	}

	public void setPrimaryKey(long pk) {
		setQoutaId(pk);
	}

	public long getQoutaId() {
		return _qoutaId;
	}

	public void setQoutaId(long qoutaId) {
		_qoutaId = qoutaId;
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

	private long _qoutaId;
	private long _classNameId;
	private String _classPK;
	private long _quotaAssigned;
	private long _quotaUsed;
	private int _quotaStatus;
	private int _quotaAlert;
}