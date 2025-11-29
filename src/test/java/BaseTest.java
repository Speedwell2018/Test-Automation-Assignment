import com.example.app.config.DriverFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.appium.java_client.AppiumDriver;

public class BaseTest {

    protected AppiumDriver driver;

    @BeforeClass
    public void setup() {
        driver = DriverFactory.getDriver();
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        DriverFactory.quitDriver();
    }


}
