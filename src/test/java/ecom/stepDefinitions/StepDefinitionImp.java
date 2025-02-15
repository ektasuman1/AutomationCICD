package ecom.stepDefinitions;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import ecom.TestComponents.BaseTest;
import ecom.pageobjects.CartPage;
import ecom.pageobjects.CheckoutPage;
import ecom.pageobjects.ConfirmationPage;
import ecom.pageobjects.Landing;
import ecom.pageobjects.ProductCatalogue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImp extends BaseTest{

	public Landing landingPage;
	public ProductCatalogue productcatalogue;
	public ConfirmationPage confirmationpage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() {
		landingPage=launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Login_user_pass(String username,String password) {
		productcatalogue = landingPage.loginApplication(username,password);
	}
	
	
	@When("^I add product (.+) from Cart$")
	public void Add_Product_from_Cart(String productname) {
		List<WebElement> products =	productcatalogue.getProductsList();
		productcatalogue.addProductToCart(productname);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void Checkout_and_Submit(String productname) {
		CartPage cartpage = productcatalogue.goToCart();
		Boolean match = cartpage.VerifyProductDisplay(productname);
		Assert.assertTrue(match);
		CheckoutPage checkoutpage = cartpage.gotoCheckOut();
		checkoutpage.selectCountry("india");
		confirmationpage = checkoutpage.submitOrder();
	}
	
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_confirmation(String string) {
		String confirmMessage = confirmationpage.verifyConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
		driver.close();
	}
	
	
	@Then("{string} message is displayed")
	public void message_displayed(String strval) {
		Assert.assertEquals(strval,landingPage.getErrorMessage());

	}
	
}
