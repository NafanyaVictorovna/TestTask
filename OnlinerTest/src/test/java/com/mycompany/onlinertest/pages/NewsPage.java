/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinertest.pages;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
/**
 *
 * @author NafanyaVictorovna
 */
public class NewsPage {
    
    private static final int LENGTH = 3;
    
//    private final WebDriver driver;
    private boolean writeToFile = false;
    
    @FindBy(how=How.XPATH, using = "//div[@id]/a[@href=\"https://tech.onliner.by\"]")
    private WebElement tabLink;
    
    @FindBy(how=How.XPATH, using = "//time[@datetime]")
    private List<WebElement> dateList;
    
    @FindBy(how=How.XPATH, using = "//article/*/a/span[not(@class)]")
    private List<WebElement> titleList;
        
    public NewsPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    
    public void clickTab(){
        tabLink.click();
    }
    
    private ArrayList<String> getTitlesValue(){
        ArrayList<String> strTitle = new ArrayList<>();
        for(WebElement title: titleList){
            if(strTitle.size()<LENGTH){
                strTitle.add(title.getText());
            }
            
        }
        return strTitle;
    }

    private ArrayList<String> getDates(){
        ArrayList<String> strDate = new ArrayList<>();
        SimpleDateFormat resFormat = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ssX");
        for(WebElement date: dateList){
            if(strDate.size()<LENGTH){
                String attribute = date.getAttribute("datetime");
                    try {
                        Date d = resFormat.parse(attribute);
                        strDate.add(resFormat.format(d));
                    } catch (ParseException ex) {
                        Logger.getLogger(NewsPage.class.getName()).log(Level.SEVERE, null, ex);
                    }        
            }
        }
        return strDate;
    }

    private void writeDataToFile(String[] data){
        File file = new File("src/resources/files/3 articles.txt");
        try{
            if(!file.exists()){
                file.getParentFile().mkdirs(); 
                file.createNewFile();
            }
            try(PrintWriter outWriter = new PrintWriter(file.getAbsoluteFile())) 
            {
                for(int i=0; i<LENGTH; i++){
                    outWriter.print(data[i]);
                }
            } finally {
                writeToFile = true;
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    
    public boolean isWriting(){
        return writeToFile;
    }
    
    public void readNews(){
        String[] data = new String[dateList.size()];
        for (int i=0; i<data.length; i++) {
            if(i<LENGTH){
            data[i] = getTitlesValue().get(i)+" "+getDates().get(i);
            } else break;
        }
        this.writeDataToFile(data);
    }
    
    public void techNews(){
        this.clickTab();
        this.readNews();
    }
}
