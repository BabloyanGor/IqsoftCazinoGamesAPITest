package TestDataJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpcomingMatchesJson {

//    @SerializedName("ResponseCode")
//    @Expose
    private int ResponseCode;

    public int getResponseCode() {
        return ResponseCode;
    }
    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }



//    @SerializedName("Description")
//    @Expose
    private String Description;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }



//    @SerializedName("UpcomingMatchesCount")
//    @Expose
    private int UpcomingMatchesCount;


    public int getUpcomingMatchesCount() {
        return UpcomingMatchesCount;
    }

    public void setUpcomingMatchesCount(int upcomingMatchesCount) {
        UpcomingMatchesCount = upcomingMatchesCount;
    }



//    @SerializedName("AlarmOn")
//    @Expose
    private boolean AlarmOn;


    public boolean isAlarmOn() {
        return AlarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        AlarmOn = alarmOn;
    }




}
