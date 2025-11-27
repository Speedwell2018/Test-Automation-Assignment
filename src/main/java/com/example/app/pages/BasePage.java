package com.example.app.pages;

import com.example.app.config.DriverFactory;
import com.example.app.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BasePage {
    protected AppiumDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }

    protected WebElement find(By locator) {
        return WaitUtils.waitForElementVisible(driver, locator);
    }

    protected void click(By locator) {
        find(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement el = find(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        return find(locator).getText();
    }
}
