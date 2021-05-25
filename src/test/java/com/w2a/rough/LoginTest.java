package com.w2a.rough;

import com.w2a.pages.HomePage;
import com.w2a.pages.LoginPage;
import com.w2a.pages.ZohoAppPage;
import com.w2a.pages.campaigns.AddContactsPage;
import com.w2a.pages.campaigns.CampaignHomePage;
import com.w2a.pages.campaigns.ContactDetailsPage;

public class LoginTest {

	public static void main(String[] args) {
	
		
		HomePage home = new HomePage();		
		LoginPage login= home.goToLogin();	
		ZohoAppPage zp= login.doLogin("test.webelement@gmail.com", "12345678@zoho");
		CampaignHomePage ch = zp.goToCampaigns();
		AddContactsPage contact = ch.gotoAddContacts();
		contact.addNewContacts();
		ContactDetailsPage cdp = contact.addSingleContact();
		cdp.addContactDetails("abc@gmail.com");
		
		
		
		
		
		
		
	}

}
