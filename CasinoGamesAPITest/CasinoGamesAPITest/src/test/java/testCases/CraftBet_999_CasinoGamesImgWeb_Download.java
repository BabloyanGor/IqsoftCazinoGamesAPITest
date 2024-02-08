package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;
import pageObjects.BasePage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CraftBet_999_CasinoGamesImgWeb_Download extends BaseTest {


    public CraftBet_999_CasinoGamesImgWeb_Download() throws AWTException {
    }

    public void getGamesPicturesDownload(String getGamesAPIUrl, String origin, String recurse, String partnerName)
            throws UnirestException, JSONException, IOException {

        int count = 1;
        int k = 0;
        ArrayList<String> srces = new ArrayList<>();
        ArrayList<String> gameNames = new ArrayList<>();
        ArrayList<String> gameProviderNames = new ArrayList<>();
        Set<String> gameProviderNamesSet = new HashSet<>();

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                .header("content-type", "application/json")
                .header("origin", origin)
                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                .asString();

        System.out.println("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        Unirest.shutdown();
        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
        System.out.println("Get games Api Response was captured");


        for (int j = 0; j < jsonArrayGames.length(); j++) {
            String first = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(first);
            String i = jsonObjectGame.getString("i");    // Game src
            String n = jsonObjectGame.getString("n");    //Game Name
            String sp = jsonObjectGame.getString("sp");  //Provider Name
            gameNames.add(n);
            gameProviderNames.add(sp);
            gameProviderNamesSet.add(sp);

            if (i.contains("http") && i.contains(" ")) {
                String change = i.replace(" ", "%20");
                srces.add(change);
            } else if (!i.contains("http") && !i.contains(" ")) {
                srces.add(recurse + i);
            } else if (!i.contains("http") && i.contains(" ")) {
                String change = recurse + i.replace(" ", "%20");
                srces.add(change);
            } else {
                srces.add(i);
            }
        }


        System.out.println("All captured games images was added into ArrayList");

        basePage.createFolder(System.getProperty("user.dir") + "\\" + partnerName + "IMG");
        for (String prName : gameProviderNamesSet) {                                 //Create folders for all providers
            if (!basePage.createFolder(System.getProperty("user.dir") + "\\" + partnerName + "IMG\\" + prName)) {
                System.out.println(prName + "  >>> Folder problem");
            }
            System.out.println(prName);
        }


        for (int i = 0; i < srces.size(); i++) {
//            for (int i = 0; i < 5; i++) {
            String src = srces.get(i);
            if (src == null || src.isEmpty()) {
                System.out.println(k + "   Game Provider Name = " + gameProviderNames.get(k) + "  " +
                        "Game Name = " + gameNames.get(k) + " :   " + ":   src = " + src + " :" + " this games image src has empty/null value");
            } else {
                if (src.contains("\\")) {
                    src = src.replaceAll("\\\\", "/");
                }
                String ImgName = "";
                try {
                    ImgName = src.substring(src.lastIndexOf("/") + 1);

                    String folderPath = System.getProperty("user.dir") + "\\" + partnerName + "IMG\\" + gameProviderNames.get(i);

                    String fileName = ImgName;
                    File file = new File(folderPath, fileName);
                    if (file.exists()) {
                        ImgName = basePage.generateRandomKey() + "_" + ImgName;
                    }


                    String target = System.getProperty("user.dir") + "\\" + partnerName + "IMG\\" + gameProviderNames.get(i) + "\\" + ImgName;
                    basePage.saveImage(src, target);

//                    if (gameProviderNames.get(i).equals("PlaynGo")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\PlaynGo\\" + ImgName;
////                        String target ="C:\\Users\\Nerses Khachatryan\\Desktop\\Playngo";
//                        saveImage(src, target);
//                    }


                } catch (Exception e) {

                    System.out.println(count + " " + i + "  Exception for saving Src: " + src + "   Img name: " + ImgName);
//                    BaseTest.logger.info("Exception for saving Src: " + src + "   Img name: " + ImgName);
                    count ++;
                }
            }
        }
    }

    @Test
    public void gamesImgTestDownload() throws UnirestException, IOException {
        getGamesPicturesDownload(getGamesAPIUrl, getGamesOrigin, getGamesResource, getGamesPartnerName);
//        getGamesPicturesDownload("https://websitewebapi.craftbet.com/1/api/Main/GetGames", "https://craftbet.com", "https://resources.craftbet.com/products/", BaseTest.getGamesPartnerName);
    }


}
