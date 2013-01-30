package org.lsug.quota.server.util;

import org.lsug.quota.model.Quota;
import org.lsug.quota.service.QuotaLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;

public class ServerVO {

	private String nameInstance;

	private String numUser;

	private Quota quota;

	public ServerVO(Quota q) {
		setQuota(q);
		try {
			Company company = CompanyLocalServiceUtil.getCompany(quota
					.getClassPK());
			setNameInstance(company.getDefaultWebId());
			// TODO: recover user group from instance
			setNumUser("100");
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}

	public ServerVO(Company company) {
		final Quota quota = QuotaLocalServiceUtil.createQuota(0);
		
		quota.setClassPK(company.getCompanyId());
		setQuota( quota );
		setNameInstance(company.getDefaultWebId());
		setNumUser("100");
	}

	public String getNameInstance() {
		return nameInstance;
	}

	public void setNameInstance(String nameInstance) {
		this.nameInstance = nameInstance;
	}

	public String getNumUser() {
		return numUser;
	}

	public void setNumUser(String numUser) {
		this.numUser = numUser;
	}

	public Quota getQuota() {
		return quota;
	}

	public void setQuota(Quota quota) {
		this.quota = quota;
	}

}
