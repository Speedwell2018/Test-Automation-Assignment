package com.example.app.pages;

import com.example.app.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BasePage {
    protected AppiumDriver driver;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
    }

    protected WebElement findEl(By locator) {
        return WaitUtils.waitForElementVisible(driver, locator);
    }

    protected List<WebElement> findEls(By locator){
        return WaitUtils.waitForElementsVisible(driver, locator);
    }

    protected WebElement findElByText(String text){

    }

    protected void click(By locator) {
        WaitUtils.waitForElementClickable(driver,locator).click();
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
