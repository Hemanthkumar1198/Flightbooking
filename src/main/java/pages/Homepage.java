package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Homepage {

    WebDriver driver;

    @FindBy(xpath = "//div[@class='jumbotron']//h1")
    WebElement title;

    @FindBy(partialLinkText = "destination of the week")
    WebElement destinationLink;

    @FindBy(name = "fromPort")
    WebElement departureCityDropdown;

    @FindBy(name = "toPort")
    WebElement destinationCityDropdown;

    @FindBy(css = ".btn-primary")
    WebElement findFlightsButton;

    public Homepage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return title.getText();
    }

    public boolean clickdestinationofweek() {
    	destinationLink.click();
    	boolean verifyUrl = driver.getCurrentUrl().endsWith("vacation.html");
    	return verifyUrl;
    }
    
    public void navigateback() {
    	driver.navigate().back();
    }

    public void selectDepartureCity(String city) {
        new Select(departureCityDropdown).selectByVisibleText(city);
    }

    public void selectDestinationCity(String city) {
        new Select(destinationCityDropdown).selectByVisibleText(city);
    }

    public void searchFlights() {
        findFlightsButton.click();
    }
}


