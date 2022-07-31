package org.example.steps

import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.db.DBManager
import org.example.core.pageobject.WomenPage
import org.postgresql.util.PSQLException

class WomenPageSteps(
    private val womenPage: WomenPage = WomenPage(),
    private val log: KLogger = KotlinLogging.logger {},
    private val dbManager: DBManager = DBManager(),
) {
    val actualProductPricesList: List<Double>
        get() {
            return this.womenPage.products.map { it.price }
        }

    fun changeProductTableView(
        sortOrder: String,
        collectionView: String,
    ): WomenPageSteps {
        log.info { "Sorting products.." }
        womenPage.selectSortOrder(sortOrder)
        log.info { "Changing product collection view.." }
        womenPage.selectCollectionView(collectionView)
        return this
    }

    fun storeProductInfoInDB() {
        log.info { "Collecting products info.." }
        womenPage.collectProductsInfo()
        log.info { "filling DB.." }
        storeDb()
    }

    fun isProductPricesInDescOrder(): Boolean {
        log.info { "Verifying price list.." }
        var isSortedDescending = false
        for (i in 0 until actualProductPricesList.size - 1) {
            if (actualProductPricesList[i] < actualProductPricesList[i + 1]) {
                isSortedDescending = true
            }
        }
        if (isSortedDescending) log.info { "price list in descending order" }
        return isSortedDescending
    }

    private fun storeDb() =
        try {
            dbManager.connectToDb()
            for (product in womenPage.products) {
                dbManager.insertIntoIfNotExists(product)
            }
        } catch (e: PSQLException) {
            error(e.message.toString())
        } finally {
            dbManager.closeDb()
        }

    private fun getPriceListFromDb() =
        try {
            dbManager.connectToDb()
            dbManager.selectByColumn("price")
        } catch (e: PSQLException) {
            error(e.message.toString())
        } finally {
            dbManager.closeDb()
        }
}
