package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testSearchByTitleAndDescriptionFirstThreeOccurances() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        String search_line = "Java";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForElementByTitleAndDescription(search_line, "Island of Indonesia");
        SearchPageObject.waitForElementByTitleAndDescription(search_line, "Object-oriented programming language");
        SearchPageObject.waitForElementByTitleAndDescription(search_line, "Programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
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
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_field = "xsdfcghvbjnknb";
        SearchPageObject.typeSearchLine(search_field);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testSearchForWordAndCancel() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        String word = "mercedes";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(word);
        SearchPageObject.waitForListItemTitleToAppear();
        SearchPageObject.checkIfItemTitleHasSpecifiedWord(word, 3);
    }

    @Test
    public void testSearchForAllMatchesOfWord() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        String word = "bmw";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(word);
        SearchPageObject.waitForListItemTitleToAppear();
        SearchPageObject.checkIfAllItemTitlesHaveSpecifiedWord(word);
    }

}
