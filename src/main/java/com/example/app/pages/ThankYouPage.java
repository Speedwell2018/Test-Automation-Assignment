package com.example.app.pages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ThankYouPage extends BasePage {
    private final By checkoutCompleteTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"CHECKOUT: COMPLETE!\")");
    private final By backHomeButton = AppiumBy.accessibilityId("test-BACK HOME");

    public ThankYouPage(AppiumDriver driver){
        super(driver);
    }

    public boolean isThankYouPageOpened() {
        log.info("Checking if Thank You page is opened...");
        try{
            findEl(checkoutCompleteTitle);
            log.info("Thank You page is opened");
            return true;
        } catch (Exception e){
            log.warn("Thank You page is NOT opened");
            return false;
        }
    }

    public void clickBckHomeButton(){
        log.info("Clicking BackHome button...");
        click(backHomeButton);
        log.info("Navigating to Products page");
    }
}
