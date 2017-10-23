package org.lsug.quota.web.internal.upgrade;

import org.lsug.quota.util.PortletKeys;
import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class QuotaWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"org.lsug.quota.web", "0.0.0", "1.0.0",
			new DummyUpgradeStep());

		registry.register(
			"org.lsug.quota.web", "0.0.1", "1.0.0",
			new BaseUpgradePortletId() {
				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {
						new String[] {"serverquotaportlet_WAR_quotacontrolportlet", PortletKeys.ServerQuotaWeb},
						new String[] {"sitesquotaportlet_WAR_quotacontrolportlet", PortletKeys.SitesQuotaWeb},
						new String[] {"myquotaportlet_WAR_quotacontrolportlet", PortletKeys.UserQuotaWeb},
						new String[] {"siteconfigurationquotaportlet_WAR_quotacontrolportlet", PortletKeys.SiteConfigurationQuotaWeb},
					};
				}

			});
	}

}
