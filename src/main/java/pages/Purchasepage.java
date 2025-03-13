package pages;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Purchasepage {
	
	WebDriver driver;
	
	@FindBy(xpath = "//p[contains(text(),'Total Cost:')]")
	 WebElement totalCostElement;
	
	@FindBy(xpath = "//input[@value='Purchase Flight']")
	 WebElement purchaseButton;
	
	public Purchasepage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void verifytotalcost() {
     String totalCostText = totalCostElement.getText().replace("Total Cost: ", "").trim();
     
     if (Pattern.matches("\\d+\\.\\d{2}", totalCostText)) {
         System.out.println("Total Cost is correctly formatted: " + totalCostText);
     } else {
         System.out.println("Total Cost format is incorrect: " + totalCostText);
         //captureScreenshot("TotalCostFormatError");
     }
	}

    public void purchacebtnclick() {
     purchaseButton.click();
    }

}
