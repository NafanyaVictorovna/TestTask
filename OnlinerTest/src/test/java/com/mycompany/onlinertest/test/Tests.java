/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinertest.test;

import com.mycompany.onlinertest.pages.AutoLogin;
import com.mycompany.onlinertest.pages.NewsPage;
import com.mycompany.onlinertest.pages.SearchPage;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
/**
 *
 * @author NafanyaVictorovna
 */
public class Tests {
    
    WebDriver driver;
    AutoLogin objectLogin;
    NewsPage techNews;
    SearchPage search;  
    
    @BeforeClass
    public void setup(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"./src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
    }  
    
    @BeforeMethod
    public void setUp(){
        driver.get("https://www.onliner.by");
        objectLogin = new AutoLogin(driver);
    }
    @AfterClass
    public void tearDown(){
        driver.close();
    }
    @DataProvider
    private Object[][] setData(){
        return new Object[][]{
            {"@mail.ru", ".*-/"},
            {"babochkagirl@mail.ru", "remember"},
            {"11 111 111", "1 2 3 6 5 4 7 8 9"}
        };
    } 
    @Test(dataProvider = "setData")
    public void loginTest(String login, String password){
        try{
            objectLogin.loginToSite(login, password);
            if(objectLogin.logIn()){
                System.out.println("user logged");
                objectLogin.logOut();
            }
        } catch(ElementNotVisibleException e){
            throw new ElementNotVisibleException(" wrong data");
        } 
    }
    @Test
    public void searchTest(){
        objectLogin.loginToSite("babochkagirl@mail.ru", "remember");
        search = new SearchPage(driver);   
        search.SearchAction("Ноутбук");
         if(search.correctLaptop()){
            System.out.println("correct laptop");
        } else {
            System.out.println("wrong result");
        }
        objectLogin.logOut();
    }
    @Test
    public void TechTabTest(){
        objectLogin.loginToSite("babochkagirl@mail.ru", "remember");
        techNews = new NewsPage(driver);
        techNews.techNews();
        if(techNews.isWriting()){
            System.out.println("check file");
        } else { 
            System.out.println("don't create file");
        }
        objectLogin.logOut();
    }
}