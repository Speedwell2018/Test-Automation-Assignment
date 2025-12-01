package com.example.app.pages;

import com.example.app.utils.ScrollUtils;
import com.example.app.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasePage {

    private final By footerLocator = By.xpath("//*[contains(@text, 'Terms of Service')]");

    protected AppiumDriver driver;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected ScrollUtils scrollUtils;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.scrollUtils = new ScrollUtils(driver);
        log.info("BasePage initialized with driver: {}", driver);
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
        return parent.findElement(childLocator);
    }


    protected void click(By locator) {
        log.info("Clicking {} element", locator);
        WaitUtils.waitForElementClickable(driver, locator).click();
    }

    protected void type(By locator, String text) {
        log.info("Clearing {} field", locator);
        WebElement el = findEl(locator);
        el.clear();
        log.info("Inserting {} text into field {}", text, locator);
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        log.info("Get text of {}", locator);
        return findEl(locator).getText();
    }

    public WebElement scrollUntilVisible(By locator) {
        log.info("Scrolling until element becomes visible: {}", locator);
        int maxScrolls = 5;
        for (int i = 0; i < maxScrolls; i++) {
            List<WebElement> found = findEls(locator);
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


            List<WebElement> elements = findEls(locator);
            if (!elements.isEmpty()) {
                log.info("Element EXISTS: {}", locator);
                return true;
            }


            if (isFooterVisible()) {
                log.info("Footer reached — element does NOT exist: {}", locator);
                return false;
            }
            scrollUtils.scrollUp();
            attempts++;
        }
        log.info("Element NOT found after {} scrolls: {}", maxScrolls, locator);

        return false;
    }

    public List<String> getAllTexts(By locator) {
        Set<String> seenTexts = new HashSet<>();
        List<String> values = new ArrayList<>();


        boolean reachedEnd = false;

        while (!reachedEnd) {
            List<WebElement> elements = findEls(locator);


            for (WebElement el : elements) {
                String text = el.getText();
                if (seenTexts.add(text)) {
                    values.add(text);
                }
            }


            if (isFooterVisible()) {
                    log.info("Footer is displayed");
                    reachedEnd = true;
                }
            else {
            scrollUtils.scrollUp();
            }
        }

        scrollDownToElement(locator);
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
        try {
            return !driver.findElements(footerLocator).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
