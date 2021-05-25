package com.w2a.pages.campaigns;

import org.openqa.selenium.By;

import com.w2a.base.Page;

public class ContactDetailsPage extends Page{
	
	public void addContactDetails(String contactEmail) {
		
		type("contactEmail_CSS",contactEmail);
		
	}

}
