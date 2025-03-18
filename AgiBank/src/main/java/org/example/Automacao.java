package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.LandingPage;
import utils.Base;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Automacao extends Base {

    private WebDriver driver;
    LandingPage landingPage;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "caminho/para/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://blogdoagi.com.br/");
    }

    @Test
    public void testPesquisaComResultado() throws InterruptedException {
        sendKeys(landingPage.pesquisar, "finanças");
        verifyText(landingPage.textofinancas, "Renda Extra em 2024? 10 dicas para melhorar suas finanças");
    }

    @Test
    public void testPesquisaSemResultado() throws InterruptedException {
        sendKeys(landingPage.pesquisar, "semresultados");
        verifyText(landingPage.textofinancas, "Lamentamos, mas nada foi encontrado para sua pesquisa, tente novamente com outras palavras.");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}