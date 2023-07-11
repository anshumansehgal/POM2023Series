package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;

	// How to supply prop to above guy? Creating const. of this class
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	// ChromeOptions
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("Running chrome in headless");
			co.addArguments("--headless");

		}
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			System.out.println("Running chrome in incognito");
			co.addArguments("--incognito");
		}

		return co;
	}

	// FirefoxOptions
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("Running firefox in headless");
			fo.addArguments("--headless");
		}
			
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			System.out.println("Running firefox in incognito");
			fo.addArguments("--incognito");
		}
		return fo;
	}

}
