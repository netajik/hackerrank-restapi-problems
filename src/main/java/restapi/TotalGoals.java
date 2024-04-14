package restapi;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class Result {

    public static int getTotalGoals(String team, int year) throws Exception {
        String matchUrl = "https://jsonmock.hackerrank.com/api/football_matches";
        String competitionUrl = "https://jsonmock.hackerrank.com/api/football_competitions";
        String teamUrl1 = String.format(matchUrl + "?year=%d&team1=%s", year, URLEncoder.encode(team, "UTF-8"));
        String teamUrl2 = String.format(competitionUrl + "?year=%d&team2=%s", year, URLEncoder.encode(team, "UTF-8"));
        return getTeamGoals(teamUrl1, "team1", 1, 0) + getTeamGoals(teamUrl2, "team2", 1, 0);
    }

    public static int getTeamGoals(String teamUrl, String teamType, int page, int totalGoals) throws Exception {
        String response = getResponsePerPage(teamUrl, page);

        // Parse JSON response manually
        String dataStart = "\"data\":[";
        String totalPagesKey = "\"total_pages\":";
        int dataIndex = response.indexOf(dataStart) + dataStart.length();
        int totalPagesIndex = response.indexOf(totalPagesKey) + totalPagesKey.length();

        int totalPages = Integer
                .parseInt(response.substring(totalPagesIndex, response.indexOf(",", totalPagesIndex)).trim());

        while (dataIndex < response.length()) {
            int goalIndex = response.indexOf("\"" + teamType + "goals\":", dataIndex);
            if (goalIndex == -1) {
                break;
            }

            int goalEndIndex = response.indexOf(",", goalIndex);
            totalGoals += Integer
                    .parseInt(response.substring(goalIndex + (teamType + "goals").length() + 3, goalEndIndex).trim());
            dataIndex = goalEndIndex;
        }

        return totalGoals == page ? totalGoals : getTeamGoals(teamUrl, teamType, page + 1, totalGoals);
    }

    static String getResponsePerPage(String endPoint, int page) throws Exception {
        URL url = new URL(endPoint + "&page=" + page); // Fixed typo here
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.addRequestProperty("Content-Type", "application/json");
        int status = con.getResponseCode();
        if (status < 200 || status > 300) {
            throw new IOException("Error while reading the data");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response;
        StringBuilder sb = new StringBuilder();
        while ((response = br.readLine()) != null) {
            sb.append(response);
        }

        br.close();
        con.disconnect();

        return sb.toString();
    }
}

public class TotalGoals {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String team = bufferedReader.readLine();

        int year = Integer.parseInt(bufferedReader.readLine().trim());

        int result = 0;
        try {
            result = Result.getTotalGoals(team, year);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
