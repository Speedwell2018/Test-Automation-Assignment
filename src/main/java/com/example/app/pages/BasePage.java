package com.example.app.pages;

import com.example.app.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

public class BasePage {
    protected AppiumDriver driver;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
    }

    protected WebElement findEl(By locator) {
        log.info("Finding element: {}", locator);
        return WaitUtils.waitForElementVisible(driver, locator);
    }
    protected WebElement findEl(By locator, int timeInSec) {
        log.info("Finding element: {} with timeout {}", locator, timeInSec);
        return WaitUtils.waitForElementVisible(driver, locator, timeInSec);
    }

    protected List<WebElement> findEls(By locator){
        log.info("Finding elements: {}", locator);
        return WaitUtils.waitForElementsVisible(driver, locator);
    }
    public WebElement findElIn(WebElement parent, By childLocator) {
        log.info("Finding elements: {} in {} element", childLocator, parent);
        return parent.findElement(childLocator);
    }


    protected void click(By locator) {
        log.info("Clicking {} element", locator);
        WaitUtils.waitForElementClickable(driver,locator).click();
    }

    protected void type(By locator, String text) {
        log.info("Clearing {} field", locator);
        WebElement el = findEl(locator);
        el.clear();
        log.info("Inserting {} text into field {}",text, locator);
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        log.info("Get text of {}", locator);
        return findEl(locator).getText();
    }

  }
