package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC - 100: Design login for OpenCart App")
@Story("US-Login: 101: Design login page features for OpenCart")
public class LoginPageTest extends BaseTest {
	
	@Severity(SeverityLevel.TRIVIAL)
	@Description("This method is checking title on the login page.")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		//inherit login page reference from BaseTest| Benefit-No need to create unnecessary obj. creation here
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Description("This method is checking the URL on the login page.")
	@Test(priority = 2)
	public void loginPageURLTest() {
		
		String actUrl = loginPage.getLoginPageURL();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("This method is checking forgot pwd link exist on the login page.")
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("This method is checking user is able to login to app with correct username and password.")
	@Test(priority = 4)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	

}
