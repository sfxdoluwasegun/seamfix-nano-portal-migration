package com.nano.portal.portlets.support;

import java.io.IOException;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.cdi.portlet.bridge.CDIBeanManagerUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class Support
 */

public class Support extends MVCPortlet {
	
	private SupportService supportService ;
	
	@Override
	public void init(PortletConfig config) throws PortletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		supportService = (SupportService) CDIBeanManagerUtil.getManagedBeanReference(SupportService.class);
	}

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		// TODO Auto-generated method stub
		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		// TODO Auto-generated method stub
		super.serveResource(resourceRequest, resourceResponse);
	}
	
	
 

}
