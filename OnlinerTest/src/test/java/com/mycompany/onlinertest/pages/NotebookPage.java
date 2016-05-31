/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinertest.pages;

import java.util.List;
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
public class NotebookPage {
    
    private WebDriver driver;
            
    @FindBy(how=How.XPATH, using = "//h2[@class=\"catalog-masthead__title\"]")
    WebElement title;
    
    @FindBy(how=How.XPATH, using = "//span[@class=\"value__text\"]")
    List<WebElement> elementList;
    
    public NotebookPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public boolean getValue(String prop){
        boolean bool = false;
        for(WebElement element : elementList){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollBy(0,50)", "");
        String elementText = element.getText();
            if(elementText.equals(prop) || elementText.contains(prop)){
                bool = true;
                break;
            } else{ bool = false;}
        } if(title.getText().contains(prop)){
            bool = true;
        }
        return bool;
    }
}
