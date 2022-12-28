package com.medium.implementations;

import com.medium.steps.BaseSteps;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;

public class Implementations {
    public BaseSteps baseSteps;

    public Implementations() {
        baseSteps = new BaseSteps();
    }

    @Step("Xpath ile <xpath> elementin görünürlüğü kontrol edilir.")
    public void isElemenVisible(String xpath) {
        baseSteps.isElementVisible(By.xpath(xpath));
    }

    @Step("Xpath ile <xpath> elementin görünürlüğü dinamik bekleme ile kontrol edilir.")
    public void isElemenVisibleWithWait(String xpath) {
        baseSteps.isElementVisibleWithWait(By.xpath(xpath));
    }

    @Step("Saniye <seconds> kadar beklenir.")
    public void waitSeconds(long seconds) {
        baseSteps.waitSeconds(seconds);
    }
}
