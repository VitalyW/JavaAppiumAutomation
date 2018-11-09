import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
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
    SearchPageObject SearchPageObject = new SearchPageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.waitForCancelButtonToAppear();
    SearchPageObject.clickCancelSearch();
    SearchPageObject.waitForCancelButtonToDisappear();
  }

  @Test
  public void testCompareArticleTitle() {
    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
    String article_title = ArticlePageObject.getArticleTitle();

    assertEquals(
            "Titles don't match",
            "Java (programming language)",
            article_title
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
    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Appium");
    SearchPageObject.clickByArticleWithSubstring("Appium");
    ArticlePageObject.swipeToFooter();
  }

  @Test
  public void testSaveFirstArticleToMyList() {

    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject.waitForTitleElement();

    String article_title = ArticlePageObject.getArticleTitle();
    String name_of_folder = "Learning programming";

    ArticlePageObject.addArticleToMyList(name_of_folder);
    ArticlePageObject.closeArticle();

    NavigationUI NavigationUI = new NavigationUI(driver);
    NavigationUI.clickMyLists();

    MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
    MyListsPageObject.openFolderByName(name_of_folder);

    MyListsPageObject.swipeByArticleToDelete(article_title);

  }

  @Test
  public void testAmountOfNotEmptySearch() {
    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    String search_field = "linkin park discography";
    SearchPageObject.typeSearchLine(search_field);
    int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
    assertTrue(
            "We found too few results",
            amount_of_search_results > 0
    );

  }

  @Test
  public void testAmountOfEmptySearch() {
    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    String search_field = "xsdfcghvbjnknb";
    SearchPageObject.typeSearchLine(search_field);
    SearchPageObject.waitForEmptyResultsLabel();
    SearchPageObject.assertThereIsNoResultOfSearch();

  }

  @Test
  public void testChangeScreenOrientationOnSearchResults() {
    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    String title_before_rotation = ArticlePageObject.getArticleTitle();

    rotateScreenLandscape();

    String title_after_rotation = ArticlePageObject.getArticleTitle();

    assertEquals(
            "Title of the article has been changed after rotation",
            title_before_rotation,
            title_after_rotation
    );

    rotateScreenPortrait();

    String title_after_second_rotation = ArticlePageObject.getArticleTitle();

    assertEquals(
            "Title of the article has been changed after rotation",
            title_before_rotation,
            title_after_second_rotation
    );

  }

  @Test
  public void testCheckSearchArticleInBackground() {
    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.waitForSearchResult("Object-oriented programming language");
    backgroundApp(5);
    SearchPageObject.waitForSearchResult("Object-oriented programming language");

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
