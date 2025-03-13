package Tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class Testcase extends Basetest{
	
	@Test(dataProvider = "testdata", dataProviderClass = ExcelReading.class)
	public void testFlightBooking(String departureCity, String destinationCity) {
	       
        Assert.assertEquals(homepage.getPageTitle(), "Welcome to the Simple Travel Agency!");
        homepage.clickdestinationofweek();
        homepage.navigateback();
        homepage.selectDepartureCity(departureCity);
        homepage.selectDestinationCity(destinationCity);
        homepage.searchFlights();
        
        flightBookingPage.lowestPriceFinder();
        flightBookingPage.chooseflightbtnclick();
        
        purchasePage.verifytotalcost();
        purchasePage.purchacebtnclick();
        
        String ID = confirmationPage.getconfirmationId();
        System.out.println("Booking Confirmation ID: " + ID);
    }

}
