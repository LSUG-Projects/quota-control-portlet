package org.lsug.quota.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import org.lsug.model.Quota;

public class SitesQuotaPortlet extends MVCPortlet {
	
	public Quota updateQuota(ActionRequest actionRequest, ActionResponse actionResponse) 
			throws Exception {
		
		long quotaId = ParamUtil.getLong(actionRequest, "quotaId");
		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
	    long classPK = ParamUtil.getLong(actionRequest, "classPK");
		int quotaAlert = ParamUtil.getInteger(actionRequest, "quotaAlert");
		long quotaAssigned = ParamUtil.getLong(actionRequest, "quotaAssigned");
		long quotaUsed = ParamUtil.getLong(actionRequest, "quotaUsed");
		
		return QuotaServiceUtil.updateQuota(quotaId, classNameId, classPK, 
				quotaAlert, quotaAssigned, quotaUsed);
	}

}
