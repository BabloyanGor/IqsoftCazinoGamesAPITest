package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.*;
import pageObjects.*;
import utilities.DriverFactory;
import utilities.ReadConfig;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static org.apache.poi.ss.usermodel.DateUtil.SECONDS_PER_MINUTE;


public class BaseTest extends DriverFactory {

    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    public static Logger logger;
    public int partnerID;
    public String getGamesAPIUrl;
    public String getURLAPIUrl;
    public int getUserID;
    public String getGamesOrigin;
    public String getGamesRecurse;
    public String getGamesPartnerName;
    public String getGamesBaseURL;
    public String getAllLifeGames;
    public CraftBet_01_Header_Page craftBet_01_header_page;
    public CraftBet_03_Login_PopUp_Page craftBet_03_login_popUp_page;
    ReadConfig readConfig = new ReadConfig();
    public String isHeadless = readConfig.isHeadless();
    public String browser = readConfig.getBrowser();
    public String username = readConfig.getUsername();
    public String password = readConfig.getPassword();
    public String language = readConfig.getLanguage();
    public int DimensionHeight = readConfig.getDimensionHeight();
    //region <Page Class Instances  >
    public int DimensionWidth = readConfig.getDimensionWidth();
    public int partnerConfigNum = readConfig.partnerConfigNum();

    //endregion
    public BaseTest() {
    }


    private static Period getPeriod(LocalDateTime dob, LocalDateTime now) {
        return Period.between(dob.toLocalDate(), now.toLocalDate());
    }

    private static long[] getTime(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long secs = (seconds % SECONDS_PER_MINUTE);

        return new long[]{hours, minutes, secs};
    }


