package pages;

import org.example.WebPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WindowsPage extends WebPage {

    @FindBy(id = "c-shellmenu_50")
    private WebElement windows10Menu;

    @FindBy(xpath = "//button[@id='c-shellmenu_50']/following-sibling::ul/li/a")
    private List<WebElement> windows10MenuItems;

    @FindBy(id = "search")
    private WebElement searchButton;

    @FindBy(id = "cli_shellHeaderSearchInput")
    private WebElement searchInput;

    public WindowsPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getWindows10Menu() {
        return windows10Menu;
    }

    public List<WebElement> getWindows10MenuItems() {
        return windows10MenuItems;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    public WebElement getSearchInput() {
        return searchInput;
    }
}
