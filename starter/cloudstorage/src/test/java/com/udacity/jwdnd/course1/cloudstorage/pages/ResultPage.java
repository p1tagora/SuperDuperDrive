package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ResultPage {
    private final WebDriverWait wait;

    @FindBy(id="a_success")
    private WebElement linkAfterSuccess;

    @FindBy(css="a_fail")
    private WebElement linkAfterFail;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.wait = new WebDriverWait(webDriver, 10);
    }

    public void clickAfterSuccess() {
        wait.withTimeout(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(linkAfterSuccess)).click();
    }
}
