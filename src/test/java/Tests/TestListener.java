package Tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
	private static WebDriver driver;

    public static void setDriver(WebDriver driverInstance) {
        driver = driverInstance;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenshot(result.getMethod().getMethodName());
    }

    private void takeScreenshot(String methodName) {
        if (driver == null) {
            System.out.println("Driver is null, cannot take screenshot.");
            return;
        }

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File screenshotDir = new File("./screenshots/");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs(); // Create folder if it doesn't exist
        }

        try {
            File destFile = new File(screenshotDir, methodName + ".png");
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot taken: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	

}
