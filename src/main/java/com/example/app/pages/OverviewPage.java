package com.example.app.pages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;


public class OverviewPage extends BasePage{
    private final By OverviewTitle = AppiumBy.androidUIAutomator( "new UiSelector().text(\"CHECKOUT: OVERVIEW\")");
    private final String item = "new UiSelector().text(\"%s\")";
    private final By finishButton = AppiumBy.accessibilityId("test-FINISH");

    public OverviewPage(AppiumDriver driver){
        super(driver);
    }

    public boolean isOverviewOpened() {
        log.info("Checking if Overview page is opened...");
        try{
            findEl(OverviewTitle);
            log.info("Overview page is opened");
            return true;} catch (Exception e){
            log.warn("Overview page is NOT opened");

            return false;
        }
    }

    public boolean isSelectedProductTitleDisplayed(String itemTitle) {
        log.info("Checking if product title '{}' is displayed in cart...", itemTitle);
        boolean displayed=scrollForElemntExistanceCheck(AppiumBy.androidUIAutomator(String.format(item,itemTitle)));
        log.info("Product title '{}' displayed: {}", itemTitle, displayed);
        return displayed;
    }

    public boolean isSelectedProductPriceDisplayed(String itemPrice) {
        log.info("Checking if product price '{}' is displayed in cart...", itemPrice);
        boolean displayed = scrollForElemntExistanceCheck(AppiumBy.androidUIAutomator(String.format(item,itemPrice)));
        log.info("Product price '{}' displayed: {}", itemPrice, displayed);
        return displayed;
    }

    public ThankYouPage clickFinish(){
        log.info("Scrolling to and clicking Finish button...");
        scrollUntilVisible(finishButton).click();
        log.info("Finish button clicked, navigating to Thank You page");
        return new ThankYouPage(driver);
    }






}
