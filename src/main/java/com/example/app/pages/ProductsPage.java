package com.example.app.pages;
import com.example.app.models.ProductModel;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.example.app.utils.ActionsHelper.scrollUp;


public class ProductsPage extends BasePage {


    private By productItem = AppiumBy.accessibilityId("test-Item");
    private By titleInsideItem = AppiumBy.accessibilityId("test-Item title");
    private By priceInsideItem = AppiumBy.accessibilityId("test-Price");
    private By addToCartInsideItem = AppiumBy.accessibilityId("test-ADD TO CART");
    private By removeCartInsideItem = AppiumBy.accessibilityId("test-Remove");
    private final By cartIcon = AppiumBy.accessibilityId("test-Cart");
    private final By counterBadge = By.xpath("//android.view.ViewGroup[@content-desc='test-Cart']");

    public ProductsPage(AppiumDriver driver){
        super(driver);
    }
    public ProductModel selectFirstAvailableProduct() {

        int maxScrolls = 3;

        for (int i = 0; i < maxScrolls; i++) {
        List<WebElement> products = findEls(productItem);

        for (WebElement product : products) {
            List<WebElement> addButtons = product.findElements(addToCartInsideItem);
            if (!addButtons.isEmpty()) {
                String title = product.findElement(titleInsideItem).getText();
                String price = product.findElement(priceInsideItem).getText();
                addButtons.get(0).click();

                return new ProductModel(title, price);
            }
        }
            scrollUp(driver);
        }

        throw new RuntimeException("No available product found after scrolling.");
    }

    public CartPage openCart() {
        click(cartIcon);

        return new CartPage(driver);

    }





}
