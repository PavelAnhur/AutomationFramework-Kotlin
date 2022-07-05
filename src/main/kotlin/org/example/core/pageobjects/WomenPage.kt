package org.example.core.pageobjects

import org.example.core.utils.UIElement
import org.openqa.selenium.By

class WomenPage : BasePage() {
    private val sortDropdown = UIElement(By.id("productsSortForm"), "sort order dropdown")
    
    fun selectSortOrder(sortOrder: String): WomenPage {
        logger.info { "Sorting products.." }
        sortDropdown.click()
        UIElement(
            By.xpath("//*[@value='${getSortLocatorAttributeValue(sortOrder)}']"),
            "order from sort dropdown"
        ).click()
            .also { return this }
    }
    
    private fun getSortLocatorAttributeValue(sortOrder: String): String? {
        var value: String? = null
        when {
            sortOrder.lowercase().startsWith("desc") -> value = "price:desc"
            sortOrder.lowercase().startsWith("asc") -> value = "price:asc"
        }
        return value
    }
    
    fun selectProductView(view: String): WomenPage {
        logger.info { "Changing products view.." }
        UIElement(
            By.xpath("//a[@title='${getViewLocatorAttributeTitle(view)}']"),
            "product view"
        ).click()
            .also { return this }
    }
    
    private fun getViewLocatorAttributeTitle(view: String): String? {
        var titleAttributeValue: String? = null
        when {
            view.lowercase() == "list" -> titleAttributeValue = "List"
            view.lowercase() == "grid" -> titleAttributeValue = "Grid"
        }
        return titleAttributeValue
    }
    
    fun collectProductInfo(): WomenPage {
        //TODO: implement DB storing
        return this
    }
}