import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

public class FirstTest extends CoreTestCase {

  private MainPageObject MainPageObject;

  protected void setUp() throws Exception {
    super.setUp();
    MainPageObject = new MainPageObject(driver);
  }

  @Test
  public void testSearch() {
    SearchPageObject SearchPageObject = new SearchPageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.waitForSearchResult("Object-oriented programming language");
  }

  @Test
  public void testCancelSearch() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Can't find ",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Can't click on 'Cancel Search' button",
            5
    );

    MainPageObject.waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "'Cancel Search' button is still on the page",
            5
    );
  }

  @Test
  public void testCompareArticleTitle() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Can't find 'Search Wikipedia' input",
            10
    );

    WebElement title_element = MainPageObject.waitForElementPresent(
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
  public void testCompareSearchFieldText() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    WebElement search_field_input = MainPageObject.waitForElementPresent(
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
  public void testSearchForWordAndCancel() {
    String word = "mercedes";

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            word,
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/page_list_item_title"),
            "Can't find results for the given word",
            15
    );

    MainPageObject.ifTitlesHaveSpecificWord(
            By.id("org.wikipedia:id/page_list_item_title"),
            word,
            3
    );

  }

  @Test
  public void testSearchForAllMatchesOfWord() {
    String word = "bmw";

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            word,
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/page_list_item_title"),
            "Can't find results for the given word",
            15
    );

    MainPageObject.ifTitlesHaveSpecificWord(
            By.id("org.wikipedia:id/page_list_item_title"),
            word,
            MainPageObject.sizeOfTitles(By.id("org.wikipedia:id/page_list_item_title"))
    );

  }

  @Test
  public void testSwipeArticle() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Appium",
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Can't find 'Appium' article",
            10
    );

    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find header of the article",
            15
    );

    MainPageObject.swipeUpToFindElement(
            By.xpath("//*[@text='View page in browser']"),
            "Cannot find the end of the article",
            20
    );

  }

  @Test
  public void testSaveFirstArticleToMyList() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Can't find 'Search Wikipedia' input",
            10
    );

    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find header of the article",
            15
    );

    MainPageObject.waitForElementAndClick(
             By.xpath("//android.widget.ImageView[@content-desc='More options']"),
             "Cannot find button to open article options",
             5
     );

    MainPageObject.waitForElementAndClick(
             By.xpath("//*[@text='Add to reading list']"),
             "Cannot find option to add article to reading list",
             5
     );

    MainPageObject.waitForElementAndClick(
             By.id("org.wikipedia:id/onboarding_button"),
             "Cannot find 'Got It' button",
             5
     );

    MainPageObject.waitForElementAndClear(
             By.id("org.wikipedia:id/text_input"),
             "Cannot find input to set name of the article folder",
             5
     );

     String name_of_folder = "Learning programming";

    MainPageObject.waitForElementAndSendKeys(
             By.id("org.wikipedia:id/text_input"),
             name_of_folder,
             "Cannot input text to the article folder input",
             5
     );

    MainPageObject.waitForElementAndClick(
             By.xpath("//*[@text='OK']"),
             "Cannot find 'OK' button to save article to a folder",
             5
     );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot find X button to close the article",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to My Lists",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5
    );

    MainPageObject.swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article"
    );

    MainPageObject.waitForElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot delete saved article",
            5
    );

  }

  @Test
  public void testAmountOfNotEmptySearch() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    String search_feild = "linkin park discography";

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_feild,
            "Can't find search input field",
            5
    );

    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

    MainPageObject.waitForElementPresent(
            By.xpath(search_result_locator),
            "Cannot find anything by the given request " + search_feild,
            15
    );

    int amount_of_search_results = MainPageObject.getAmountOfElements(
            By.xpath(search_result_locator)
    );
    assertTrue(
            "We found too few results",
            amount_of_search_results > 0
    );

  }

  @Test
  public void testAmountOfEmptySearch() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    String search_line = "xsdfcghvbjnknb";

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Can't find search input field",
            5
    );

    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    String empty_result_label = "org.wikipedia:id/search_empty_text";

    MainPageObject.waitForElementPresent(
           By.id(empty_result_label),
           "Cannot find empty result label by the request: " + search_line,
           15
    );

    MainPageObject.assertElementNotPresent(
            By.xpath(search_result_locator),
            "We have found some results by the request: " + search_line
    );

  }

  @Test
  public void testChangeScreenOrientationOnSearchResults() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    String search_line = "Java";

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' article searching by " + search_line,
            10
    );

    String title_before_rotation = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = MainPageObject.waitForElementAndGetAttribute(
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

    String title_after_second_rotation = MainPageObject.waitForElementAndGetAttribute(
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
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Can't find 'Search Wikipedia' input",
            10
    );

    driver.runAppInBackground(3);

    MainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find article after returning from backgroun",
            10
    );

  }

  @Test
  public void testAssertArticleHasTitle() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    String search_line = "Java";

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' article searching by " + search_line,
            10
    );

    MainPageObject.assertElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "This article has no title element"
    );

  }

  @Test
  public void testSaveFirstTwoArticlesToMyList() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    String search_line = "Java";

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Can't find 'Search Wikipedia' input",
            10
    );

    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find header of the article",
            15
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got It' button",
            5
    );

    MainPageObject.waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of the article folder",
            5
    );

    String name_of_folder = "Learning programming";

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            name_of_folder,
            "Cannot input text to the article folder input",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot find 'OK' button to save article to a folder",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot find X button to close the article",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Can't find 'Search Wikipedia' input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Can't find search input field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Programming language']"),
            "Can't find 'Search Wikipedia' input",
            10
    );

    String title_before_adding_article_to_reading_list = MainPageObject.waitForElementAndGetAttribute(
            By.xpath("//*[@resource-id='org.wikipedia:id/view_page_subtitle_text']"),
            "text",
            "Cannot find title of article",
            10
    );

    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find header of the article",
            15
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find option to add article to reading list",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot find X button to close the article",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to My Lists",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5
    );

    MainPageObject.swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article"
    );

    MainPageObject.waitForElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot delete saved article",
            5
    );

    MainPageObject.assertElementPresent(
            By.xpath("//*[@text='programming language']"),
            "Second article is not in the folder"
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='programming language']"),
            "Cannot find created folder",
            5
    );

    String title_after_adding_article_to_reading_list = MainPageObject.waitForElementAndGetAttribute(
            By.xpath("//*[@resource-id='org.wikipedia:id/view_page_subtitle_text']"),
            "text",
            "Cannot find title of article",
            10
    );

    assertEquals(
            "Titles of the article before adding to the list and after are different",
            title_before_adding_article_to_reading_list,
            title_after_adding_article_to_reading_list
    );

  }

}
