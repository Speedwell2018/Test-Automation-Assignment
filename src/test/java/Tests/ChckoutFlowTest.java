package Tests;

import com.example.app.components.HeaderComponent;
import com.example.app.models.ProductModel;
import com.example.app.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChckoutFlowTest extends BaseTest {

    @Test(dataProvider = "checkoutFlowData", dataProviderClass = data.CheckoutData.class,description = "Complete checkout flow from cart to order confirmation")
    public void completeCheckoutFlow(String firstName, String lastName, String zip) {

        HeaderComponent header = new HeaderComponent(driver);
        ProductsPage products = new ProductsPage(driver);
        ProductModel selectedProduct = products.selectProductNotInCart();
        String selectedTitle = selectedProduct.getTitle();
        String selectedPrice = selectedProduct.getPrice();

        CartPage cart = header.openCart();

        Assert.assertTrue(cart.isCartOpened(), "Cart page is not opened");
        ChekoutInfoPage chekout = cart.clickCheckout();

        chekout.enterCheckoutInformation(firstName, lastName, zip);
        OverviewPage overviewPage = chekout.clickContinue();

        Assert.assertTrue(overviewPage.isOverviewOpened(), "Overview page is not opened");
        Assert.assertTrue(overviewPage.isSelectedProductTitleDisplayed(selectedTitle), "Selected product title is not found");
        Assert.assertTrue(overviewPage.isSelectedProductPriceDisplayed(selectedPrice), "Selected product price is not found");

        ThankYouPage thankYouPage = overviewPage.clickFinish();

        Assert.assertTrue(thankYouPage.isThankYouPageOpened(), "Thank you page is not displayed");
        Assert.assertFalse(header.isBadgeDisplayed(), "Badge should disappear after checkout");

    }

}
