import com.google.gson.JsonObject;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class bit2CAPITest {


    @Test
    public void shouldRetrieveSomethingFromBit2C() throws  Exception{

        Bit2CClient bit2CClient = new Bit2CClient();

        JsonObject jsonObject = bit2CClient.getTicker();

        float num = jsonObject.get("a").getAsFloat();

        assertThat(num, is(39.340626F));

    }

    @Test
    public void shouldReturnLiteCoinBalance(){

        Bit2CClient bit2CClient = new Bit2CClient();

        bit2CClient.getLiteCoinBalance();

    }

    @Test
    public void shouldReturnLowestSellBid() {

    }

    @Test
    public void shouldBidAtHighPrice(){

    }



    }
