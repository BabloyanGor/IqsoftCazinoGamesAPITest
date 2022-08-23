package testCases;

import org.json.JSONException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class GetAllGamesAPI_IMG_Test extends BaseTest {


    @Test
    public void gamesImgTest() throws UnirestException, JSONException, IOException {

        if (getGamesAPICheckPictures(getGamesAPIUrl, getGamesOrigin, getGamesRecurse, getGamesPartnerName)){
            Assert.assertTrue(true);
        }
        else {
            Assert.fail();
        }
    }

}






