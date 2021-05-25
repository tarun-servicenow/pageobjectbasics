package com.w2a.pages.campaigns;

import org.openqa.selenium.By;

import com.w2a.base.Page;

public class CampaignHomePage extends Page {

	public AddContactsPage gotoAddContacts() {

		click("campaigns_CSS");

		return new AddContactsPage();
	}

	public void gotoCreateSignUpForm() {

		driver.findElement(By.cssSelector(
				"div[class='getstrtcntr']>div[class='c dtbl']>div:nth-child(2)>div:nth-child(3)>button[class='btn prim tc']"))
				.click();
	}

	public void gotoSendInvite() {

		driver.findElement(By.cssSelector("button[class='btn prim tc zcrmp-adduserCl'])")).click();

	}

}
