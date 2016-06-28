package com.nano.portal.jbeans;

import java.util.Locale;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.PwdGenerator;

/**
 * POJO for {@link User} management.
 * 
 * @author segz
 *
 */

public class LiferayUserData {
	
	private long creatorId;
	private Long companyId;
	private Long faceBookId = 0L;

	private long[] roleIds;
	private long[] groupIds;
	private long[] userGroupIds;
	private long[] organizationIds = null;

	private String userEmail;
	private String screenName;
	private String openId = "";

	private String firstName;
	private String middleName;
	private String lastName;
	private String password = PwdGenerator.getPassword(10);
	private String passwordConfirmation = password;
	private String jobTitle;

	private int prefixId = 0;
	private int suffixId = 0;
	private int birthdayMonth = 5;
	private int birthdayDay = 28;
	private int birthdayYear = 1981;

	private boolean autoPassword = false;
	private boolean autoScreenName = false;
	private boolean male = false;

	private Locale userLocale = Locale.ENGLISH;
	
	/**
	 * 
	 * @param creatorid
	 * @param companyid
	 * @param roleids
	 * @param groupids
	 * @param userGroupids
	 * @param userEmail
	 * @param screenname
	 * @param firstname
	 * @param lastname
	 * @param middlename
	 * @param jobTitle
	 */
	public LiferayUserData(long creatorid, 
			long companyid, long[] roleids, long[] groupids, long[] userGroupids, 
			String userEmail, String screenname, String firstname, String lastname, String middlename, String jobTitle, String password, 
			boolean male){
		
		PwdGenerator.getPinNumber();
		
		this.creatorId = creatorid;
		this.companyId = companyid;
		this.roleIds = roleids;
		this.groupIds = groupids;
		this.userGroupIds = userGroupids;
		this.userEmail = userEmail;
		this.screenName = screenname;
		this.firstName = firstname;
		this.lastName = lastname;
		this.middleName = middlename;
		this.jobTitle = jobTitle;
		this.male = male;
		
		if (Validator.isNotNull(password)){
			this.password = password;
			this.passwordConfirmation = password;
		}
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getFaceBookId() {
		return faceBookId;
	}

	public void setFaceBookId(Long faceBookId) {
		this.faceBookId = faceBookId;
	}

	public long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(long[] roleIds) {
		this.roleIds = roleIds;
	}

	public long[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(long[] groupIds) {
		this.groupIds = groupIds;
	}

	public long[] getUserGroupIds() {
		return userGroupIds;
	}

	public void setUserGroupIds(long[] userGroupIds) {
		this.userGroupIds = userGroupIds;
	}

	public long[] getOrganizationIds() {
		return organizationIds;
	}

	public void setOrganizationIds(long[] organizationIds) {
		this.organizationIds = organizationIds;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getPrefixId() {
		return prefixId;
	}

	public void setPrefixId(int prefixId) {
		this.prefixId = prefixId;
	}

	public int getSuffixId() {
		return suffixId;
	}

	public void setSuffixId(int suffixId) {
		this.suffixId = suffixId;
	}

	public int getBirthdayMonth() {
		return birthdayMonth;
	}

	public void setBirthdayMonth(int birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}

	public int getBirthdayDay() {
		return birthdayDay;
	}

	public void setBirthdayDay(int birthdayDay) {
		this.birthdayDay = birthdayDay;
	}

	public int getBirthdayYear() {
		return birthdayYear;
	}

	public void setBirthdayYear(int birthdayYear) {
		this.birthdayYear = birthdayYear;
	}

	public boolean isAutoPassword() {
		return autoPassword;
	}

	public void setAutoPassword(boolean autoPassword) {
		this.autoPassword = autoPassword;
	}

	public boolean isAutoScreenName() {
		return autoScreenName;
	}

	public void setAutoScreenName(boolean autoScreenName) {
		this.autoScreenName = autoScreenName;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public Locale getUserLocale() {
		return userLocale;
	}

	public void setUserLocale(Locale userLocale) {
		this.userLocale = userLocale;
	}

}