package com.example.app.pages;
import com.example.app.components.HeaderComponent;
import com.example.app.models.ProductModel;
import com.example.app.utils.ScrollUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;



public class ProductsPage extends BasePage {

    private final By ProductTitle = AppiumBy.androidUIAutomator( "new UiSelector().text(\"PRODUCTS\")");
    private By productItem = AppiumBy.accessibilityId("test-Item");
    private By titleInsideItem = AppiumBy.accessibilityId("test-Item title");
    private By priceInsideItem = AppiumBy.accessibilityId("test-Price");
    private By addToCartInsideItem = AppiumBy.accessibilityId("test-ADD TO CART");
    private By removeCartInsideItem = AppiumBy.accessibilityId("test-REMOVE");
    private final By sortButton = AppiumBy.accessibilityId("test-Modal Selector Button");
    private final String sortOption="new UiSelector().text(\"%s\")";


    public ProductsPage(AppiumDriver driver){
        super(driver);
    }

    public void openProductsPage(){
        log.info("Checking if Products page is opened...");
        if (!isProductPageOpened()){
            log.info("Open Products Page...");
            HeaderComponent header=new HeaderComponent(driver);
            header.openProductsPage();
        }
    }

    public boolean isProductPageOpened() {
        log.info("Checking if Products page is opened...");
        try{
            findEl(ProductTitle);
            log.info("Products page is opened");
            return true;} catch (Exception e){
            log.warn("Products page is NOT opened");

            return false;
        }
    }

    public ProductModel selectProductNotInCart() {
        log.info("Selecting first product not in cart...");
        ScrollUtils scrollUtils = new ScrollUtils(driver);
        int maxScrolls = 3;
        for (int i = 0; i < maxScrolls; i++) {
            log.debug("Scroll attempt {}/{}", i + 1, maxScrolls);
            List<WebElement> products = findEls(productItem);

            for (WebElement product : products) {
                List<WebElement> addButtons = product.findElements(addToCartInsideItem);
                if (!addButtons.isEmpty()) {
                    String title = findElIn(product,titleInsideItem).getText();
                    String price = findElIn(product,priceInsideItem).getText();
                    addButtons.get(0).click();
                    log.info("Added product '{}' with price '{}' to cart", title, price);

                    return new ProductModel(title, price);
                }
            }
            scrollUtils.scrollUp();
        }
        log.warn("No available product found after {} scrolls", maxScrolls);
        throw new RuntimeException("No available product found after scrolling.");
    }

    public WebElement findProductByName(String productName) {
        log.info("Finding product by name: '{}'", productName);
        List<WebElement> products = findEls(productItem);
        for (WebElement product : products) {
            String title = findElIn(product,titleInsideItem).getText();
            if (title.equalsIgnoreCase(productName)) {
                return product;
            }
        }
        log.warn("Product not found: '{}'", productName);
        throw new RuntimeException("Product not found: " + productName);
    }

    public void removeProductWithParticularName(String productName){
        log.info("Removing product from cart: '{}'", productName);
        WebElement product = findProductByName(productName);
        findElIn(product, removeCartInsideItem).click();

    }

    public boolean isAddToCartButtonForProduct(String productName) {
        log.info("Checking if 'Add to Cart' button exists for '{}'", productName);
        WebElement product = findProductByName(productName);
        try{
            findElIn(product, addToCartInsideItem);
            log.info("'Add to Cart' button exists for '{}'", productName);
            return true;
        }catch(Exception e){
            log.info("'Add to Cart' button NOT found for '{}'", productName);
            return false;
        }
    }

    public boolean isRemoveButtonForProduct(String productName) {
        log.info("Checking if 'Remove' button exists for '{}'", productName);
        WebElement product = findProductByName(productName);
        try{
            findElIn(product, removeCartInsideItem);
            log.info("'Remove' button exists for '{}'", productName);
            return true;
        }catch(Exception e){
            log.info("'Remove' button NOT found for '{}'", productName);
            return false;
        }
    }

    public void selectSortOption(String optionText) {
        log.info("Selecting sort option: {}", optionText);
        try{
            log.debug("Clicking sort button: {}", sortButton);
            findEl(sortButton).click();
            String locator = String.format(sortOption, optionText);
            log.debug("Generated sort option locator: {}", locator);
            log.info("Clicking on sort option: {}", optionText);
            click(AppiumBy.androidUIAutomator(locator));
            log.info("Sort option '{}' selected successfully.", optionText);
        } catch (Exception e) {
            log.error("Failed to select sort option '{}': {}", optionText, e.getMessage(), e);
            throw e;
        }
    }

    public List<String> getAllProductTitles() {
        log.info("Extracting ALL product titles with scrolling...");
        List<String> titles= getAllTexts(titleInsideItem);
        log.info("Collected {} titles", titles.size());
        return titles;

    }

    public List<Double> getAllProductPrices() {
        log.info("Extracting ALL product prices with scrolling...");
        List<Double> prices= getAllTexts(priceInsideItem)
                .stream()
                .map(t -> Double.parseDouble(t.replace("$", "")))
                .toList();
        log.info("Collected {} prices", prices.size());
        return prices;
    }
}


