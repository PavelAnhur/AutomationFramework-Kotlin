package org.example.core.pageobjects

import org.example.core.utils.UIElement
import org.openqa.selenium.By

class WomenPage : BasePage() {
    private val sortDropdown = UIElement(By.id("selectProductSort"), "sort order dropdown")
    
    fun selectSortOrder(sortOrder: String) {
        sortDropdown.waitForDisplayed(5)
        sortDropdown.click()
        val orderFromDropdown = UIElement(
            By.xpath("//*[@value='${getSortLocatorValueAttribute(sortOrder)}']"),
            "order from sort dropdown"
        )
    }
    
    private fun getSortLocatorValueAttribute(sortOrder: String): String? {
        var value: String? = null
        when {
            sortOrder.startsWith("desc") -> value = "price:desc"
            sortOrder.startsWith("asc") -> value = "price:asc"
        }
        return value
    }
}