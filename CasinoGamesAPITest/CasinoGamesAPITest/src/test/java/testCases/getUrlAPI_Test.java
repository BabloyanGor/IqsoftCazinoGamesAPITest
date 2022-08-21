package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;

public class getUrlAPI_Test extends BaseTest {
    String token;

    @BeforeMethod
    public void logIn() {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        craftBet_01_header_page.clickOnLogInButtonIfVisible();
        craftBet_03_login_popUp_page.loginPopUpEmailOrUsernameSendKeys(username);
        logger.info("username passed");
        craftBet_03_login_popUp_page.loginPopUpPasswordSendKeys(password);
        logger.info("password passed");
        craftBet_03_login_popUp_page.clickLoginPopUpLogInButton();
        logger.info("Log In Button was clicked");
        if (craftBet_01_header_page.userIdLabelIsEnabled()) {
            token = craftBet_01_header_page.getItem("token");
            logger.info("Token was captured " + token);
        }
    }

    @Test
    public void getUrlAPITest() throws UnirestException, JSONException, IOException {
        if (craftBet_01_header_page.getUrlCheckGamesUrl(getGamesAPIUrl, getGamesOrigin, getURLAPIUrl, token, getUsertID, partnerID, getGamesPartnerName)) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }


}