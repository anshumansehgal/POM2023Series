package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;

	// How to supply prop to above guy? Creating const. of this class
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	// ChromeOptions
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();

		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setBrowserVersion(prop.getProperty("browserversion"));
			co.setCapability("browsername", "chrome");
			co.setCapability("enableVNC", true);
			co.setCapability("name", prop.getProperty("testcasename"));
		}
		
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

	// Edge Options
	public EdgeOptions getEdgeOptions() {
			eo = new EdgeOptions();

			if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {
				System.out.println("Running edge in headless");
				eo.addArguments("--headless");
			}
				
			if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
				System.out.println("Running edge in incognito");
				eo.addArguments("--incognito");
			}
			return eo;
		}
}
