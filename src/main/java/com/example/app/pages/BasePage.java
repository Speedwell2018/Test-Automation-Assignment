package com.example.app.pages;

import com.example.app.utils.ScrollUtils;
import com.example.app.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasePage {

    private final By footerLocator = AppiumBy.androidUIAutomator("new UiSelector().text(\"© 2025 Sauce Labs. All Rights Reserved.\")");
    private static final int DEFAULT_RETRIES = 2;
    private static final int DEFAULT_DELAY = 300;

    protected AppiumDriver driver;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected ScrollUtils scrollUtils;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.scrollUtils = new ScrollUtils(driver);
        log.info("BasePage initialized with driver: {}", driver);
    }

    private <T> T retry(Action<T> action,  String description) {
        Exception lastException = null;

        for (int i = 1; i <= DEFAULT_RETRIES; i++) {
            try {
                log.debug("Attempt {}/{} for: {}", i, DEFAULT_RETRIES, description);
                return action.execute();
            } catch (Exception e) {
                lastException = e;
                log.warn("Failed attempt {}/{} for {} — error: {}", i, DEFAULT_RETRIES, description, e.getMessage());

                if (i < DEFAULT_RETRIES) {
                    try { Thread.sleep(DEFAULT_DELAY); } catch (InterruptedException ignored) {}
                }
            }
        }

        log.error("All {} retry attempts FAILED for: {}", DEFAULT_RETRIES, description);
        throw new RuntimeException("Retry failed for: " + description, lastException);
    }

    @FunctionalInterface
    private interface Action<T> {
        T execute();
    }


    protected WebElement findEl(By locator) {
        log.info("Finding element: {}", locator);
        return WaitUtils.waitForElementVisible(driver, locator);
    }

    protected WebElement findEl(By locator, int timeInSec) {
        log.info("Finding element: {} with timeout {}", locator, timeInSec);
        return WaitUtils.waitForElementVisible(driver, locator, timeInSec);
    }

    protected List<WebElement> findEls(By locator) {
        log.info("Finding elements: {}", locator);
        return WaitUtils.waitForElementsVisible(driver, locator);
    }

    public WebElement findElIn(WebElement parent, By childLocator) {
        log.info("Finding elements: {} in {} element", childLocator, parent);
        return WaitUtils.waitForElementInParent(driver, parent, childLocator);
    }


    protected void click(By locator) {
        retry(() -> {
            log.info("Clicking element: {}", locator);
            WebElement el = WaitUtils.waitForElementClickable(driver, locator);
            el.click();
            return null;
        },  "click on " + locator);
    }

    protected void type(By locator, String text) {
        retry(() -> {
            log.info("Typing '{}' into {}", text, locator);
            WebElement el = findEl(locator);
            el.clear();
            el.sendKeys(text);
            return null;
        },  "type into " + locator);
    }

    protected String getText(By locator) {
        return retry(() -> {
            log.info("Getting text of {}", locator);
            return findEl(locator).getText();
        },  "get text from " + locator);
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
                    }
                } catch (Exception e) {
                    log.debug("Element found but not interactable yet: {}", e.getMessage());
                }
            }
            if (isFooterVisible()) {
                log.warn("Reached footer '{}' before finding element: {}", footerLocator, locator);
                throw new RuntimeException(
                        "Reached footer before finding element: " + footerLocator);
            }

            scrollUtils.scrollUp();

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
            log.debug("Attempt {}/{}: found {} elements", attempts, maxScrolls, elements.size());
            if (!elements.isEmpty()) {
                log.info("Element EXISTS: {}", locator);
                return true;
            }
            log.info("Scrolling the page to find element");


            if (isFooterVisible()) {
                log.info("Footer reached — element does NOT exist: {}", locator);
                return false;
            }
            log.debug("Element not found, scrolling page (attempt {}/{})", attempts, maxScrolls);
            scrollUtils.scrollUp();
            attempts++;
        }
        log.info("Element NOT found after {} scrolls: {}", maxScrolls, locator);

        return false;
    }

    public List<String> getAllTexts(By locator) {
        log.info("Starting getAllTexts for locator: {}", locator);
        Set<String> seenTexts = new HashSet<>();
        List<String> values = new ArrayList<>();


        boolean reachedEnd = false;

        while (!reachedEnd) {
            log.debug("Fetching elements for locator: {}", locator);
            List<WebElement> elements = findEls(locator);
            log.debug("Found {} elements", elements.size());


            for (WebElement el : elements) {
                String text = el.getText();
                if (seenTexts.add(text)) {
                    values.add(text);
                    log.debug("Added new text: {}", text);
                }else {
                    log.trace("Duplicate text skipped: {}", text);
                }
            }


            if (isFooterVisible()) {
                log.info("Footer is displayed");
                reachedEnd = true;
            } else {
                log.debug("Footer not visible. Scrolling up...");
                scrollUtils.scrollUp();
            }
        }
        log.debug("Scrolling back down to target element: {}", locator);
        scrollDownToElement(locator);
        log.info("Completed getAllTexts. Total unique texts collected: {}", values.size());
        return values;
    }

    public void scrollDownToElement(By locator) {
        log.info("Scrolling DOWN to reset list to top for locator: {}", locator);
        String lastFirstText = "";

        for (int i = 0; i < 5; i++) {
            List<WebElement> items = findEls(locator);
            if (items.isEmpty()) return;

            String currentFirst = items.get(0).getText();

            if (currentFirst.equals(lastFirstText)) {
                log.debug("Reached TOP of the list");
                return;
            }

            lastFirstText = currentFirst;
            scrollUtils.scrollDown();
        }
    }

    private boolean isFooterVisible() {
        log.debug("Checking if footer is visible using locator: {}", footerLocator);
        try {
            boolean visible = !driver.findElements(footerLocator).isEmpty();
            log.info("Footer is visible. Found {} footer element(s).");
            return visible;
        } catch (Exception e) {
            return false;
        }
    }
}
