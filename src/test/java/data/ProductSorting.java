package data;

import com.example.app.enums.SortOption;
import org.testng.annotations.DataProvider;

import java.util.Arrays;

public class ProductSorting {
    @DataProvider(name = "sortOptions")
    public Object[][] provideSortOptions() {

        // Convert enum values → Object[2] {enumValue, enumValue.text}
        return Arrays.stream(SortOption.values())
                .map(option -> new Object[]{option, option.text })
                .toArray(Object[][]::new);
    }

}
