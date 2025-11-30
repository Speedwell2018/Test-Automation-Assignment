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

    public static WebElement waitForElementVisible(AppiumDriver driver, By locator) {
        int timeoutInSeconds = Integer.parseInt(
                System.getenv().getOrDefault("EXPLICIT_WAIT", "15")
        );
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

    public static WebElement waitForElementClickable(AppiumDriver driver, By locator) {
        int timeoutInSeconds = Integer.parseInt(
                System.getenv().getOrDefault("EXPLICIT_WAIT", "15")
        );
        log.info("Waiting for element CLICKABLE: {} ({}s)", locator, timeoutInSeconds);

        try{

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement el= wait.until(ExpectedConditions.elementToBeClickable(locator));
            log.info("Element is clickable: {}", locator);
            return el;
        } catch (Exception e) {
            log.error("Element NOT clickable: {} | Reason: {}", locator, e.getMessage());
            throw e;
        }
    }

    public static List<WebElement> waitForElementsVisible(AppiumDriver driver, By locator) {
        int timeoutInSeconds = Integer.parseInt(
                System.getenv().getOrDefault("EXPLICIT_WAIT", "15")
        );
        log.info("Waiting for ALL elements VISIBLE: {} ({}s)", locator, timeoutInSeconds);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

            log.info("Elements are visible: {}", locator);
            return elements;
        } catch (Exception e) {
            log.error("Elements NOT visible: {} | Reason: {}", locator, e.getMessage());
            throw e;
        }

    }


}
