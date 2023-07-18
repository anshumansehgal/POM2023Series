package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class OrderPage {
	
	By location = By.id("location");
	By proce = By.id("price");
	
	public void getOrder() {
		System.out.println("Order page--get order");
	}

	public void getPrice() {
		System.out.println("Order page--get price");
	}
}
