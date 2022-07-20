package org.example.core.pageobjects

import org.example.core.db.DBManager
import org.example.core.model.Product
import org.example.core.utils.Locator.Companion.getSortLocatorAttributeValue
import org.example.core.utils.Locator.Companion.getViewLocatorId
import org.example.core.utils.UIElement
import org.example.core.utils.UIElementList
import org.openqa.selenium.By
import org.postgresql.util.PSQLException
import kotlin.streams.toList

private const val PRODUCT_XPATH_PREFIX = "//*[@class='product_list row list']/li"

class WomenPage : BasePage() {
    var actualPriceList: MutableList<Double> = ArrayList()
    var expectedPriceList: MutableList<Double> = ArrayList()
    private val dbManager = DBManager()
    private val sortDropdown = UIElement(By.id("productsSortForm"), "sort order dropdown")
    private val loadingSpinner = UIElement(By.xpath("//ul//br"))
    private val productRows = UIElementList(By.xpath(PRODUCT_XPATH_PREFIX), "product rows")
    private val productNameXPath = "$PRODUCT_XPATH_PREFIX[%d]//a[@class='product-name']"
    private val productCostXpath =
        "$PRODUCT_XPATH_PREFIX[%d]//*[starts-with(@class,'right-block')]//*[@class='price product-price']"
    private val productDescXpath = "$PRODUCT_XPATH_PREFIX[%d]//*[@class='product-desc']"
    private lateinit var products: List<Product>
    
    fun selectSortOrder(sortOrder: String): WomenPage {
        logger.info { "Sorting products.." }
        sortDropdown.click()
        UIElement(
            By.xpath("//*[@value='${getSortLocatorAttributeValue(sortOrder)}']"),
            "order from sort dropdown"
        ).click()
        loadingSpinner.waitForDisappear()
        return this
    }
    
    fun selectCollectionView(view: String): WomenPage {
        logger.info { "Changing products view.." }
        UIElement(
            By.id("${getViewLocatorId(view)}"),
            "product view"
        ).click()
        return this
    }
    
    fun collectProductsInfo(): WomenPage {
        products = ArrayList()
        productRows.waitForDisplayed()
        val productRowsNumber: Int = productRows.size()!!
        for (i in 1..productRowsNumber) {
            val product = Product.Builder()
                .name(productName(i))
                .cost(productCost(i))
                .description(productDescription(i))
                .build()
            storeDb(product)
            (products as ArrayList<Product>).add(product)
        }
        products.stream()
            .map { it.cost }
            .toList().toMutableList()
            .also { expectedPriceList = it }
        return this
    }
    
    fun getPriceListFromDb() {
        try {
            dbManager.connectToDb()
                .selectByColumn("cost")
            logger.info { "price list from DB:\n$actualPriceList" }
        } catch (e: PSQLException) {
            error(e.message.toString())
        } finally {
            dbManager.closeDb()
        }
    }
    
    fun isProductPricesOrdered(): Boolean {
        return actualPriceList == expectedPriceList
    }
    
    private fun storeDb(product: Product) =
        try {
            dbManager.connectToDb()
                .insertIntoIfNotExists(product)
        } catch (e: PSQLException) {
            error(e.message.toString())
        } finally {
            dbManager.closeDb()
        }
    
    private fun productName(rowNumber: Int): String {
        val name = UIElement(By.xpath(String.format(productNameXPath, rowNumber)), "product name").getText()
        logger.info { "product name of '$rowNumber' row: $name" }
        return name
    }
    
    private fun productCost(rowNumber: Int): Double {
        val cost = UIElement(By.xpath(String.format(productCostXpath, rowNumber)), "product price")
            .getText()
            .filter { it.isDigit() || it == '.' }
        logger.info { "product cost of '$rowNumber' row: $cost" }
        return cost.toDouble()
    }
    
    private fun productDescription(rowNumber: Int): String {
        val description = UIElement(By.xpath(String.format(productDescXpath, rowNumber)), "product description")
            .getText()
        logger.info { "product description of '$rowNumber' row: $description" }
        return description
    }
}
