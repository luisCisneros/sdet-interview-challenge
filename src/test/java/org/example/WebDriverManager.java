package org.example;

import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WebDriverManager {

    public static final String PROPERTIES_PATH = "src/test/resources/config.properties";
    private String url;
    private WebDriver driver;

    public String getUrl() {
        return url;
    }

    public WebDriver setUpDriver() {
        System.out.println("Properties file located at: " + PROPERTIES_PATH);
        Properties properties = getProperties(PROPERTIES_PATH);
        url = properties.getProperty("url");
        long timeout = Long.parseLong(properties.getProperty("timeout.in.seconds"));
        boolean isMaximizeWindow = Boolean.parseBoolean(properties.getProperty("maximize.window"));
        String chromeDriverLocation = properties.getProperty("chrome.driver.location");

        System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        if (isMaximizeWindow) {
            driver.manage().window().maximize();
        }

        return driver;
    }

    public Properties getProperties(String path) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public void teardown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String testName = scenario.getName();
            scenario.embed(screenshot, "image/png", testName);
        }
        driver.quit();
    }
}
