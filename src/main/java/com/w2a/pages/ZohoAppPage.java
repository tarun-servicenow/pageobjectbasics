package com.w2a.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.w2a.base.Page;
import com.w2a.pages.campaigns.CampaignHomePage;


public class ZohoAppPage extends Page{
	

	public void goToConnect() {
		
		driver.findElement(By.cssSelector("div>div:nth-child(5)>.tc")).click();
	}
	
	public CampaignHomePage goToCampaigns() {
		
		click("campaigns_CSS");	
		
		return new CampaignHomePage();
	}
	
	public void goToDesk() {
		
		driver.findElement(By.cssSelector("div>div:nth-child(7)>.tc")).click();
	}
}
