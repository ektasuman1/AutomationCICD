package ecom.tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import ecom.TestComponents.BaseTest;
import ecom.pageobjects.CartPage;
import ecom.pageobjects.ProductCatalogue;

public class ErrorValidation extends BaseTest{

	@Test(groups= {"ErrorHandling"},retryAnalyzer=ecom.TestComponents.Retry.class)
	public void LoginErrorValidation() {
		landingPage.loginApplication("annie@nn.com","Asdfghjkl0*");
		System.out.print("fsdfsfd"+landingPage.getErrorMessage());
		Assert.assertEquals("Incorrect email or password.",landingPage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidation() {
		String productname = "ADIDAS ORIGINAL";	
		ProductCatalogue productcatalogue = landingPage.loginApplication("annie@gmail.com","Asdfghjkl0*");
 		List<WebElement> products =	productcatalogue.getProductsList();
		productcatalogue.addProductToCart(productname);
		CartPage cartpage = productcatalogue.goToCart();
 		Boolean match = cartpage.VerifyProductDisplay("Abc");
		Assert.assertFalse(match);
	}
	
}
