package Tests;

import com.example.app.enums.SortOption;
import com.example.app.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class ProductsSortingTest extends BaseTest{


    @Test(dataProvider = "sortOptions", dataProviderClass = data.ProductSorting.class, description = "Sorting verification")
    public void verifySorting(SortOption option, String optionText){

        ProductsPage products = new ProductsPage(driver);

        products.selectSortOption(optionText);

        switch (option) {

            case NAME_ASC -> {
                List<String> names = products.getAllProductTitles();
                List<String> sorted = names.stream().sorted().toList();
                Assert.assertEquals(names, sorted, "Sorting NAME_ASC failed");
            }

            case NAME_DESC -> {
                List<String> names = products.getAllProductTitles();
                List<String> sorted = names.stream().sorted(Comparator.reverseOrder()).toList();
                Assert.assertEquals(names, sorted, "Sorting NAME_DESC failed");
            }

            case PRICE_ASC -> {
                List<Double> prices = products.getAllProductPrices();
                List<Double> sorted = prices.stream().sorted().toList();
                Assert.assertEquals(prices, sorted, "Sorting PRICE_ASC failed");
            }

            case PRICE_DESC -> {
                List<Double> prices = products.getAllProductPrices();
                List<Double> sorted = prices.stream().sorted(Comparator.reverseOrder()).toList();
                Assert.assertEquals(prices, sorted, "Sorting PRICE_DESC failed");
            }
        }
    }

}
