package restapi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HealthCheckup {

    static  int healthCheckup(int lowerLimit, int upperLimit) throws Exception {
        // set variables
        Long totalPages=1l, page=1l;
        int total=0;
        //get request
        while(page<=totalPages) {
            URL url = new URL("https://jsonmock.hackerrank.com/api/medical_records?page="+page);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.addRequestProperty("Content-Type","application/json");
            int statusCode = httpURLConnection.getResponseCode();
            if(statusCode<200 ||statusCode>300){
                throw new Exception("Exception");
            }
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(new InputStreamReader(httpURLConnection.getInputStream()));

            if(page==1){
                totalPages=(Long)jsonResponse.get("total_pages");
            }
            JSONArray jsonArray = (JSONArray) jsonResponse.get("data");
            for(Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject vitals = (JSONObject) jsonObject.get("vitals");
                Long dia = (Long) vitals.get("bloodPressureDiastole");
                if(dia>=lowerLimit&&dia<=upperLimit) total++;
            }
            //goto next page
            page++;
        }
        return total;
    }
    public static void main(String[] args) throws Exception {
        System.out.println(healthCheckup(120,160));
    }
}
