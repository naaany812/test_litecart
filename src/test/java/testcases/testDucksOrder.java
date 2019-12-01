package testcases;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.DuckDetailsPage;
import pages.LitecartHeader;
import pages.MainPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class testDucksOrder {

    static WebDriver driver = new ChromeDriver();
    Map<String, String> customerInputData = new HashMap<String, String>();
    Map<String, ArrayList<Float>> testDataOrder = new HashMap<String, ArrayList<Float>>();

    /**
     * Fill objects by testdata
     */
    public void configureTestData() {
        customerInputData.put("firstname", "ivan");
        customerInputData.put("lastname", "Ivanov");
        customerInputData.put("address1", "Nevskiy avenue");
        customerInputData.put("postcode", "193333");
        customerInputData.put("city", "Saint-Petersburg");
        customerInputData.put("country_code", "RU");
        customerInputData.put("email", "ivan.ivanov@testing.com");
        customerInputData.put("phone", "+78126654545");
        ArrayList<Float> duck1 = new ArrayList<Float>();
        duck1.add(0, 18.0f);
        duck1.add(1, 2.0f);
        ArrayList<Float> duck2 = new ArrayList<Float>();
        duck2.add(0, 20.0f);
        duck2.add(1, 3.0f);
        testDataOrder.put("Yellow Duck", duck1);
        testDataOrder.put("Purple Duck", duck2);
    }

    @BeforeAll
    public static void initDriver() {
        driver.get("http://localhost/litecart/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testOrderCreation() {
        MainPage mainPage = new MainPage(driver);
        mainPage.selectDuckByColor("Yellow");
        DuckDetailsPage duckPage = new DuckDetailsPage(driver);
        duckPage.selectSizeOfDuck('S');
        duckPage.enterCountOfDucksAndSubmit(2);
        LitecartHeader header = new LitecartHeader(driver);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElement(header.countOfItemsInCart, "2"));
        duckPage.selectDuckByColor("Purple");
        duckPage.enterCountOfDucksAndSubmit(3);
        header = new LitecartHeader(driver);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElement(header.countOfItemsInCart, "5"));
        header.openCart();
        CartPage cartPage = new CartPage(driver);
        configureTestData();
        cartPage.enterCustomerDetails(customerInputData);
        Float sum = checkOrderItemsAndGetOrderSum(testDataOrder, cartPage.orderItems);
        Assertions.assertEquals(sum, cartPage.orderSum, 0.0001, "Sum before shipping should be equal to calculated value");
        cartPage.sendTheOrder();
        header = new LitecartHeader(driver);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElement(header.countOfItemsInCart, ""));
        header.openCart();
        Assertions.assertTrue(cartPage.isShoppingCartEmpty());
    }

    /**
     * Check that there is correct data on ShoppingCart page and return price of order
     * @param order - arraylist with structure: Type of duck -> [price, count]
     * @param orderItems - data from shopping cart page
     * @return price for whole order
     */
    public Float checkOrderItemsAndGetOrderSum(Map<String, ArrayList<Float>> order, List<WebElement> orderItems) {
        Float sum = 0f;
        for (String duck : order.keySet()) {
            for (WebElement orderItem : orderItems) {
                if (orderItem.getAttribute("data-name").equalsIgnoreCase(duck)) {
                    Float price = order.get(duck).get(0);
                    Float count = order.get(duck).get(1);
                    Assertions.assertEquals(price, Float.valueOf(orderItem.getAttribute("data-price")), "Compare prices from test data and value on ShoppingCart Page");
                    Assertions.assertEquals(count, Float.valueOf(orderItem.getAttribute("data-quantity")), "Compare quantity from test data and value on ShoppingCart Page");
                    sum += price * count;
                    continue;
                }
            }
        }
        return sum;
    }

    @AfterAll
    public static void closeDriver() {
        driver.close();
    }
}
