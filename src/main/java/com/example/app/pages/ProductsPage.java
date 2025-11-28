package com.example.app.pages;
import com.example.app.models.ProductModel;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.example.app.utils.WaitUtils.waitForElementsVisible;

public class ProductsPage extends BasePage {


    private  By productList = AppiumBy.accessibilityId("test-Item");
    private By titleInsideItem = AppiumBy.accessibilityId("test-Item title");
    private By priceInsideItem = AppiumBy.accessibilityId("test-Price");
    private By addToCartInsideItem = AppiumBy.accessibilityId("test-ADD TO CART");
    private By removeCartInsideItem = AppiumBy.accessibilityId("test-Remove");
    private final By cartIcon = AppiumBy.accessibilityId("test-Cart");
    private final By counterBadge = By.xpath("//android.view.ViewGroup[@content-desc='test-Cart']");

    public ProductsPage(AppiumDriver driver){
        super(driver);
    }
    public ProductModel selectFirstProductItem() {

            WebElement firstProduct = findEls(productList).get(0);
            String title = firstProduct.findElement(titleInsideItem).getText();
            String price = firstProduct.findElement(priceInsideItem).getText();
            WebElement addButton = firstProduct.findElement(addToCartInsideItem);
            addButton.click();

           return new ProductModel(title, price);
    }


    public CartPage openCart() {
        click(cartIcon);

        return new CartPage(driver);

    }





}
