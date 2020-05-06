package pages;

import org.example.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DetailsPage extends WebPage {

    @FindBy(id = "R1MarketRedirect-1")
    private WebElement redirectPopUp;

    @FindBy(css = "#ProductPrice_productPrice_PriceContainer span")
    private WebElement productPrice;

    @FindBy(id = "buttonPanel_AddToCartButton")
    private WebElement addToCart;

    public DetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    public WebElement getAddToCart() {
        return addToCart;
    }

    public void closePopUpIfPresent() {
        if (redirectPopUp.isDisplayed()) {
            redirectPopUp.findElement(By.cssSelector("#R1MarketRedirect-1 button.glyph-cancel")).click();
        }
    }
}
