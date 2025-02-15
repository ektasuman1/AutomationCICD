package ecom.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ecom.AbstractComponent.AbstractComponent;

public class CartPage extends AbstractComponent{
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	WebDriver driver;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutEle;
	
	@FindBy(css=".cartSection h3")
	private List<WebElement> cartProdcts;
	
	public Boolean VerifyProductDisplay(String productname) {
		Boolean match = cartProdcts.stream().anyMatch(product->product.getText().equalsIgnoreCase(productname));
		return match;
	}
	
	public CheckoutPage gotoCheckOut() {
		checkoutEle.click();
		return new CheckoutPage(driver);
	}
}
