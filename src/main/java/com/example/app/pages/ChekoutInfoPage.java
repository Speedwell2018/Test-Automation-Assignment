package com.example.app.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ChekoutInfoPage extends BasePage{

    private final By firstNameField = AppiumBy.accessibilityId("test-First Name");
    private final By lastNameField = AppiumBy.accessibilityId("test-Last Name");
    private final By zipField = AppiumBy.accessibilityId("test-Zip/Postal Code");
    private final By continueButton = AppiumBy.accessibilityId("test-CONTINUE");

    public ChekoutInfoPage(AppiumDriver driver){
        super(driver);
    }

    public void enterCheckoutInformation(String firstName, String lastName, String zip) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(zipField, zip);
    }

    public OverviewPage clickContinue() {
        click(continueButton);
        return new OverviewPage(driver);
    }
}


