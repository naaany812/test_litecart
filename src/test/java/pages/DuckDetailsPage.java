package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DuckDetailsPage {
    WebDriver driver;
    String title;
    String color;

    public DuckDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.title = driver.findElement(By.cssSelector("h1.title")).getText();
        //add small check that the page is opened
        Assertions.assertTrue(title.endsWith("Duck"));
        //define the color of selected duck
        color = title.replace(" Duck", "");

    }

    /**
     * Method that select size of duck if it is possible
     *
     * @param size Either 'S', 'M' or 'L'
     */
    public void selectSizeOfDuck(char size) {
        Select selectSize = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
        switch (size) {
            case 'S':
                selectSize.selectByValue("Small");
                break;
            case 'M':
                selectSize.selectByValue("Medium");
                break;
            case 'L':
                selectSize.selectByValue("Large");
                break;
        }
    }

    /**
     * Method that adds a needed quantity of ducks to cart
     *
     * @param quantity Count of ducks
     */
    public void enterCountOfDucksAndSubmit(Integer quantity) {
        WebElement inputQuantity = driver.findElement(By.cssSelector("input[name='quantity']"));
        WebElement addToCartButton = driver.findElement(By.cssSelector("button[name='add_cart_product']"));
        inputQuantity.clear();
        inputQuantity.sendKeys(quantity.toString());
        addToCartButton.click();
    }


    public void selectDuckByColor(String color) {
        if (!this.color.equals(color)) {
            driver.findElement(By.xpath("//div[text()='" + color + " Duck']")).click();
        }
    }

}
