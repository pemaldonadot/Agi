package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.Base;

public class LandingPage extends Base{

    public WebDriver driver;

    public By pesquisar = By.xpath("//span[text()='Pesquisar']/following-sibling::span[1]");
    public By textofinancas = By.xpath("//a[contains(text(),'Renda Extra em 2024? 10 dicas para melhorar suas finanças')]");

    public LandingPage(WebDriver driver) {
        this.driver = driver;
    }
}
