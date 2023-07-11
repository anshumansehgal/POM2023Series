package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtils {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	// Create a const. of this class and provide the driver
	public ElementUtils(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	/**
	 * This method is used to get element.
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement getElement(By locator) {
		
		WebElement ele = driver.findElement(locator);
		
		//If highlight is true then highlight it otherwise don't
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(ele);	
		}
		
		return ele;
	}

	public WebElement getElement(By locator, int timeOut) {
		return waitForElementToBeVisible(locator, timeOut);
	}

	/**
	 * This method is used to send keys by passing locator and value.
	 * 
	 * @param locator
	 * @param value
	 */
	public void doSendKeys(By locator, String value) {
		
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	/**
	 * This method is used to click the element by passing locator.
	 * 
	 * @param locator
	 */
	public void doClick(By locator) {
		getElement(locator).click();
	}

	/**
	 * This method is used to send keys using Actions class. Helpful in case of
	 * ElementNotInteractableException and ElementNotInterceptedException
	 * 
	 * @param locator
	 * @param value
	 */

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);

		act.sendKeys(getElement(locator), value).build().perform();
	}

	/**
	 * This method is used to Click using Actions class. Helpful in case of
	 * ElementNotInteractableException and ElementNotInterceptedException
	 * 
	 * @param locator
	 */
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);

		act.click(getElement(locator)).build().perform();
	}

	/**
	 * This method is used to get text of the webElement.
	 * 
	 * @param locator
	 * @return
	 */
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	/**
	 * This method is used to check if an element is displayed or not.
	 * 
	 * @param locator
	 * @return
	 */
	public boolean CheckisDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	/**
	 * This method is used to get Element Attribute
	 * 
	 * @param locator
	 * @param attribute
	 * @return
	 */
	public String getElementAttribute(By locator, String attribute) {
		return getElement(locator).getAttribute(attribute);

	}

	/**
	 * This method is used to find list of webelements.
	 * 
	 * @param locator
	 * @return
	 */
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	/**
	 * This method is used for webscrapping and getting attribute values
	 * 
	 * @param locator
	 * @param value
	 */
	public void getElementAttributes(By locator, String value) {
		List<WebElement> eleList = getElements(locator);

		for (WebElement e : eleList) {
			String attrValues = e.getAttribute(value);
			System.out.println(attrValues);
		}
	}

	/**
	 * Finds total no. of elements on the page
	 * 
	 * @param locator
	 */
	public int getTotalElements(By locator) {
		int eleCount = getElements(locator).size();
		System.out.println("Total elements for : " + locator + "-->" + eleCount);
		return eleCount;
	}

	/**
	 * This method is use to fetch something from specific location
	 * 
	 * @param locator
	 */
	public List<String> getElementsTextList(By locator) {

		List<String> eleTextList = new ArrayList<String>();// Empty list size = 0

		List<WebElement> eleList = getElements(locator);

		for (WebElement e : eleList) {
			String text = e.getText();

			// create an empty array list and store it inside
			eleTextList.add(text);
		}
		return eleTextList;
	}

	// *********Select based Drop-down Utils******

	public void doSelectDropDownByIndex(By locator, int index) {

		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByValue(By locator, String value) {

		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectDropDownByVisibleText(By locator, String value) {

		Select select = new Select(getElement(locator));
		select.selectByVisibleText(value);
		;
	}

	public List<WebElement> getDropDownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions();

	}

	public List<String> getDropDownTextList(By locator) {

		List<WebElement> optionsList = getDropDownOptionsList(locator);

		List<String> optionsTextList = new ArrayList<String>();

		for (WebElement e : optionsList) {
			String text = e.getText();

			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	public void selectDropDownValue(By locator, String expValue) {
		List<WebElement> optionsList = getDropDownOptionsList(locator);

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(expValue)) {
				e.click();
				break;
			}
		}

	}

	public int getTotalDropDownOptions(By locator) {
		int optionsCount = getDropDownOptionsList(locator).size();
		System.out.println("Total options " + optionsCount);
		return optionsCount;
	}

	/**
	 * Seach from suggestion list and click resp. value E.g. Google search
	 * 
	 * @param suggList
	 * @param suggName
	 */
	public void doSearch(By suggList, String suggName) {

		List<WebElement> suggLists = getElements(suggList);
		System.out.println(suggLists.size()); // 11

		// capture all the texts
		for (WebElement e : suggLists) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(suggName)) {
				e.click();
				break;
			}
		}
	}

	// **********************Wait Utils******************
	/**
	 * This method checks that an element is present on the DOM of a page. This does
	 * not necessarily mean that the element is visible on the page. Will not give
	 * 100% acurate result or is not that powerful.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	// Elements
	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * Check both Presence and visibility on the page. An expectation for checking
	 * that an element is present on the DOM of a page and visible. Visibility means
	 * that the element is not only displayed but also has a height and width that
	 * is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Elements
	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	// Alert
	public Alert waitForAlertPresence(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {
		return waitForAlertPresence(timeOut).getText();
	}

	public void acceptAlert(int timeOut) {
		waitForAlertPresence(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForAlertPresence(timeOut).dismiss();
	}

	public void alertSendKeys(int timeOut, String value) {
		waitForAlertPresence(timeOut).sendKeys(value);
	}

	// Title contains--for huge title
	public String waitForTitleContainsAndFetch(int timeOut, String titleFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.titleContains(titleFractionValue));
		return driver.getTitle();
	}

	// exact title
	public String waitForTitleIsAndFetch(int timeOut, String titleFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.titleIs(titleFractionValue));
		return driver.getTitle();
	}

	// URL contains and fetch
	public String waitForURLContainsAndFetch(int timeOut, String URLFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.urlContains(URLFractionValue));
		return driver.getCurrentUrl();
	}

	// URL contains
	public boolean waitForURLContains(int timeOut, String URLFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.urlContains(URLFractionValue));
	}

	// URL To be
	public String waitForURLIsAndFetch(int timeOut, String URLValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.urlToBe(URLValue));
		return driver.getCurrentUrl();
	}

	// *******Frames Utilities********
	public void waitForFramesAndSwitchToItByIDOrName(int timeOut, String idOrName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	public void waitForFramesAndSwitchToItByIndex(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFramesAndSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	// better than 3rd
	public void waitForFramesAndSwitchToItByFrameLocator(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	// Element to be clickable and click on it
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param timeOut
	 * @param locator
	 */
	public void clickWhenReady(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	public WebElement waitForElementToBeClickable(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	//With Actions class
	public void doClickWithActionsAndWait(int timeOut, By locator) {
		WebElement ele = waitForElementToBeClickable(timeOut, locator);
		Actions act = new Actions(driver);
		act.click(ele).build().perform();
	}
}
