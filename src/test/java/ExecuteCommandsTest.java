import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

public class ExecuteCommandsTest {
    TraderClientSeller traderClientSeller = spy(new DummyTraderClientSeller());
    BiddingScheduler executeCommands = new BiddingScheduler(traderClientSeller);


    @Test
    public void shouldInvokeTraderClientMethod() throws InterruptedException {

        executeCommands.scheduleCommand(2, TimeUnit.SECONDS);

        Thread.sleep(7 * 1000);

        verify(traderClientSeller, times(4)).sell();
    }

}
