//package testCases;
//
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.testng.annotations.Test;

//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;

//public class CraftBet_005_CasinoGamesImgWeb_Download extends BaseTest {
//    public void getGamesPicturesDownload(String getGamesAPIUrl, String origin, String recurse, String partnerName)
//            throws UnirestException, JSONException, IOException {
//
//        int k = 0;
//        ArrayList<String> srces = new ArrayList<>();
//        ArrayList<String> gameNames = new ArrayList<>();
//        ArrayList<String> gameProviderNames = new ArrayList<>();
//
//        Unirest.setTimeouts(0, 0);
//        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
//                .header("content-type", "application/json")
//                .header("origin", origin)
//                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
//                .asString();
//
//        logger.info("Get games Api call was sent");
//        JSONObject jsonObjectBody = new JSONObject(response.getBody());
//        Unirest.shutdown();
//        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
//        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
//        logger.info("Get games Api Response was captured");
//
//
//        for (int j = 0; j < jsonArrayGames.length(); j++) {
//            String first = String.valueOf(jsonArrayGames.get(j));
//            JSONObject jsonObjectGame = new JSONObject(first);
//            String i = jsonObjectGame.getString("i");    // Game src
//            String n = jsonObjectGame.getString("n");    //Game Name
//            String sp = jsonObjectGame.getString("sp");  //Provider Name
//            gameNames.add(n);
//            gameProviderNames.add(sp);
//
//            if (i.contains("http") && i.contains(" ")) {
//                String change = i.replace(" ", "%20");
//                srces.add(change);
//            } else if (!i.contains("http") && !i.contains(" ")) {
//                srces.add(recurse + i);
//            } else if (!i.contains("http") && i.contains(" ")) {
//                String change = recurse + i.replace(" ", "%20");
//                srces.add(change);
//            } else {
//                srces.add(i);
//            }
//        }
//        logger.info("All captured games images was added into ArrayList");
//
//        for (int i = 0; i < srces.size(); i++) {
////            for (int i = 0; i < 5; i++) {
//            String src = srces.get(i);
//            if (src == null || src.isEmpty()) {
//
//                logger.info(k + "   Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + " :   " + ":   src = " + src + " :" + " this games image src has empty/null value");
//
//            } else {
//                String ImgName = "";
//
//                try {
//                    if (gameProviderNames.get(i).equals("Amatic")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Amatic\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("PlaysonDirect")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Playson\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("NoLimitCity")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Nolimit City\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("RedTiger")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\RedTiger\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("PragmaticPlay")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Pragmaticplay\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("Betsoft")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Betsoft\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("BoomingGames")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\booming\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("Booongo")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Booongo\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("EGT")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\EGT\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("Evolution")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Evolution\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("Evoplay")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Evoplay\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("GameArt")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Gameart\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("Habanero")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Habanero\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("Igrosoft")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Igrosoft\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("NetEnt")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Netent\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("Mascot")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Mascot Gaming\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("PlaynGo")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Playn Go\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("PushGaming")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Pushgaming\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("QuickSpin")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Quickspin\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("TomHorn")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\TomHornGaming\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("Wazdan")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Wazdan\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("WorldMatch")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Worldmatch\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("Ezugi")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Ezugi\\" + ImgName;
//                        saveImage(src, target);
//                    } else if (gameProviderNames.get(i).equals("MicroGaming")) {
//                        ImgName = src.substring(src.lastIndexOf("/") + 1);
//                        String target = System.getProperty("user.dir") + "\\New folder\\Microgaming\\" + ImgName;
//                        saveImage(src, target);
//                    }
//
//                } catch (Exception e) {
//                    logger.info("Exception for saving Src: " + src + "   Img name: " + ImgName);
//                }
//            }
//        }
//    }
//
//    @Test
//    public void gamesImgTestDownload() throws UnirestException, IOException {
//        getGamesPicturesDownload(getGamesAPIUrl, getGamesOrigin, getGamesRecurse, getGamesPartnerName);
//        System.out.println();
//    }
//
//}
