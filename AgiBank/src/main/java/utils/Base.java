package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class Base {

    public static WebDriver driver;
    public static Properties configProp;
    public static Properties dataProp;
    public final Duration timeoutDuration = Duration.ofSeconds(10);

    public static WebDriver navigateToURL() throws IOException {
        try{
            configProp = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\config.properties");
            configProp.load(fis);
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(configProp.getProperty("url"));
        } catch (Exception e) {
            System.out.println("Causa: " + e.getCause());
            System.out.println("Mensagem: " + e.getMessage());
            System.out.println(e.getStackTrace());
        }
        return driver;
    }

    public void highlightElement (WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: 2px solid red;");
            Thread.sleep(250);
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: 2px solid red;");

        } catch (Exception e) {
            System.out.println("Causa: " + e.getCause());
            System.out.println("Mensagem: " + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    public WebElement getWebElement (By obj) {
        WebElement element = null;
        try {
            List<WebElement> elements = driver.findElements(obj);
            int elecount = elements.size();
            if (elecount > 0) {
                if (elecount==1) {
                    if(elements.get(0).isDisplayed()) {
                        highlightElement(elements.get(0));
                        return elements.get(0);
                    } else {
                        return null;
                    }
                }
                else if (elecount > 1) {
                    for (int i=0;i<elecount;i++) {
                        if(elements.get(i).isDisplayed()) {
                            highlightElement(elements.get(i));
                            return elements.get(i);
                        }
                    }
                }
                else {
                    return null;
                }
            }
        }catch (Exception e) {
            System.out.println(e.getStackTrace());
            return null;
        }
        return element;
    }

    public void clickWebElement(By locator) throws InterruptedException {
        Thread.sleep(2000);
        try {
            WebElement element = getWebElement(locator);
            WebDriverWait 	wait = new WebDriverWait(driver, timeoutDuration);
            wait.until(ExpectedConditions.visibilityOf(element));
            element.click();
        }catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void sendKeys(By locator, String variable) throws InterruptedException {
        Thread.sleep(2000);
        try {
            dataProp = new Properties();
            InputStream input = new FileInputStream(new File(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\data.properties"));
            dataProp.load(input);
            String text = dataProp.getProperty(variable);
            WebElement element = getWebElement(locator);
            WebDriverWait 	wait = new WebDriverWait(driver, timeoutDuration);
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text + Keys.ENTER);
        }catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void verifyText(By locator, String expectedResult) {
        WebElement element = getWebElement(locator);
        WebDriverWait 	wait = new WebDriverWait(driver, timeoutDuration);
        wait.until(ExpectedConditions.visibilityOf(element));
        String text = driver.findElement(locator).getText();
        Assert.assertEquals(expectedResult, text);
    }



    public static void loadData() throws IOException {

    }
}
