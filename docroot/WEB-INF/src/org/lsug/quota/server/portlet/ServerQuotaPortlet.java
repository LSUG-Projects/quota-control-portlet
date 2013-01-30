package org.lsug.quota.server.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.lsug.quota.NoSuchQuotaException;
import org.lsug.quota.model.Quota;
import org.lsug.quota.server.util.ServerVO;
import org.lsug.quota.service.QuotaLocalServiceUtil;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

public class ServerQuotaPortlet extends com.liferay.util.bridges.mvc.MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		final String cmd = ParamUtil.getString(renderRequest, Constants.CMD,
				StringPool.BLANK);

		if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
			editQuota(renderRequest, renderResponse);
		} else {
			listServerQuotas(renderRequest, renderResponse);
		}

	}

	private void listServerQuotas(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		int cur = ParamUtil.getInteger(renderRequest, "cur", 0);
		int paramDelta = ParamUtil.getInteger(renderRequest, "delta", 10);
		PortletURL portletURL = renderResponse.createRenderURL();

		SearchContainer<ServerVO> searchContainer = new SearchContainer<ServerVO>(
				renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
				cur, paramDelta, portletURL, null, null);
		searchContainer.setDelta(paramDelta);
		searchContainer.setDeltaConfigurable(false);

		List<Quota> listQuotas = new ArrayList<Quota>();
		List<ServerVO> listServerVOs = new ArrayList<ServerVO>();
		int listQuotasCount = 0;
		try {
			// TODO: Cambiar por count
			listQuotasCount = QuotaLocalServiceUtil.getQuotas(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS).size();

			List<Company> listCompany = CompanyLocalServiceUtil.getCompanies();

			listQuotasCount = listCompany.size();
			ServerVO serverVO = null;
			for (Company company : listCompany) {
				try {
					Quota quota = QuotaLocalServiceUtil
							.getQuotaByClassNameIdClassPK(PortalUtil
									.getClassNameId(Company.class.getName()),
									company.getCompanyId());
					serverVO = new ServerVO(quota);
				} catch (NoSuchQuotaException e) {
					serverVO = new ServerVO(company);
				}
				listServerVOs.add(serverVO);
			}
		} catch (SystemException e) {
			// TODO: Exceptions control
		}
		renderRequest.setAttribute("searchContainer", searchContainer);
		renderRequest.setAttribute("list", listServerVOs);
		renderRequest.setAttribute("count", listQuotasCount);
		super.doView(renderRequest, renderResponse);
	}

	private void editQuota(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		Quota quota = null;
		final long quotaId = ParamUtil.getLong(renderRequest, "quotaId", 0);
		if (quotaId == 0) {
			quota = QuotaLocalServiceUtil.createQuota(0);
		} else {
			try {
				quota = QuotaLocalServiceUtil.getQuota(ParamUtil.getLong(
						renderRequest, "quotaId"));
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		renderRequest.setAttribute("quota", quota);

		include("/html/server-quota/edit_quota.jsp", renderRequest,
				renderResponse);
	}

	public void saveServerQuota(final ActionRequest req,
			final ActionResponse res) throws SystemException {

		final String cmd = ParamUtil.getString(req, Constants.CMD,
				StringPool.BLANK);

		final long quotaId = ParamUtil.getLong(req, "quotaId");

		final long classPK = ParamUtil.getLong(req, "classPK");

		final int quotaStatus = ParamUtil.getInteger(req, "quotaStatus");

		final long quotaAssigned = ParamUtil.getLong(req, "quotaAssigned");

		final int quotaAlert = ParamUtil.getInteger(req, "quotaAlert");

		final Quota quota = QuotaLocalServiceUtil.createQuota(0);

		quota.setQuotaId(quotaId);
		quota.setClassNameId(PortalUtil.getClassNameId(Company.class));
		quota.setClassPK(classPK);
		quota.setQuotaStatus(quotaStatus);
		quota.setQuotaAssigned(quotaAssigned);
		quota.setQuotaAlert(quotaAlert);

		// TODO: handle exceptions and remove the throws clause from method
		// signature
		if (cmd.equals(Constants.ADD)) {
			final long newQuotaId = CounterLocalServiceUtil
					.increment(Quota.class.getName());

			quota.setQuotaId(newQuotaId);
			QuotaLocalServiceUtil.addQuota(quota);
		} else if (cmd.equals(Constants.UPDATE)) {
			QuotaLocalServiceUtil.updateQuota(quota);
		} else {
			SessionErrors.add(req, "quota-server-invalid-command");
		}

	}
}
