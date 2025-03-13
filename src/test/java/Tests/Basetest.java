package Tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import pages.*;

public class Basetest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;
    protected Homepage homepage;
    protected Flightbookingpage flightBookingPage;
    protected Purchasepage purchasePage;
    protected Confirmationpage confirmationPage;

    @BeforeMethod
    public void setup(ITestContext context) {
        //System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        homepage = new Homepage(driver);
        flightBookingPage = new Flightbookingpage(driver);
        purchasePage = new Purchasepage(driver);
        confirmationPage = new Confirmationpage(driver);
        
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        test = extent.createTest(context.getName());
        
        driver.get("https://blazedemo.com/");
        TestListener.setDriver(driver);
    }
    

    @AfterMethod
    public void teardown() {
        driver.quit();
        extent.flush();
    }
}