import Bit2CJavaApi.src.Api.Bit2cClient;
import Bit2CJavaApi.src.Objects.UserBalance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class bit2CAPITest {


    @Test
    public void shouldRetrieveSomethingFromBit2C() throws  Exception{

        B2CClient b2CClient = new B2CClient();

        JsonObject jsonObject = b2CClient.getTicker();

        float num = jsonObject.get("a").getAsFloat();

        assertThat(num, is(39.340626F));

    }
}
