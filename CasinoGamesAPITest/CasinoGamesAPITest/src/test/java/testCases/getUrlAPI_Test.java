package testCases;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class getUrlAPI_Test extends BaseTest {
    String token;

    @BeforeMethod
    public void logIn() {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
        if (craftBet_01_header_page.getUrlCheckGamesUrl(getGamesAPIUrl, getGamesOrigin, getURLAPIUrl, token, getUserID, partnerID, getGamesPartnerName)) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }


}