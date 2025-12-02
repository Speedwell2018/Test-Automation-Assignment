package Tests;

import com.example.app.components.HeaderComponent;
import com.example.app.config.DriverFactory;
import com.example.app.pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected AppiumDriver driver;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeClass
    public void setup() {
        log.info("Initializing driver for the test suite...");
        driver = DriverFactory.getDriver();
        log.info("Driver initialized and app launched.");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login();


    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        log.info("Tearing down driver and resetting app...");
        DriverFactory.quitDriver();
        log.info("Driver quit and app reset complete.");
    }


}
