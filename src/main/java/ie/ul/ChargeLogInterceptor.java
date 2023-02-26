package ie.ul;

public class ChargeLogInterceptor implements LogInterceptor {
    @Override
    public void intercept(MovieRentalContext co) {
        String charge = String.format("%.2f", co.getCharge());
        
        String message = co.getCustomer().getName() + " was charged €" + 
                         charge + " after renting " + 
                         co.getMovie().getTitle() + ".";
        System.out.println(message);
    }
}
