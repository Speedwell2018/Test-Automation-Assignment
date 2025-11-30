package com.example.app.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class LoginPage extends BasePage{

    private final By standardUser=By.xpath("//android.widget.TextView[@text='standard_user']");
    private final By loginButton= AppiumBy.accessibilityId("test-LOGIN");

    public LoginPage(AppiumDriver driver){
        super(driver);
    }
    public void fillCredentialsFields(){
        log.info("Selecting standard user credentials...");
        click(standardUser);
        log.info("Standard user selected");
    }

    public void clickLoginButton(){
        log.info("Clicking Login button...");
        click(loginButton);
        log.info("Login button clicked");
    }

    public void login(){
        fillCredentialsFields();
        clickLoginButton();
    }


}
