package com.example.app.pages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class CartPage extends BasePage {

    private final By cartTitle = By.xpath("//*[@text='YOUR CART']");
    private final String item = "//android.widget.TextView[@text='%s']";

    private final By checkoutButton = AppiumBy.accessibilityId("test-CHECKOUT");

    public CartPage(AppiumDriver driver){
        super(driver);
    }

    public boolean isCartOpened() {
        log.info("Checking if Cart page is opened...");
        try{
            findEl(cartTitle);
            log.info("Cart page is opened");
            return true;
        } catch (Exception e){
            log.warn("Cart page is NOT opened");
            return false;
        }
    }

    public boolean isSelectedProductTitleDisplayed(String itemTitle) {
        log.info("Checking if product title '{}' is displayed in cart...", itemTitle);
        boolean displayed=scrollForElemntExistanceCheck(AppiumBy.androidUIAutomator(String.format(item,itemTitle)));
        log.info("Product title '{}' displayed: {}", itemTitle, displayed);
        return displayed;
    }

    public boolean isSelectedProductPriceDisplayed(String itemPrice) {
        log.info("Checking if product price '{}' is displayed in cart...", itemPrice);

        By priceLocator=By.xpath("//android.widget.TextView[@text='" + itemPrice + "']");

        boolean displayed = scrollForElemntExistanceCheck(priceLocator);
        log.info("Product price '{}' displayed: {}", itemPrice, displayed);
        return displayed;

    }

    public ChekoutInfoPage clickCheckout() {
        log.info("Clicking checkout button...");
        click(checkoutButton); // BasePage logs click
        log.info("Navigated to Checkout Info Page");
        return new ChekoutInfoPage(driver);
    }

}
