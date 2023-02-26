package ie.ul;

import java.util.ArrayList;
import java.util.List;

public class Dispatcher {
    private List<LogInterceptor> interceptors = new ArrayList<>();

    public void attach(LogInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void detach(LogInterceptor interceptor) {
        interceptors.remove(interceptor);
    }

    public void notify(MovieRentalContext context) {
        for(LogInterceptor interceptor : interceptors) {
            interceptor.intercept(context);
        }
    }
}
