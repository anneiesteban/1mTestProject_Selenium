package com.volvo.test.mTestProject_Selenium.utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;


public class BrowserFactory {
	public static WebDriver driver;
	
	
	public WebDriver getDriver(String browserType, String URL) throws IOException {
		GetPropertiesConfig conf = new GetPropertiesConfig();
		
		String driverPath = conf.getPropValues("path.drivers");
		switch(browserType.trim().toUpperCase()) {
		case Common.FIREFOX:
			System.out.println("Launching Firefox browser...");
			FirefoxOptions ffOptions = new FirefoxOptions();
			ffOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
			ffOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			ffOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			System.setProperty("webdriver.gecko.driver", driverPath+ "geckodriver.exe");
			driver = new FirefoxDriver(ffOptions);
			break;
		case Common.CHROME:
			System.out.println("Launching Chrome browser...");
			ChromeOptions cOptions = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
			driver = new ChromeDriver(cOptions);
			break;
		case Common.IE:
			System.out.println("Launching IE browser...");
			InternetExplorerOptions ieOptions = new InternetExplorerOptions();
			ieOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			ieOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer.exe");
			driver = new InternetExplorerDriver(ieOptions);
			break;
		case Common.EDGE:
			System.out.println("Launching Edge browser...");
			System.setProperty("webdriver.edge.driver", driverPath + "msedgedriver.exe");
			driver = new EdgeDriver();
			break;
		case Common.OPERA:
			System.out.println("Launching Opera browser...");
			System.setProperty("webdriver.opera.driver", driverPath + "operadriver.exe");
			driver = new OperaDriver();
			break;
		default:
			System.out.println("Launching Chrome browser...");
			ChromeOptions cOptions1 = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
			driver = new ChromeDriver(cOptions1);
			break;
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(URL);
		return driver;
	}	
}
