package com.example.app.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChekoutInfoPage extends BasePage{
        private final By checkoutInfoTitle = By.xpath("//*[@text='CHECKOUT: INFORMATION']");
    private final By firstNameField = AppiumBy.accessibilityId("test-First Name");
    private final By lastNameField = AppiumBy.accessibilityId("test-Last Name");
    private final By zipField = AppiumBy.accessibilityId("test-Zip/Postal Code");
    private final By continueButton = AppiumBy.accessibilityId("test-CONTINUE");
    private final By errorMsg = By.xpath("//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView");


    public ChekoutInfoPage(AppiumDriver driver){
        super(driver);
    }

    public boolean isCheckOutPageOpened() {
        log.info("Checking if Checkout Info page is opened...");
        try{
            findEl(checkoutInfoTitle);
            log.info("Checkout Info page is opened");
            return true;
        } catch (Exception e){
            log.warn("Checkout Info page is NOT opened");
            return false;
        }
    }

    public void enterCheckoutInformation(String firstName, String lastName, String zip) {
          enterFirstName(firstName);
        enterLastName(lastName);
        enterZipCode(zip);
    }

    public void enterFirstName(String firstName) {
        log.info("Entering first name: '{}'", firstName);
        type(firstNameField, firstName);

    }
    public void enterLastName(String lastName) {
        log.info("Entering last name: '{}'", lastName);
        type(lastNameField, lastName);
    }

    public void enterZipCode(String zip) {
        log.info("Entering zip code: '{}'", zip);
        type(zipField, zip);
    }

    public String getErrorMessage() {
        String msg = getText(errorMsg);
        log.info("Retrieved error message: '{}'", msg);
        return msg;

    }

    public OverviewPage clickContinue() {
        log.info("Clicking Continue button on Checkout Info page");
        click(continueButton);
        log.info("Navigated to Overview Page");
        return new OverviewPage(driver);
    }
}


