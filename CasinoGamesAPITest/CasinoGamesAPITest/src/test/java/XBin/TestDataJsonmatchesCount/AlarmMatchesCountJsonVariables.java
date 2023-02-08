package XBin.TestDataJsonmatchesCount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlarmMatchesCountJsonVariables {

    @SerializedName("ResponseCode")
    @Expose
    private int ResponseCode;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }


    @SerializedName("Description")
    @Expose
    private String Description;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    @SerializedName("UpcomingMatchesCount")
    @Expose
    private int UpcomingMatchesCount;

    public int getUpcomingMatchesCount() {
        return UpcomingMatchesCount;
    }

    public void setUpcomingMatchesCount(int upcomingMatchesCount) {
        UpcomingMatchesCount = upcomingMatchesCount;
    }

    @SerializedName("LifeMatchesCount")
    @Expose
    private int LifeMatchesCount;

    public int getLifeMatchesCount() {
        return LifeMatchesCount;
    }

    public void setLifeMatchesCount(int lifeMatchesCount) {
        LifeMatchesCount = lifeMatchesCount;
    }




    @SerializedName("PreMatchesCount")
    @Expose
    private int PreMatchesCount;

    public int getPreMatchesCount() {
        return PreMatchesCount;
    }

    public void setPreMatchesCount(int preMatchesCount) {
        PreMatchesCount = preMatchesCount;
    }


    @SerializedName("AlarmOn")
    @Expose
    private boolean AlarmOn;


    public boolean isAlarmOn() {
        return AlarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        AlarmOn = alarmOn;
    }


}
