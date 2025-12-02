package com.example.app.components;
import com.example.app.pages.BasePage;
import com.example.app.pages.CartPage;
import com.example.app.pages.ProductsPage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class HeaderComponent extends BasePage {

    private final By cartIcon = AppiumBy.accessibilityId("test-Cart");
    private final By menuIcon = AppiumBy.accessibilityId("test-Menu");
    private final By allItems = AppiumBy.accessibilityId("test-ALL ITEMS");
    private final By badge = By.xpath("//android.view.ViewGroup[@content-desc='test-Cart']//android.widget.TextView");   // Change if needed

    public HeaderComponent(AppiumDriver driver) {
        super(driver);
    }


    public boolean isBadgeDisplayed() {
        log.info("Checking if cart badge is displayed...");
        try {
            boolean displayed= findEl(badge,5).isDisplayed();
            log.info("Cart badge displayed: {}", displayed);
            return displayed;
        } catch (Exception ignored) {
            log.info("Cart badge not displayed");
            return false;
        }
    }

    public boolean isMenuButtonDisplayed() {
        log.info("Checking if cart badge is displayed...");
        try {
            boolean displayed= findEl(menuIcon,15).isDisplayed();
            log.info("Menu icon is displayed: {}", displayed);
            return displayed;
        } catch (Exception ignored) {
            log.info("Menu icon is not displayed");
            return false;
        }
    }


    public int getBadgeCount() {
        log.info("Retrieving cart badge count...");

        try {
            WebElement badgeElement=findEl(badge, 5);
            if (!isBadgeDisplayed()){
                log.info("Badge not displayed, returning count 0");
                return 0;}
            int count = Integer.parseInt(badgeElement.getText().trim());
            log.info("Cart badge count: {}", count);
            return count;
        } catch (Exception e) {
            log.warn("Unable to retrieve badge count, returning 0 | Reason: {}", e.getMessage());
            return 0;
        }
    }

    public CartPage openCart() {
        log.info("Opening cart page...");
        click(cartIcon);
        log.info("Cart page opened");
        return new CartPage(driver);

    }

    public ProductsPage openProductsPage(){
        log.info("Opening Menu list");
        click(menuIcon);
        log.info("Selecting Products page");
        click(allItems);
        return new ProductsPage(driver);
    }



}
