package pages;

import org.example.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPage extends WebPage {

    @FindBy(id = "R1MarketRedirect-1")
    private WebElement redirectPopUp;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void closePopUpIfPresent() {
        if (redirectPopUp.isDisplayed()) {
            redirectPopUp.findElement(By.cssSelector("#R1MarketRedirect-1 button.glyph-cancel")).click();
        }
    }

    public List<WebElement> getResultsFromCategory(String category) {
        return driver.findElements(By.cssSelector("div[aria-label*='" + category.toLowerCase() + "'] a"));
    }
}
