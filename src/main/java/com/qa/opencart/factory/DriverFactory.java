package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This method is initializing the driver on the basis of given browser name.
	 * @param browserName
	 * @return this returns the driver
	 */
	public WebDriver initDriver(Properties prop) {
		
		optionsManager = new OptionsManager(prop);
		
		highlight = prop.getProperty("highlight").trim();
		String browserName = prop.getProperty("browser").trim().toLowerCase();//from config.prop
		
		System.out.println("Browser name is " + browserName);
		
		//Chrome
		if(browserName.equalsIgnoreCase("chrome")) {
			
			//Hub properties from config
			if(Boolean.parseBoolean(prop.getProperty("remote"))){
				//Run on remote/selenium grid
				init_remoteDriver("chrome");
			}
			else {
				//local execution
				//driver = new ChromeDriver(optionsManager.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
		}
		//Firefox
		else if(browserName.equalsIgnoreCase("firefox")) {
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))){
				//Run on remote/selenium grid
				init_remoteDriver("firefox");
			}
			else {
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
		}
		//Edge
		else if(browserName.equalsIgnoreCase("edge")) {
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))){
				init_remoteDriver("edge");
			}
			else {
				//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
		}
		//Safari doesn't support remote execution so only local
		else if(browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}
		
		else {
			System.out.println("Please pass right browser name..." + browserName);
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());
		return getDriver();
	}

	/**
	 * This method is called internally to initialize the driver with RemoteWebDriver
	 * @param browser
	 */
	private void init_remoteDriver(String browser) {

		System.out.println("Running tests on Grid Server:: " + browser);
		
		try {
			switch (browser.toLowerCase()) {
			case "chrome":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
				break;
			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
				break;
			default:
				System.out.println("Please pass the right browser for remote execution.." + browser);
				throw new FrameworkException("NOREMOTEBROWSEREXCEPTION");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	// synchronized--every driver will get its respective copy. Get the local thread
	// copy of the driver.
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is reading the properties from .properties file.
	 * 
	 * @return
	 */
	public Properties initProperty() {

		/*
		 * command line argument mvn clean install -Denv="dev" mvn clean install -->what
		 * is the value of env in this case? null --so we should be putting a null check
		 */

		// create obj of properties
		prop = new Properties();
		FileInputStream ip = null;
		String envName = System.getProperty("env");
		System.out.println("Running test cases on: " + envName);

		try {
			if (envName == null) {
				System.out.println("No environment is passed...Running test on qa env");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {

				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stag.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;
				default:
					System.out.println("Wrong env is passed...No need to run the test cases.");
					throw new FrameworkException("INCORRECT ENV IS PASSED....");
				// break;
				}
			}
		} catch (FileNotFoundException e) {

		}
		// Load the prop.
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * This method is used to take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return path;

	}

}
