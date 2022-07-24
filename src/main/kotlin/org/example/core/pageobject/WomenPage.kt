package org.example.core.pageobject

import org.example.core.model.Product
import org.example.core.ui.element.Locator.Companion.getSortLocatorAttributeValue
import org.example.core.ui.element.Locator.Companion.getViewLocatorId
import org.example.core.ui.element.UIElement
import org.example.core.ui.element.UIElementList
import org.openqa.selenium.By

private const val PRODUCT_XPATH_PREFIX = "//*[@class='product_list row list']/li"

class WomenPage : BasePage() {
    val products: MutableList<Product> = ArrayList()
    private val sortDropdown = UIElement(By.id("productsSortForm"), "sort order dropdown")
    private val loadingSpinner = UIElement(By.xpath("//ul//br"))
    private val productRows = UIElementList(By.xpath(PRODUCT_XPATH_PREFIX), "product rows")
    private val productNameXPath = "$PRODUCT_XPATH_PREFIX[%d]//a[@class='product-name']"
    private val productPriceXpath =
        "$PRODUCT_XPATH_PREFIX[%d]//*[starts-with(@class,'right-block')]//*[@class='price product-price']"
    private val productDescXpath = "$PRODUCT_XPATH_PREFIX[%d]//*[@class='product-desc']"

    fun selectSortOrder(sortOrder: String): WomenPage {
        sortDropdown.click()
        UIElement(
            By.xpath("//*[@value='${getSortLocatorAttributeValue(sortOrder)}']"),
            "order from sort dropdown"
        ).click()
        loadingSpinner.waitForDisappear()
        return this
    }

    fun selectCollectionView(view: String): WomenPage {
        UIElement(
            By.id("${getViewLocatorId(view)}"),
            "product view"
        ).click()
        return this
    }

    fun collectProductsInfo() {
        productRows.waitForDisplayed()
        val productRowsNumber: Int? = productRows.size()
        for (i in 1..productRowsNumber!!) {
            val product = Product.Builder()
                .name(productName(i))
                .price(productPrice(i))
                .description(productDescription(i))
                .build()
            products.add(product)
        }
    }

    private fun productName(rowNumber: Int): String {
        val name = UIElement(By.xpath(String.format(productNameXPath, rowNumber)), "product name").getText()
        logger.info { "product #$rowNumber name= $name" }
        return name
    }

    private fun productPrice(rowNumber: Int): Double {
        val price = UIElement(By.xpath(String.format(productPriceXpath, rowNumber)), "product price")
            .getText()
            .filter { it.isDigit() || it == '.' }
        logger.info { "product #$rowNumber price= $price" }
        return price.toDouble()
    }

    private fun productDescription(rowNumber: Int): String {
        val description = UIElement(By.xpath(String.format(productDescXpath, rowNumber)), "product description")
            .getText()
        logger.info { "product #$rowNumber description= $description" }
        return description
    }
}
