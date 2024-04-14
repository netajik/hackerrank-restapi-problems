package restapi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class RestaurantVotes {
    public static void main(String[] args) throws IOException, ParseException {
        int result = getVoteCount("Denver",200);
        System.out.println(result);
    }

    static int getVoteCount(String cityName, Integer estimatedCost) throws IOException, ParseException {
        //set variable
        int result=0;
        long page=1,totalpages=1;

        //handle edge case
        int edgeCaseResult = handleEdgecase(cityName);
        if(edgeCaseResult==-1) return -1;

        // make each page request
        while(page<=totalpages){
            String httpsUrl = String.format("https://jsonmock.hackerrank.com/api/food_outlets?city=%s&estimated_cost=%d",
                    cityName ,estimatedCost);
            JSONObject jsonObject = getResponse(httpsUrl);

            //get total pages
            if(page==1){
                totalpages = (long) jsonObject.get("total_pages");
            }

            // get data of restaurant
            JSONArray restaurants = (JSONArray) jsonObject.get("data");

            for(Object restaurant: restaurants){
                JSONObject restaurantObj = (JSONObject) restaurant;
                JSONObject ratings = (JSONObject) restaurantObj.get("user_rating");
                long votes = (long) ratings.get("votes");
                result+=votes;
            }

            //goto next page
            page++;
        }
        return result;
    }

    static JSONObject getResponse(String httpsUrl) throws IOException, ParseException {
        URL url = new URL(httpsUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.addRequestProperty("Content-Type","application/json");
        int status = connection.getResponseCode();
        if(status<200||status>300){
            throw new IOException("IOException");
        }
        JSONParser parser = new JSONParser();

        JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(connection.getInputStream()));
        return jsonObject;
    }

    static int handleEdgecase(String cityName) throws IOException, ParseException {
        String httpsUrl = String.format("https://jsonmock.hackerrank.com/api/food_outlets?city=%s",
                cityName);
        // get data of restaurant
        JSONObject jsonObject = getResponse(httpsUrl);
        JSONArray restaurants = (JSONArray) jsonObject.get("data");
        //System.out.println(restaurants);
        if(restaurants.size()==0){
            return -1;
        }
        return 0;
    }
}
