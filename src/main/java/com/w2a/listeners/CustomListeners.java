package com.w2a.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.Page;
import com.w2a.utilities.MonitoringMail;
import com.w2a.utilities.TestConfig;
import com.w2a.utilities.Utilities;

public class CustomListeners extends Page implements ITestListener, ISuiteListener{
	
	public void onTestStart(ITestResult result) {
		
	    test=rep.startTest(result.getName().toUpperCase());
	    
	  }
	
	public void onTestFailure(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			Utilities.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL,result.getName().toUpperCase() +" Failed with Exception : "+ result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(Utilities.screenshotName));
		rep.endTest(test);
		rep.flush();
		
		Reporter.log("Click link to see screenshot");
		Reporter.log("<a target = \"_blank\" href="+Utilities.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<a target = \"_blank\" href="+Utilities.screenshotName+"><img width=50 height=50 src="+Utilities.screenshotName+"></img></a>");
		
	}
	
	public void onTestSkipped(ITestResult result) {
		
		test.log(LogStatus.SKIP,  result.getName().toUpperCase() +" Skipped as Runmode is NO");
		rep.endTest(test);
		rep.flush();
	  }
	public void onTestSuccess(ITestResult result) {
		
		Page.test.log(LogStatus.PASS, result.getName().toUpperCase() +" PASS");
		rep.endTest(test);
		rep.flush();
		
	}
	public static String messageBody;
	public void onFinish(ISuite suite) {
		MonitoringMail mail =new MonitoringMail();
		
	   try {
		messageBody = "http://"+InetAddress.getLocalHost().getHostAddress() + ":8080/job/DataDrivenLiveProject/Extent_20Reports/";
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   try {
		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
	} catch (AddressException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	  }

}
