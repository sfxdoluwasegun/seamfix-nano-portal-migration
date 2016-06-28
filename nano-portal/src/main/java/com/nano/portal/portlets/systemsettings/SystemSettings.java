package com.nano.portal.portlets.systemsettings;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.cdi.portlet.bridge.CDIBeanManagerUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.nano.jpa.entity.Settings;
import com.nano.jpa.entity.Settlement;
import com.nano.jpa.entity.Thresholds;
import com.nano.jpa.entity.ras.BorrowableAmount;
import com.nano.jpa.enums.BankData;
import com.nano.jpa.enums.Creditor;
import com.nano.jpa.enums.SettlementType;

/**
 * Portlet implementation class SystemSettings
 */

public class SystemSettings extends MVCPortlet {

	private SettingService settingService;

	@Override
	public void init(PortletConfig config) throws PortletException {

		super.init(config);
		settingService = (SettingService) CDIBeanManagerUtil.getManagedBeanReference(SettingService.class);
		// String viewJSP = getInitParameter("View-Template");

	}

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		renderRequest.setAttribute("selectedTab", "general");

		GetGeneralSettingsList(renderRequest, renderResponse);

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		super.serveResource(resourceRequest, resourceResponse);
	}

	public void GetGeneralSettingsList(RenderRequest renderRequest, RenderResponse renderResponse) {
		List<Settings> generalSettings = settingService.getGeneralSettingsList();

		renderRequest.setAttribute("generalSettings", generalSettings);
		renderRequest.setAttribute("selectedTab", "general");
	}

	public void hideLiferayDefaultErrorMessage(ActionRequest request) {
		SessionMessages.add(request,
				PortalUtil.getPortletId(request) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}

	public void showError(ActionRequest request, String error) {
		SessionErrors.add(request, error);
		hideLiferayDefaultErrorMessage(request);
	}

	@ProcessAction(name = "addGeneralSetting")
	public void addGeneralSetting(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);

		String name = ParamUtil.getString(request, "Name");
		String description = ParamUtil.getString(request, "Description");
		String value = ParamUtil.getString(request, "Value");

		request.setAttribute("selectedTab", "general");

		String result = settingService.addGeneralSetting(name, description, value);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
			showGeneralSettingsAddPage(request, response);
		} else {
			SessionMessages.add(request, "showSuccess");
			this.showGeneralSettingsList(request, response);
		}

	}

	@ProcessAction(name = "addAirtimePoolSetting")
	public void addAirtimePoolSetting(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);

		String name = ParamUtil.getString(request, "Name");
		String description = ParamUtil.getString(request, "Description");
		String value = ParamUtil.getString(request, "Value");
		request.setAttribute("selectedTab", "pool");

		String result = settingService.addAirtimePoolSetting(name, description, value);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
			showAirtimePoolAddPage(request, response);
		} else {
			SessionMessages.add(request, "showSuccess");
			this.showAirtimePoolList(request, response);
		}

	}

	@ProcessAction(name = "addThreshold")
	public void addThreshold(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);

		String name = ParamUtil.getString(request, "Name");
		BigDecimal amount = new BigDecimal(ParamUtil.getDouble(request, "Amount"), MathContext.DECIMAL64);

		request.setAttribute("selectedTab", "threshold");

		String result = settingService.addThreshold(name, amount);
		if (result != settingService.messageSuccessful) {
			showError(request, result);
			showThresholdAddPage(request, response);
		} else {
			SessionMessages.add(request, "showSuccess");
			showThresholdList(request, response);
		}

	}

	@ProcessAction(name = "addRASSetting")
	public void addRASSetting(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);

		int minAgeOnNetwork = ParamUtil.getInteger(request, "AgeOnNetwork");
		int minBalance = ParamUtil.getInteger(request, "Balance");
		int minTopUp = ParamUtil.getInteger(request, "NoOfTopUps");
		int minTopUpDuration = ParamUtil.getInteger(request, "TopUpsDuration");
		int minTopUpValue = ParamUtil.getInteger(request, "TopUpsValue");
		int amount = ParamUtil.getInteger(request, "Amount");
		int serviceFee = ParamUtil.getInteger(request, "ServiceFee");

		request.setAttribute("selectedTab", "ras");

		String result = settingService.addRASSetting(minAgeOnNetwork, minBalance, minTopUp, minTopUpDuration,
				minTopUpValue, amount, serviceFee);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
			request.setAttribute("selectedTab", "ras");
			request.setAttribute("amount", amount);
			response.setRenderParameter("jspPage", "/html/systemsettings/AddRAS.jsp");
		} else {
			SessionMessages.add(request, "showSuccess");
			showRASList(request, response);
		}

	}

	@ProcessAction(name = "checkRASAmount")
	public void checkRASAmount(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		int rasAmount = ParamUtil.getInteger(request, "Amount");
		String result = settingService.checkRASAmount(rasAmount);

		if (result == settingService.messageSuccessful) {
			request.setAttribute("selectedTab", "ras");
			request.setAttribute("amount", rasAmount);
			response.setRenderParameter("jspPage", "/html/systemsettings/AddRAS.jsp");
		} else {
			showError(request, result);
			showRASList(request, response);
		}
	}

	@ProcessAction(name = "addSettlementSetting")
	public void addSettlementSetting(ActionRequest request, ActionResponse response) {

		String accountNo = ParamUtil.getString(request, "accountNumber");
		BankData bank = BankData.fromCode(request.getParameter("bank"));
		Double percentage = ParamUtil.getDouble(request, "settlementPercentage");
		SettlementType settlementType = SettlementType.valueOf(request.getParameter("settlementType"));
		Creditor creditor = Creditor.valueOf(request.getParameter("creditor"));

		request.setAttribute("selectedTab", "settlement");

		String result = settingService.addSettlementSetting(accountNo, percentage, settlementType, bank, creditor);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
			showSettlementAddPage(request, response);
		} else {
			SessionMessages.add(request, "showSuccess");
			showSettlementList(request, response);
		}

	}

	@ProcessAction(name = "editThreshold")
	public void editThreshold(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		String name = ParamUtil.getString(request, "Name");
		BigDecimal amount = new BigDecimal(ParamUtil.getDouble(request, "Amount"), MathContext.DECIMAL64);
		Long pk = Long.parseLong(ParamUtil.getString(request, "pk"));

		request.setAttribute("selectedTab", "threshold");

		String result = settingService.editThreshold(pk, name, amount);
		if (result != settingService.messageSuccessful) {
			showError(request, result);
			Thresholds threshold = settingService.getThresholdByPk(pk);

			request.setAttribute("threshold", threshold);
			request.setAttribute("selectedTab", "threshold");
			response.setRenderParameter("jspPage", "/html/systemsettings/EditThreshold.jsp");

		} else {
			SessionMessages.add(request, "showSuccess");
			showThresholdList(request, response);
		}

	}

	@ProcessAction(name = "editAirtimePoolSetting")
	public void editAirtimePoolSetting(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		Long pkay = Long.parseLong(ParamUtil.getString(request, "pk"));
		String name = ParamUtil.getString(request, "Name");
		String description = ParamUtil.getString(request, "Description");
		String value = ParamUtil.getString(request, "Value");

		String result = settingService.editAirtimePoolSetting(pkay, name, description, value);

		if (result != settingService.messageSuccessful) {
			showError(request, result);

			Settings setting = settingService.getAirtimePoolByPK(pkay);

			request.setAttribute("setting", setting);
			request.setAttribute("selectedTab", "pool");
			response.setRenderParameter("jspPage", "/html/systemsettings/EditAirtimePool.jsp");

		} else {
			SessionMessages.add(request, "showSuccess");
			showAirtimePoolList(request, response);
		}

	}

	@ProcessAction(name = "editGeneralSetting")
	public void editGeneralSetting(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		String name = ParamUtil.getString(request, "Name");
		Long pk = Long.parseLong(ParamUtil.getString(request, "pk"));
		String description = ParamUtil.getString(request, "Description");
		String value = ParamUtil.getString(request, "Value");

		String result = settingService.editGeneralSetting(pk, name, description, value);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
			Settings setting = settingService.getGeneralSettingsByPK(pk);

			request.setAttribute("setting", setting);
			request.setAttribute("selectedTab", "general");
			response.setRenderParameter("jspPage", "/html/systemsettings/EditGeneralSetting.jsp");

		} else {
			SessionMessages.add(request, "showSuccess");
			showGeneralSettingsList(request, response);
		}

	}

	@ProcessAction(name = "editRASSetting")
	public void editRASSetting(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		int minAgeOnNetwork = ParamUtil.getInteger(request, "AgeOnNetwork");
		int minBalance = ParamUtil.getInteger(request, "Balance");
		int minTopUp = ParamUtil.getInteger(request, "NoOfTopUps");
		int minTopUpDuration = ParamUtil.getInteger(request, "TopUpsDuration");
		int minTopUpValue = ParamUtil.getInteger(request, "TopUpsValue");
		int amount = ParamUtil.getInteger(request, "Amount");
		int serviceFee = ParamUtil.getInteger(request, "ServiceFee");
		Long pk = Long.parseLong(ParamUtil.getString(request, "pk"));

		String result = settingService.editRASSetting(pk, amount, serviceFee, minAgeOnNetwork, minBalance, minTopUp,
				minTopUpDuration, minTopUpValue);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
			BorrowableAmount ras = settingService.getRASByPK(pk);

			request.setAttribute("ras", ras);
			request.setAttribute("selectedTab", "ras");
			response.setRenderParameter("jspPage", "/html/systemsettings/EditRAS.jsp");
		} else {
			SessionMessages.add(request, "showSuccess");
			showRASList(request, response);
		}

	}

	@ProcessAction(name = "editSettlement")
	public void editSettlement(ActionRequest request, ActionResponse response) {

		Long pk = Long.parseLong(ParamUtil.getString(request, "pk"));
		String accountNo = ParamUtil.getString(request, "accountNumber");
		BankData bank = BankData.fromCode(request.getParameter("bank"));
		Double percentage = ParamUtil.getDouble(request, "settlementPercentage");
		SettlementType settlementType = SettlementType.valueOf(request.getParameter("settlementType"));
		Creditor creditor = Creditor.valueOf(request.getParameter("creditor"));

		String result = settingService.editSettlement(pk, accountNo, percentage, settlementType, bank, creditor);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
			Settlement settlement = settingService.getSettlementByPK(pk);

			SettlementType[] settlementTypes = SettlementType.values();
			BankData[] banks = BankData.values();
			Creditor[] creditors = Creditor.values();

			request.setAttribute("settlementTypes", settlementTypes);
			request.setAttribute("banks", banks);
			request.setAttribute("creditors", creditors);

			request.setAttribute("settlement", settlement);
			request.setAttribute("selectedTab", "settlement");
			response.setRenderParameter("jspPage", "/html/systemsettings/EditSettlement.jsp");
		} else {
			SessionMessages.add(request, "showSuccess");
			showSettlementList(request, response);
		}

	}

	@ProcessAction(name = "deactivateThreshold")
	public void deactivateThreshold(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		Long pk = Long.parseLong(ParamUtil.getString(request, "settingsID"));

		String result = settingService.deactivateThreshold(pk);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
		} else {
			SessionMessages.add(request, "showSuccess");
		}
		showThresholdList(request, response);
	}

	@ProcessAction(name = "deactivateAirtimePoolSetting")
	public void deactivateAirtimePoolSetting(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		Long pKey = Long.parseLong(ParamUtil.getString(request, "settingsID"));
		String result = settingService.deactivateAirtimePoolSetting(pKey);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
		} else {
			SessionMessages.add(request, "showSuccess");
		}
		this.showAirtimePoolList(request, response);
	}

	@ProcessAction(name = "deactivateGeneralSetting")
	public void deactivateGeneralSetting(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		Long pk = Long.parseLong(ParamUtil.getString(request, "settingsID"));
		String result = settingService.deactivateGeneralSetting(pk);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
		} else {
			SessionMessages.add(request, "showSuccess");
		}
		showGeneralSettingsList(request, response);
	}

	@ProcessAction(name = "deactivateRASSetting")
	public void deactivateRASSetting(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		Long pk = Long.parseLong(ParamUtil.getString(request, "settingsID"));
		String result = settingService.deactivateRASSetting(pk);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
		} else {
			SessionMessages.add(request, "showSuccess");
		}
		showRASList(request, response);

	}

	@ProcessAction(name = "deactivateSettlement")
	public void deactivateSettlement(ActionRequest request, ActionResponse response) {
		SessionErrors.clear(request);
		Long pk = Long.parseLong(ParamUtil.getString(request, "settingsID"));
		String result = settingService.deactivateSettlementSetting(pk);

		if (result != settingService.messageSuccessful) {
			showError(request, result);
		} else {
			SessionMessages.add(request, "showSuccess");
		}

		showSettlementList(request, response);
	}

	@ProcessAction(name = "showRASList")
	public void showRASList(ActionRequest request, ActionResponse response) {
		List<BorrowableAmount> rasSettings = settingService.getRASList();
		request.setAttribute("rasSettings", rasSettings);
		request.setAttribute("selectedTab", "ras");
		response.setRenderParameter("jspPage", "/html/systemsettings/ViewRAS.jsp");
	}

	@ProcessAction(name = "showGeneralSettingsList")
	public void showGeneralSettingsList(ActionRequest renderRequest, ActionResponse renderResponse) {
		// SessionErrors.clear(renderRequest);
		List<Settings> generalSettings = settingService.getGeneralSettingsList();

		renderRequest.setAttribute("generalSettings", generalSettings);
		renderRequest.setAttribute("selectedTab", "general");
		renderResponse.setRenderParameter("jspPage", "/html/systemsettings/view.jsp");
	}

	@ProcessAction(name = "showAirtimePoolList")
	public void showAirtimePoolList(ActionRequest request, ActionResponse response) {
		// SessionErrors.clear(request);
		List<Settings> airtimePoolSettings = settingService.getAirtimePoolList();

		request.setAttribute("airtimePoolSettings", airtimePoolSettings);
		request.setAttribute("selectedTab", "pool");
		response.setRenderParameter("jspPage", "/html/systemsettings/ViewAirtimePools.jsp");
	}

	@ProcessAction(name = "showThresholdList")
	public void showThresholdList(ActionRequest request, ActionResponse response) {
		// SessionErrors.clear(request);
		List<Thresholds> thresholdSettings = settingService.getThresholdList();
		request.setAttribute("thresholdSettings", thresholdSettings);
		request.setAttribute("selectedTab", "threshold");
		response.setRenderParameter("jspPage", "/html/systemsettings/ViewThresholds.jsp");
	}

	@ProcessAction(name = "showSettlementList")
	public void showSettlementList(ActionRequest request, ActionResponse response) {
		// SessionErrors.clear(request);
		List<Settlement> settlementSettings = settingService.getSettlementList();
		request.setAttribute("settlementSettings", settlementSettings);
		request.setAttribute("selectedTab", "settlement");
		response.setRenderParameter("jspPage", "/html/systemsettings/ViewSettlements.jsp");
	}

	@ProcessAction(name = "showRASAddPage")
	public void showRASAddPage(ActionRequest request, ActionResponse response) {
		request.setAttribute("selectedTab", "ras");
		response.setRenderParameter("jspPage", "/html/systemsettings/AddRAS.jsp");
	}

	@ProcessAction(name = "showGeneralSettingsAddPage")
	public void showGeneralSettingsAddPage(ActionRequest renderRequest, ActionResponse renderResponse) {
		renderRequest.setAttribute("selectedTab", "general");
		renderResponse.setRenderParameter("jspPage", "/html/systemsettings/AddGeneralSetting.jsp");
	}

	@ProcessAction(name = "showAirtimePoolAddPage")
	public void showAirtimePoolAddPage(ActionRequest request, ActionResponse response) {
		request.setAttribute("selectedTab", "pool");
		response.setRenderParameter("jspPage", "/html/systemsettings/AddAirtimePool.jsp");
	}

	@ProcessAction(name = "showThresholdAddPage")
	public void showThresholdAddPage(ActionRequest request, ActionResponse response) {
		request.setAttribute("selectedTab", "threshold");
		response.setRenderParameter("jspPage", "/html/systemsettings/AddThreshold.jsp");
	}

	@ProcessAction(name = "showSettlementAddPage")
	public void showSettlementAddPage(ActionRequest request, ActionResponse response) {
		SettlementType[] settlementTypes = SettlementType.values();
		BankData[] banks = BankData.values();
		Creditor[] creditors = Creditor.values();

		request.setAttribute("settlementTypes", settlementTypes);
		request.setAttribute("banks", banks);
		request.setAttribute("creditors", creditors);

		request.setAttribute("selectedTab", "settlement");
		response.setRenderParameter("jspPage", "/html/systemsettings/AddSettlement.jsp");
	}

	@ProcessAction(name = "editRASPage")
	public void editRASPage(ActionRequest request, ActionResponse response) {
		String pKey = request.getParameter("settingsID");
		BorrowableAmount ras = settingService.getRASByPK(Long.parseLong(pKey));

		request.setAttribute("ras", ras);
		request.setAttribute("selectedTab", "ras");
		response.setRenderParameter("jspPage", "/html/systemsettings/EditRAS.jsp");
	}

	@ProcessAction(name = "editGeneralSettingsPage")
	public void editGeneralSettingsPage(ActionRequest request, ActionResponse response) {
		String pKey = request.getParameter("settingsID");
		Settings setting = settingService.getGeneralSettingsByPK(Long.parseLong(pKey));

		request.setAttribute("setting", setting);
		request.setAttribute("selectedTab", "general");
		response.setRenderParameter("jspPage", "/html/systemsettings/EditGeneralSetting.jsp");
	}

	@ProcessAction(name = "editAirtimePoolPage")
	public void editAirtimePoolPage(ActionRequest request, ActionResponse response) {
		String pKey = request.getParameter("settingsID");
		Settings setting = settingService.getAirtimePoolByPK(Long.parseLong(pKey));

		request.setAttribute("setting", setting);
		request.setAttribute("selectedTab", "pool");
		response.setRenderParameter("jspPage", "/html/systemsettings/EditAirtimePool.jsp");
	}

	@ProcessAction(name = "editThresholdPage")
	public void editThresholdPage(ActionRequest request, ActionResponse response) {
		String pKey = request.getParameter("settingsID");
		Thresholds threshold = settingService.getThresholdByPk(Long.parseLong(pKey));
		request.setAttribute("threshold", threshold);
		request.setAttribute("selectedTab", "threshold");
		response.setRenderParameter("jspPage", "/html/systemsettings/EditThreshold.jsp");
	}

	@ProcessAction(name = "editSettlementPage")
	public void editSettlemenPage(ActionRequest request, ActionResponse response) {
		String pKey = request.getParameter("settingsID");
		Settlement settlement = settingService.getSettlementByPK(Long.parseLong(pKey));

		SettlementType[] settlementTypes = SettlementType.values();
		BankData[] banks = BankData.values();
		Creditor[] creditors = Creditor.values();

		request.setAttribute("settlementTypes", settlementTypes);
		request.setAttribute("banks", banks);
		request.setAttribute("creditors", creditors);

		request.setAttribute("settlement", settlement);
		request.setAttribute("selectedTab", "settlement");
		response.setRenderParameter("jspPage", "/html/systemsettings/EditSettlement.jsp");
	}

}
