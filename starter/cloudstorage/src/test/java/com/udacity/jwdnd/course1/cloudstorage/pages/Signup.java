package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Signup {
    @FindBy(css = "#inputFirstName" )
    private WebElement firstnameField;

    @FindBy(css = "#inputLastName")
    private WebElement lastnameField;

    @FindBy(css = "#inputUsername")
    private WebElement usernameField;

    @FindBy(css = "#inputPassword")
    private WebElement passwordField;

    @FindBy(css = "#submit-button")
    private WebElement submitButton;

    public Signup(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signup(String firstname, String lastname, String username, String password){
        this.firstnameField.sendKeys(firstname);
        this.lastnameField.sendKeys(lastname);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.submit();
    }
}
