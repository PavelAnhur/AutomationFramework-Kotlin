package org.example.tests.ui

import org.example.core.infra.property.PropertyService
import org.testng.Assert
import org.testng.annotations.Test

class WomenCollectionsTest : BaseTest() {
    private val sortOrder = PropertyService.getProperty().productSortOrder().toString()
    private val collectionView = PropertyService.getProperty().collectionView().toString()

    @Test(description = "Women collections compering")
    fun womenCollectionsComperingTest() {
        log.info { "<<<Compare women collections by price order>>>" }
        homePageSteps
            .openHomePage()
            .openWomenPage()
        womenPageSteps
            .changeProductTableView(sortOrder, collectionView)
            .storeProductInfoInDB()

        Assert.assertTrue(
            womenPageSteps.isProductPricesInOrder(sortOrder),
            """The price list isn't in $sortOrder order
                |Actual price list: ${womenPageSteps.actualProductPricesList}
                """.trimMargin()
        )
    }
}
