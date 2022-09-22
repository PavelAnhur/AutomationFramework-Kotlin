package org.example.core.pageobject.store

import org.example.core.pageobject.BasePage
import org.example.core.ui.element.UIElement
import org.example.core.ui.element.UIElementList
import org.openqa.selenium.By

class SearchPage : BasePage() {
    val productSearchResult = UIElementList(
        By.xpath("//*[@class='product-container']//a[@class='product-name']"),
        "products from search result"
    )

    fun waitForSearchResult(searchInput: String) = UIElement(
        By.xpath("//h1[contains(text(),'Search')]//*[contains(text(),'$searchInput')]"),
        "search title"
    ).waitForDisplayed()

    fun productName(index: Int): String = UIElement(
        By.xpath("//li[starts-with(@class,'ajax_block_product')][$index]//a[@class='product-name']"),
        "product name from search result"
    ).getText()
}
