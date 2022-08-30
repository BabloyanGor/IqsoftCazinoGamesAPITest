package testCases;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetAllGamesIMGMobile_Test extends BaseTest{


    @Test
    public void mobileGamesImgTest() throws UnirestException, JSONException, IOException {

        if (getGamesAPICheckPicturesForMobile(getGamesAPIUrl, getGamesOrigin, getGamesRecurse, getGamesPartnerName)){
            Assert.assertTrue(true);
        }
        else {
            Assert.fail();
        }
    }
}
