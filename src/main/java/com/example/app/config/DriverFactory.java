package com.example.app.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;

public class DriverFactory {
    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private static AppiumDriver driver;
    private static Dotenv dotenv;

    static{
        log.info("Initializing Dotenv and loading environment variables...");
        dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir")) // папка проекта
                .ignoreIfMissing() // не упадёт, если .env нет
                .load();
        log.info(".env file loaded successfully (or ignored if missing).");
    }

    public static AppiumDriver getDriver(){
        if (driver==null){
            log.info("Driver is null. Creating a new Appium driver instance...");

            try{
                DesiredCapabilities caps = new DesiredCapabilities();

                caps.setCapability("platformName",getenv("PLATFORM_NAME", "Android"));
                caps.setCapability("appium:platformVersion", getenv("PLATFORM_VERSION", "16"));
                caps.setCapability("appium:deviceName", getenv("DEVICE_NAME", "emulator-5554"));
                caps.setCapability("appium:automationName", getenv("AUTOMATION_NAME", "UiAutomator2"));
                caps.setCapability("appium:app", getenv("APP_PATH",".app/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk"));
                //caps.setCapability("appium:appActivity", ".MainActivity");
                caps.setCapability("appium:appPackage", "com.swaglabsmobileapp");
                caps.setCapability("appium:appActivity", "com.swaglabsmobileapp.MainActivity");


                caps.setCapability("appium:adbExecTimeout", 30000);
                caps.setCapability("appium:uiautomator2ServerInstallTimeout", 60000);
                caps.setCapability("appium:uiautomator2ServerLaunchTimeout", 60000);

                caps.setCapability("appium:newCommandTimeout", 120);
                //caps.setCapability("appium:noReset", true);
                //caps.setCapability("appium:fullReset", false);
                caps.setCapability("appium:skipDeviceInitialization", true);
                caps.setCapability("appium:skipServerInstallation", true);
                caps.setCapability("appium:skipUnlock", true);
                caps.setCapability("appium:autoGrantPermissions", true);
                caps.setCapability("appium:skipServerInstallation", true);
                caps.setCapability("appium:ignoreHiddenApiPolicyError", true);

                driver = new AndroidDriver(new URL(getenv("APPIUM_SERVER", "http://127.0.0.1:4723")), caps);
                int implicitWait = Integer.parseInt(getenv("IMPLICIT_WAIT", "15"));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
                log.info("Appium driver created successfully with implicit wait: {} sec", implicitWait);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create Appium driver: " + e.getMessage(), e);
            }
        }
        log.debug("Returning existing Appium driver instance.");
        return driver;
    }

    private static String getenv(String key, String defaultValue) {

        String value = dotenv.get(key, defaultValue);
        log.debug("Env variable {} = {}", key, value);
        return value;
    }


    public static void quitDriver() {
        if (driver != null) {
            log.info("Quitting Appium driver...");
            try {
                driver.quit();
                log.info("Driver quit successfully.");
            } finally {
                driver = null;
                log.debug("Driver reference cleared (set to null).");
            }
        }
    }


}
