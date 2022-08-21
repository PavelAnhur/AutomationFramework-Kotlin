package org.example.tests.ui

import org.example.core.infra.property.PropertyManager
import org.testng.Assert
import org.testng.annotations.Test

class WomenCollectionsTest : BaseTest() {
    private val sortOrder = PropertyManager.config().productSortOrder().toString()

    @Test(description = "Women collections compering")
    fun womenCollectionsComperingTest() {
        log.info { "<<<Compare women collections by price order>>>" }
        homePageSteps
            .openHomePage()
            .openWomenPage()
        womenPageSteps
            .changeProductTableView(sortOrder, collectionView = "list")
            .storeProductInfoInDB()

        Assert.assertTrue(
            womenPageSteps.isProductPricesInOrder(sortOrder),
            """The price list isn't in $sortOrder order
                |Actual price list: ${womenPageSteps.actualProductPricesList}
                """.trimMargin()
        )
    }
}
