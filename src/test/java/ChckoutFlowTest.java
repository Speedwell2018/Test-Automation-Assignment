import com.example.app.models.ProductModel;
import com.example.app.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChckoutFlowTest extends BaseTest {
    @Test(description = "Complete checkout flow from cart to order confirmation")
    public void completeCheckoutFlow(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login();
        ProductsPage productsPage=new ProductsPage(driver);
        ProductModel selectedProduct=productsPage.selectFirstAvailableProduct();
        String selectedTitle=selectedProduct.getTitle();
        String selectedPrice=selectedProduct.getPrice();
        CartPage cartpage =productsPage.openCart();

        Assert.assertTrue(cartpage.isCartOpened(),"Cart page is not opened");
        Assert.assertTrue(cartpage.isSelectedProductTitleDisplayed(selectedTitle));
        Assert.assertTrue(cartpage.isSelectedProductPriceDisplayed(selectedPrice));

        ChekoutInfoPage chekoutInfoPage=cartpage.clickCheckout();
        chekoutInfoPage.enterCheckoutInformation("John", "Smith", "123456");
        OverviewPage overviewPage=chekoutInfoPage.clickContinue();
        Assert.assertTrue(overviewPage.isOverviewOpened(),"Overview page is not opened");
        Assert.assertTrue(overviewPage.isSelectedProductTitleDisplayed(selectedTitle), "Selected product title is not found");
        Assert.assertTrue(overviewPage.isSelectedProductPriceDisplayed(selectedPrice), "Selected product price is not found");
        ThankYouPage thankYouPage=overviewPage.clickFinish();
        Assert.assertTrue(thankYouPage.isThankYouPageOpened(), "Thank you page is not displayed");

    }

}
