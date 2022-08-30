package testCases;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetPrematchGamesMarkets_Test extends BaseTest{


    @Test
    public void gatPreMatchGamesMarket() throws UnirestException, JSONException, IOException {
        if (getGamesPartnerName.equals("Craftbet")){
            if (getPreMatchGamesAPICheckMarkets(getPreMatchTree, getMarketByID, getPrematchTreeOrigin,getGamesPartnerName)){
                Assert.assertTrue(true);
            }
            else {
                Assert.fail();
            }
        }
        else{
            logger.error("Please provide Craftbet id  as test Partner ");
            Assert.fail();
        }

    }


}
