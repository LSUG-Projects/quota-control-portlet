/**
 * Copyright (c) 2013-present Liferay Spain User Group All rights reserved.
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

package org.lsug.quota.web.internal.util;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.CacheResourceBundleLoader;
import com.liferay.portal.kernel.util.ClassResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

/**
 * 
 * @author Juan Gonzalez
 *
 */
@Component(
		immediate = true,
		property = {
			"bundle.symbolic.name=com.liferay.document.library.web",
			"resource.bundle.base.name=content.Language",
			"servlet.context.name=document-library-web"
		}
	)
public class QuotaDocumentLibraryResourceBundleLoader implements ResourceBundleLoader {

	@Override
	public ResourceBundle loadResourceBundle(String languageId) {
		return _resourceBundleLoader.loadResourceBundle(languageId);
	}

	@Reference(
		target = "(&(bundle.symbolic.name=com.liferay.document.library.web)(!(component.name=org.lsug.quota.web.internal.util.QuotaDocumentLibraryResourceBundleLoader)))"
	)
	public void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			new CacheResourceBundleLoader(
				new ClassResourceBundleLoader(
					"content.Language",
					QuotaDocumentLibraryResourceBundleLoader.class.getClassLoader())),
			resourceBundleLoader);
	}

	private AggregateResourceBundleLoader _resourceBundleLoader;

}
