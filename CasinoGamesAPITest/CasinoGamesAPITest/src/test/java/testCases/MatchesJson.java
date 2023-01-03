package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

public class MatchesJson {
    TestDataJson.MatchesJson matchesJson = new TestDataJson.MatchesJson();
    int upcomingMatchesCount = 0;
    int lifeMatchesCount = 0;
    int preMatchMatchesCount = 0;
    int responseCod = 0;
    String description = null;

    ArrayList<Object> upcomingMatches = new ArrayList<>();
    boolean alarmOn = false;

    public int getUpcomingMatchesCount() {

        int count = 0;
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
        } catch (Exception e) {
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
            } catch (Exception k) {
                System.out.println("Count Exception  " + e);
                return count;
            }
        }
    }


    public int getLifeMatchesCount() {

        int count = 0;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/gettoplivematches?LanguageId=en&TimeZone=4&SportId=3&IncludeMarketTypeIds=3001&IncludeMarketTypeIds=3007&IncludeMarketTypeIds=5649")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .header("content-type", "application/json")
                    .asString();
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();

            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ms");
            count = jsonArrayGames.length();
            return count;
        } catch (Exception e) {
            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/gettoplivematches?LanguageId=en&TimeZone=4&SportId=3&IncludeMarketTypeIds=3001&IncludeMarketTypeIds=3007&IncludeMarketTypeIds=5649")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .header("content-type", "application/json")
                        .asString();
                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutdown();

                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ms");
                count = jsonArrayGames.length();
                return count;
            } catch (Exception k) {
                System.out.println("Count Exception  " + e);
                return count;
            }
        }
    }


    public int getPreMatchMatchesCount() {

        int count = 0;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .header("content-type", "application/json")
                    .asString();
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();

            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ss");
            count = jsonArrayGames.length();
            return count;
        } catch (Exception e) {
            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .header("content-type", "application/json")
                        .asString();
                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutdown();

                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ss");
                count = jsonArrayGames.length();
                return count;
            } catch (Exception k) {
                System.out.println("Count Exception  " + e);
                return count;
            }
        }
    }

    @Test
    public void getMatchesCount() {

        SoftAssert softAssert = new SoftAssert();
        upcomingMatchesCount = getUpcomingMatchesCount();
        lifeMatchesCount = getLifeMatchesCount();
        preMatchMatchesCount = getPreMatchMatchesCount();

        int compareUpcomingMatchesCount = 5;
        int compareLifeMatchesCount = 1;
        int comparePreMatchSportsCount = 1;

        if (upcomingMatchesCount < compareUpcomingMatchesCount) {
            responseCod = 1000;
            description = "Upcoming Matches count is less then: " + compareUpcomingMatchesCount;
            alarmOn = true;
            softAssert.fail("Upcoming Matches count is: " + getUpcomingMatchesCount());
        } else if (lifeMatchesCount < compareLifeMatchesCount) {
            responseCod = 1001;
            description = "Life Matches count is less then: " + compareLifeMatchesCount;
            alarmOn = true;
            softAssert.fail("Life Matches count is: " + getLifeMatchesCount());
        } else if (preMatchMatchesCount < 1) {
            responseCod = 1002;
            description = "PreMatch Sports count is less then: " + comparePreMatchSportsCount;
            alarmOn = true;
            softAssert.fail("PreMatch Sports count is: " + getPreMatchMatchesCount());
        } else {
            responseCod = 0;
            description = "null";
            alarmOn = false;
            softAssert.assertTrue(true);
        }

        matchesJson.setUpcomingMatchesCount(upcomingMatchesCount);
        matchesJson.setLifeMatchesCount(lifeMatchesCount);
        matchesJson.setPreMatchesCount(preMatchMatchesCount);
        matchesJson.setResponseCode(responseCod);
        matchesJson.setDescription(description);
        matchesJson.setAlarmOn(alarmOn);

        JSONObject jsonObjectMain = new JSONObject();     // Working version
        JSONObject jsonObjectResponseObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();

        JSONObject jsonResponseObject = new JSONObject();

        jsonObjectResponseObject.put("UpcomingMatchesCount", matchesJson.getUpcomingMatchesCount());
        jsonObjectResponseObject.put("LifeMatchesCount", matchesJson.getLifeMatchesCount());
        jsonObjectResponseObject.put("PreMatchesCount", matchesJson.getPreMatchesCount());

        jsonResponseObject.put("ResponseCod", matchesJson.getResponseCode());
        jsonResponseObject.put("Description", matchesJson.getDescription());
        jsonResponseObject.put("ResponseObject", jsonObjectResponseObject);

//        jsonArray.put(jsonResponseObject);
//        jsonObjectMain.put("UpcomingMatches",jsonArray);
        jsonObjectMain.put("AlarmOn", alarmOn);
        jsonObjectMain.put("MatchesCount", jsonResponseObject);

        System.out.println(jsonObjectMain);

    }
}
