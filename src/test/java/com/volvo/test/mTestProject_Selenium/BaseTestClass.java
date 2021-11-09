package com.volvo.test.mTestProject_Selenium;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import com.volvo.test.mTestProject_Selenium.utilities.Browser;
import com.volvo.test.mTestProject_Selenium.utilities.BrowserFactory;
import com.volvo.test.mTestProject_Selenium.utilities.Common;
import com.volvo.test.mTestProject_Selenium.utilities.DataRetriever;
import com.volvo.test.mTestProject_Selenium.utilities.GetPropertiesConfig;

public class BaseTestClass {

	public static String TEST_CASE = "";

	public static String excelPath;
	public static String excelName;

	@BeforeTest
	public void beforeTest() throws IOException {
		String browserType = getData(Common.LOGIN, Common.BROWSER_TYPE, "");
		String siteURL = getData(Common.LOGIN, Common.URL, "");
		BrowserFactory browser = new BrowserFactory();
		Browser.driver = browser.getDriver(browserType, siteURL);
	}

	@AfterTest
	public void afterTest() {

	}

	@BeforeSuite
	public void beforeSuite() {

	}

	@AfterSuite
	public void afterSuite() {
		if (Browser.driver != null) {
			Browser.driver.close();
		}
	}

	protected String getData(String table, String fields, String query) throws IOException {
		GetPropertiesConfig conf = new GetPropertiesConfig();
		
		String excelPath = conf.getPropValues("path.excel");
		String excelName = conf.getPropValues("excel.filename");
		if (!DataRetriever.readExcel(excelPath+excelName)) {
			print("No Connection.");
		}
		Recordset rs = DataRetriever.getSheetData(table, "", query);
		String data = null;
		try {
			while (rs.next()) {
				String fieldname = "" + fields + "";
				data = rs.getField(fieldname);
			}
		} catch (FilloException e) {
			print("Error in retrieving data: " + e.getMessage());
		}
		return data;
	}

	protected ArrayList<String> getDataList(String table, String fields, String query) throws IOException {
		GetPropertiesConfig conf = new GetPropertiesConfig();
		
		String excelPath = conf.getPropValues("path.excel");
		String excelName = conf.getPropValues("excel.filename");
		
		if (!DataRetriever.readExcel(excelPath+excelName)) {
			print("No Connection.");
		}
		Recordset rs = DataRetriever.getSheetData(table, "", query);
		ArrayList<String> data = new ArrayList<String>();
		try {
			while (rs.next()) {
				String fieldname = "" + fields + "";
				if (rs.getField(fieldname).equals(null) || rs.getField(fieldname).equals("")) {
					break;
				} else {
					data.add(rs.getField(fieldname));
				}
			}
		} catch (FilloException e) {
			print("Error in retrieving data: " + e.getMessage() + "\n");
		}
		return data;
	}

	protected void print(String msg) {
		System.out.println(); 
		System.out.println(msg);
	}
	protected void writeQuery(String table, String field, String status, String testcase) {
		DataRetriever dr = new DataRetriever();
		String writeQuery = "UPDATE \"" + table + "\"" + "SET \"" + field + "\" ='" + status + "'" + " WHERE \"" + Common.AUTOMATATION_TC_NAME+ "\" ='"+testcase+"'";
		dr.writeData(writeQuery);
	}
}
