package com.example.app.utils;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class ScrollUtils {
    private static final Logger log = LoggerFactory.getLogger(ScrollUtils.class);
    private final AppiumDriver driver;

    public ScrollUtils(AppiumDriver driver) {
        this.driver = driver;
    }

    private void swipe(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        log.debug("Performing swipe: start=({}, {}), end=({}, {})", startX, startY, endX, endY);

        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(0))
                .addAction(finger.createPointerMove(Duration.ofMillis(600),
                        PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger.createPointerUp(0));

        driver.perform(List.of(swipe));
    }

    public void scrollUp() {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.85);
        int endY = (int) (size.height * 0.25);
        int x = size.width / 2;

        log.info("Scrolling up... screenSize=({}x{}), startY={}, endY={}", size.width, size.height, startY, endY);

        swipe(x, startY, x, endY);
    }

    public void scrollDown() {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.25);
        int endY = (int) (size.height * 0.85);
        int x = size.width / 2;

        log.info("Scrolling down... screenSize=({}x{}), startY={}, endY={}", size.width, size.height, startY, endY);

        swipe(x, startY, x, endY);
    }

}
