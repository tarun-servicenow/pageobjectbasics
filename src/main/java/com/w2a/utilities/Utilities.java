package com.w2a.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.w2a.base.Page;

public class Utilities extends Page{
	
	public static String screenshotPath;   
	public static String screenshotName;
	
	public static void captureScreenshot() throws IOException {
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		Date d = new Date();
		screenshotName = d.toString().replace(":","_").replace(" ","_") + ".jpg";
		
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+ "\\target\\surefire-reports\\html\\"+ screenshotName));
	
	}
	
	@DataProvider(name="dp")
	public Object[][] getData(Method m){
		
		String sheet = m.getName();
		int rows = excel.getRowCount(sheet);
		int cols = excel.getColumnCount(sheet);
		Object data[][] = new Object[rows-1][1];
		Hashtable<String,String> table = null;
		
		for(int rowNum=2; rowNum<=rows; rowNum++) {
			table= new Hashtable<String,String>();
			for(int colNum = 0; colNum<cols;colNum++) {
				table.put(excel.getCellData(sheet,colNum, 1), excel.getCellData(sheet,colNum, rowNum));
				data[rowNum-2][0] = table;
				
			}
		}
		return data;
		
		
	}

}
