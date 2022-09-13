package org.example.steps

import com.google.common.collect.Comparators.isInOrder
import io.qameta.allure.Step
import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.allure.Reporter
import org.example.core.infra.db.DBManager
import org.example.core.infra.db.DBService
import org.example.core.infra.db.IDBManager
import org.example.core.infra.file.FileReader
import org.example.core.infra.property.PropertyService
import org.example.core.model.Product
import org.example.core.pageobject.WomenPage
import org.postgresql.util.PSQLException

class WomenPageSteps(
    private val log: KLogger = KotlinLogging.logger {},
    private val reporter: Reporter = Reporter.instance,
    private val womenPage: WomenPage = WomenPage(),
    private val dbManager: IDBManager = DBManager()
) {
    private lateinit var products: MutableList<Product>
    val actualProductPricesList: List<Double>
        get() = this.products.map { it.price }
    private val tableName = PropertyService.getProperty().dbTableName().toString()
    private val jdbcUrl = PropertyService.getProperty().dbUrl().toString()
    private val dbUser = PropertyService.getProperty().dbUser().toString()
    private val dbPasswordPath = PropertyService.getProperty().dbPasswordPath().toString()
    private val dbPassword = dbPasswordPath.let { FileReader.readLineFromFile(it) }

    @Step("Change collection table view with '{0}' sort and '{1}' view ")
    fun changeProductTableView(sortOrder: String, collectionView: String): WomenPageSteps {
        log.info { "sorting products.." }
        womenPage.selectSortOrder(sortOrder)
        log.info { "changing product collection view.." }
        womenPage.selectCollectionView(collectionView)
        reporter.info("Products are sorted '$sortOrder' and view is '$collectionView'")
        return this
    }

    @Step("Fill DB table with product info")
    fun storeProductInfoInDB() {
        log.info { "Collecting products info.." }
        collectProductsInfo()
        log.info { "filling DB.." }
        storeDb()
        reporter.info("Product info stored in DB")
    }

    @Step("Collect product info")
    fun collectProductsInfo() {
        products = ArrayList()
        womenPage.productRows.waitForDisplayed()
        (1..womenPage.productRows.size()).asSequence().map {
            Product.Builder()
                .name(womenPage.productName(it))
                .price(womenPage.productPrice(it))
                .description(womenPage.productDescription(it))
                .build()
        }.forEach { products.add(it) }
    }

    @Step("Store products in database")
    private fun storeDb() {
        val connection = dbManager.connectToDb(jdbcUrl, dbUser, dbPassword)
        try {
            val dbService = DBService(connection)
            products.forEach { product ->
                dbService.insertIfNotExists(tableName, product)
            }
        } catch (e: PSQLException) {
            error(e.printStackTrace())
        } finally {
            dbManager.closeDb(connection)
        }
    }

    @Step("Verify price list in '{0}' order")
    fun isProductPricesInOrder(order: String): Boolean {
        log.info { "verifying price list order.." }
        var isSorted = false
        when {
            order.lowercase().startsWith("asc") -> isSorted = isInOrder(actualProductPricesList, naturalOrder())
            order.lowercase().startsWith("desc") -> isSorted = isInOrder(actualProductPricesList, reverseOrder())
        }
        if (isSorted) reporter.info("price list is in $order order")
        else reporter.warn("price list isn't in $order order")
        return isSorted
    }
}
