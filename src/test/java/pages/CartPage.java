package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Map;

public class CartPage {
    public float orderSum = 0;
    public List<WebElement> orderItems;
    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        Assertions.assertTrue(driver.findElement(By.cssSelector("h2.title")).getText().equalsIgnoreCase("Shopping Cart"));
        orderItems = driver.findElements(By.cssSelector("tr.item"));
    }

    /**
     * On the shopping cart enter all testdata to corresponding input fields
     * @param customerDetails testdata Map object
     */
    public void enterCustomerDetails(Map<String, String> customerDetails) {
        orderSum = Float.parseFloat(driver.findElement(By.className("formatted-value")).getText().replace("$", ""));
        WebElement inputFirstName = driver.findElement(By.cssSelector("input[name='firstname']"));
        WebElement inputLastName = driver.findElement(By.cssSelector("input[name='lastname']"));
        WebElement inputAddress = driver.findElement(By.cssSelector("input[name='address1']"));
        WebElement inputPostcode = driver.findElement(By.cssSelector("input[name='postcode']"));
        WebElement inputCity = driver.findElement(By.cssSelector("input[name='city']"));
        Select selectCountry = new Select(driver.findElement(By.cssSelector("select[name='country_code']")));
        WebElement inputEmail = driver.findElement(By.cssSelector("input[name='email']"));
        WebElement inputPhone = driver.findElement(By.cssSelector("input[name='phone']"));
        WebElement saveDetailsButton = driver.findElement(By.cssSelector("button[name='save_customer_details']"));
        inputFirstName.clear();
        inputFirstName.sendKeys(customerDetails.get("firstname"));
        inputLastName.clear();
        inputLastName.sendKeys(customerDetails.get("lastname"));
        inputAddress.clear();
        inputAddress.sendKeys(customerDetails.get("address1"));
        inputPostcode.clear();
        inputPostcode.sendKeys(customerDetails.get("postcode"));
        inputCity.clear();
        inputCity.sendKeys(customerDetails.get("city"));
        selectCountry.selectByValue(customerDetails.get("country_code"));
        inputEmail.clear();
        inputEmail.sendKeys(customerDetails.get("email"));
        inputPhone.clear();
        inputPhone.sendKeys(customerDetails.get("phone"));
        saveDetailsButton.click();
    }

    /**
     * Click the checkbox and press the confirm button.
     */
    public void sendTheOrder() {
        WebElement consentCheckbox = driver.findElement(By.cssSelector("input[name='terms_agreed']"));
        WebElement confirmOrderButton = driver.findElement(By.cssSelector("button[name='confirm_order']"));
        consentCheckbox.click();
        confirmOrderButton.click();
    }

    /**
     * Check that the shipping cart is empty
     * @return true or false
     */
    public boolean isShoppingCartEmpty() {
        return driver.findElement(By.xpath("//*[text()='There are no items in your cart.']")).isDisplayed();
    }

}
