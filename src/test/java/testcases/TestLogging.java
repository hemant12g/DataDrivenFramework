package testcases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public  class TestLogging {


		/*
		 * Log4J dependency
		 * Logger - getLogger() -- INFO,DEBUG, ERROR
		 * Log4j properties - PropertyConfigurator
		 * Appenders - store the logs file and patterns etc
		 */
		static WebDriver driver;
		static Properties OR = new Properties();
		static Properties config = new Properties();
		static Logger log = Logger.getLogger(TestLogging.class);	
		
		public static void main(String[] args) throws IOException {
			PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
			//System.setProperty("log4j.debug", "true");
			log.info("Test execution started!");
			
			FileInputStream fis = new FileInputStream("./src/test/resources/properties/OR.properties");
			OR.load(fis);
			log.info("OR Properties Loaded");
			
			fis = new FileInputStream("./src/test/resources/properties/config.properties");
			config.load(fis);
			log.info("config Properties Loaded");
			//log.debug("config Properties Loaded DEBUG");
			log.info("URL user "+ config.getProperty("testsiteurl"));
			
			if(config.getProperty("browser").equals("chrome")){
				driver = new ChromeDriver(); // Open the Browser
				log.info("Chrome Browser Launched");
				driver.get(config.getProperty("testsiteurl"));
				driver.findElement(By.xpath(OR.getProperty("username_XPATH"))).sendKeys("username");
				log.info("Username Loaded");
				driver.findElement(By.id("password_ID")).sendKeys("password");
				log.info("Password Loaded");
				driver.findElement(By.xpath(OR.getProperty("login_NAME"))).click();
				log.info("Login Completed");
				//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
				System.out.println("You have Searched: "+driver.getTitle());
				System.out.println("Your current Searched url: "+driver.getCurrentUrl());
				driver.quit();		
			}else {
				log.info("The browser is not Chrome");
				System.out.println("The browser is not Chrome");
			}
				
		log.info("Test execution completed!");
			
		}
		
		
	}
