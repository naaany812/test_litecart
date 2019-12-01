package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {
    WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver = driver;
        //add small check that the page is opened
        Assertions.assertTrue(driver.findElement(By.cssSelector("img[title='ACME Corp.']")).isDisplayed());
    }

    /**
     * Method find duck on the page and click on the tile
     * @param color - the color of the selected duck
     */
    public void selectDuckByColor(String color){
        driver.findElement(By.xpath("//div[text()='" + color + " Duck']")).click();
    }
}
