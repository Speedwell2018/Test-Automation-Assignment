package Tests;

import com.example.app.components.HeaderComponent;
import com.example.app.models.ProductModel;
import com.example.app.pages.*;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AddToCartTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Add the first avaialable product to the cart")
    public void AddToCartTest() {

        ProductsPage products = new ProductsPage(driver);
        HeaderComponent header = new HeaderComponent(driver);
        int beforeAdd = header.getBadgeCount();

        ProductModel selectedProduct = products.selectProductNotInCart();

        String selectedTitle = selectedProduct.getTitle();
        String selectedPrice = selectedProduct.getPrice();
        Assert.assertTrue(products.isRemoveButtonForProduct(selectedTitle), "Button label did not change to 'Remove' after adding product");

        Assert.assertEquals(header.getBadgeCount(), beforeAdd + 1, "Badge counter didn't increase by one");

        CartPage cart = header.openCart();

        Assert.assertTrue(cart.isCartOpened(), "Cart page is not opened");
        Assert.assertTrue(cart.isSelectedProductTitleDisplayed(selectedTitle), "Selected product title is not found in Your Cart");
        Assert.assertTrue(cart.isSelectedProductPriceDisplayed(selectedPrice), "Selected product price is not found in Your Cart");
        cart.clickContinueShopping();
    }



}
