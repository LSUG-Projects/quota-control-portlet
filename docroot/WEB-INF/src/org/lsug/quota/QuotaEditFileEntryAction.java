/**
 * Copyright (c) 2013 Liferay Spain User Group All rights reserved.
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

package org.lsug.quota;
  
import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;


public class QuotaEditFileEntryAction extends BaseStrutsPortletAction {

    @Override
	public void processAction(StrutsPortletAction originalStrutsPortletAction,
	       PortletConfig portletConfig, ActionRequest actionRequest,
	       ActionResponse actionResponse) 
	   throws Exception {

		try {
			originalStrutsPortletAction.processAction(
					originalStrutsPortletAction, portletConfig, actionRequest, 
					actionResponse);
		}
		catch (QuotaExceededException qee) {
			ExecutionException e = new ExecutionException();
			
			SessionErrors.add(actionRequest, e.getClass(), e);
			
			return;
		}
   }

   @Override
	public String render(StrutsPortletAction originalStrutsPortletAction,
	       PortletConfig portletConfig, RenderRequest renderRequest,
	       RenderResponse renderResponse) 
	   throws Exception {             
	   
	   return originalStrutsPortletAction.render(
		   originalStrutsPortletAction, portletConfig, renderRequest, 
		   renderResponse);
   }

	       @Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction,
	       PortletConfig portletConfig, ResourceRequest resourceRequest,
	       ResourceResponse resourceResponse) 
	   throws Exception {
	             
	       System.out.println("__CutomStrutsPortletAction   serveResource");
	
	       originalStrutsPortletAction.serveResource(originalStrutsPortletAction, portletConfig,resourceRequest, resourceResponse);
       }

}