package org.example.steps

import com.google.common.collect.Comparators.isInOrder
import io.qameta.allure.Step
import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.db.DBManager
import org.example.core.infra.db.DBService
import org.example.core.infra.db.IDBManager
import org.example.core.pageobject.WomenPage
import org.postgresql.util.PSQLException

class WomenPageSteps(
    private val log: KLogger = KotlinLogging.logger {},
    private val womenPage: WomenPage = WomenPage(),
    private val dbManager: IDBManager = DBManager()
) {
    val actualProductPricesList: List<Double>
        get() {
            return this.womenPage.products.map { it.price }
        }

    @Step("Change collection table view sorting {0} and {1} view ")
    fun changeProductTableView(sortOrder: String, collectionView: String): WomenPageSteps {
        log.info { "Sorting products.." }
        womenPage.selectSortOrder(sortOrder)
        log.info { "Changing product collection view.." }
        womenPage.selectCollectionView(collectionView)
        return this
    }

    @Step("Fill DB table with product info")
    fun storeProductInfoInDB() {
        log.info { "Collecting products info.." }
        womenPage.collectProductsInfo()
        log.info { "filling DB.." }
        storeDb()
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

    @Step("Verify price list in {0} order")
    fun isProductPricesInOrder(order: String): Boolean {
        log.info { "Verifying price list order.." }
        var isSorted = false
        when {
            order.lowercase().startsWith("asc") -> isSorted = isInOrder(actualProductPricesList, naturalOrder())
            order.lowercase().startsWith("desc") -> isSorted = isInOrder(actualProductPricesList, reverseOrder())
        }
        if (isSorted) log.info { "price list in $order order" }
        return isSorted
    }
}
