/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinertest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
/**
 * @author NafanyaVictorovna
 */
public class AutoLogin {

    private WebDriver driver;
    
    @FindBy(how=How.XPATH, using = "//div[@id=\"userbar\"]/*/div")
    private WebElement enter;
    
    @FindBy(how=How.XPATH, using = "//input[@data-field=\"login\"][1]")
    private WebElement login;
    @FindBy(how=How.XPATH, using = "//input[@type=\"password\"][1]")
    private WebElement password;
    
    @FindBy(how=How.XPATH, using = "//button[@class=\"auth-box__auth-submit "
                                    + "auth__btn auth__btn--green\"]")
    private WebElement loginBtn;

    @FindBy(how=How.XPATH, using = "//p[@class=\"user-name\"]")
    private WebElement userName;
    
    @FindBy (how=How.XPATH, using = "//a[@class=\"exit\"]")
    private WebElement exit;
           
    public AutoLogin(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public AutoLogin setLogin(String userLogin){
        login.sendKeys(userLogin);
        return this;
    }
    public AutoLogin setPassword(String userPassword){
        password.sendKeys(userPassword);
        return this;
    }
    public void clickEnterBtn(){
        enter.click();
    }
    public void clickLoginBtn(){
        loginBtn.click();
    }
    
    public boolean logIn(){
        return userName.isEnabled();
    }
    
    public void logOut(){
        exit.click();
    }
    public void loginToSite(String userLogin, String userPassword){
        this.clickEnterBtn();
        this.setLogin(userLogin);
        this.setPassword(userPassword);
        this.clickLoginBtn();
    }        
}
