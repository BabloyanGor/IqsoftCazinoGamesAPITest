package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.awt.*;

public class CraftBet_01_Header_Page extends BasePage {
    private final BasePage basePage;

    public CraftBet_01_Header_Page(WebDriver driver) throws AWTException {
        super(driver);
        basePage = new BasePage(driver);
    }

    @FindBy(xpath = "//img[@class='logo_img']")
    @CacheLookup
    WebElement logo;

    public void clickOnLogo() {
        try {
            basePage.waitElementToBeVisible(logo);
            basePage.javaScriptClick(logo);
        } catch (Exception e) {
        }
    }
    @FindBy(xpath = "//*[@class='user_id']/span[1]")
    @CacheLookup
    WebElement UserIdLabel;

    public boolean userIdLabelIsEnabled() {
        try{
            basePage.waitElementToBeVisible(UserIdLabel);
            return basePage.elementIsEnable(UserIdLabel);
        }
        catch (Exception e){
            return  false;
        }
    }
    public boolean userIdLabelIsDisplayed() {
        try{
            return basePage.elementIsDisplayed(UserIdLabel);
        }
        catch (Exception e){
            return false;
        }
    }
    public String getTextUserIdLabel() {
        try{
            basePage.waitElementToBeVisible(UserIdLabel);
            return basePage.getText(UserIdLabel);
        }
        catch (Exception e){
            return  "Cant find webElement UserIdLabel ";
        }
    }

