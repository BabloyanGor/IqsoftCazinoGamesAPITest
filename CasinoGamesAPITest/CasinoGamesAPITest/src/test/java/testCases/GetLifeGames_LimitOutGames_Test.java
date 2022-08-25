package testCases;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetLifeGames_LimitOutGames_Test extends BaseTest{


    @Test
    public void gatLimitOutGames() throws UnirestException, JSONException, IOException {

        if (getLimitOutGamesApiCheck(getAllLifeGames, getGamesPartnerName)){
            Assert.assertTrue(true);
        }
        else {
            Assert.fail();
        }
    }

}
