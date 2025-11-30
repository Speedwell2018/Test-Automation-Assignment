package com.example.app.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class DriverFactory {
    private static AppiumDriver driver;
    private static Dotenv dotenv;

    static{
        dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir")) // папка проекта
                .ignoreIfMissing() // не упадёт, если .env нет
                .load();
    }

    public static AppiumDriver getDriver(){
        if (driver==null){

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
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                        Integer.parseInt(getenv("IMPLICIT_WAIT","5"))
                ));
            } catch (Exception e) {
                throw new RuntimeException("Failed to create Appium driver: " + e.getMessage(), e);
            }
        }
        return driver;
    }

    private static String getenv(String key, String defaultValue) {
        return dotenv.get(key, defaultValue);
    }


    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driver = null;
            }
        }
    }


}
