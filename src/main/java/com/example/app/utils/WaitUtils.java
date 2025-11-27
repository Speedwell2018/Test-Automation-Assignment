package com.example.app.utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    public static WebElement waitForElementVisible(AppiumDriver driver, By locator) {
        int timeoutInSeconds = Integer.parseInt(
                System.getenv().getOrDefault("EXPLICIT_WAIT", "15")
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage("Element cannot be found");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
