package org.example.steps

import io.qameta.allure.Step
import org.example.core.pageobject.SearchPage

class SearchPageSteps {
    private val searchPage = SearchPage()

    @Step("Get diff list of search result")
    fun getDiffList(expectedValue: String): List<String> {
        return searchPage.productNamesFromSearchResult()
            .filter { (!it.lowercase().contains(expectedValue)) }
            .toCollection(mutableListOf())
    }
}
