package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.DataUtil;

public class BankManagerLoginTest extends BaseTest {

	@Test
	public void loginAsBankManager() {
		
		click("bmlBtn_CSS");
		Assert.assertTrue(isElementPresent("addCustomer_CSS"),"Bank manager not logged in");
		
	}
	
	
	
	
}
