package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import extentlisteners.ExtentListeners;
import utilities.DbManager;
import utilities.ExcelReader;

public class BaseTest {

	/*
	 * WebDriver TestNG Excel Log4j Properties EXtentReports Db connectivity
	 * Screenshots Implicit vs Explicit Waits Keywords
	 * 
	 * 
	 * 
	 */

	public static WebDriver driver;
	public static ExcelReader excel = new ExcelReader("./src/test/resources/excel/testdata.xlsx");
	public static Properties OR = new Properties();
	public static Properties config = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(BaseTest.class);
	public static WebDriverWait wait;
	
	
	
	public static void click(String locatorKey) {

		try {
			if (locatorKey.endsWith("_XPATH")) {
				((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'",
						driver.findElement(By.xpath(OR.getProperty(locatorKey))));

				driver.findElement(By.xpath(OR.getProperty(locatorKey))).click();
			} else if (locatorKey.endsWith("_ID")) {
				((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'",
						driver.findElement(By.id(OR.getProperty(locatorKey))));

				driver.findElement(By.id(OR.getProperty(locatorKey))).click();

			} else if (locatorKey.endsWith("_NAME")) {
				((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'",
						driver.findElement(By.name(OR.getProperty(locatorKey))));

				driver.findElement(By.name(OR.getProperty(locatorKey))).click();

			} else if (locatorKey.endsWith("_CSS")) {
				((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'",
						driver.findElement(By.cssSelector(OR.getProperty(locatorKey))));

				driver.findElement(By.cssSelector(OR.getProperty(locatorKey))).click();

			}

			log.info("Clicking on the " + locatorKey);
			ExtentListeners.test.info("Clicking on the " + locatorKey);
		} catch (Throwable t) {

			log.error(t.getMessage());
			log.error("Error while clicking on element +" + locatorKey);
			ExtentListeners.test.fail("Error while clicking on element +" + locatorKey+" Exception message : "+t.getMessage());
			Assert.fail(t.getMessage());
		}
	}

	
	
	

	public static void type(String locatorKey, String value) {

		try {
			if (locatorKey.endsWith("_XPATH")) {
		
				driver.findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(value);
			} else if (locatorKey.endsWith("_ID")) {
			
				driver.findElement(By.id(OR.getProperty(locatorKey))).sendKeys(value);

			} else if (locatorKey.endsWith("_NAME")) {
		
				driver.findElement(By.name(OR.getProperty(locatorKey))).sendKeys(value);

			} else if (locatorKey.endsWith("_CSS")) {
			
				driver.findElement(By.cssSelector(OR.getProperty(locatorKey))).sendKeys(value);

			}

			log.info("Typing in an Element " + locatorKey+ " and entered the value as : "+value);
			ExtentListeners.test.info("Typing in an Element " + locatorKey+ " and entered the value as : "+value);
		} catch (Throwable t) {

			log.error(t.getMessage());
			log.error("Error while typing in an element +" + locatorKey);
			ExtentListeners.test.fail("Error while typing in an element +" + locatorKey+" Exception message : "+t.getMessage());
			Assert.fail(t.getMessage());
		}
	}
	
	
	
	
	public static boolean isElementPresent(String locatorKey) {

		try {
			if (locatorKey.endsWith("_XPATH")) {
		
				driver.findElement(By.xpath(OR.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_ID")) {
			
				driver.findElement(By.id(OR.getProperty(locatorKey)));

			} else if (locatorKey.endsWith("_NAME")) {
		
				driver.findElement(By.name(OR.getProperty(locatorKey)));

			} else if (locatorKey.endsWith("_CSS")) {
			
				driver.findElement(By.cssSelector(OR.getProperty(locatorKey)));

			}

			log.info("Finding presence of an Element " + locatorKey);
			ExtentListeners.test.info("Finding presence of an Element " + locatorKey);
			return true;
		} catch (Throwable t) {

			log.error(t.getMessage());
			log.error("Error while finding presence of an element +" + locatorKey);
			ExtentListeners.test.fail("Error while finding presence of an element +" + locatorKey+" Exception message : "+t.getMessage());
			return false;
		}
	}
	
	
	
	
	

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");

			log.info("------Test execution started-----");
			ExtentListeners.test.info("------Test execution started-----");			

			try {
				fis = new FileInputStream("./src/test/resources/properties/config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.info("Config Property file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream("./src/test/resources/properties/OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.info("OR Property file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (config.getProperty("browser").equals("chrome")) {

				driver = new ChromeDriver();
				log.info("Chrome browser launched");
			} else if (config.getProperty("browser").equals("firefox")) {

				driver = new FirefoxDriver();
				log.info("Firefox browser launched");
			}

			
			driver.get(config.getProperty("testsiteurl"));
			log.info("Navigated to : "+config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
			
			wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
			
			
			try {
				DbManager.setMysqlDbConnection();
				log.info("Db Connection established");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@AfterSuite
	public void tearDown() {

		driver.quit();
		log.info("------Test execution completed-----");
	}

}
