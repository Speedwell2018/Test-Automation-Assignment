package Tests;

import com.example.app.components.HeaderComponent;
import com.example.app.pages.CartPage;
import com.example.app.pages.ChekoutInfoPage;
import com.example.app.pages.ProductsPage;
import org.testng.Assert;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckOutRequiredFieldsValidationTest extends BaseTest {
    private ChekoutInfoPage checkoutPage;

    @BeforeClass
    public void prepareCheckoutPage() {

        ProductsPage products = new ProductsPage(driver);
        products.selectProductNotInCart();

        // 2. Open cart
        HeaderComponent header = new HeaderComponent(driver);
        CartPage cart = header.openCart();

        // 3. Open checkout info page
        checkoutPage = cart.clickCheckout();
    }


    @Test(dataProvider = "checkoutFieldValidationData", dataProviderClass = data.CheckoutData.class, description = "Validate required field errors on Checkout Info page")
    public void validateRequiredFields(String firstName, String lastName, String zip, String expectedError) {
        checkoutPage.enterCheckoutInformation(firstName, lastName, zip);
        checkoutPage.clickContinue();


        Assert.assertEquals(checkoutPage.getErrorMessage(), expectedError, "Error message mismatch");
    }
}
