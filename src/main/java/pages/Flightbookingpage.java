package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Flightbookingpage {

	WebDriver driver;
	List<Double> prices;
	int minIndex;

	@FindBy(xpath = "//table[@class='table']//td[contains(text(), '$')]")
	private List<WebElement> priceElements;

	@FindBy(xpath = "//table[@class='table']//input[@type='submit']")
	private List<WebElement> button;
	
	public Flightbookingpage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void lowestPriceFinder() {
		
		prices = new ArrayList<>();
		double minPrice = Double.MAX_VALUE;
		minIndex = -1;

		for (int i = 0; i < priceElements.size(); i++) {
			String priceText = priceElements.get(i).getText().replace("$", "").trim();
			double price = Double.parseDouble(priceText);
			prices.add(price);

			if (price < minPrice) {
				minPrice = price;
				minIndex = i;
			}
		}

		System.out.println("Lowest price found: $" + minPrice);
	}
	
	public void chooseflightbtnclick() {
		if (minIndex != -1) {
			button.get(minIndex).click();
			System.out.println("Clicked on the lowest price button.");
		}
	}
}
