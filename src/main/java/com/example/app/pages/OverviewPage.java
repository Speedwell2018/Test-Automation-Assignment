package com.example.app.pages;

import com.example.app.utils.ScrollUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.slf4j.Logger;

public class OverviewPage extends BasePage{
    private final By OverviewTitle = By.xpath("//*[@text='CHECKOUT: OVERVIEW']");

    private final By finishButton = AppiumBy.accessibilityId("test-FINISH");

    public OverviewPage(AppiumDriver driver){
        super(driver);
    }

    public boolean isOverviewOpened() {
        log.info("Checking if Overview page is opened...");
        try{
            findEl(OverviewTitle);
            log.info("Overview page is opened");
            return true;}
        catch (Exception e){
            log.warn("Overview page is NOT opened");

            return false;
        }
    }

    public boolean isSelectedProductTitleDisplayed(String itemTitle) {
        log.info("Checking if product title '{}' is displayed on Overview page...", itemTitle);
        By titleLocator=By.xpath("//android.widget.TextView[@text='" + itemTitle + "']");



        boolean displayed = scrollUntilVisible(titleLocator).isDisplayed();
        log.info("Product title '{}' displayed: {}", itemTitle, displayed);
        return displayed;

    }

    public boolean isSelectedProductPriceDisplayed(String itemPrice) {
        log.info("Checking if product price '{}' is displayed on Overview page...", itemPrice);
        By priceLocator=By.xpath("//android.widget.TextView[@text='" + itemPrice + "']");
        boolean displayed = scrollUntilVisible(priceLocator).isDisplayed();
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
