package data;

import org.testng.annotations.DataProvider;
import java.io.IOException;

public class CheckoutData {

    @DataProvider(name = "checkoutFieldValidationData")
    public static Object[][] checkoutFieldValidationData() throws IOException {
        return CSVUtils.readCSV("src/test/resources/testdata/checkout_validation_data.csv");
    }

    @DataProvider(name = "checkoutFlowData")
    public static Object[][] checkoutFlowData() throws IOException {
        return CSVUtils.readCSV("src/test/resources/testdata/checkout_flow_data.csv");
    }

    @DataProvider(name = "sortingOptions")
    public static Object[][] sortingOptions() throws IOException {
        return CSVUtils.readCSV("src/test/resources/testdata/sorting_options_list.csv");
    }

}