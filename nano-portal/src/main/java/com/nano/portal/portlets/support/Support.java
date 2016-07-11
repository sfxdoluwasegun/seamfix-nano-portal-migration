package com.nano.portal.portlets.support;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.cdi.portlet.bridge.CDIBeanManagerUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.nano.jpa.entity.admin.Admin;

/**
 * Portlet implementation class Support
 */

public class Support extends MVCPortlet {
	
	private SupportService supportService ;
	
	
	@Override
	public void init(PortletConfig config) throws PortletException {
		super.init(config);
		
		supportService = (SupportService) CDIBeanManagerUtil.getManagedBeanReference(SupportService.class);
	}

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		super.doView(renderRequest, renderResponse);
		getUserList(renderRequest, renderResponse);
		
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		super.serveResource(resourceRequest, resourceResponse);
	}
	
	
	@ProcessAction(name = "showUserList")
	public void showUserList(ActionRequest request, ActionResponse response) {
		List<Admin> users = supportService.getUserList();
		request.setAttribute("users", users);
		request.setAttribute("selectedTab", "user");
		response.setRenderParameter("jspPage", "/html/support/view.jsp");
	}
	
	@ProcessAction(name = "getUserList")
	public void getUserList(RenderRequest request, RenderResponse response) {
		List<Admin> users = supportService.getUserList();
		request.setAttribute("users", users);
		request.setAttribute("selectedTab", "user");
	}
	
	@ProcessAction(name = "addUser")
	public void addUser(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);

		String firstname = ParamUtil.getString(request, "firstname");
		String middlename = ParamUtil.getString(request, "middlename");
		String lastname = ParamUtil.getString(request, "lastname");
		String gender = ParamUtil.getString(request, "gender");
		String email = ParamUtil.getString(request, "email");
		String phone = ParamUtil.getString(request, "phone");
		String community = ParamUtil.getString(request, "community");
				
		// verify that the user is logged in
		if (Validator.isNull(getLoggedInUser(request))) {
			SessionErrors.add(request, "loginError");

			showAddUserPage(request, response);
		} else {
			// check if user exist
			User emailUser = supportService.getUserByEmail(email);
		}

		String result = supportService.addUser(firstname,middlename,lastname,middlename,gender,email,phone,community);

		if (result != supportService.messageSuccessful) {
			showError(request, result);
		} else {
			SessionMessages.add(request, "showSuccess");
		}
		showUserList(request, response);
	}
	
	
	public User getLoggedInUser(PortletRequest portletRequest) {
		if (Validator.isNotNull(portletRequest.getRemoteUser()))
			return supportService.getUserById(Long.valueOf(portletRequest.getRemoteUser()));
		else
			return null;
	}

	@ProcessAction(name = "deactivateUser")
	public void deactivateUser(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		Long pk = Long.parseLong(ParamUtil.getString(request, "userID"));

		String result = supportService.deactivateUser(pk);

		if (result != supportService.messageSuccessful) {
			showError(request, result);
		} else {
			SessionMessages.add(request, "showSuccess");
		}
		showUserList(request, response);
	}
	
	public void showError(ActionRequest request, String error) {
		SessionErrors.add(request, error);
		hideLiferayDefaultErrorMessage(request);
	}
	
	public void hideLiferayDefaultErrorMessage(ActionRequest request) {
		SessionMessages.add(request,
				PortalUtil.getPortletId(request) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}

	@ProcessAction(name = "showEditUserPage")
	public void showEditUserPage(ActionRequest request, ActionResponse response) {
		request.setAttribute("selectedTab", "user");
		List<Group> groups = supportService.getAllGroups();

		request.setAttribute("groups", groups);
		response.setRenderParameter("jspPage", "/html/support/editUser.jsp");
	}
	
	@ProcessAction(name = "showAddUserPage")
	public void showAddUserPage(ActionRequest request, ActionResponse response) {
		request.setAttribute("selectedTab", "user");
		List<Group> groups = supportService.getAllGroups();

		request.setAttribute("groups", groups);
		response.setRenderParameter("jspPage", "/html/support/addUser.jsp");
	}
	

}
