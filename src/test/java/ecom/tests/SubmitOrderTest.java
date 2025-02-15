package ecom.tests;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ecom.TestComponents.BaseTest;
import ecom.pageobjects.CartPage;
import ecom.pageobjects.OrderPage;
import ecom.pageobjects.CheckoutPage;
import ecom.pageobjects.ConfirmationPage;
import ecom.pageobjects.Landing;
import ecom.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest {
	String productname = "ADIDAS ORIGINAL";	
	// Hello

	@Test(dataProvider= "getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) {
		ProductCatalogue productcatalogue = landingPage.loginApplication(input.get("email"),input.get("password"));
//		ProductCatalogue productcatalogue = new ProductCatalogue(driver);
		List<WebElement> products =	productcatalogue.getProductsList();
		productcatalogue.addProductToCart(input.get("product"));
		CartPage cartpage = productcatalogue.goToCart();
//		CartPage cartpage = new CartPage(driver);
		Boolean match = cartpage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutpage = cartpage.gotoCheckOut();
		checkoutpage.selectCountry("india");
		ConfirmationPage confirmationpage = checkoutpage.submitOrder();
		String confirmMessage = confirmationpage.verifyConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
		}

	@Test(dependsOnMethods={"submitOrder"})
	public void orderHistory() {
		ProductCatalogue productcatalogue = landingPage.loginApplication("annie@gmail.com","Asdfghjkl0*");
		OrderPage orderspage = productcatalogue.goToOrderPage();
		Assert.assertTrue(orderspage.verifyOrderDisplay(productname));
	}
	
	
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String,String>> data = getJsonDatatoMap(System.getProperty("user.dir")+"//src//test//java//ecom//data//PurchaseOrder.json");
		return new Object[][]  {{data.get(0)},{data.get(1)}};

//		HashMap<String,String> map = new HashMap<String,String>();
//		map.put("email", "annie@gmail.com");
//		map.put("password", "Asdfghjkl0*");
//		map.put("product", "ADIDAS ORIGINAL");
//		
//		HashMap<String,String> map1 = new HashMap<String,String>();
//		map1.put("email", "annie@gmail.com");
//		map1.put("password", "Asdfghjkl0*");
//		map1.put("product", "IPHONE 13 PRO");s
	}
	
	
}
