package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.Utilities;

public class Page {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("LogResult");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	public static String browser;

	public Page() {

		if (driver == null) {

			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\w2a\\properties\\Config.properties");

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.info("Loaded Config Properties");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\properties\\OR.properties");

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.info("Loaded OR Properties");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Jenkins filter envn setup
			if (!(System.getenv("browser") == null) && !(System.getenv("browser").isEmpty())) {
				browser = System.getenv("browser");
			} else {
				browser = config.getProperty("browser");
			}

			config.setProperty("browser", browser);

			if (config.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\w2a\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();

			} else if (config.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\w2a\\executables\\chromedriver.exe");
				driver = new ChromeDriver();

			} else if (config.getProperty("browser").equals("ie")) {
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\w2a\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
			driver.get(config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			log.info("Invoked Webiste " + config.getProperty("testsiteurl"));
			wait = new WebDriverWait(driver, 5);
		}

	}

	// KeyWords
	public void click(String locator) {
		if (locator.endsWith("CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		log.info("Clicking on " + locator);
		test.log(LogStatus.INFO, "Clicking on " + locator);
	}

	public void type(String locator, String value) {
		if (locator.endsWith("CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		log.info("Typed in "+ locator + " as "+ value);
		test.log(LogStatus.INFO, "Typed in " + locator + " as " + value);

	}

	static WebElement dropdown;

	public void select(String locator, String value) {
		if (locator.endsWith("CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}

		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		log.info("Selected value from "+locator+" as "+ value);
		test.log(LogStatus.INFO, "Selected value from " + locator + " as " + value);

	}

	public boolean isElementPresent(By by) {

		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {

			return false;
		}
	}

	public void verifyEquals(String actVal, String expVal) throws IOException {
		try {

			Assert.assertEquals(actVal, expVal);

		} catch (Throwable t) {
			Utilities.captureScreenshot();
			// Extent Report
			test.log(LogStatus.FAIL, "Verification Failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(Utilities.screenshotName));

			// ReportNG
			Reporter.log("<br>" + "Verification Failed with Exception : " + t.getMessage());
			Reporter.log("<br>");
			Reporter.log("<a target = \"_blank\" href=" + Utilities.screenshotName + "><img width=50 height=50 src="
					+ Utilities.screenshotName + "></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
		}

	}

	public boolean isTestRunnable(String testName, ExcelReader excel) {

		String sheetName = "test_suite";
		int rows = excel.getRowCount(sheetName);

		for (int rNum = 2; rNum <= rows; rNum++) {

			String test = excel.getCellData(sheetName, "TCID", rNum);

			if (test.equalsIgnoreCase(testName)) {
				if (excel.getCellData(sheetName, "Runmode", rNum).equalsIgnoreCase("Y")) {
					return true;
				} else
					return false;
			}

		}

		return false;

	}

	public static void quit() {

		driver.quit();
	}

}