    public boolean getGamesAPICheckPictures(String getGamesAPIUrl, String origin, String recurse, String partnerName)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 0;
        ArrayList<String> srces = new ArrayList<>();
        ArrayList<String> gameNames = new ArrayList<>();
        ArrayList<String> gameProviderNames = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                .header("content-type", "application/json")
                .header("origin", origin)
                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                .asString();
        logger.info("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.getString("ResponseObject"));
        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
        logger.info("Get games Api Response was captured");

        for (int j = 0; j < jsonArrayGames.length(); j++) {

            String first = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(first);
            String i = jsonObjectGame.getString("i");    // Game src
            String n = jsonObjectGame.getString("n");    //Game Name
            String sp = jsonObjectGame.getString("sp");  //Provider Name
            gameNames.add(n);
            gameProviderNames.add(sp);

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
        logger.info("All captured games images was added into ArrayList");
        for (String src : srces) {
            if (src == null || src.isEmpty()) {
                logger.info(k + "   Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + " :   " + ":   src = " + src + " :" + " this games image src has empty/null value");
                errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + "  " + ":   src = " + src + " " + " ----->  this games image src has empty/null value");
            } else {
                int cod;
                try {
                    URL img = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) img.openConnection();
                    connection.connect();
                    cod = connection.getResponseCode();

                    if (cod >= 400) {
                        logger.error(k + "   Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name =  " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
                        errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "cod = " + cod + "   src = " + src);
                    }
                } catch (Exception e) {
                    try {
                        URL img = new URL(src);
                        HttpURLConnection connection = (HttpURLConnection) img.openConnection();
                        connection.connect();
                        cod = connection.getResponseCode();

                        if (cod >= 400) {
                            logger.error(k + "  Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name =  " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
                            errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "cod = " + cod + "   src = " + src);
                        }
                    } catch (Exception ex) {
                        logger.error(k + " Game Provider Name = " + gameProviderNames.get(k) + " :                    " + "Game Name = " + gameNames.get(k) + " :                    " + "   src = " + src + "         " + e);
                        errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "src = " + src);
                    }
                }
            }
            k++;
        }
        logger.info("Broken images are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {
            String target = System.getProperty("user.dir") + "/src/test/java/APICasinoGamesCasinoImagesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "DataBrokenIMGList.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream file = new FileOutputStream(target);
            XSSFSheet sheet = workbook.createSheet("brokenIMG");
            sheet.setColumnWidth(0, 20000);
            int l = 0;
            for (String err : errorSrcXl) {
                XSSFRow row = sheet.createRow(l);
                row.createCell(0).setCellValue(err);
                l++;
            }
            workbook.write(file);
            workbook.close();
            isPassed = false;
        }
        return isPassed;
    }

    public boolean getALLGamesAPICheckCopyGames(String getGamesAPIUrl, String origin, String partnerName)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 1;
        ArrayList<String> gameNames = new ArrayList<>();
        ArrayList<String> gameProviderNames = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                .header("content-type", "application/json")
                .header("origin", origin)
                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                .asString();
        logger.info("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.getString("ResponseObject"));
        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
        logger.info("Get games Api Response was captured");

        for (int j = 0; j < jsonArrayGames.length(); j++) {

            String first = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(first);
            String n = jsonObjectGame.getString("n");    //Game Name
            String sp = jsonObjectGame.getString("sp");  //Provider Name
            gameProviderNames.add(sp);
            gameNames.add(n);
        }

        for (int i = 0; i < gameNames.size(); i++) {
            String name = gameNames.get(i);
            for (int j = i + 1; j < gameNames.size(); j++) {
                String x = gameNames.get(j);
                if (name.equals(x)) {
                    logger.info("Dupliicate game Name =  " + gameNames.get(j));
                    errorSrcXl.add(k + "  This game has duplicate Please check it :  Name =  " + gameNames.get(j));
                    k++;
                }
            }
        }


        logger.info("Duplicate games are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {
            String target = System.getProperty("user.dir") + "/src/test/java/APICasinoGamesCasinoImagesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "DataDuplicateGamesList.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream file = new FileOutputStream(target);
            XSSFSheet sheet = workbook.createSheet("brokenIMG");
            sheet.setColumnWidth(0, 20000);
            int l = 0;
            for (String err : errorSrcXl) {
                XSSFRow row = sheet.createRow(l);
                row.createCell(0).setCellValue(err);
                l++;
            }
            workbook.write(file);
            workbook.close();
            isPassed = false;
        }
        return isPassed;
    }


    public boolean getLimitOutGamesApiCheck(String getGamesAPIUrl, String partnerName)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 1;
        ArrayList<String> gameID = new ArrayList<>();
        ArrayList<String> leagueType = new ArrayList<>();
        ArrayList<String> gameType = new ArrayList<>();
        ArrayList<String> gameStartTime = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();

        ArrayList<String> soccerGameStartTime = new ArrayList<>();

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(getAllLifeGames).asString();


        logger.info("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONArray jsonArrayGames = jsonObjectBody.getJSONArray("Ms");

        System.out.println("Games are " + jsonArrayGames.length());

        logger.info("Get Life games Api Response was captured");

        for (int j = 0; j < jsonArrayGames.length(); j++) {

            String first = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(first);
            String MI = jsonObjectGame.getString("MI");  // Game ID
            String CN = jsonObjectGame.getString("CN");  //League Name
            String SN = jsonObjectGame.getString("SN");    // Game Type
            String ST = jsonObjectGame.getString("ST");    //Game start Time

            gameID.add(MI);
            leagueType.add(CN);
            gameType.add(SN);
            gameStartTime.add(ST);
            k++;
        }
        for (int i = 0; i < gameType.size(); i++) {

            String timeStart = gameStartTime.get(i);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime much = LocalDateTime.parse(timeStart);
            Period period = getPeriod(now, much);
            long time[] = getTime(now, much);
//                System.out.println(period.getYears() + " years " +
//                        period.getMonths() + " months " +
//                        period.getDays() + " days " +
//                        time[0] + " hours " +
//                        time[1] + " minutes " +
//                        time[2] + " seconds." + gameID.get(i) + "   " + gameStartTime.get(i));
            String hoursString = String.valueOf(time[0]);
            String daysString = String.valueOf(period.getDays());
            String monthsString = String.valueOf(period.getMonths());
            String yearsString = String.valueOf(period.getYears());

            String errMessageHours = gameID.get(i) + "   " + leagueType.get(i) + "   " + gameType.get(i) + "   " + gameStartTime.get(i) + "   After game start tim out hours are :  " + time[0];
            String errMessageDayMonthYear = gameID.get(i) + "   " + leagueType.get(i) + "   " + gameType.get(i) + "   " + gameStartTime.get(i) + "   " + daysString + " / " + monthsString + " / " + yearsString;


            int hour;
            try {
                if (hoursString.contains("-")) {
                    hour = Integer.parseInt(hoursString.substring(1));
                } else {
                    hour = Integer.parseInt(hoursString);
                }
            } catch (Exception e) {
                logger.info("Hours cant be parseInt Exception  " + time[0]);
                hour = 0;
            }
            //region <Soccer>>
            if (gameType.get(i).equals("Soccer")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 3) {
                        logger.error("The Time Out games are :   " + gameID.get(i) + "   " + leagueType.get(i) + "   " + gameType.get(i) + "   " + gameStartTime.get(i) + "   Time from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + gameID.get(i) + "   " + leagueType.get(i) + "   " + gameType.get(i) + "   " + gameStartTime.get(i) + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <Basketball>>
            else if (gameType.get(i).equals("Basketball")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 2) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <Tennis>
            else if (gameType.get(i).equals("Tennis")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 2) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }


            //endregion
            //region <Volleyball>
            else if (gameType.get(i).equals("Volleyball")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 2) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }

            //endregion
            //region <Ice Hockey>
            else if (gameType.get(i).equals("Ice Hockey")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 4) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <Table Tennis>
            else if (gameType.get(i).equals("Table Tennis")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 4) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <Handball>
            else if (gameType.get(i).equals("Handball")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 4) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <E-sports>
            else if (gameType.get(i).equals("E-sports")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 4) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <Badminton>
            else if (gameType.get(i).equals("Badminton")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 4) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <Darts>
            else if (gameType.get(i).equals("Darts")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 4) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <Cricket>
            else if (gameType.get(i).equals("Cricket")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 4) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <Horse Racing>
            else if (gameType.get(i).equals("Horse Racing")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 4) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <Table nE-Football>
            else if (gameType.get(i).contains("nE-Football")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 4) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
            //region <Snooker>
            else if (gameType.get(i).equals("Snooker")) {
                if (daysString.contains("0") && monthsString.contains("0") && yearsString.contains("0")) {
                    if (hour >= 4) {
                        logger.error("The Time Out games are :   " + errMessageHours + "   Hours from Game Start   " + time[0]);
                        errorSrcXl.add(errMessageHours);
                    }
                } else {
                    logger.error("The Time Out games are :   " + errMessageDayMonthYear + "   Time from Game Start   " + daysString + " / " + monthsString + " / " + yearsString);
                    errorSrcXl.add(errMessageDayMonthYear);
                }
            }
            //endregion
        }

        logger.info("Time out Games are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {
            String target = System.getProperty("user.dir") + "/src/test/java/timeOutGames/" + readConfig.partnerConfigNum() + partnerName + "DataBrokenIMGList.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream file = new FileOutputStream(target);
            XSSFSheet sheet = workbook.createSheet("TimeOutGames");
            sheet.setColumnWidth(0, 20000);
            int l = 0;
            for (String err : errorSrcXl) {
                XSSFRow row = sheet.createRow(l);
                row.createCell(0).setCellValue(err);
                l++;
            }
            workbook.write(file);
            workbook.close();
            isPassed = false;
        }
        return isPassed;
    }


    public boolean getUrlCheckGamesUrl(String getGamesAPIUrl, String origin, String getURLAPIurl, String token,
                                       int userID, int partnerID, String partnerName)
            throws JSONException, IOException, UnirestException {
        boolean isPassed;
        ArrayList<String> productID = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> provider = new ArrayList<>();
        ArrayList<String> brokenURL = new ArrayList<>();

        int errCount = 1;
        int k = 0;

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                .header("content-type", "application/json")
                .header("origin", origin)
                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                .asString();
        logger.info("Get All games API call ");

        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.getString("ResponseObject"));
        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
        logger.info("From Get All games API call body captured ");


        for (int j = 0; j < jsonArrayGames.length(); j++) {
            String first = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(first);
            String p = jsonObjectGame.getString("p");
            String n = jsonObjectGame.getString("n");
            String sp = jsonObjectGame.getString("sp");
            productID.add(p);
            name.add(n);
            provider.add(sp);
        }
        logger.info("From Get All games API productIDes and Names was captured ");

        for (String id : productID) {
            int proID = parseInt(id);

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> responseUrl = Unirest.post(getURLAPIurl)
                    .header("Content-Type", "application/json")
                    .header("origin", origin)
                    .body("{\"Loader\":true,\"PartnerId\":" + partnerID + ",\"TimeZone\":4,\"LanguageId\":\"en\",\"ProductId\":" + proID + ",\"Method\":\"GetProductUrl\",\"Controller\":\"Main\",\"CategoryId\":null,\"PageIndex\":0,\"PageSize\":100,\"ProviderIds\":[],\"Index\":null,\"ActivationKey\":null,\"MobileNumber\":null,\"Code\":null,\"RequestData\":\"{}\",\"IsForDemo\":false,\"IsForMobile\":false,\"Position\":\"\",\"DeviceType\":1,\"ClientId\":" + userID + ",\"Token\":\"" + token + "\"}")
                    .asString();


            JSONObject jsonObjectGetUrl = new JSONObject(responseUrl.getBody());

            String code = jsonObjectGetUrl.getString("ResponseCode");
            String description = jsonObjectGetUrl.getString("Description");
            String url = jsonObjectGetUrl.getString("ResponseObject");
            String s;
            try {
                if (!code.equals("0") || url == null || url.length() < 10) {
                    s = errCount + " " + k + " ID=" + id + " Provider=" + provider.get(k) + " Name=" + name.get(k) + " cod=" + code + " description=" + description + " ResponseObject=" + url;
                    logger.info(s);
                    brokenURL.add(s);
                    errCount++;
                }
            } catch (Exception e) {
                logger.info(k + " Unirest Exception ");
            }
            k++;
        }
        logger.info("From Get URL API broken games are captured");

        logger.info("Broken url-es are:  " + brokenURL.size());
        if (brokenURL.size() == 0) {
            logger.info("Broken Games are null");
            isPassed = true;

        } else {
            String target = System.getProperty("user.dir") + "/src/test/java/APICasinoGamesCasinoImagesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "DataBrokenURLList.xlsx";
            FileOutputStream file = new FileOutputStream(target);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("brokenURL");
            sheet.setColumnWidth(0, 20000);
            int l = 0;
            for (String err : brokenURL) {
                XSSFRow row = sheet.createRow(l);
                row.createCell(0).setCellValue(err);
                l++;
            }
            workbook.write(file);
            workbook.close();
            isPassed = false;
        }
        return isPassed;
    }

    @BeforeMethod
    public void setup() throws InterruptedException {
        logger = Logger.getLogger("craftBet");
        PropertyConfigurator.configure("Log4j.properties");

        switch (partnerConfigNum) {
            case 1: {
                partnerID = 1;
                getGamesAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetProductUrl";
                getUserID = 1630845;
                getGamesOrigin = "https://craftbet.com";
                getGamesRecurse = "https://resources.craftbet.com/products/";
                getGamesPartnerName = "Craftbet";
                getGamesBaseURL = "https://craftbet.com";


                getAllLifeGames = "https://sportsbookwebsitewebapi.craftbet.com/website/getlivematchesoverview?LanguageId=en&TimeZone=4&origin=https://sportsbookwebsite.craftbet.com";
                break;
            }
            case 2: {
                partnerID = 56;
                getGamesAPIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetProductUrl";
                getUserID = 1650272;
                getGamesOrigin = "https://pokies2go.io";
                getGamesRecurse = "https://resources.pokies2go.io/products/";
                getGamesPartnerName = "Pokies2go";
                getGamesBaseURL = "https://pokies2go.io";
                break;
            }

            case 3: {
                partnerID = 59;
                getGamesAPIUrl = "https://websitewebapi.tigerbet001.com/59/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.tigerbet001.com/59/api/Main/GetProductUrl";
                getUserID = 1650824;
                getGamesOrigin = "https://tigerbet001.com";
                getGamesRecurse = "https://resources.tigerbet001.com/products/";
                getGamesPartnerName = "tigerbet001";
                getGamesBaseURL = "https://tigerbet001.com";
                break;
            }

            case 4: {
                partnerID = 52;
                getGamesAPIUrl = "https://websitewebapi.graciazz.com/52/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.graciazz.com/52/api/Main/GetProductUrl";
                getUserID = 1650804;
                getGamesOrigin = "https://graciazz.com";
                getGamesRecurse = "https://resources.graciazz.com/products/";
                getGamesPartnerName = "graciazz";
                getGamesBaseURL = "https://graciazz.com";
                break;
            }

            case 5: {
                partnerID = 47;
                getGamesAPIUrl = "https://websitewebapi.crypto-casino.games/47/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.crypto-casino.games/47/api/Main/GetProductUrl";
                getUserID = 1650805;
                getGamesOrigin = "https://crypto-casino.games";
                getGamesRecurse = "https://resources.crypto-casino.games/products/";
                getGamesPartnerName = "cryptoCasino";
                getGamesBaseURL = "https://crypto-casino.games";
                break;
            }

            case 6: {
                partnerID = 48;
                getGamesAPIUrl = "https://websitewebapi.betvito.com/48/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.betvito.com/48/api/Main/GetProductUrl";
                getUserID = 1650817;
                getGamesOrigin = "https://betvito.com";
                getGamesRecurse = "https://resources.https://betvito.com/products/";
                getGamesPartnerName = "Betvito";
                getGamesBaseURL = "https://betvito.com";
                break;
            }
            case 7: {
                partnerID = 41;
                getGamesAPIUrl = "https://websitewebapi.vikwin.com/41/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.vikwin.com/41/api/Main/GetProductUrl";
                getUserID = 1650818;
                getGamesOrigin = "https://vikwin.com";
                getGamesRecurse = "https://resources.https://vikwin.com/products/";
                getGamesPartnerName = "Vikwin";
                getGamesBaseURL = "https://vikwin.com";
                break;
            }
            case 8: {
                partnerID = 38;
                getGamesAPIUrl = "https://websitewebapi.bravowin.com/38/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.bravowin.com/38/api/Main/GetProductUrl";
                getUserID = 1650819;
                getGamesOrigin = "https://bravowin.com";
                getGamesRecurse = "https://resources.bravowin.com/products/";
                getGamesPartnerName = "Bravowin";
                getGamesBaseURL = "https://bravowin.com";
                break;
            }
            case 9: {
                partnerID = 54;
                getGamesAPIUrl = "https://websitewebapi.tetherbet.io/54/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.tetherbet.io/54/api/Main/GetProductUrl";
                getUserID = 1650822;
                getGamesOrigin = "https://tetherbet.io";
                getGamesRecurse = "https://resources.tetherbet.io/products/";
                getGamesPartnerName = "Tetherbet";
                getGamesBaseURL = "https://tetherbet.io";
                break;
            }

            case 10: {
                partnerID = 45;
                getGamesAPIUrl = "https://websitewebapi.bet2win.vip/45/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.bet2win.vip/45/api/Main/GetProductUrl";
                getUserID = 1650845;
                getGamesOrigin = "https://bet2win.vip";
                getGamesRecurse = "https://resources.bet2win.vip/products/";
                getGamesPartnerName = "bet2win";
                getGamesBaseURL = "https://bet2win.vip";
                break;
            }


            case 100: {
                partnerID = 1;
                getGamesAPIUrl = "https://websitewebapi.exclusivebet-stage.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.exclusivebet-stage.com/1/api/Main/GetProductUrl";
                getUserID = 2;
                getGamesOrigin = "https://exclusivebet-stage.com";
                getGamesRecurse = "https://resources.exclusivebet-stage.com/products/";
                getGamesPartnerName = "Exclusivebet";
                getGamesBaseURL = "https://exclusivebet-stage.com";
                break;
            }


            default: {
                logger.error("Wrong Partner ID: From config.properties file insert the right PartnerID Please");
                throw new SkipException("From config.properties file choose the right PartnerID Please");
            }
        }


        try {
            super.initDriver(getGamesBaseURL, browser, isHeadless);
            logger.info("URL: " + getGamesBaseURL + "  Browser: " + browser);
            Dimension d = new Dimension(DimensionWidth, DimensionHeight);  //
            if (DimensionWidth > 1250 && DimensionHeight > 750) {
                driver.manage().window().setSize(d);
                logger.info("Window size is: " + DimensionWidth + " x " + DimensionHeight);
            }
        } catch (org.openqa.selenium.TimeoutException exception) {
            super.initDriver(getGamesBaseURL, browser, isHeadless);

        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //region <Page Class Instance Initialization >
        craftBet_01_header_page = PageFactory.initElements(driver, CraftBet_01_Header_Page.class);
        craftBet_03_login_popUp_page = PageFactory.initElements(driver, CraftBet_03_Login_PopUp_Page.class);

        logger.info("All Page elements are initialized");

        //endregion

        craftBet_01_header_page.setItem("lang", language);
        craftBet_01_header_page.navigateRefresh();
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Test started ");
    }

    @AfterMethod
    public void tearDown() {
        try {
            driver.quit();
            logger.info("Browser closed");
        } catch (Exception exception) {
            driver.quit();
            logger.info("Browser close_order has an exception");
        }
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Test finished ");
    }

    @AfterSuite
    public void Finish() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Test Suite finished  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  ");
    }
}
