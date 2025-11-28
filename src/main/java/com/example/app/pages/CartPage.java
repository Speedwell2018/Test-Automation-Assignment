package com.example.app.pages;

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
                return true;}
            catch (Exception e){
                return false;
            }
        }

    public boolean isSelectedProductTitleDisplayed(String itemTitle) {

            By titleLocator=By.xpath("//android.widget.TextView[@text='" + itemTitle + "']");
        try {
            findEl(titleLocator);
            return true;
        }catch(Exception e){
            return false;
        }
        }

    public boolean isSelectedProductPriceDisplayed(String itemPrice) {

        By titleLocator=By.xpath("//android.widget.TextView[@text='" + itemPrice + "']");
        try {
            findEl(titleLocator);
            return true;
        }catch(Exception e){
            return false;
        }
    }


        public ChekoutInfoPage clickCheckout() {
            click(checkoutButton);
            return new ChekoutInfoPage(driver);
        }

}
