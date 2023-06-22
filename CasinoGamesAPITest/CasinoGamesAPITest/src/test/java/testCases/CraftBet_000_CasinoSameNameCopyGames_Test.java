package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class CraftBet_000_CasinoSameNameCopyGames_Test extends BaseTest {
    public CraftBet_000_CasinoSameNameCopyGames_Test() throws AWTException {
    }


    public boolean getALLGamesAPICheckCopyGames(String getGamesAPIUrl, String origin, String partnerName)
            throws JSONException, IOException {

        boolean isPassed;
        int k = 1;
        ArrayList<String> gameNames = new ArrayList<>();
        ArrayList<String> gameProviderNames = new ArrayList<>();
//        ArrayList<String> gameProviderNamesList = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();
        HashSet<String> errSet = new HashSet<String>();

        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                    .header("content-type", "application/json")
                    .header("origin", origin)
                    .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                    .asString();
            logger.info("Get games Api call was sent");
            JSONObject jsonObjectBody = new JSONObject(response.getBody());

            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
            logger.info("Get games Api Response was captured");
            for (int j = 0; j < jsonArrayGames.length(); j++) {

                String first = String.valueOf(jsonArrayGames.get(j));
                JSONObject jsonObjectGame = new JSONObject(first);
                String n = jsonObjectGame.getString("n");    //Game Name
                String sp = jsonObjectGame.getString("sp");  //Provider Name
                gameProviderNames.add(sp);
                gameNames.add(n);
//            if (!gameProviderNamesList.contains(sp)){                     For Providers List
//                gameProviderNamesList.add(sp);
//            }
            }
        } catch (Exception e) {
            logger.info("Get games Api Response has an Exception: " + e);
        } finally {
            Unirest.shutdown();
        }


        for (int i = 0; i < gameNames.size(); i++) {
            String name = gameNames.get(i);
            for (int j = i + 1; j < gameNames.size(); j++) {
                String x = gameNames.get(j);
//                if (name.contains(x)) {
                if (name.equals(x)) {
                    logger.info("Duplicate game Name =  " + gameNames.get(j));
//                    errorSrcXl.add(k + "  This game has duplicate Please check it :  Name =  " + gameNames.get(j));
//                    errSet.add(k + "  This game has duplicate Please check it :  Name =  " + gameNames.get(j));
                    errSet.add(gameNames.get(j));
                    k++;
                }
            }
        }
//        logger.info("Duplicate games are:  " + errorSrcXl.size());
        logger.info("Duplicate games are:  " + errSet.size());
//        if (errorSrcXl.size() == 0) {
        if (errSet.size() == 0) {
//            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "CopyGames");
            basePage.writeInExelSet(errSet, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "CopyGames");

            isPassed = true;
        } else {
//            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "CopyGames");
            basePage.writeInExelSet(errSet, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "CopyGames");
            isPassed = false;
        }
        // writeInExel(gameProviderNamesList, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "ProvidersList.xlsx", "Providers");
        // For Providers List
        return isPassed;
    }

    @Test
    public void getDuplicateGames() throws JSONException {
        try {
            Assert.assertTrue(getALLGamesAPICheckCopyGames(getGamesAPIUrl, getGamesOrigin, getGamesPartnerName));
        } catch (Exception e) {
            System.out.println("getDuplicateGamesTest has an exception" + e);
            Assert.fail();
        }
    }


    // For checking games name Japan translation


//    public void casinoGamesNamesTranslationCheck(String getGamesAPIUrl, String origin, String partnerName)
//            throws UnirestException, JSONException, IOException {
//        ArrayList<String> casinoGameNames = new ArrayList<>();
//        ArrayList<String> errorSrcXl = new ArrayList<>();
//        ArrayList<String> errorSrcXl2 = new ArrayList<>();
//        Unirest.setTimeouts(0, 0);
//        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
//                .header("content-type", "application/json")
//                .header("origin", origin)
//                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"ja\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
//                .asString();
//        logger.info("Get games Api call was sent");
//        JSONObject jsonObjectBody = new JSONObject(response.getBody());
//        Unirest.shutdown();
//        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
//        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
//        logger.info("Get games Api Response was captured");
//
//        for (int j = 0; j < jsonArrayGames.length(); j++) {
//
//            String first = String.valueOf(jsonArrayGames.get(j));
//            JSONObject jsonObjectGame = new JSONObject(first);
//            String n = jsonObjectGame.getString("n");    //Game Name
//            String sp = jsonObjectGame.getString("sp");  //Provider Name
//            casinoGameNames.add(n);
//            if (basePage.regexCheck1("^[^A-Za-z]+$",n)){
////                if (regexCheck("[a-zA-Z. .,!&'™:\n0-9]{1,300}",n)){
//                    errorSrcXl.add(n);
//            }
//            else{
//                errorSrcXl2.add(n);
//            }
//
//        }
//        System.out.println("Games with japan " + errorSrcXl.size());
//        System.out.println("Games withOut japan " + errorSrcXl2.size());
//        if (errorSrcXl.size() == 0) {
//            System.out.println("");
//        } else {
//            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "GamesJapanTranslation.xlsx", "Games");
//        }
//        if (errorSrcXl2.size() == 0) {
//            System.out.println("");
//        } else {
//            basePage.writeInExel(errorSrcXl2, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "GamesNotTranslated.xlsx", "Games");
//        }
//
//    }


    // For Proxy connection


//    public static void main(String[] args) throws UnknownHostException, SocketException {
//        InetAddress ip =  InetAddress.getLocalHost();
//
//        Enumeration e = NetworkInterface.getNetworkInterfaces();
//        while (e.hasMoreElements()){
//            NetworkInterface n = (NetworkInterface) e.nextElement();
//            Enumeration ee = n.getInetAddresses();
//            while (ee.hasMoreElements()){
//                InetAddress i = (InetAddress) ee.nextElement();
//                System.out.println("IPS " + i);
//            }
//        }
////        System.out.println("Ip: " + ip);
////        System.out.println("Ips: " + e);
//
//            List<String> proxyList = new ArrayList<>(); // list of proxy IP addresses
//            proxyList.add("192.168.1.1");
//            proxyList.add("192.168.1.2");
//            proxyList.add("192.168.1.3");
//            proxyList.add("192.168.1.4");
//            proxyList.add("192.168.1.5");
//            proxyList.add("192.168.1.6");
//            proxyList.add("192.168.1.7");
//            proxyList.add("192.168.1.8");
//            proxyList.add("192.168.1.9");
//            proxyList.add("192.168.1.10");
//
//            for (String proxyAddress : proxyList) {
//                try {
//                    HttpResponse<String> response = Unirest.get("https://your-api-endpoint.com")
//                            .header("X-Mashape-Key", "your-mashape-key")
//                            .header("Accept", "application/json")
//                            .proxy(proxyAddress, 8080) // set the proxy address for this request
//                            .asString();;
//
//                    System.out.println("Response status: " + response.getStatus());
//                    System.out.println("Response body: " + response.getBody());
//                } catch (UnirestException eee) {
//                    eee.printStackTrace();
//                }
//            }
//        }
//

}
