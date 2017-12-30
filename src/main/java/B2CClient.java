import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class B2CClient {
    private CloseableHttpClient httpclient =  HttpClients.createDefault();


    public JsonObject getTicker() throws IOException {
        HttpGet httpGet = new HttpGet("https://www.bit2c.co.il/Exchanges/BtgNis/Ticker.json");

        CloseableHttpResponse response = httpclient.execute(httpGet);

        HttpEntity entity = response.getEntity();

        return new Gson().fromJson(EntityUtils.toString(entity), JsonObject.class);

    }
}
