package com.example.app.pages;

import com.example.app.utils.ScrollUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class OverviewPage extends BasePage{
    private final By OverviewTitle = By.xpath("//*[@text='CHECKOUT:OVERVIEW']");

    private final By finishButton = AppiumBy.accessibilityId("test-FINISH");

    public OverviewPage(AppiumDriver driver){
        super(driver);
    }
    public boolean isOverviewOpened() {
        try{
            findEl(OverviewTitle);
            return true;}
        catch (Exception e){
            return false;
        }
    }
    public boolean isSelectedProductTitleDisplayed(String itemTitle) {

        By titleLocator=By.xpath("//android.widget.TextView[@text='" + itemTitle + "']");
        try {
            findEl(titleLocator);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public boolean isSelectedProductPriceDisplayed(String itemPrice) {

        By titleLocator=By.xpath("//android.widget.TextView[@text='" + itemPrice + "']");
        try {
            findEl(titleLocator);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public ThankYouPage clickFinish(){
        ScrollUtils scrollUtils=new ScrollUtils(driver);
        scrollUtils.scrollUntilVisible(finishButton,3);
        click(finishButton);
        return new ThankYouPage(driver);
    }






}
