package pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class demo {
	static WebDriver driver;
	static ExtentReports extent;
	
    public static void main(String[] args) throws IOException {
    	ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/FlightBookingReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        ExtentTest test = extent.createTest("Flight Booking Test");
        
         driver = new ChromeDriver();
        driver.get("https://www.blazedemo.com/index.php");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        
        String excelFilePath = ".\\src\\test\\resources\\Excel\\Book1.xlsx";
        String[] cities = readExcelData(excelFilePath);
        String fromCity = cities[0];
        String toCity = cities[1];
        // Verify the welcome message
        String text = driver.findElement(By.xpath("//div[@class='jumbotron']//h1")).getText();
//        if (text.equals("Welcome to the Simple Travel Agency!")) {
//            System.out.println("Page title verified.");
//        }
        test.log(Status.INFO, "Opened BlazeDemo travel website.");
       
        // Navigate to vacation page and verify
        driver.findElement(By.xpath("//a[text()='destination of the week! The Beach!']")).click();
        boolean verifyUrl = driver.getCurrentUrl().endsWith("vacation.html");
        System.out.println("Vacation page verification: " + verifyUrl);
        driver.navigate().back();

        // Select departure and destination cities
        Select fromPort = new Select(driver.findElement(By.name("fromPort")));
        fromPort.selectByValue(fromCity);
        test.log(Status.PASS, "Selected 'From' city: " + fromCity);
        

        Select toPort = new Select(driver.findElement(By.name("toPort")));
        toPort.selectByValue(toCity);
        test.log(Status.PASS, "Selected 'To' city: " + toCity);
        
        driver.findElement(By.cssSelector(".btn-primary")).click();

        // Fetch all price elements and buttons
        List<WebElement> priceElements = driver.findElements(By.xpath("//table[@class='table']//td[contains(text(), '$')]"));
        List<WebElement> buttons = driver.findElements(By.xpath("//table[@class='table']//input[@type='submit']"));

        List<Double> prices = new ArrayList<>();
        double minPrice = Double.MAX_VALUE;
        int minIndex = -1;

        // Extract prices and find the minimum value
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

        // Click the button corresponding to the lowest price
        if (minIndex != -1) {
            buttons.get(minIndex).click();
            System.out.println("Clicked on the lowest price button.");
        }
        
        WebElement totalCostElement = driver.findElement(By.xpath("//p[contains(text(),'Total Cost:')]"));
        String totalCostText = totalCostElement.getText().replace("Total Cost: ", "").trim();
        
        // Regular Expression to match xxx.xx format
        if (Pattern.matches("\\d+\\.\\d{2}", totalCostText)) {
            System.out.println("Total Cost is correctly formatted: " + totalCostText);
        } else {
            System.out.println("Total Cost format is incorrect: " + totalCostText);
            captureScreenshot("TotalCostFormatError");
        }

        // Click on 'Purchase Flight' button
        WebElement purchaseButton = driver.findElement(By.xpath("//input[@value='Purchase Flight']"));
        purchaseButton.click();

        // Verify if navigated to Purchase Confirmation page
        WebElement confirmationIdElement = driver.findElement(By.xpath("//td[contains(text(),'Id')]/following-sibling::td"));
        String confirmationId = confirmationIdElement.getText();
        System.out.println("Purchase Confirmation ID: " + confirmationId);
        
        extent.flush();
        driver.quit();
    }
    
    
    
    public static void captureScreenshot(String fileName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File("screenshots/" + fileName + ".png");
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
    
   
    public static String[] readExcelData(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);  // Read first sheet

        // Read cities from first row
        Row row = sheet.getRow(1); // Assuming cities start from row index 1 (skip header)
        String fromCity = row.getCell(0).getStringCellValue();
        String toCity = row.getCell(1).getStringCellValue();

        file.close();
        workbook.close();

        return new String[]{fromCity, toCity};
    }
}
