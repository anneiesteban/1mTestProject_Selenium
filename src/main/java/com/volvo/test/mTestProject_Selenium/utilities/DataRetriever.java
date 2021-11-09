package com.volvo.test.mTestProject_Selenium.utilities;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class DataRetriever {
	
	public static Fillo fillo;
	public static Connection connection;
	
	public static boolean readExcel(String path) {
		boolean result = true;
		fillo = new Fillo();
		connection = null;
		print("READ EXCEL starting...");
		try {
			connection = fillo.getConnection(path);
			print("CONNECTION SUCCESS.");
		} catch (FilloException e) {
			print("Connection error: " + e.getMessage());
			result = false;
		}
		print("END READ EXCEL...");
		return result;
	}
	
	public static Recordset getSheetData(String table, String whereclause, String query) {
		Recordset rs = null;
		String queryValue = "";
		if(query.length() > 0) {
			queryValue = query;
		}else {
			if(table.length() == 0) {
				print("Table or worksheet name not defined.");
			}else {
				queryValue = "SELECT * FROM \"" + table + "\"" + " " + whereclause;
			}
		}
		try {
			rs = connection.executeQuery(queryValue);
		}catch (FilloException e) {
			if(query.length() > 0) {
				print("No records found for the query: "+ query +".\n");
			}else{
				print("No records found for worksheet: "+ table +"\n");
			}
		}			
		return rs;
	}
	
	public void writeData(String query) {
		try {
			connection.executeUpdate(query);
		}catch (FilloException e) {
			print(e.getMessage());
		}
	}
	private static void print(String msg) {
		System.out.println(msg);
	}
	
}