    @FindBy(xpath = "//*[@class='user_id']/span[2]")
    @CacheLookup
    WebElement UserId;
    public boolean userIdIsEnabled() {
        try{
            basePage.waitElementToBeVisible(UserId);
            return basePage.elementIsEnable(UserId);
        }
        catch (Exception e){
            return  false;
        }
    }
    public String getTextUserId() {
        try{
            basePage.waitElementToBeVisible(UserId);
            return basePage.getText(UserId);
        }
        catch (Exception e){
            return  "Cant find webElement UserId ";
        }
    }
    @FindBy(xpath = "//i[@class= 'icon icon-message']")
    @CacheLookup
    WebElement messageIcon;
    public void clickOnMessageIconIfVisible() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(messageIcon);
                basePage.javaScriptClick(messageIcon);
                basePage.waitAction(200);
            } catch (Exception e) {
            }
        }
    }
    @FindBy(xpath = "//*[@class = 'dropdown-style1-type-Dpd']")
    @CacheLookup
    WebElement languageDropDown;
    @FindBy(xpath = "//span[text()= 'English']")
    @CacheLookup
    WebElement EnglishLanguageDropDown;

    @FindBy(xpath = "//span[text()= 'Русский']")
    @CacheLookup
    WebElement RussianLanguageDropDown;

    @FindBy(xpath = "//span[text()= 'Հայերեն']")
    @CacheLookup
    WebElement ArmenianLanguageDropDown;

    public void selectEnglishLanguageFromDropDown() {
        try{
            clickOnElementIfVisible(languageDropDown);
            clickOnElementIfVisible(EnglishLanguageDropDown);
        }
        catch (Exception e){
        }
    }
    public void selectRussianLanguageFromDropDown() {
        try{
            clickOnElementIfVisible(languageDropDown);
            clickOnElementIfVisible(RussianLanguageDropDown);
        }
        catch (Exception e){
        }
    }
    public void selectArmenianLanguageFromDropDown() {
        try{
            clickOnElementIfVisible(languageDropDown);
            clickOnElementIfVisible(ArmenianLanguageDropDown);
        }
        catch (Exception e){
        }
    }
    @FindBy(xpath = "//button[@class = 'button-style2-type-btn global_login-btn pointer']")
    @CacheLookup
    WebElement logInButton;

    public void clickOnLogInButtonIfVisible() {
        try{
            javaScriptClick(logInButton);
        }
        catch (Exception e){
        }
    }

    public boolean checkLoginButtonVisibility() {
       try{
           basePage.waitElementToBeVisible(logInButton);
           return true;
       }
       catch (Exception e){
           return  false;
       }
    }

    @FindBy(xpath = "//button[@class = 'button-style1-type-btn global_register-btn']")
    @CacheLookup
    WebElement signUp;

    public void clickOnSignUp() {
        try{
            basePage.waitElementToBeVisible(signUp);
            basePage.clickOnElementIfClickable(signUp);
        }
        catch (Exception e){

        }
    }





    @FindBy(xpath = "//li[@class='text-style2 TEST_HeaderPanel1Menu_Balance']/div[@class='balance_section']/span[1]")
    @CacheLookup
    WebElement balanceLabel;

    public String getTextBalanceLabel() {
        try{
            basePage.waitElementToBeVisible(balanceLabel);
            return basePage.getText(balanceLabel);
        }
        catch (Exception e){
            return  "Cant find webElement balanceLabel ";
        }
    }
    public boolean balanceLabelIsEnabled() {
        try{
            basePage.waitElementToBeVisible(balanceLabel);
            return basePage.elementIsEnable(balanceLabel);
        }
        catch (Exception e){
            return  false;
        }
    }

    @FindBy(xpath = "//li[@class='text-style2 TEST_HeaderPanel1Menu_Balance']/div[@class='balance_section']/span[2]")
    @CacheLookup
    WebElement balance;
    public boolean balanceIsEnabled() {
        try{
            basePage.waitElementToBeVisible(balance);
            return basePage.elementIsEnable(balance);
        }
        catch (Exception e){
            return  false;
        }
    }
    public String getTextBalance() {
        try{
            basePage.waitElementToBeVisible(balanceLabel);
            return basePage.getText(balanceLabel);
        }
        catch (Exception e){
            return  "Cant find webElement balanceLabel ";
        }
    }
    @FindBy(xpath = "//li[@class='text-style2 TEST_HeaderPanel1Menu_Bonus Balance']/div[@class='balance_section']/span[1]")
    @CacheLookup
    WebElement bonusLabel;
    public boolean bonusLabelIsEnabled() {
        try{
            basePage.waitElementToBeVisible(bonusLabel);
            return basePage.elementIsEnable(bonusLabel);
        }
        catch (Exception e){
            return  false;
        }
    }
    public String getTextBonusLabel() {
        try{
            basePage.waitElementToBeVisible(bonusLabel);
            return basePage.getText(bonusLabel);
        }
        catch (Exception e){
            return  "Cant find webElement bonusLabel ";
        }
    }

    @FindBy(xpath = "//li[@class='text-style2 TEST_HeaderPanel1Menu_Bonus Balance']/div[@class='balance_section']/span[2]")
    @CacheLookup
    WebElement bonus;
    public boolean bonusIsEnabled() {
        try{
            basePage.waitElementToBeVisible(bonus);
            return basePage.elementIsEnable(bonus);
        }
        catch (Exception e){
            return  false;
        }
    }
    public String getTextBonus() {
        try{
            basePage.waitElementToBeVisible(bonus);
            return basePage.getText(bonus);
        }
        catch (Exception e){
            return  "Cant find webElement bonus ";
        }
    }

    @FindBy(xpath = "//li[@class='button-style3 TEST_HeaderPanel1Menu_Deposit']/div")
    @CacheLookup
    WebElement depositButton;
    public void clickOnDepositButton() {
        try{
            basePage.waitElementToBeVisible(depositButton);
            basePage.waitElementTobeClickable(depositButton);
            basePage.javaScriptClick(depositButton);
            basePage.waitAction(200);
        }
        catch (Exception e){
        }
    }

    @FindBy(xpath = "//div[@id='custom_dropdown_style1']")
    @CacheLookup
    WebElement customDropDown;
    public void clickOnCustomDropDown() {
        try{
            basePage.waitElementToBeVisible(customDropDown);
            basePage.javaScriptClick(customDropDown);
        }
        catch (Exception e){

        }
    }

    @FindBy(xpath = "//div[@class='TEST_ACCOUNT_DPD_Deposit list-section']")
    @CacheLookup
    WebElement customDropDownDeposit;
    public void clickOnCustomDropDownDeposit() {
        try{
            basePage.waitElementToBeVisible(customDropDownDeposit);
            basePage.clickOnElementIfClickable(customDropDownDeposit);
        }
        catch (Exception e){

        }
    }

    @FindBy(xpath = "//div[@class='TEST_ACCOUNT_DPD_Withdraw list-section']")
    @CacheLookup
    WebElement customDropDownWithdraw;
    public void clickOnCustomDropDownWithdraw() {
        try{
            basePage.waitElementToBeVisible(customDropDownWithdraw);
            basePage.clickOnElementIfClickable(customDropDownWithdraw);
        }
        catch (Exception e){

        }
    }


    @FindBy(xpath = "//div[@class='TEST_ACCOUNT_DPD_Bank_Accounts list-section']")
    @CacheLookup
    WebElement customDropDownBankAccounts;
    public void clickOnCustomDropDownBankAccounts() {
        try{
            basePage.waitElementToBeVisible(customDropDownBankAccounts);
            basePage.clickOnElementIfClickable(customDropDownBankAccounts);
        }
        catch (Exception e){

        }
    }

    @FindBy(xpath = "//div[@class='TEST_ACCOUNT_DPD_My_bets list-section']")
    @CacheLookup
    WebElement customDropDownHistory;
    public void clickOnCustomDropDownHistory() {
        try{
            basePage.waitElementToBeVisible(customDropDownHistory);
            basePage.clickOnElementIfClickable(customDropDownHistory);
        }
        catch (Exception e){

        }
    }

    @FindBy(xpath = "//div[@class='TEST_ACCOUNT_DPD_Settings list-section']")
    @CacheLookup
    WebElement customDropDownSettings;
    public void clickOnCustomDropDownSettings() {
        try{
            basePage.waitElementToBeVisible(customDropDownSettings);
            basePage.clickOnElementIfClickable(customDropDownSettings);
        }
        catch (Exception e){

        }
    }

    @FindBy(xpath = "//div[@class='TEST_ACCOUNT_DPD_My_tickets list-section']")
    @CacheLookup
    WebElement customDropDownMyTickets;
    public void clickOnCustomDropDownMyTickets() {
        try{
            basePage.waitElementToBeVisible(customDropDownMyTickets);
            basePage.clickOnElementIfClickable(customDropDownMyTickets);
        }
        catch (Exception e){

        }
    }

    @FindBy(xpath = "//div[@class='Friends TEST_ACCOUNT_DPD_My list-section']")
    @CacheLookup
    WebElement customDropDownMyFriends;
    public void clickOnCustomDropDownMyFriends() {
        try{
            basePage.waitElementToBeVisible(customDropDownMyFriends);
            basePage.clickOnElementIfClickable(customDropDownMyFriends);
        }
        catch (Exception e){

        }
    }

    @FindBy(xpath = "//div[@class='TEST_ACCOUNT_DPD_Logout list-section']")
    @CacheLookup
    WebElement customDropDownLogOut;
    public void clickOnCustomDropDownLogOut() {
        try{
            basePage.waitElementToBeVisible(customDropDownLogOut);
            basePage.clickOnElementIfClickable(customDropDownLogOut);
        }
        catch (Exception e){

        }
    }






    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Sport')]")
    @CacheLookup
    WebElement sportsLink;

    public void clickOnSportsLink() {
        if(userIdLabelIsEnabled()){
            try {
                basePage.waitElementToBeVisible(sportsLink);
                basePage.javaScriptClick(sportsLink);
            } catch (Exception e) {
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_In-Play')]")
    @CacheLookup
    WebElement lifeLink;

    public void clickOnLifeLink() {
        if(userIdLabelIsEnabled()){
            try {
                basePage.waitElementToBeVisible(lifeLink);
                basePage.javaScriptClick(lifeLink);
            } catch (Exception e) {
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Asian')]")
    @CacheLookup
    WebElement asianSportLink;

    public void clickOnAsianSportLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(asianSportLink);
                basePage.javaScriptClick(asianSportLink);
            } catch (Exception e) {
            }
        }
    }


    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Virtual Sport ')]")
    @CacheLookup
    WebElement virtualSportLink;

    public void clickOnVirtualSportLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(virtualSportLink);
                basePage.javaScriptClick(virtualSportLink);
            } catch (Exception e) {
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_MoreMenu')]")
    @CacheLookup
    WebElement more2DropDown;
    public void hoverOnMore2DropDown() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(more2DropDown);
                basePage.actionMoveToElement(more2DropDown);
            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Casino ')]")
    @CacheLookup
    WebElement casinoLink;

    public void clickOnCasinoLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(casinoLink);
                basePage.javaScriptClick(casinoLink);
            } catch (Exception e) {
//                try {
//                    hoverOnMore2DropDown();
//                    basePage.waitElementToBeVisible(casinoLink);
//                    basePage.javaScriptClick(casinoLink);
//                }
//                catch (Exception e2){
//                }
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Live Casino ')]")
    @CacheLookup
    WebElement lifeCasinoLink;

    public void clickOnLifeCasinoLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(lifeCasinoLink);
                basePage.javaScriptClick(lifeCasinoLink);
            } catch (Exception e) {
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Virtual Games')]")
    @CacheLookup
    WebElement virtualGamesLink;

    public void clickOnVirtualGamesLink() {
        if(userIdLabelIsEnabled()){
        try {
            basePage.waitElementToBeVisible(virtualGamesLink);
            basePage.javaScriptClick(virtualGamesLink);
        } catch (Exception e) {
        }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Skill')]")
    @CacheLookup
    WebElement skillGamesLink;

    public void clickOnSkillGamesLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(skillGamesLink);
                basePage.javaScriptClick(skillGamesLink);
            } catch (Exception e) {
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Keno ')]")
    @CacheLookup
    WebElement kenoGameLink;

    public void clickOnKenoGameLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(kenoGameLink);
                basePage.javaScriptClick(kenoGameLink);
            } catch (Exception e) {
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_High Low')]")
    @CacheLookup
    WebElement highLowGameLink;

    public void clickOnHighLowGameLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(highLowGameLink);
                basePage.javaScriptClick(highLowGameLink);
            } catch (Exception e) {
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Crash')]")
    @CacheLookup
    WebElement crashGameLink;

    public void clickOnCrashGameLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(crashGameLink);
                basePage.javaScriptClick(crashGameLink);
            } catch (Exception e) {
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Lottery ')]")
    @CacheLookup
    WebElement lotteryGameLink;

    public void clickOnLotteryGameLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(lotteryGameLink);
                basePage.javaScriptClick(lotteryGameLink);
            } catch (Exception e) {
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_Promotions')]")
    @CacheLookup
    WebElement promotionsLink;

    public void clickOnPromotionsLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(promotionsLink);
                basePage.javaScriptClick(promotionsLink);
                basePage.waitAction(500);
            } catch (Exception e) {

            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_News ')]")
    @CacheLookup
    WebElement newsLink;

    public void clickOnNewsLink() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(newsLink);
                basePage.javaScriptClick(newsLink);
            } catch (Exception e) {
            }
        }
    }

    @FindBy(xpath = "//li[contains (@class, 'TEST_HeaderPanel2Menu_MobileApps')]")
    @CacheLookup
    WebElement mobileAppsLink;

    public void clickOnMobileApps() {
        if(userIdLabelIsEnabled()) {
            try {
                basePage.waitElementToBeVisible(mobileAppsLink);
                basePage.javaScriptClick(mobileAppsLink);
            } catch (Exception e) {
            }
        }
    }


    @FindBy(xpath = "//span[@class='icon-timezone']")
    @CacheLookup
    WebElement iconTimeZone;
    public boolean isVisibleIconTimeZone(){
        try{
            basePage.waitElementToBeVisible(iconTimeZone);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    @FindBy(xpath = "//div[@class='timezone']/span[2]")
    @CacheLookup
    WebElement timeTimeZone;
    public boolean isVisibleTimeTimeZone(){
        try{
            basePage.waitElementToBeVisible(timeTimeZone);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    @FindBy(xpath = "//div[@class='timezone']/span[3]")
    @CacheLookup
    WebElement currentTimeTimeZone;
    public boolean isVisibleCurrentTimeTimeZone(){
        try{
            basePage.waitElementToBeVisible(currentTimeTimeZone);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @FindBy(xpath = "//div[@class='header-section_bottomPanel flex_between']//a[text()='More']")
    @CacheLookup
    WebElement moreDropDown;
    public void moveToMoveDropDown(){
        basePage.waitElementToBeVisible(moreDropDown);
        basePage.actionMoveToElementClick(moreDropDown);
    }




























}



