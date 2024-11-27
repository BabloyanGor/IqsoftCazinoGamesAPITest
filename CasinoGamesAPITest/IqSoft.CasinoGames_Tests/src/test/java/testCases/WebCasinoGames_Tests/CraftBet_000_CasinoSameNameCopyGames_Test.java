package testCases.WebCasinoGames_Tests;

import kong.unirest.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;

public class CraftBet_000_CasinoSameNameCopyGames_Test extends BaseTest {
    public CraftBet_000_CasinoSameNameCopyGames_Test() throws AWTException {
    }


    public boolean getALLGamesAPICheckCopyGames()
            throws JSONException, IOException {

//        if (partnerConfigNum < 1000){
//            getGamesInfo(false);
//        }
//        else{
//            getGamesInfoBO(false);
//        }

        boolean isPassed;
        HashSet<String> errSet = new HashSet<>();

        for (int i = 0; i < gameNames.size(); i++) {
            String name = gameNames.get(i);
            for (int j = i + 1; j < gameNames.size(); j++) {
                String x = gameNames.get(j);
                if (name.equals(x)) {
                    logger.info("Duplicate game Name =  " + gameNames.get(j));
                    errSet.add(gameNames.get(j));
                }
            }
        }
        logger.info("Duplicate games are:  " + errSet.size());

        if (errSet.size() == 0) {
            basePage.writeDataToExcel( "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + getGamesPartnerName + "BrokenData.xlsx", errSet,"CopyGames");
            isPassed = true;
        } else {
            basePage.writeDataToExcel( "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + getGamesPartnerName + "BrokenData.xlsx", errSet,"CopyGames");
            isPassed = false;
        }
        return isPassed;
    }

    @Test
    public void getDuplicateGames() throws JSONException {
        try {
            Assert.assertTrue(getALLGamesAPICheckCopyGames());
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
