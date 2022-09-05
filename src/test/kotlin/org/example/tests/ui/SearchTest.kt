package org.example.tests.ui

import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

open class SearchTest : BaseTest() {

    @Test(dataProvider = "searchTestData", description = "Validate search field")
    fun searchTest(searchInput: String) {
        reporter.info("<<<Search field validation>>>")
        homePageSteps
            .openHomePage()
            .searchForValue(searchInput)
        val productNamesWithoutSearchInput = searchPageSteps.getDiffList(searchInput)

        Assert.assertTrue(
            productNamesWithoutSearchInput.isEmpty(),
            """some of the search results don't contain $searchInput
                |Product names without search input:
                |$productNamesWithoutSearchInput
            """.trimMargin()
        )
    }

    @DataProvider
    fun searchTestData(): Iterator<String> {
        val data = listOf("t-shirt", "dress", "blouse")
        return data.listIterator()
    }
}
