package com.example.app.utils;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;



public class WaitUtils {
    private static final Logger log = LoggerFactory.getLogger(WaitUtils.class);
    private static final int explicitTimeout=15;

    public static WebElement waitForElementVisible(AppiumDriver driver, By locator) {

        log.info("Waiting for element VISIBLE: {} ({}s)", locator, explicitTimeout);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitTimeout));
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.info("Element is visible: {}", locator);
            return el;
        } catch (Exception e) {
            log.error("Element NOT visible: {} | Reason: {}", locator, e.getMessage());
            throw e;
        }

    }

    public static WebElement waitForElementVisible(AppiumDriver driver, By locator, int timeoutInSeconds ) {
        log.info("Waiting for element VISIBLE: {} ({}s)", locator, timeoutInSeconds);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.info("Element is visible: {}", locator);
            return el;
        } catch (Exception e) {
            log.error("Element NOT visible: {} | Reason: {}", locator, e.getMessage());
            throw e;
        }
    }

    public static WebElement waitForElementInParent(AppiumDriver driver, WebElement parent, By childLocator) {
        int timeoutInSeconds = 15; // default timeout
        log.info("Waiting up to {}s for element '{}' inside parent element {}", timeoutInSeconds, childLocator, parent);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(d -> {
                try {
                    WebElement el = parent.findElement(childLocator);
                    if (el.isDisplayed()) {
                        log.debug("Element '{}' found and visible inside parent.", childLocator);
                        return el;
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    log.trace("Element '{}' not yet present inside parent: {}", childLocator, e.getMessage());
                    return null;
                }
            });
        } catch (Exception e) {
            log.error("Failed to find element '{}' inside parent after {}s", childLocator, timeoutInSeconds);
            throw new RuntimeException("Element not found inside parent: " + childLocator, e);
        }
    }

    public static WebElement waitForElementClickable(AppiumDriver driver, By locator) {

        log.info("Waiting for element CLICKABLE: {} ({}s)", locator, explicitTimeout);

        try{

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitTimeout));
            WebElement el= wait.until(ExpectedConditions.elementToBeClickable(locator));
            log.info("Element is clickable: {}", locator);
            return el;
        } catch (Exception e) {
            log.error("Element NOT clickable: {} | Reason: {}", locator, e.getMessage());
            throw e;
        }
    }

    public static List<WebElement> waitForElementsVisible(AppiumDriver driver, By locator) {

        log.info("Waiting for ALL elements VISIBLE: {} ({}s)", locator, explicitTimeout);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitTimeout));
        wait.withMessage("Element cannot be found");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

    }


}
