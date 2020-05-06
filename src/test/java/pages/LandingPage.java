package pages;

import org.example.WebPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LandingPage extends WebPage {

    @FindBy(css = "#uhf-g-nav li a")
    private List<WebElement> navigationBarLinks;

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> navigationBarLinks() {
        return navigationBarLinks;
    }
}
