/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinertest.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
/**
 *
 * @author NafanyaVictorovna
 */
public class SearchPage {
    
    private final static int PROPERTY_COUNT = 3;
    
    private final WebDriver driver;
    private List<String> stringProperties = new ArrayList<>();
    private NotebookPage laptop;
    
    @FindBy(how=How.XPATH, using = "//iframe[@class=\"modal-iframe\"]")
    WebElement frame;
            
    @FindBy(how=How.XPATH, using = "//input[@name=\"query\"]")
    private WebElement search;
    
    @FindBy(how=How.XPATH, using = "//input[@class=\"fast-search__input\"]")
    private WebElement searchBtn;
    
    @FindBy(how=How.XPATH, using = "//a[@target=\"_parent\"]")
    private WebElement aElement;
    
    @FindBy(how=How.XPATH, using = "//ul/li[1]/label")
    private List<WebElement> checkBtnList;
        
    @FindBy(how=How.XPATH, using = "//div[@class=\"schema-product__title\"]/a")
    private WebElement laptopLink;
    
    @FindBy(how=How.XPATH, using = "//ul/li[1]/label/span[2]")
    private List<WebElement> properties;    
    
    public ArrayList<String> returnCheckProperties(){
        for(WebElement prop : properties){
            if(stringProperties.size()<PROPERTY_COUNT){
                stringProperties.add(prop.getText());
            } else break;
        }
        return (ArrayList<String>) stringProperties;
    }    
        public boolean correctLaptop(){
        int tmp = 0;
        for (String prop : stringProperties) {            
            if(laptop.getValue(prop)) tmp++;
        }
        return tmp == stringProperties.size();
    }    
    public SearchPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public SearchPage setSearchValue(String value){
        search.sendKeys(value);
        return this;
    }

    public void clickSearchBtn(){
        searchBtn.click();
    }
    
    public void clickAElement(){
        driver.switchTo().frame(frame);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(SearchPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        aElement.click(); 
    }
    
    public void chooseLaptop(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(SearchPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", laptopLink);
        laptop = new NotebookPage(driver);
    }
    
    public void setCheckBtn(){
        int count = 0;
        for(WebElement checkBtn : checkBtnList){
            if(count<PROPERTY_COUNT){
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", checkBtn);
                count++;
            } else break;
        }
    }

    public void SearchAction(String value){
        this.clickSearchBtn();
        this.setSearchValue(value);
        this.clickAElement();
        this.setCheckBtn();
        this.returnCheckProperties();
        this.chooseLaptop();
    }        
}
