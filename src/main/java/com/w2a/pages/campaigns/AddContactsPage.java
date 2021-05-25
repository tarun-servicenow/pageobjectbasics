package com.w2a.pages.campaigns;

import org.openqa.selenium.By;

import com.w2a.base.Page;

public class AddContactsPage extends Page{
	
	public void addNewContacts() {
		
		click("addNewContact_CSS");
	}
	
	public ContactDetailsPage addSingleContact() {
		
		click("addSingleContactBtn_CSS");
		
		return new ContactDetailsPage();
				
	}
	
	
	public void addMultipleContacts() {
		
		driver.findElement(By.cssSelector("ul>li>a[id='allSubAddMultipleSubs']")).click();
	}
	
	public void importContacts() {
		
	}
	
	public void syncContacts() {
		
	}
	
	public void signupForm() {
		
	}

}
