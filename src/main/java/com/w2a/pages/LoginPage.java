package com.w2a.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.w2a.base.Page;

public class LoginPage extends Page{

	
	
	public ZohoAppPage doLogin(String userName, String password) {
		
		type("username_CSS",userName);
        click("nextbtn_CSS");
		type("password_CSS",password);
		click("loginbtn_CSS");
		
		return new ZohoAppPage();
	}
	
	
}
