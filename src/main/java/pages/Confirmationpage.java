package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Confirmationpage {

	WebDriver driver;
	
	@FindBy(xpath = "//td[contains(text(),'Id')]/following-sibling::td")
	WebElement confirmationIdElement;
	
	
	public Confirmationpage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getconfirmationId() {
		String confirmationId = confirmationIdElement.getText();
		return confirmationId;
	}
    
}
