package Tests;

import com.example.app.components.HeaderComponent;
import com.example.app.models.ProductModel;
import com.example.app.pages.CartPage;
import com.example.app.pages.ProductsPage;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveFromCart extends BaseTest {
    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Remove the first avaialable product from the cart")
    public void RemoveProductFromTheCartTest() throws InterruptedException {

        ProductsPage products = new ProductsPage(driver);
        HeaderComponent header = new HeaderComponent(driver);

        ProductModel selectedProduct = products.selectProductNotInCart();

        String selectedTitle = selectedProduct.getTitle();
        int beforeRemoval = header.getBadgeCount();

        products.removeProductWithParticularName(selectedTitle);
        Assert.assertTrue(products.isAddToCartButtonForProduct(selectedTitle), "Button label did not change to 'Add to Cart' after adding product");
        Assert.assertEquals(header.getBadgeCount(), beforeRemoval -1, "Badge counter didn't deccrease by one");

        CartPage cart=header.openCart();
        cart.isCartOpened();
        Assert.assertFalse(cart.isSelectedProductTitleDisplayed(selectedTitle), "Selected product title is still in Your Cart");

        cart.clickContinueShopping();

    }
}
