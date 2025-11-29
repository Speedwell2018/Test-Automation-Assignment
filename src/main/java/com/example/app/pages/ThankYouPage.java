package com.example.app.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ThankYouPage extends BasePage {
    private final By checkoutCompleteTitle = By.xpath("//*[@text='CHECKOUT: COMPLETE!']");

    private final By finishButton = AppiumBy.accessibilityId("test-FINISH");

    public ThankYouPage(AppiumDriver driver){
        super(driver);
    }
    public boolean isThankYouPageOpened() {
        try{
            findEl(checkoutCompleteTitle);
            return true;}
        catch (Exception e){
            return false;
        }
    }
}
