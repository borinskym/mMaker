import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BiddingScheduler {
    TraderClientSeller seller;

    public BiddingScheduler(TraderClientSeller seller) {
        this.seller = seller;
    }

    public void scheduleCommand(int interval, TimeUnit unit) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> seller.sell(), 0, interval, unit);

    }
}
