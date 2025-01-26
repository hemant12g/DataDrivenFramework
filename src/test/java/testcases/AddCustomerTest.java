package testcases;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.DataUtil;

public class AddCustomerTest extends BaseTest {

	
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "dp")
	public void addCustomer(String firstname, String lastname, String postcode) {
		
		click("addCustomer_CSS");
		type("firstName_CSS",firstname);
		type("lastName_CSS",lastname);
		type("postcode_CSS",postcode);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		click("addcust_CSS");
		
		
		
		
		Alert alert = driver.switchTo().alert();
		Assert.assertTrue(alert.getText().contains("Customer added successfully"),"Customer not added successfully");
		
		alert.accept();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
