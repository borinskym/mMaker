/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bit2CJavaApi.src.Api;

import Bit2CJavaApi.src.Enums.PairType;
import Bit2CJavaApi.src.Objects.*;
import com.google.gson.Gson;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author AMiT
 */
public class Bit2cClient {

    private final String Key;
    private final String Secret;
    private final String Url;
    private final BASE64Encoder encoder;
    private long nonce;

    public Bit2cClient(String Url, String Key, String Secret) throws Exception {
        this.Key = Key;
        this.Secret = Secret.toUpperCase();
        this.Url = Url;
        this.encoder = new BASE64Encoder();
        this.nonce = (new Date()).getTime() / 1000;
    }

    private String ComputeHash(String message) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secret_spec = new SecretKeySpec(this.Secret.getBytes(), "HmacSHA512");
        mac.init(secret_spec);
        String signature = (new BASE64Encoder()).encode(mac.doFinal(message.getBytes())).replaceAll("\r\n", "");
        return signature;
    }

    private String Query(HashMap<String, String> data, String Url) {
        try {
            // add nonce and build arg lis
            String post_data = buildQueryString(data);
            URL queryUrl = new URL(Url);

            // create connection
            HttpURLConnection connection = (HttpURLConnection) queryUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Timeout", "5000");
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("content-length", String.valueOf(post_data.length()));
            connection.setDoOutput(true);
            connection.setRequestProperty("Key", this.Key);
            connection.setRequestProperty("Sign", this.ComputeHash(post_data));
            // write post
            connection.getOutputStream().write(post_data.getBytes());

            // read info
            byte buffer[] = new byte[16384];
            int len = connection.getInputStream().read(buffer, 0, 16384);
            return new String(buffer, 0, len, "UTF-8");
        } catch (Exception e) {
        }
        return null;
    }

    private String buildQueryString(HashMap<String, String> args) {
        String result = new String();
        for (String hashkey : args.keySet()) {
            if (result.length() > 0) {
                result += '&';
            }
            try {
                result += URLEncoder.encode(hashkey, "UTF-8") + "="
                        + URLEncoder.encode(args.get(hashkey), "UTF-8");
            } catch (Exception ex) {
            }
        }
        if (result.isEmpty()) {
            result = "nonce=" + this.nonce;
        } else {
            result += "&nonce=" + this.nonce;
        }
        this.nonce++;
        return result;
    }

    public UserBalance Balance() {
        String url = this.Url + "Account/Balance";
        String data = Query(new HashMap<String, String>(), url);

        Gson gson = new Gson();
        UserBalance balance = gson.fromJson(data, UserBalance.class);

        return balance;
    }

    private String DownloadString(String Url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL(Url)).openConnection();
            connection.getContent();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();

            String inputStr = "";
            while ((inputStr = streamReader.readLine()) != null) {
                response.append(inputStr);
            }
            return response.toString();
        } catch (Exception ex) {

        }
        return null;
    }

    public ArrayList<ExchangesTrade> GetTrades(PairType Pair, Long since, Double date) {
        ArrayList<ExchangesTrade> result = new ArrayList<ExchangesTrade>();
        try {
            String Url = this.Url + "Exchanges/" + Pair.ordinal() + "/trades.json";
            String response = DownloadString(Url);
            Gson gson = new Gson();
            ExchangesTrade[] trades = gson.fromJson(response.toString(), ExchangesTrade[].class);
            result.addAll(Arrays.asList(trades));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Ticker GetTicker(PairType Pair) {
        try {
            String Url = this.Url + "Exchanges/" + Pair.ordinal() + "/Ticker.json";
            String response = DownloadString(Url);
            Gson gson = new Gson();
            Ticker fromJSON = gson.fromJson(response, Ticker.class);
            return fromJSON;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrderBook GetOrderBook(PairType Pair) {
        OrderBook orderBook = null;
        try {
            String Url = this.Url + "Exchanges/" + Pair.ordinal() + "/orderbook.json";
            String response = DownloadString(Url);
            Gson gson = new Gson();
            orderBook = gson.fromJson(response, OrderBook.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderBook;
    }

    public AddOrderResponse AddOrder(OrderData data) {
        String Url = this.Url + "Order/AddOrder";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Amount", String.valueOf(data.Amount.toPlainString()));
        params.put("IsBid", String.valueOf(data.IsBid));
        params.put("Pair", String.valueOf(data.Pair));
        params.put("Price", String.valueOf(data.Price.toPlainString()));
        params.put("Total", String.valueOf(data.Total.toPlainString()));
        String response = Query(params, Url);

        Gson gson = new Gson();
        AddOrderResponse addOrderResponse = gson.fromJson(response, AddOrderResponse.class);
        return addOrderResponse;
    }

    public Orders MyOrders(PairType pair) {
        String Url = this.Url + "Order/MyOrders";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("pair", String.valueOf(pair));
        String response = Query(params, Url);

        Gson gson = new Gson();
        Orders orders = gson.fromJson(response, Orders.class);
        return orders;
    }

    public ArrayList<AccountRaw> AccountHistory(Date fromTime, Date toTime) {
        String Url = this.Url + "Order/AccountHistory";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fromTime", String.valueOf(fromTime == null ? 0 : fromTime));
        params.put("toTime", String.valueOf(toTime == null ? 0 : toTime));
        String response = Query(params, Url);
        ArrayList<AccountRaw> result = new ArrayList<AccountRaw>();
        Gson gson = new Gson();
        AccountRaw[] accountRaws = gson.fromJson(response, AccountRaw[].class);
        result.addAll(Arrays.asList(accountRaws));
        return result;
    }
    

    public CheckoutResponse CancelOrder(double id) {
        String Url = this.Url + "Order/CancelOrder";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));
        String response = Query(params, Url);

        Gson gson = new Gson();
        CheckoutResponse checkoutResponse = gson.fromJson(response, CheckoutResponse.class);
        return checkoutResponse;
    }

    public CheckoutResponse CreateCheckout(CheckoutLinkModel data) {
        String Url = this.Url + "Merchant/CreateCheckout";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Price", String.valueOf(data.Price.toPlainString()));
        params.put("Description", String.valueOf(data.Description));
        params.put("CoinType", String.valueOf(data.CoinType.ordinal()));
        params.put("ReturnURL", String.valueOf(data.ReturnURL));
        params.put("CancelURL", String.valueOf(data.CancelURL));
        params.put("NotifyByEmail", String.valueOf(data.NotifyByEmail));
        String response = Query(params, Url);

        Gson gson = new Gson();
        CheckoutResponse checkoutResponse = gson.fromJson(response, CheckoutResponse.class);
        return checkoutResponse;
    }

}
