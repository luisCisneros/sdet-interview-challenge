package pages;

import org.example.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CartPage extends WebPage {

    @FindBy(css = "div.item-quantity select")
    private WebElement itemQuantity;

    @FindBy(xpath = "//span/strong[contains(text(),'Total')]/../following-sibling::span//span[@itemprop='price']")
    private WebElement total;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getTotal() {
        return total;
    }

    public boolean areThePricesCorrect(String expectedPrice) {
        String itemSummaryPrice = driver.findElement(By.cssSelector("div.item-price span[itemprop='price'] span")).getText();
        String subtotal = driver.findElement(By.xpath("//h2[.='Order summary']/..//span[@itemprop='price']/span")).getText();

        return expectedPrice.equals(itemSummaryPrice) && expectedPrice.equals(subtotal) && expectedPrice.equals(total.getText());
    }

    public void selectItemQuantity(String quantity) {
        Select select = new Select(itemQuantity);
        select.selectByValue(quantity);
    }
}
