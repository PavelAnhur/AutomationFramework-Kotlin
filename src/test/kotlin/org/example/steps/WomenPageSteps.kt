package org.example.steps

import mu.KLogger
import org.example.core.infra.ObjectManager
import org.example.core.infra.db.DBManager
import org.example.core.pageobject.WomenPage
import org.postgresql.util.PSQLException

class WomenPageSteps(
    private val womenPage: WomenPage = WomenPage(),
    private val logger: KLogger = ObjectManager.logger,
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
        logger.info { "Sorting products.." }
        womenPage.selectSortOrder(sortOrder)
        logger.info { "Changing product collection view.." }
        womenPage.selectCollectionView(collectionView)
        return this
    }

    fun storeProductInfoInDB() {
        logger.info { "Collecting products info.." }
        womenPage.collectProductsInfo()
        logger.info { "filling DB.." }
        storeDb()
    }

    fun isProductPriceListInDescOrder(): Boolean {
        logger.info { "Verifying price list.." }
        var isSortedDescending = false
        for (i in 0 until actualProductPricesList.size - 1) {
            if (actualProductPricesList[i] < actualProductPricesList[i + 1]) {
                isSortedDescending = true
            }
        }
        if (isSortedDescending) logger.info { "price list in descending order" }
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
