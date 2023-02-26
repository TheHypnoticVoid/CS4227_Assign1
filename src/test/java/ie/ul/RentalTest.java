package ie.ul;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ie.ul.src.Customer;
import ie.ul.src.Movie;
import ie.ul.src.Rental;

/**
 * Unit test for Movie Rental System.
 */
public class RentalTest 
{
    private Customer cust;
    private List<Rental> rentals = new ArrayList<>();
    private Dispatcher dispatch;
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    
    @Before
    public void setUp() {
        // Create a customer and rented movies
        cust = new Customer("John");
        
        Rental rent1 = new Rental(new Movie("Bullet Train", Movie.NEW_RELEASE), 5);
        Rental rent2 = new Rental(new Movie("Ratatouille", Movie.CHILDRENS), 2);
        Rental rent3 = new Rental(new Movie("Goodfellas", Movie.REGULAR), 5);

        // Add rentals to customer
        cust.addRental(rent1);
        cust.addRental(rent2);
        cust.addRental(rent3);

        // Add rentals to list
        rentals.add(rent1);
        rentals.add(rent2);
        rentals.add(rent3);

        // Create dispatcher
        dispatch = new Dispatcher();

        // Set up streams so that system prints can be tested
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    
    @Test
    public void rentalTest()
    {
        // Check if customer and rental was created successfully
        assertNotNull(cust);

        List<Rental> rentalsTest = new ArrayList<>();
        
        rentalsTest.add(new Rental(new Movie("Bullet Train", Movie.NEW_RELEASE), 5));
        rentalsTest.add(new Rental(new Movie("Ratatouille", Movie.CHILDRENS), 2));
        rentalsTest.add(new Rental(new Movie("Goodfellas", Movie.REGULAR), 5));

        for(int i = 0; i < 3; i++) {
            assertEquals(rentals.get(i).getMovie().getTitle(), rentalsTest.get(i).getMovie().getTitle());
        }
    }

    @Test
    public void logFRPTest()
    {
        FRPLogInterceptor freqRentPointsLog = new FRPLogInterceptor();
        dispatch.attach(freqRentPointsLog);
        
        String expected = "John has obtained 1 Frequent Renter Points after renting Ratatouille.";
        
        MovieRentalContext context = new MovieRentalContext(cust, rentals.get(1));
        dispatch.notify(context);
        
        assertEquals(expected, outContent.toString().trim());

        dispatch.detach(freqRentPointsLog);
    }

    @Test
    public void logChargeTest()
    {
        ChargeLogInterceptor chargeLog = new ChargeLogInterceptor();
        dispatch.attach(chargeLog);
        
        String expected = "John was charged €15.00 after renting Bullet Train.";
        
        MovieRentalContext context = new MovieRentalContext(cust, rentals.get(0));
        dispatch.notify(context);
        
        assertEquals(expected, outContent.toString().trim());

        dispatch.detach(chargeLog);
    }
    
}
