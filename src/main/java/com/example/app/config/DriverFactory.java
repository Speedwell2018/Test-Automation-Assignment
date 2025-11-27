package com.example.app.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class DriverFactory {
    private static AppiumDriver driver;

    public static AppiumDriver getDriver(){
        if (driver==null){
            try{
                DesiredCapabilities caps = new DesiredCapabilities();

                caps.setCapability("platformName",System.getenv().get("PLATFORM_NAME"));
                caps.setCapability("platformVersion", System.getenv().get("PLATFORM_VERSION"));
                caps.setCapability("deviceName", System.getenv().get("DEVICE_NAME"));
                caps.setCapability("automationName", System.getenv().get("AUTOMATION_NAME"));
                caps.setCapability("app", System.getenv().get("PATH"));
                caps.setCapability("appActivity", ".MainActivity");
                caps.setCapability("newCommandTimeout", 300);

                driver = new AndroidDriver(new URL(System.getenv().get("APPIUM_SERVER")), caps);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                        Integer.parseInt(System.getenv().get("IMPLICIT_WAIT"))
                ));
            } catch (Exception e) {
                throw new RuntimeException("Failed to create Appium driver: " + e.getMessage(), e);
            }
        }
        return driver;
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
