package org.example.steps

import com.google.common.collect.Comparators.isInOrder
import io.qameta.allure.Step
import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.allure.Reporter
import org.example.core.infra.db.DBManager
import org.example.core.infra.db.DBService
import org.example.core.infra.db.IDBManager
import org.example.core.pageobject.WomenPage
import org.postgresql.util.PSQLException

class WomenPageSteps(
    private val log: KLogger = KotlinLogging.logger {},
    private val reporter: Reporter = Reporter.instance,
    private val womenPage: WomenPage = WomenPage(),
    private val dbManager: IDBManager = DBManager()
) {

    val actualProductPricesList: List<Double>
        get() {
            return this.womenPage.products.map { it.price }
        }

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
        womenPage.collectProductsInfo()
        log.info { "filling DB.." }
        storeDb()
        reporter.info("Product info stored in DB")
    }

    private fun storeDb() {
        val connection = dbManager.connectToDb()
        try {
            val dbService = DBService(connection)
            for (product in womenPage.products) {
                dbService.insertIfNotExists(product)
            }
        } catch (e: PSQLException) {
            error(e.message.toString())
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
