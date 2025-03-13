package Tests;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;


public class ExcelReading {
	
	@DataProvider(name = "testdata")
	public static Object[][] readExcelData() throws IOException {
	    FileInputStream file = new FileInputStream(new File("C:\\Users\\heman\\eclipse-workspace\\Selenium_maven2\\src\\test\\resources\\Excel\\Book1.xlsx"));
	    Workbook workbook = WorkbookFactory.create(file);
	    Sheet sheet = workbook.getSheetAt(0);

	    int rowCount = sheet.getPhysicalNumberOfRows();
	    Object[][] data = new Object[rowCount - 1][2]; 
	    for (int i = 1; i < rowCount; i++) {
	        Row row = sheet.getRow(i);
	        data[i - 1][0] = row.getCell(0).getStringCellValue(); 
	        data[i - 1][1] = row.getCell(1).getStringCellValue();
	    }

	    file.close();
	    workbook.close();

	    return data;
	}

}
