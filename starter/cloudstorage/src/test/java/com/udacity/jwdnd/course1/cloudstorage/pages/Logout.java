package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Logout {
    private WebDriverWait wait;

    @FindBy(css = "#logout-btn")
    private WebElement logoutBtn;

    public Logout(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout(WebDriver driver) throws InterruptedException {
        wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));
        Assertions.assertNotNull(logoutBtn);
        this.logoutBtn.click();
    }

}
