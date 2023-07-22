package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class AccountsPage {

	// Do same things as did on LoginPage
	private WebDriver driver;
	private ElementUtils eleUtil;

	// create By locators
	private By logoutLink = By.linkText("Logout");
	private By accsHeaders = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("#search button");

	// create constructor as did on Login page
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}

	//page actions/page methods 
	//Private locators used in public methods---Encapsulation concept
	public String getAccPageTitle() {
	//	String title = driver.getTitle();
		
		String title = eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
		System.out.println("Account page title is: " + title);
		return title;
	}
	
	public String getAccPageUrl() {
//		String url = driver.getCurrentUrl();
		
		String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNTS_PAGE_URL_FRACTION_VALUE);
		System.out.println("Account page url is: " + url);
		return url;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementToBeVisible(logoutLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public boolean isSearchExist() {
		return eleUtil.waitForElementToBeVisible(search, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountsPageHeadersList() {
		
		List<WebElement> accHeadersList	= eleUtil.waitForElementsToBeVisible(accsHeaders, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
			
		//create empty arraylist
		List<String> accHeadersValList = new ArrayList<String>();
			
		//return text of all headers
		for (WebElement e : accHeadersList) {
			String text = e.getText();
			
			//fill arraylist
			accHeadersValList.add(text);
		}
		return accHeadersValList;
	}
	
	public SearchPage performSearch(String searchKey) {
		
		if(isSearchExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchIcon); //After clicking it's landing on new page
			
			return new SearchPage(driver); //always create next landing page class obj. whenever we click
		}
		else {
			System.out.println("Search field is not displayed on the page");
			return null;
		}
		
	}
	
	
}
