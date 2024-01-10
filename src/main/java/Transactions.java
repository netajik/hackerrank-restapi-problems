import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class Transactions {
    public static void main(String[] args) throws IOException, ParseException {
        String[] result = getMaximumTransfer("John Oliver", "Brownlee");
        System.out.println("Credit Amount: " + result[0] + " Debit Amount: " + result[1]);
    }

    static String[] getMaximumTransfer(String userName, String city) throws IOException, ParseException {
        // set variables
        long totalPages = 1;
        int page = 1;
        double maxCredit = 0.0, maxDebt = 0.0;

        while (page <= totalPages) {
            String url = String.format("https://jsonmock.hackerrank.com/api/transactions?page=", page);
            JSONObject jsonObject = getResponse(url);

            // get total pages
            if (page == 1) {
                totalPages = (long) jsonObject.get("total_pages");
            }

            // get data
            JSONArray trans = (JSONArray) jsonObject.get("data");
            for (Object tran : trans) {
                JSONObject jsonObject1 = (JSONObject) tran;
                JSONObject location = (JSONObject) jsonObject1.get("location");
                if (jsonObject1.get("userName").equals(userName) && location.get("city").equals(city)) {
                    String amountStr = (String) jsonObject1.get("amount");
                    double amount = Double.parseDouble(amountStr.replace("$", "").replace(",", ""));
                    String transType = (String) jsonObject1.get("txnType");
                    if (transType.equals("credit")) {
                        maxCredit += amount;
                    } else {
                        maxDebt += amount;
                    }
                }
            }

            // goto next page
            page++;
        }
        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        String creditAmount = format.format(maxCredit);
        String debitAmount = format.format(maxDebt);
        return new String[] { creditAmount, debitAmount };
    }

    static JSONObject getResponse(String urlStr) throws IOException, ParseException {

        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.addRequestProperty("Content-Type", "application/json");
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(new InputStreamReader(connection.getInputStream()));
    }
}
