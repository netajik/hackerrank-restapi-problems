package restapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Most Active Authors
 *
 * API URL: https://jsonmock.hackerrank.com/api/article_users?page=<pageNumber>
 * threshold: integer that represents the threshold value for the number of submission count
 * The function should return an array of strings that represent the usernames
 * of users whose submission count is greater than the given threshold. 
 * The usernames in the array must be ordered in the
 * order they appear in the API response
 *
 */

public class MostActiveAuthors {
    public static void main(String[] args) throws IOException {
        int threshold = 10;
        List<String> users = null;
        try {
            users = getUserNames(threshold);
        } catch (MalformedURLException | ParseException e) {
            e.printStackTrace();
        }
        if(users !=null)
            users.stream().forEach(System.out::println);
    }

    static List<String> getUserNames(int threshold) throws IOException, ParseException {
        // set variables
        List<String> users = new ArrayList<>();
        Long page = 1l, totalPages = 1l;

        while (page <= totalPages) {
            // make request for each page
            URL url = new URL("https://jsonmock.hackerrank.com/api/article_users?page=" + page);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Content-Type", "application/json");
            int status = connection.getResponseCode();
            if (status < 200 || status > 300) {
                throw new IOException("Exception");
            }
            JSONParser parser = new JSONParser();
            JSONObject responseObj = (JSONObject) parser.parse(new InputStreamReader(connection.getInputStream()));

            //set total pages
            if(page==1){
                totalPages = (Long)responseObj.get("total_pages");
            }

            //get data for each user
            JSONArray articals = (JSONArray) responseObj.get("data");
            for(Object artical:articals){
                JSONObject articalObj = (JSONObject) artical;
                Long submissinCount = (Long) articalObj.get("submission_count");
                String userName = (String) articalObj.get("username");
                if(submissinCount>threshold){
                    users.add(userName);
                }
            }

            //goto next page
            page++;
        }
        return users;
    }
}