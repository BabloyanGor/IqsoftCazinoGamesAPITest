package testCases;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class GetCopyGames_API_Test extends BaseTest{



        @Test
        public void gatDuplicateGames() throws UnirestException, JSONException, IOException {

            if (getALLGamesAPICheckCopyGames(getGamesAPIUrl, getGamesOrigin, getGamesPartnerName)){
                Assert.assertTrue(true);
            }
            else {
                Assert.fail();
            }
        }

}
