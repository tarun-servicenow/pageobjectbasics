package com.w2a.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.pages.HomePage;
import com.w2a.pages.LoginPage;
import com.w2a.pages.ZohoAppPage;
import com.w2a.utilities.Utilities;

public class LoginTest {

	@Test(dataProviderClass=Utilities.class,dataProvider="dp")
	public void loginTest(Hashtable<String,String> data){
	HomePage home = new HomePage();		
	LoginPage login= home.goToLogin();	
	ZohoAppPage zp= login.doLogin(data.get("username"), data.get("password"));
	
	
	}
}
