package ie.ul;

import ie.ul.src.Customer;
import ie.ul.src.Movie;
import ie.ul.src.Rental;

public class MovieRentalContext {
    private Customer customer;
    private Rental rental;

    public MovieRentalContext(Customer customer, Rental rental) {
        this.customer = customer;
        this.rental = rental;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Movie getMovie() {
        return rental.getMovie();
    }

    public double getCharge() {
        return rental.getCharge();
    }

    public double getFRP() {
        return rental.getFrequentRenterPoints();
    }
}
