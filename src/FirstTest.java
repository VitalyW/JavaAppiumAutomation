import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;

public class FirstTest extends CoreTestCase {

  private MainPageObject MainPageObject;

  protected void setUp() throws Exception {
    super.setUp();
    MainPageObject = new MainPageObject(driver);
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
