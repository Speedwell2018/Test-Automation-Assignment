package com.example.app.utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class ScrollUtils {
    private static final Logger log = LoggerFactory.getLogger(ScrollUtils.class);
    private final By footerLocator = By.xpath("//*[contains(@text, 'Terms of Service')]");
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
                .addAction(finger.createPointerMove(Duration.ofMillis(500),
                        PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger.createPointerUp(0));

        driver.perform(List.of(swipe));
    }
   public void scrollUp() {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.7);
        int endY = (int) (size.height * 0.3);
        int x = size.width / 2;

        log.info("Scrolling up... screenSize=({}x{}), startY={}, endY={}", size.width, size.height, startY, endY);

        swipe(x, startY, x, endY);
    }

    public WebElement scrollUntilVisible(By locator) {
        log.info("Scrolling until element becomes visible: {}", locator);
        int maxScrolls = 5;
        for (int i = 0; i < maxScrolls; i++) {
            List<WebElement> found = driver.findElements(locator);
            if (!found.isEmpty()) {
                WebElement element = found.get(0);


                try {
                    if (element.isDisplayed()) {
                        log.info("Element FOUND and VISIBLE: {}", locator);
                        return element;
                    } else {
                        log.debug("Element found but not visible yet: {}", locator);
                    }
                    } catch (Exception e) {
                        log.debug("Element found but not interactable yet: {}", e.getMessage());
                    }
                }
            try {
                WebElement footer = driver.findElement(footerLocator);
                if (footer.isDisplayed()) {
                    log.warn("Reached footer '{}' before finding element: {}", footerLocator, locator);
                    throw new RuntimeException(
                            "Reached footer before finding element: " + footerLocator);
                }
            } catch (Exception ignored) {}
            scrollUp();

        }
        log.error("Element NOT FOUND after {} scroll attempts: {}", locator);
        throw new RuntimeException("Element not found after scrolling: " + locator);
    }

    public boolean scrollForElemntExistanceCheck(By locator) {
        log.info("Scrolling to check existence of element: {}", locator);
        int attempts = 0;
        int maxScrolls = 5;

        while (attempts < maxScrolls) {
            log.debug("Existence check scroll attempt {}/{}", (attempts + 1), maxScrolls);


            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()){
                log.info("Element EXISTS: {}", locator);
                return true;}


            if (!driver.findElements(footerLocator).isEmpty()) {
                log.info("Footer reached — element does NOT exist: {}", locator);
                return false;  }// reached bottom


            scrollUp();
            attempts++;
        }
        log.info("Element NOT found after {} scrolls: {}", maxScrolls, locator);

        return false;
    }


}
