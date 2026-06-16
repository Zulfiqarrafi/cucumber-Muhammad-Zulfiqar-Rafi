package com.muhammadzulfiqarrafi.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StepDefinitions {

    WebDriver driver;

    @Before
    public void setup() {
        // Setup ChromeDriver (Pastikan Chrome terinstal)
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("User is on the login page")
    public void user_is_on_the_login_page() {
        driver.get("https://www.saucedemo.com/");
    }

    @When("User inputs valid username {string} and password {string}")
    public void user_inputs_valid_credentials(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @When("User inputs invalid username {string} and password {string}")
    public void user_inputs_invalid_credentials(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @And("User clicks login button")
    public void user_clicks_login_button() {
        driver.findElement(By.id("login-button")).click();
    }

    @Then("User is redirected to the products page")
    public void user_is_redirected_to_products() {
        boolean isLogoDisplayed = driver.findElement(By.className("app_logo")).isDisplayed();
        Assert.assertTrue("Failed to login", isLogoDisplayed);
    }

    @Then("Error message should be displayed")
    public void error_message_should_be_displayed() {
        boolean isErrorDisplayed = driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed();
        Assert.assertTrue("Error message not shown", isErrorDisplayed);
    }

    @Given("User is on the products page")
    public void user_is_on_products_page() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    @When("User selects sort option {string}")
    public void user_selects_sort_option(String visibleText) {
        Select sortDropdown = new Select(driver.findElement(By.className("product_sort_container")));
        sortDropdown.selectByVisibleText(visibleText);
    }

    // IMPLEMENTASI COLLECTION LIST<WEBELEMENT>
    @Then("The products should be sorted ascending by price")
    public void products_sorted_ascending() {
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> actualPrices = new ArrayList<>();

        for (WebElement element : priceElements) {
            // Hilangkan tanda "$" lalu convert ke Double
            actualPrices.add(Double.parseDouble(element.getText().replace("$", "")));
        }

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices); // Sort default (Low to High)

        Assert.assertEquals("Prices are not sorted low to high!", expectedPrices, actualPrices);
    }

    @Then("The products should be sorted descending by price")
    public void products_sorted_descending() {
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> actualPrices = new ArrayList<>();

        for (WebElement element : priceElements) {
            actualPrices.add(Double.parseDouble(element.getText().replace("$", "")));
        }

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        expectedPrices.sort(Collections.reverseOrder()); // Sort (High to Low)

        Assert.assertEquals("Prices are not sorted high to low!", expectedPrices, actualPrices);
    }

    @When("User clicks add to cart on the first product")
    public void add_product_to_cart() {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
    }

    @Then("The cart badge should increase")
    public void cart_badge_should_increase() {
        String badgeText = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals("Cart badge did not increase", "1", badgeText);
    }
}
