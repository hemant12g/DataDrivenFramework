package testcases;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.DataUtil;

public class LoginTest extends BaseTest {
	
	
	@Test(dataProviderClass = DataUtil.class,dataProvider = "dp")
	public void doLogin(String username, String password) {
		
		type("username_XPATH",username);
		type("password_ID",password);
		click("login_NAME");
		
	}
	
	
	
	
}
