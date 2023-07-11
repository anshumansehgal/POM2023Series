package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {

	//Timeouts
	public static final int DEFAULT_MEDIUM_TIME_OUT = 10;
	public static final int DEFAULT_SHORT_TIME_OUT = 5;
	public static final int DEFAULT_LONG_TIME_OUT = 20;
	
	//Login Page - Title and URL Fraction value
	public static final String LOGIN_PAGE_TITLE_VALUE = "Account Login";
	public static final String LOGIN_PAGE_URL_FRACTION_VALUE = "route=account/login";
	
	//Accounts page - title and url fraction value
	public static final String ACCOUNTS_PAGE_TITLE_VALUE = "My Account";
	public static final String ACCOUNTS_PAGE_URL_FRACTION_VALUE = "route=account/account";
	public static final int ACCOUNTS_PAGE_HEADERS_COUNT = 4;
	public static final List<String> EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST = Arrays.asList("My Account", "My Orders", "My Affiliate Account", "Newsletter");
	
	
	//Register page
	public static final String USER_REG_SUCCESS_MSG = "Your Account Has Been Created";
	
	
	//**************Excel Util - sheetNames**************** 
	public static final String REGISTER_SHEET_NAME = "register";
	
	
	//test data: excel/DB/Json/xml/static test data 
	//Constants: final static
	//Env data: Config properties 
}
