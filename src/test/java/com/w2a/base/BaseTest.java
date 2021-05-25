package com.w2a.base;

import org.testng.annotations.AfterSuite;

public class BaseTest extends Page{
	
	@AfterSuite
	public void tearDown(){
		
		quit();
		
		
	}

}
