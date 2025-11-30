package data;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVUtils {

    public static Object[][] readCSV(String filePath) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();
            Object[][] data = new Object[records.size() - 1][4]; // skip header
            for (int i = 1; i < records.size(); i++) {
                data[i - 1] = records.get(i);
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV: " + filePath, e);
        }
    }
}