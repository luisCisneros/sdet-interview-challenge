package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.example.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChallengeSteps {

    private WebDriverManager webDriverManager;
    private WebDriver driver;
    private WindowsPage windowsPage;
    private List<WebElement> navigationBarLinks;
    private List<WebElement> items;
    private List<String> prices;
    private String firstPrice;
    private DetailsPage detailsPage;
    private CartPage cartPage;

    @Before
    public void setUp() {
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.setUpDriver();
    }

    @After
    public void after(Scenario scenario) {
        webDriverManager.teardown(scenario);
    }

    @Given("Go to {string}")
    public void goTo(String expectedUrl) {
        driver.get(webDriverManager.getUrl());
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl);
    }

    @Then("Validate all menu items are present:")
    public void validateAllMenuItemsArePresent(List<String> expectedMenuItems) {
        LandingPage landingPage = new LandingPage(driver);
        navigationBarLinks = landingPage.navigationBarLinks();
        List<String> actualItems = new ArrayList<>();
        for (WebElement element : navigationBarLinks) {
            actualItems.add(element.getText());
        }
        assertTrue(actualItems.containsAll(expectedMenuItems));
    }

    @And("Go to Windows")
    public void goToWindows() {
        for (WebElement element : navigationBarLinks) {
            if (element.getText().equals("Windows")) {
                element.click();
                break;
            }
        }
    }

    @And("Once in Windows page, click on Windows 10 Menu")
    public void onceInWindowsPageClickOnWindowsMenu() {
        windowsPage = new WindowsPage(driver);
        WebElement windows10Menu = windowsPage.getWindows10Menu();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try { // I know this section looks weird combining explicit wait and sleep, but this was the only way I made it work before the deadline :s
            Thread.sleep(2000);
            wait.until(ExpectedConditions.elementToBeClickable(windows10Menu));
            windows10Menu.click();
        } catch (StaleElementReferenceException | InterruptedException e) {
            wait.until(ExpectedConditions.elementToBeClickable(windows10Menu));
            windows10Menu.click();
        }
    }

    @And("Print all Elements in the dropdown")
    public void printAllElementsInTheDropdown() {
        List<WebElement> menuItems = windowsPage.getWindows10MenuItems();
        for (WebElement element : menuItems) {
            System.out.println(element.getText());
        }
    }

    @And("Go to Search next to the shopping cart")
    public void goToSearchNextToTheShoppingCart() {
        windowsPage.getSearchButton().click();
    }

    @And("Search for {string}")
    public void searchForVisualStudio(String searchTerm) {
        WebElement searchInput = windowsPage.getSearchInput();
        searchInput.sendKeys(searchTerm);
        searchInput.sendKeys(Keys.ENTER);
    }

    @And("Print the price for the {int} first elements listed in {string} result list")
    public void printThePriceForTheFirstElementsListedInSoftwareResultList(int count, String category) {
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.closePopUpIfPresent();
        items = searchResultsPage.getResultsFromCategory(category);
        prices = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String price = items.get(i).findElement(By.cssSelector("span[itemprop='price']")).getText();
            System.out.println(price);
            prices.add(price);
        }
    }

    @And("Store the price of the first one")
    public void storeThePriceOfTheFirstOne() {
        firstPrice = prices.get(0);
    }

    @And("Click on the first one to go to the details page")
    public void clickOnTheFirstOneToGoToTheDetailsPage() {
        items.get(0).click();
    }

    @And("Once in the details page, validate both prices are the same")
    public void onceInTheDetailsPageValidateBothPricesAreTheSame() {
        detailsPage = new DetailsPage(driver);
        detailsPage.closePopUpIfPresent();
        String actualPrice = detailsPage.getProductPrice();
        assertEquals(firstPrice, actualPrice);
    }

    @And("Click Add To Cart")
    public void clickAddToCart() {
        detailsPage.getAddToCart().click();
    }

    @Then("Verify all 3 price amounts are the same")
    public void verifyAllPriceAmountsAreTheSame() {
        cartPage = new CartPage(driver);
        assertTrue(cartPage.areThePricesCorrect(firstPrice));
    }

    @And("On the # of items dropdown select {string} and validate the Total amount is Unit Price * {int}")
    public void onTheOfItemsDropdownSelectAndValidateTheTotalAmountIsUnitPrice(String numberOfItems, int quantity) {
        cartPage.selectItemQuantity(numberOfItems);
        double unitPrice = Double.parseDouble(firstPrice.replaceAll("\\$|,", ""));
        double expectedTotal = unitPrice * quantity;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double actualTotal = Double.parseDouble(cartPage.getTotal().getText().replaceAll("\\$|,", ""));
        assertEquals(expectedTotal, actualTotal, 0.001);
    }
}
