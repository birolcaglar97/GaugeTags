package com.medium.steps;

import com.medium.driver.BaseTest;
import com.medium.driver.DriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BaseSteps extends BaseTest {

    public Logger logger;
    public BaseSteps() {
        logger = Logger.getLogger(DriverManager.class);
    }

    public WebElement findElement(By by){
        return webWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    public void waitSeconds(long seconds){
        try {
            Thread.sleep(seconds * 1000);
            logger.info(seconds+" kadar beklendi.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isElementVisible(By by){
        try{
            webDriver.findElement(by);
            logger.info(by+" elementi görünür.");
            return true;
        }catch (Exception e){
            logger.error(by+" elementi görünür değil.");
            return false;
        }
    }

    public boolean isElementVisibleWithWait(By by){
        try{
            findElement(by);
            logger.info(by+" elementi görünür.");
            return true;
        } catch (Exception e){
            logger.error(by+" elementi görünür değil.");
            return false;
        }
    }
}
