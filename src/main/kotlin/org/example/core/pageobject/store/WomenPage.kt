package org.example.core.pageobject.store

import mu.KLogger
import mu.KotlinLogging
import org.example.core.pageobject.BasePage
import org.example.core.ui.element.LocatorAttribute
import org.example.core.ui.element.UIElement
import org.example.core.ui.element.UIElementList
import org.openqa.selenium.By

private const val PRODUCT_LIST_XPATH = "//*[@class='product_list row list']/li"

class WomenPage : BasePage() {
    private val log: KLogger = KotlinLogging.logger {}
    private val sortDropdown = UIElement(By.id("productsSortForm"), "sort order dropdown")
    private val loadingSpinner = UIElement(By.xpath("//ul//br"), "loading spinner")
    val productRows get() = UIElementList(By.xpath(PRODUCT_LIST_XPATH), "product rows")
    private val productNameXPath = "$PRODUCT_LIST_XPATH[%d]//a[@class='product-name']"
    private val productPriceXpath =
        "$PRODUCT_LIST_XPATH[%d]//*[starts-with(@class,'right-block')]//*[@class='price product-price']"
    private val productDescriptionXpath = "$PRODUCT_LIST_XPATH[%d]//*[@class='product-desc']"

    fun selectSortOrder(sortOrder: String): WomenPage {
        log.info { "sorting products $sortOrder.." }
        sortDropdown.click()
        UIElement(
            By.xpath("//*[@value='${LocatorAttribute.sortLocatorAttributeValue(sortOrder)}']"),
            "sort order dropdown"
        ).click()
        loadingSpinner.waitForDisappear()
        return this
    }

    fun selectCollectionView(view: String): WomenPage {
        log.info { "changing product collection view to $view.." }
        UIElement(By.id("${LocatorAttribute.viewLocatorId(view)}"), "product view").click()
        return this
    }

    fun productName(rowNumber: Int): String {
        val name = UIElement(By.xpath(String.format(productNameXPath, rowNumber)), "product name").getText()
        log.info { "product #$rowNumber name= $name" }
        return name
    }

    fun productPrice(rowNumber: Int): Double {
        val price = UIElement(By.xpath(String.format(productPriceXpath, rowNumber)), "product price").getText()
            .filter { it.isDigit() || it == '.' }
        log.info { "product #$rowNumber price= $price" }
        return price.toDouble()
    }

    fun productDescription(rowNumber: Int): String {
        val description =
            UIElement(By.xpath(String.format(productDescriptionXpath, rowNumber)), "product description").getText()
        log.info { "product #$rowNumber description= $description" }
        return description
    }
}
