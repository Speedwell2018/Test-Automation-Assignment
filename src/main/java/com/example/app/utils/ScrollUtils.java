package com.example.app.utils;

import com.example.app.pages.BasePage;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

public class ScrollUtils extends BasePage {


    public ScrollUtils(AppiumDriver driver) {
        super(driver);
    }

    public void scrollUp() {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.3);
        int endY = (int) (size.height * 0.7);
        int x = size.width / 2;

        swipe(x, startY, x, endY);
    }

    public void scrollUntilVisible(By locator, int maxScrolls) {
        int attempts = 0;
        while (attempts < maxScrolls) {
            try {
                findEl(locator);
                return;
            } catch (Exception ignored) {
                scrollUp();
            }
            attempts++;
        }
        throw new RuntimeException("Element not found after scrolling: " + locator);
    }

    private void swipe(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(0))
                .addAction(finger.createPointerMove(Duration.ofMillis(500),
                        PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger.createPointerUp(0));

        driver.perform(List.of(swipe));
    }
}
