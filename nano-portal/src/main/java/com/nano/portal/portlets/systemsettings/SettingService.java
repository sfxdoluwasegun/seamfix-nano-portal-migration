package com.nano.portal.portlets.systemsettings;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import com.liferay.portal.kernel.util.Validator;
import com.nano.jpa.entity.Settings;
import com.nano.jpa.entity.Settlement;
import com.nano.jpa.entity.Thresholds;
import com.nano.jpa.entity.ras.BorrowableAmount;
import com.nano.jpa.entity.ras.RasCriteria;
import com.nano.jpa.enums.BankData;
import com.nano.jpa.enums.Creditor;
import com.nano.jpa.enums.SettingType;
import com.nano.jpa.enums.SettlementType;
import com.nano.portal.tools.QueryManager;

/**
 * Manages action & resource processing for {@link SystemSettings} portlet
 * class.
 * 
 * @author segz
 *
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SettingService {

	@Inject
	private QueryManager queryManager;
	String errorNameRepetition = "nameRepetition";
	String generalError = "showError";
	String messageSuccessful = "showSuccess";
	String nameShouldNotBeNull = "nameShouldNotBeNull";
	String descriptionShouldNotBeNull = "descriptionShouldNotBeNull";
	String valueShouldNotBeNull = "valueShouldNotBeNull";
	String accountShouldNotBeNull = "accountShouldNotBeNull";
	String pkShouldNotBeNull = "idShouldNotBeNull";
	String nameNotAvailable = "nameRepetition";
	String amountIsInvalid = "amountIsinvalid";
	String amountNotAvailable = "amountRepetition";
	String accountNotAvailable = "accountNotAvailable";

	String percentageShouldNotBeNull = "percentageShouldNotBeNull";
	String bankShouldNotBeNull = "bankShouldNotBeNull";
	String creditorShouldNotBeNull = "creditorShouldNotBeNull";
	String settlementTypeShouldNotBeNull = "settlementTypeShouldNotBeNull";

	String invalidMinAgeOnNetwork = "invalidMinAgeOnNetwork";
	String invalidMinBalance = "invalidMinBalance";
	String invalidMinTopUp = "invalidMinTopUp";
	String invalidMinTopUpDuration = "invalidMinTopUpDuration";
	String invalidMinTopUpValue = "invalidMinTopUpValue";
	String invalidAmount = "invalidAmount";
	String invalidServiceFee = "invalidServiceFee";

	private String validateThresholdData(String name, BigDecimal amount) {

		if (Validator.isNull(amount)) {
			return amountIsInvalid;
		}
		if (Validator.isNull(name)) {
			return nameShouldNotBeNull;
		}

		return messageSuccessful;
	}

	private String validateSettlementEntries(String accountNo, Double percentage, SettlementType settlementType,
			BankData bank, Creditor creditor) {
		if (Validator.isNull(accountNo)) {
			return accountShouldNotBeNull;
		}
		if (Validator.isNull(percentage)) {
			return percentageShouldNotBeNull;
		}
		if (Validator.isNull(settlementType)) {
			return settlementTypeShouldNotBeNull;
		}
		if (Validator.isNull(bank)) {
			return bankShouldNotBeNull;
		}
		if (Validator.isNull(creditor)) {
			return creditorShouldNotBeNull;
		}

		return messageSuccessful;

	}

	/**
	 * Used to add a Threshold Setting to the DB
	 * 
	 * @param threshold
	 * @author Jaohar
	 */
	public String addThreshold(String name, BigDecimal amount) {

		String result = validateThresholdData(name, amount);

		if (result != messageSuccessful) {
			return result;
		}

		boolean nameAvailability = checkThresholdNameIsAvailableForAdd(name);
		boolean amountAvailability = checkThresholdAmountIsAvailableForAdd(amount);

		if (nameAvailability == false) {
			return nameNotAvailable;
		} else if (amountAvailability == false) {
			return amountNotAvailable;
		} else {
			Timestamp time = new Timestamp(new Date().getTime());
			Thresholds thresh = new Thresholds();
			thresh.setName(name);
			thresh.setAmount(amount);
			thresh.setPeriod(time);
			queryManager.create(thresh);

			return messageSuccessful;
		}

	}

	private String validateNameDescriptionAndValue(String name, String description, String value) {
		if (Validator.isNull(name)) {
			return nameShouldNotBeNull;
		}
		if (Validator.isNull(description)) {
			return descriptionShouldNotBeNull;
		}
		if (Validator.isNull(value)) {
			return valueShouldNotBeNull;
		}
		return messageSuccessful;
	}

	private String validatePkNameDescriptionAndValue(Long pk, String name, String description, String value) {

		if (Validator.isNull(name)) {
			return nameShouldNotBeNull;
		}
		if (Validator.isNull(description)) {
			return descriptionShouldNotBeNull;
		}
		if (Validator.isNull(value)) {
			return valueShouldNotBeNull;
		}
		if (Validator.isNull(pk)) {
			return pkShouldNotBeNull;
		}
		return messageSuccessful;
	}

	/**
	 * Used to add a general Setting to the DB
	 * 
	 * @param generalSetting
	 * @author Jaohar
	 */
	public String addGeneralSetting(String name, String description, String value) {
		String result = validateNameDescriptionAndValue(name, description, value);

		if (result != messageSuccessful) {
			return result;
		}
		boolean isNameAvailable = checkSettingNameIsAvailableForAdd(name);
		if (!isNameAvailable) {

			return errorNameRepetition;
		}
		Settings setin = new Settings();
		setin.setName(name);
		setin.setDescription(description);
		setin.setValue(value);
		setin.setType(SettingType.GENERAL);
		setin.setDeleted(false);

		queryManager.create(setin);
		return messageSuccessful;

	}

	/**
	 * Used to get a Setting from the DB where name is same as the one passed
	 * 
	 * @param name
	 * @author Jaohar
	 */
	public Settings getSettingByName(String name) {
		return queryManager.getSettingsByName(name);
	}

	/**
	 * Used to get a Threshold from the DB where name is same as the one passed
	 * 
	 * @param name
	 * @author Jaohar
	 */
	public Thresholds getThresholdByName(String name) {
		return queryManager.getThresholdByName(name);
	}

	/**
	 * Used to get a Threshold from the DB where amount is same as the one
	 * passed
	 * 
	 * @param amount
	 * @author Jaohar
	 */
	public Thresholds getThresholdByAmount(BigDecimal amount) {
		return queryManager.getThresholdByAmount(amount);
	}

	public String checkRASAmount(int amount) {
		if (Validator.isNull(amount) || Validator.equals(amount, 0)) {
			return amountIsInvalid;
		}

		if (Validator.isNull(getRASByAmount(amount))) {
			return messageSuccessful;
		}

		return amountNotAvailable;

	}

	private String validateRASEntries(int minAgeOnNetwork, int minBalance, int minTopUp, int minTopUpDuration,
			int minTopUpValue, int amount, int serviceFee) {

		if (minAgeOnNetwork < 0) {
			return invalidMinAgeOnNetwork;
		}
		if (minBalance < 0) {
			return invalidMinBalance;
		}
		if (minTopUp < 0) {
			return invalidMinTopUp;
		}
		if (minTopUpDuration < 0) {
			return invalidMinTopUpDuration;
		}
		if (minTopUpValue < 0) {
			return invalidMinTopUpValue;
		}
		if (amount < 0) {
			return invalidAmount;
		}
		if (serviceFee < 0) {
			return invalidServiceFee;
		}
		return messageSuccessful;

	}

	/**
	 * Used to add a RAS Borrowable Amount Setting to the DB
	 * 
	 * @param RASSetting
	 * @author Jaohar
	 */
	public String addRASSetting(int minAgeOnNetwork, int minBalance, int minTopUp, int minTopUpDuration,
			int minTopUpValue, int amount, int serviceFee) {

		String result = validateRASEntries(minAgeOnNetwork, minBalance, minTopUp, minTopUpDuration, minTopUpValue,
				amount, serviceFee);

		if (result != messageSuccessful) {
			return result;
		}
		BorrowableAmount borrowableAmount = new BorrowableAmount();
		RasCriteria crite = new RasCriteria();

		crite.setMinAgeOnNetwork(minAgeOnNetwork);
		crite.setMinBalance(minBalance);
		crite.setMinTopUps(minTopUp);
		crite.setMinTopUpsDuration(minTopUpDuration);
		crite.setMinTopUpValue(minTopUpValue);
		borrowableAmount.setAmount(amount);
		borrowableAmount.setServiceFee(serviceFee);
		borrowableAmount.setDeleted(false);
		borrowableAmount.setCriteria(crite);

		queryManager.create(borrowableAmount);
		return messageSuccessful;
	}

	/**
	 * Used to add a Settlement Setting to the DB
	 * 
	 * @param SettlementSetting
	 * @author Jaohar
	 */
	public String addSettlementSetting(String accountNo, Double percentage, SettlementType settlementType,
			BankData bank, Creditor creditor) {

		String result = validateSettlementEntries(accountNo, percentage, settlementType, bank, creditor);

		if (result != messageSuccessful) {
			return result;
		}

		boolean accountAvailable = checkSettlementAccountIsAvailableForAdd(accountNo, settlementType);

		if (!accountAvailable) {
			return accountNotAvailable;
		}

		Settlement settlement = new Settlement();
		settlement.setAccountNumber(accountNo);
		settlement.setPercentage(percentage);
		settlement.setSettlementType(settlementType);
		settlement.setBankData(bank);
		settlement.setCreditor(creditor);

		queryManager.create(settlement);
		return messageSuccessful;
	}

	/**
	 * Used to add a AirtimePool Setting to the DB
	 * 
	 * @param airtimePoolSetting
	 * @author Jaohar
	 */
	public String addAirtimePoolSetting(String name, String description, String value) {

		String result = validateNameDescriptionAndValue(name, description, value);

		if (result != messageSuccessful) {
			return result;
		}

		boolean nameAvailable = checkSettingNameIsAvailableForAdd(name);
		if (!nameAvailable) {
			return errorNameRepetition;
		}

		Settings setin = new Settings();
		setin.setName(name);
		setin.setDescription(description);
		setin.setValue(value);
		setin.setType(SettingType.POOL);

		queryManager.create(setin);
		return messageSuccessful;
	}

	/**
	 * GetRAS List Borrowable Amounts as Stub Used to retrieve a list of all RAS
	 *
	 * @author Jaohar
	 */
	public List<BorrowableAmount> getRASList() {
		return queryManager.getListByClass(BorrowableAmount.class);
		// return null;
	}

	/**
	 * Used to retrieve a list of all Settings
	 *
	 * @author Jaohar
	 */
	public List<Settings> getGeneralSettingsList() {
		return queryManager.getSettingsBySettingsType(SettingType.GENERAL);
		// return queryManager.getListByClass(Settings.class);
		// return null;
	}

	/**
	 * Used to retrieve a list of all Settlement Configurations
	 *
	 * @author Jaohar
	 */
	public List<Settlement> getSettlementList() {
		return queryManager.getListByClass(Settlement.class);
		// return null;
	}

	/**
	 * Used to retrieve a list of all Settings with type pool (AirtimePool)
	 *
	 * @author Jaohar
	 */
	public List<Settings> getAirtimePoolList() {
		return queryManager.getSettingsBySettingsType(SettingType.POOL);
		// return queryManager.getListByClass(Settings.class);
		// return null;
	}

	/**
	 * Used to retrieve a list of all Thresholds
	 *
	 * @author Jaohar
	 */
	public List<Thresholds> getThresholdList() {
		return queryManager.getListByClass(Thresholds.class);
		// return null;
	}

	private boolean checkSettingNameIsAvailableForEdit(Long pk, String name) {
		Settings setting = getSettingByName(name);
		if (Validator.isNotNull(setting) && Validator.equals(setting.getPk(), pk)) {
			// if the setting that returned is by the same object
			return true;
		} else if (Validator.isNull(setting)) {// name is available
			return true;
		}
		return false;
	}

	private boolean checkSettingNameIsAvailableForAdd(String name) {

		if (Validator.isNull(getSettingByName(name))) {
			return true;
		}
		return false;
	}

	private boolean checkSettlementAccountIsAvailableForEdit(Long pk, String account, SettlementType settlementType) {
		List<Settlement> settlement = getSettlementByAccountNumberAndSettlementType(account, settlementType);
		if (Validator.isNotNull(settlement)) {
			// if the settlement that returned is by the same object
			if (settlement.size() > 0) {
				if (Validator.equals(settlement.get(0).getPk(), pk)) {
					return true;
				}
				return false;
			}
			return false;
		} else if (Validator.isNull(settlement)) {// name is available
			return true;
		}
		return false;
	}

	@SuppressWarnings("unused")
	private boolean checkSettlementAccountIsAvailableForAdd(String account) {

		if (Validator.isNull(getSettlementByAccountNumber(account))) {
			return true;
		}

		return false;
	}

	private boolean checkSettlementAccountIsAvailableForAdd(String account, SettlementType settlementType) {

		if (Validator.isNull(getSettlementByAccountNumberAndSettlementType(account, settlementType))) {
			return true;
		}

		return false;
	}

	/**
	 * GetRAS Borrowable Amounts Used to retrieve a RAS with PK passed
	 *
	 * @author Jaohar
	 */
	public BorrowableAmount getRASByPK(Long pk) {
		if (pk != null && pk != 0) {
			return (BorrowableAmount) queryManager.getByPk(BorrowableAmount.class, pk);
		}
		return null;
	}

	/**
	 * Get matching Borrowable Amounts
	 *
	 * @author Jaohar
	 */
	public BorrowableAmount getRASByAmount(int amount) {
		return (BorrowableAmount) queryManager.getBorrowableAmountByAmount(amount);
		// return null;
	}

	/**
	 * Used to retrieve a list of all Settings
	 * 
	 * @param pk
	 * @author Jaohar
	 */
	public Settings getGeneralSettingsByPK(Long pk) {
		if (pk != null && pk != 0) {
			return (Settings) queryManager.getByPk(Settings.class, pk);
		}
		return null;

	}

	/**
	 * Used to retrieve a list of all Settlement Configurations
	 * 
	 * @param pk
	 * @author Jaohar
	 */
	public Settlement getSettlementByPK(Long pk) {
		return (Settlement) queryManager.getByPk(Settlement.class, pk);
		// return null;
	}

	/**
	 * Used to retrieve a list of all Settlement Configurations
	 * 
	 * @param accountNumber
	 * @author Jaohar
	 */
	public Settlement getSettlementByAccountNumber(String accountNumber) {
		return (Settlement) queryManager.getSettlementByAccountNumber(accountNumber);
		// return null;
	}

	public List<Settlement> getSettlementByAccountNumberAndSettlementType(String accountNumber,
			SettlementType settlementType) {
		return queryManager.getSettlementByAccountNumberAndSettlementType(accountNumber, settlementType);
		// return null;
	}

	/**
	 * Used to retrieve a list of all Settings with type pool (AirtimePool)
	 * 
	 * @param pk
	 * @author Jaohar
	 */
	public Settings getAirtimePoolByPK(Long pk) {
		if (pk != null && pk != 0) {
			return (Settings) queryManager.getByPk(Settings.class, pk);
		}
		return null;
	}

	/**
	 * Used to retrieve a list of all Thresholds
	 * 
	 * @param pk
	 * @author Jaohar
	 */
	public Thresholds getThresholdByPk(Long pk) {
		if (pk != null && pk != 0) {
			return (Thresholds) queryManager.getByPk(Thresholds.class, pk);
		}
		return null;
	}

	/**
	 * Used to Edit an Airtime Pool Setting to the DB
	 * 
	 * @param airtimePoolSetting
	 * @author Jaohar
	 */
	public String editAirtimePoolSetting(Long pkay, String name, String description, String value) {

		String result = validatePkNameDescriptionAndValue(pkay, name, description, value);

		if (result != messageSuccessful) {
			return result;
		}
		boolean nameAvailable = checkSettingNameIsAvailableForEdit(pkay, name);
		if (!nameAvailable) {
			return nameNotAvailable;
		}
		Settings setin = getAirtimePoolByPK(pkay);
		setin.setName(name);
		setin.setDescription(description);
		setin.setValue(value);
		setin.setType(SettingType.POOL);

		queryManager.update(setin);
		return messageSuccessful;
	}

	/**
	 * Used to Deactivate an Airtime Pool Setting
	 * 
	 * @param pk
	 * @author Jaohar
	 */
	public String deactivateAirtimePoolSetting(Long pk) {

		if (Validator.isNull(pk)) {
			return generalError;
		}

		Settings setin = getAirtimePoolByPK(pk);
		setin.setDeleted(true);

		queryManager.update(setin);
		return messageSuccessful;
	}

	/**
	 * Used to Deactivate a Threshold
	 * 
	 * @param pk
	 * @author Jaohar
	 */
	public String deactivateThreshold(Long pk) {

		if (Validator.isNull(pk)) {
			return generalError;
		}

		Thresholds threshold = getThresholdByPk(pk);
		threshold.setDeleted(true);

		queryManager.update(threshold);
		return messageSuccessful;
	}

	/**
	 * Used to Deactivate a General Setting
	 * 
	 * @param pk
	 * @author Jaohar
	 */
	public String deactivateGeneralSetting(Long pk) {

		if (Validator.isNull(pk)) {
			return generalError;
		}

		Settings setin = getGeneralSettingsByPK(pk);
		setin.setDeleted(true);

		queryManager.update(setin);
		return messageSuccessful;
	}

	/**
	 * Used to Deactivate a RAS Criterion
	 * 
	 * @param pk
	 * @author Jaohar
	 */
	public String deactivateRASSetting(Long pk) {

		if (Validator.isNull(pk)) {
			return generalError;
		}

		BorrowableAmount ras = getRASByPK(pk);
		ras.setDeleted(true);

		queryManager.update(ras);
		return messageSuccessful;
	}

	/**
	 * Used to Deactivate a RAS Criterion
	 * 
	 * @param pk
	 * @author Jaohar
	 */
	public String deactivateSettlementSetting(Long pk) {

		if (Validator.isNull(pk)) {
			return generalError;
		}

		Settlement settlement = getSettlementByPK(pk);
		settlement.setDeleted(true);

		queryManager.update(settlement);
		return messageSuccessful;
	}

	/**
	 * Used to Edit a general Setting
	 * 
	 * @param pk
	 * @param name
	 * @param description
	 * @param value
	 * @author Jaohar
	 */
	public String editGeneralSetting(Long pk, String name, String description, String value) {

		String result = validatePkNameDescriptionAndValue(pk, name, description, value);

		if (result != messageSuccessful) {
			return result;
		}
		boolean nameAvailable = checkSettingNameIsAvailableForEdit(pk, name);
		if (!nameAvailable) {
			return nameNotAvailable;
		}
		Settings setin = getGeneralSettingsByPK(pk);
		setin.setName(name);
		setin.setDescription(description);
		setin.setValue(value);
		setin.setType(SettingType.GENERAL);

		queryManager.update(setin);
		return messageSuccessful;
	}

	/**
	 * Used to Edit a RAs Setting to the DB
	 * 
	 * @param ras
	 * @author Jaohar
	 */
	public String editRASSetting(Long pk, int amount, int serviceFee, int minAgeOnNetwork, int minBalance, int minTopUp,
			int minTopUpDuration, int minTopUpValue) {

		if (Validator.isNull(pk)) {
			return pkShouldNotBeNull;
		}
		String result = validateRASEntries(minAgeOnNetwork, minBalance, minTopUp, minTopUpDuration, minTopUpValue,
				amount, serviceFee);

		if (result != messageSuccessful) {
			return result;
		}
		BorrowableAmount rasSetting = getRASByPK(pk);
		RasCriteria criteria = new RasCriteria();

		rasSetting.setAmount(amount);
		rasSetting.setServiceFee(serviceFee);
		criteria.setMinAgeOnNetwork(minAgeOnNetwork);
		criteria.setMinBalance(minBalance);
		criteria.setMinTopUps(minTopUp);
		criteria.setMinTopUpsDuration(minTopUpDuration);
		criteria.setMinTopUpValue(minTopUpValue);
		rasSetting.setCriteria(criteria);

		queryManager.update(rasSetting);

		return messageSuccessful;
	}

	private String validateSettlementEntries(Long pk, String accountNo, Double percentage,
			SettlementType settlementType, BankData bank, Creditor creditor) {
		if (Validator.isNull(pk)) {
			return pkShouldNotBeNull;
		}
		if (Validator.isNull(accountNo)) {
			return accountShouldNotBeNull;
		}
		if (Validator.isNull(percentage)) {
			return percentageShouldNotBeNull;
		}
		if (Validator.isNull(settlementType)) {
			return settlementTypeShouldNotBeNull;
		}
		if (Validator.isNull(bank)) {
			return bankShouldNotBeNull;
		}
		if (Validator.isNull(creditor)) {
			return creditorShouldNotBeNull;
		}

		return messageSuccessful;
	}

	/**
	 * Used to Edit a Settlement Setting to the DB
	 * 
	 * @param settlement
	 * @author Jaohar
	 */
	public String editSettlement(Long pk, String accountNo, Double percentage, SettlementType settlementType,
			BankData bank, Creditor creditor) {

		String result = validateSettlementEntries(pk, accountNo, percentage, settlementType, bank, creditor);

		if (result != messageSuccessful) {
			return result;
		}
		boolean accountAvailable = checkSettlementAccountIsAvailableForEdit(pk, accountNo, settlementType);
		if (!accountAvailable) {
			return accountNotAvailable;
		}
		Settlement settlement = getSettlementByPK(pk);
		settlement.setAccountNumber(accountNo);
		settlement.setPercentage(percentage);
		settlement.setSettlementType(settlementType);
		settlement.setBankData(bank);
		settlement.setCreditor(creditor);

		queryManager.update(settlement);
		return messageSuccessful;
	}

	/**
	 * Used to Edit a Threshold Setting to the DB
	 * 
	 * @param threshold
	 * @author Jaohar
	 */
	public String editThreshold(Long pk, String name, BigDecimal amount) {

		if (Validator.isNull(pk)) {
			return pkShouldNotBeNull;
		}

		String result = validateThresholdData(name, amount);
		if (result != messageSuccessful) {
			return result;
		}
		boolean nameAvailability = checkThresholdNameIsAvailableForEdit(pk, name);
		boolean amountAvailability = checkThresholdAmountIsAvailableForEdit(pk, amount);

		if (nameAvailability == false) {
			return nameNotAvailable;
		} else if (amountAvailability == false) {
			return amountNotAvailable;
		} else {
			Timestamp time = new Timestamp(new Date().getTime());
			Thresholds threshold = getThresholdByPk(pk);
			threshold.setName(name);
			threshold.setAmount(amount);
			threshold.setPeriod(time);

			queryManager.update(threshold);
			return messageSuccessful;
		}
	}

	private boolean checkThresholdNameIsAvailableForEdit(Long pk, String name) {
		Thresholds dbThreshold = getThresholdByName(name);
		if (Validator.isNotNull(dbThreshold) && Validator.equals(dbThreshold.getPk(), pk)) {
			return true;
		} else if (Validator.isNull(dbThreshold)) {
			return true;
		}
		return false;
	}

	private boolean checkThresholdAmountIsAvailableForEdit(Long pk, BigDecimal amount) {
		Thresholds dbThreshold = getThresholdByAmount(amount);
		if (Validator.isNotNull(dbThreshold) && Validator.equals(dbThreshold.getPk(), pk)) {
			return true;
		} else if (Validator.isNull(dbThreshold)) {
			return true;
		}

		return false;
	}

	private boolean checkThresholdNameIsAvailableForAdd(String name) {
		if (Validator.isNull(getThresholdByName(name))) {
			return true;
		}
		return false;
	}

	private boolean checkThresholdAmountIsAvailableForAdd(BigDecimal amount) {
		if (Validator.isNull(getThresholdByAmount(amount))) {
			return true;
		}

		return false;
	}

}
