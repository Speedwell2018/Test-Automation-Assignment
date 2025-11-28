package com.example.app.pages;

import com.example.app.utils.ScrollUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class CartPage extends BasePage {

    private final By cartTitle = By.xpath("//*[@text='YOUR CART']");

    private final By checkoutButton = AppiumBy.accessibilityId("test-CHECKOUT");

    public CartPage(AppiumDriver driver){
        super(driver);
    }

    public boolean isCartOpened() {
        try{
            findEl(cartTitle);
            return true;} catch (Exception e){
            return false;
        }
    }

    public boolean isSelectedProductTitleDisplayed(String itemTitle) {
        By titleLocator=By.xpath("//android.widget.TextView[@text='" + itemTitle + "']");
        ScrollUtils scroll = new ScrollUtils(driver);

        scroll.scrollUntilVisible(titleLocator, 5);

        return driver.findElement(titleLocator).isDisplayed();
    }

    public boolean isSelectedProductPriceDisplayed(String itemPrice) {

        By priceLocator=By.xpath("//android.widget.TextView[@text='" + itemPrice + "']");
        ScrollUtils scroll = new ScrollUtils(driver);

        scroll.scrollUntilVisible(priceLocator, 5);

        return driver.findElement(priceLocator).isDisplayed();
    }


    public ChekoutInfoPage clickCheckout() {
        click(checkoutButton);
        return new ChekoutInfoPage(driver);
    }

}
