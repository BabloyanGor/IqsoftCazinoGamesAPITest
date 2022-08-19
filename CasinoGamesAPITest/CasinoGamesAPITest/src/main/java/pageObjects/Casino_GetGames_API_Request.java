package pageObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.openqa.selenium.WebDriver;

import java.awt.*;

public class Casino_GetGames_API_Request extends BasePage{
    private final BasePage basePage;

    public Casino_GetGames_API_Request(WebDriver driver) throws AWTException {
        super(driver);
        basePage = new BasePage(driver);
    }

    public BasePage getBasePage() {
        return basePage;
    }

    @SerializedName("PageIndex")
    @Expose
    private int PageIndex;

    @SerializedName("PageSize")
    @Expose
    private int PageSize;

    @SerializedName("WithWidget")
    @Expose
    private boolean WithWidget;

    @SerializedName("CategoryId")
    @Expose
    private int CategoryId;

    @SerializedName("ProviderIds")
    @Expose
    private int ProviderIds;

    @SerializedName("IsForMobile")
    @Expose
    private boolean IsForMobile;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("LanguageId")
    @Expose
    private String LanguageId;

    @SerializedName("Token")
    @Expose
    private String Token;

    @SerializedName("CategoryId")
    @Expose
    private int ClientId;

    @SerializedName("TimeZone")
    @Expose
    private int TimeZone;

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public boolean isWithWidget() {
        return WithWidget;
    }

    public void setWithWidget(boolean withWidget) {
        WithWidget = withWidget;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public int getProviderIds() {
        return ProviderIds;
    }

    public void setProviderIds(Integer providerIds) {
        ProviderIds = providerIds;
    }

    public boolean isForMobile() {
        return IsForMobile;
    }

    public void setForMobile(boolean forMobile) {
        IsForMobile = forMobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLanguageId() {
        return LanguageId;
    }

    public void setLanguageId(String languageId) {
        LanguageId = languageId;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int clientId) {
        ClientId = clientId;
    }

    public int getTimeZone() {
        return TimeZone;
    }

    public void setTimeZone(int timeZone) {
        TimeZone = timeZone;
    }
}
