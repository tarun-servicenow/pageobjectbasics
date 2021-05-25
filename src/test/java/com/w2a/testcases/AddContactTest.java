package com.w2a.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.pages.ZohoAppPage;
import com.w2a.pages.campaigns.AddContactsPage;
import com.w2a.pages.campaigns.CampaignHomePage;
import com.w2a.pages.campaigns.ContactDetailsPage;
import com.w2a.utilities.Utilities;

public class AddContactTest {
	
	@Test(dataProviderClass=Utilities.class,dataProvider="dp")
	public void addContactTest(Hashtable<String,String> data) {
		ZohoAppPage zp= new ZohoAppPage();
		CampaignHomePage ch = zp.goToCampaigns();
		AddContactsPage contact = ch.gotoAddContacts();
		contact.addNewContacts();
		ContactDetailsPage cdp = contact.addSingleContact();
		cdp.addContactDetails(data.get("contactemail"));
		
		Assert.fail("Add Contact Test Failed");
	}

}
