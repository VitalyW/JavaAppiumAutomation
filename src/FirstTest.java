import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class FirstTest {

  private AppiumDriver driver;

  @Before
  public void setUp() throws MalformedURLException {
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "AndroidTestDevice");
    capabilities.setCapability("platformVersion", "8.0");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability("app", "/Users/vitalykhuzeev/Documents/Code/JavaAppiumAutomation/apks/org.wikipedia.apk");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void firstTest() {

    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Can't find search input field",
            5
    );

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Can't find 'Object-oriented programming language' topic searching by 'Java'",
            5
    );
  }

  @Test
  public void testCancelSearch() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Can't find search input field",
            5
    );

    waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Can't find ",
            5
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Can't click on 'Cancel Search' button",
            5
    );

    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "'Cancel Search' button is still on the page",
            5
    );
  }

  @Test
  public void compareArticleTitle() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Can't find search input field",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Can't find 'Search Wikipedia' input",
            10
    );

    WebElement title_element = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find header of the article",
            15
    );

    String title_text = title_element.getAttribute("text");

    assertEquals(
            "Titles don't match",
            "Java (programming language)",
            title_text
    );

  }

  private WebElement waitForElementPresent(By by, String error_message, int timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by)
    );
  }

  private WebElement waitForElementPresent(By by, String error_message) {
    return waitForElementPresent(by, error_message);
  }

  private WebElement waitForElementAndClick(By by, String error_message, int timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(By by, String value, String error_message, int timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.sendKeys(value);
    return element;
  }

  private boolean waitForElementNotPresent(By by, String error_message, int timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by)
    );
  }

  private WebElement waitForElementAndClear(By by, String error_message, int timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.clear();
    return element;
  }

}
