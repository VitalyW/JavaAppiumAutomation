import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    driver.rotate(ScreenOrientation.PORTRAIT);
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

  @Test
  public void compareSearchFieldText() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    WebElement search_field_input = waitForElementPresent(
            By.id("org.wikipedia:id/search_src_text"),
            "Can't find search input field",
            15
    );

    String search_field_input_text = search_field_input.getAttribute("text");

    assertEquals(
            "Text don't match",
            "Search…",
            search_field_input_text
    );

  }

  @Test
  public void searchForWordAndCancel() {
    String word = "mercedes";

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            word,
            "Can't find search input field",
            5
    );

    waitForElementPresent(
            By.id("org.wikipedia:id/page_list_item_title"),
            "Can't find results for the given word",
            15
    );

    ifTitlesHaveSpecificWord(
            By.id("org.wikipedia:id/page_list_item_title"),
            word,
            3
    );

  }

  @Test
  public void searchForAllMatchesOfWord() {
    String word = "bmw";

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            word,
            "Can't find search input field",
            5
    );

    waitForElementPresent(
            By.id("org.wikipedia:id/page_list_item_title"),
            "Can't find results for the given word",
            15
    );

    ifTitlesHaveSpecificWord(
            By.id("org.wikipedia:id/page_list_item_title"),
            word,
            sizeOfTitles(By.id("org.wikipedia:id/page_list_item_title"))
    );

  }

  @Test
  public void testSwipeArticle() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Appium",
            "Can't find search input field",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Can't find 'Appium' article",
            10
    );

    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find header of the article",
            15
    );

    swipeUpToFindElement(
            By.xpath("//*[@text='View page in browser']"),
            "Cannot find the end of the article",
            20
    );

  }

  @Test
  public void saveFirstArticleToMyList() {
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

     waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find header of the article",
            15
    );

     waitForElementAndClick(
             By.xpath("//android.widget.ImageView[@content-desc='More options']"),
             "Cannot find button to open article options",
             5
     );

     waitForElementAndClick(
             By.xpath("//*[@text='Add to reading list']"),
             "Cannot find option to add article to reading list",
             5
     );

     waitForElementAndClick(
             By.id("org.wikipedia:id/onboarding_button"),
             "Cannot find 'Got It' button",
             5
     );

     waitForElementAndClear(
             By.id("org.wikipedia:id/text_input"),
             "Cannot find input to set name of the article folder",
             5
     );

     String name_of_folder = "Learning programming";

     waitForElementAndSendKeys(
             By.id("org.wikipedia:id/text_input"),
             name_of_folder,
             "Cannot input text to the article folder input",
             5
     );

     waitForElementAndClick(
             By.xpath("//*[@text='OK']"),
             "Cannot find 'OK' button to save article to a folder",
             5
     );

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot find X button to close the article",
            5
    );

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to My Lists",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5
    );

    swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article"
    );

    waitForElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot delete saved article",
            5
    );

  }

  @Test
  public void testAmountOfNotEmptySearch() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    String search_feild = "linkin park discography";

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_feild,
            "Can't find search input field",
            5
    );

    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

    waitForElementPresent(
            By.xpath(search_result_locator),
            "Cannot find anything by the given request " + search_feild,
            15
    );

    int amount_of_search_results = getAmountOfElements(
            By.xpath(search_result_locator)
    );
    assertTrue(
            "We found too few results",
            amount_of_search_results > 0
    );

  }

  @Test
  public void testAmountOfEmptySearch() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    String search_line = "xsdfcghvbjnknb";

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Can't find search input field",
            5
    );

    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    String empty_result_label = "org.wikipedia:id/search_empty_text";

    waitForElementPresent(
           By.id(empty_result_label),
           "Cannot find empty result label by the request: " + search_line,
           15
    );

    assertElementNotPresent(
            By.xpath(search_result_locator),
            "We have found some results by the request: " + search_line
    );

  }

  @Test
  public void testChangeScreenOrientationOnSearchResults() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    String search_line = "Java";

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Can't find search input field",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' article searching by " + search_line,
            10
    );

    String title_before_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    assertEquals(
            "Title of the article has been changed after rotation",
            title_before_rotation,
            title_after_rotation
    );

    driver.rotate(ScreenOrientation.PORTRAIT);

    String title_after_second_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    assertEquals(
            "Title of the article has been changed after rotation",
            title_before_rotation,
            title_after_second_rotation
    );

  }

  @Test
  public void testCheckSearchArticleInBackground() {
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

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Can't find 'Search Wikipedia' input",
            10
    );

    driver.runAppInBackground(3);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find article after returning from backgroun",
            10
    );

  }

  @Test
  public void assertArticleHasTitle() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    String search_line = "Java";

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Can't find search input field",
            5
    );

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' article searching by " + search_line,
            10
    );

    assertElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "This article has no title element"
    );

  }

  private void assertElementPresent(By by, String error_message) {
    int amount_of_elements = getAmountOfElements(by);
    if (amount_of_elements == 0) {
      String default_message = "An element " + by.toString() + " is supposed to be present!";
      throw new AssertionError(default_message + " " + error_message);
    }
  }

  private String waitForElementAndGetAttribute(By by, String attribute, String error_message, int timeOut) {
    WebElement element = waitForElementPresent(by, error_message, timeOut);
    return element.getAttribute(attribute);
  }

  private void assertElementNotPresent(By by, String error_message) {
    int amount_of_elements = getAmountOfElements(by);
    if (amount_of_elements > 0) {
      String default_message = "An element " + by.toString() + " is not supposed to be present!";
      throw new AssertionError(default_message + " " + error_message);
    }
  }

  private int getAmountOfElements(By by) {
    List elements = driver.findElements(by);
    return elements.size();
  }

  private void swipeElementToLeft(By by, String error_message) {
    WebElement element = waitForElementPresent(
            by,
            error_message,
            10
    );

    int left_x = element.getLocation().getX();
    int right_x = left_x + element.getSize().getWidth();
    int upper_y = element.getLocation().getY();
    int lower_y = upper_y + element.getSize().getHeight();
    int middle_y = (upper_y + lower_y) / 2;

    TouchAction action = new TouchAction(driver);
    action
            .press(right_x, middle_y)
            .waitAction(200)
            .moveTo(left_x, middle_y)
            .release()
            .perform();
  }


  private void swipeUp(int timeOut) {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int start_y = (int) (size.height * 0.8);
    int end_y = (int) (size.height * 0.2);

    action
            .press(x, start_y)
            .waitAction(timeOut)
            .moveTo(x, end_y)
            .release()
            .perform();
  }

  private void swipeUpQuick() {
    swipeUp(200);
  }

  private void swipeUpToFindElement(By by, String error_message, int max_swipe) {
    int already_swiped = 0;

    while (driver.findElements(by).size() == 0) {
      if (already_swiped > max_swipe) {
        waitForElementPresent(by, "Cannot find element by swipping up. \n" + error_message, 0);
        return;
      }
      swipeUpQuick();
      ++already_swiped;
    }
  }

  private int sizeOfTitles(By by) {
    return driver.findElements(by).size();
  }

  private boolean ifTitlesHaveSpecificWord(By by, String word, int howManyMatchesToCheck) {
    List<WebElement> all_title_elements = driver.findElements(by);
    List<String> all_title_elements_text = new ArrayList<>();

    for (int i = 0; i < howManyMatchesToCheck; i++) {
      all_title_elements_text.add(all_title_elements.get(i).getAttribute("text").trim().toLowerCase());
    }

    for (String title_text : all_title_elements_text) {
      if (title_text.contains(word)) {
        return true;
      }
    }
    return false;
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
