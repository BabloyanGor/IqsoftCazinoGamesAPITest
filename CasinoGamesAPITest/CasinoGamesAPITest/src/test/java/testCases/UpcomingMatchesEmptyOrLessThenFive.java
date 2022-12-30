package testCases;

import TestDataJson.UpcomingMatchesJson;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

public class UpcomingMatchesEmptyOrLessThenFive  {
    UpcomingMatchesJson upcomingMatchesJson = new UpcomingMatchesJson();
    int upcomingMatchesCount = 0;
    int responseCod = 0;
    String description = null;

    ArrayList<Object> upcomingMatches = new ArrayList<>();
    boolean alarmOn = false;

    public int getUpcomingMatchesCount() {

        int count=0;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingmatches?LanguageId=en&TimeZone=4&SportId=1&IncludeMarketTypeIds=1&IncludeMarketTypeIds=6021&IncludeMarketTypeIds=38")
                    .header("content-type", "application/json; charset=utf-8")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .asString();
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();

            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ms");
            count = jsonArrayGames.length();
            return count;
        }
        catch (Exception e) {
            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingmatches?LanguageId=en&TimeZone=4&SportId=1&IncludeMarketTypeIds=1&IncludeMarketTypeIds=6021&IncludeMarketTypeIds=38")
                        .header("content-type", "application/json; charset=utf-8")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .asString();
                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutdown();

                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ms");
                count = jsonArrayGames.length();
                return count;
            }
            catch (Exception k) {
                System.out.println("Count Exception  " + e);
                return count;
            }
        }
    }


    @Test
    public void getDuplicateGames() {
        SoftAssert softAssert = new SoftAssert();
        upcomingMatchesCount = getUpcomingMatchesCount();

        if (upcomingMatchesCount<5){
            responseCod = 10;
            description = "Upcoming Matches count is less then 5";
            alarmOn = true;
            softAssert.fail("Games are less then " + getUpcomingMatchesCount());
        }
        else{
            responseCod = 0;
            description = "null";
            alarmOn = false;
            softAssert.assertTrue(true);
        }


        upcomingMatchesJson.setUpcomingMatchesCount(upcomingMatchesCount);
        upcomingMatchesJson.setResponseCode(responseCod);
        upcomingMatchesJson.setDescription(description);
        upcomingMatchesJson.setAlarmOn(alarmOn);



        JSONObject jsonObjectMain = new JSONObject();     // Working version
        JSONObject jsonObjectResponseObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonResponseObject = new JSONObject();
        jsonResponseObject.put("ResponseCod",upcomingMatchesJson.getResponseCode());
        jsonResponseObject.put("Description",upcomingMatchesJson.getDescription());

        jsonObjectResponseObject.put("UpcomingMatchesCount",upcomingMatchesJson.getUpcomingMatchesCount());
        jsonResponseObject.put("ResponseObject",jsonObjectResponseObject);

        jsonArray.put(jsonResponseObject);

        jsonObjectMain.put("UpcomingMatches",jsonArray);
        jsonObjectMain.put("AlarmOn",alarmOn);


        System.out.println(jsonObjectMain);

    }
}
