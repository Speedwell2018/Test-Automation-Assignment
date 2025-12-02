package Tests;
import com.example.app.components.HeaderComponent;
import com.example.app.pages.CartPage;
import com.example.app.pages.ChekoutInfoPage;
import com.example.app.pages.ProductsPage;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CheckOutRequiredFieldsValidationTest extends BaseTest {
    private ChekoutInfoPage checkoutPage;

    @BeforeClass
    public void prepareCheckoutPage() {


        ProductsPage products = new ProductsPage(driver);
        HeaderComponent header = new HeaderComponent(driver);
        products.selectProductNotInCart();

        CartPage cart = header.openCart();
        checkoutPage=cart.clickCheckout();


    }

    @Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "checkoutFieldValidationData", dataProviderClass = data.CheckoutData.class, description = "Validate required field errors on Checkout Info page")
    public void validateRequiredFields(String firstName, String lastName, String zip, String expectedError) {

        checkoutPage.enterCheckoutInformation(firstName, lastName, zip);
        checkoutPage.clickContinue();


        Assert.assertEquals(checkoutPage.getErrorMessage(), expectedError, "Error message mismatch");
    }

    @AfterClass
    public void openProductsPage() {
        ChekoutInfoPage checkoutPage=new ChekoutInfoPage(driver);
        checkoutPage.clickCancel();
    }
}
