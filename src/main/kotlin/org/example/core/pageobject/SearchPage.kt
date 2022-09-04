package org.example.core.pageobject

import mu.KLogger
import mu.KotlinLogging
import org.example.core.ui.element.UIElement
import org.example.core.ui.element.UIElementList
import org.openqa.selenium.By

class SearchPage : BasePage() {
    private val log: KLogger = KotlinLogging.logger {}
    private val productSearchResult = UIElementList(
        By.xpath("//*[@class='product-container']//a[@class='product-name']"),
        "products from search result"
    )

    fun productNamesFromSearchResult(): List<String> {
        productSearchResult.waitForDisplayed()
        val productNames = ArrayList<String>()
        (1..productSearchResult.size()).forEach { index ->
            UIElement(
                By.xpath("//li[starts-with(@class,'ajax_block_product')][$index]//a[@class='product-name']"),
                "product name from search result"
            ).getText()
                .also {
                    log.info { "[$index] product name: $it" }
                    productNames.add(it)
                }
        }
        return productNames
    }
}
