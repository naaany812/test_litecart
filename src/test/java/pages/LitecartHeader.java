package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LitecartHeader {
    public WebElement countOfItemsInCart;
    WebDriver driver;

    public LitecartHeader(WebDriver driver) {
        this.driver = driver;
        countOfItemsInCart = driver.findElement(By.className("quantity"));
    }

    /**
     * Open shopping cart page
     */
    public void openCart() {
        WebElement cart = driver.findElement(By.id("cart"));
        cart.click();
    }

}
