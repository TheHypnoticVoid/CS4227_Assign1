package ie.ul;

public class FRPLogInterceptor implements LogInterceptor {
    @Override
    public void intercept(MovieRentalContext co) {
        int freqRentPoints = (int) co.getFRP();
        
        String message = co.getCustomer().getName() + " has obtained " + 
                         freqRentPoints + " Frequent Renter Points after renting " + 
                         co.getMovie().getTitle() + ".";
        System.out.println(message);
    }
}
