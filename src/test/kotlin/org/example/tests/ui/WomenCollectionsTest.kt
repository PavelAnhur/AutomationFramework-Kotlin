package org.example.tests.ui

import org.testng.Assert
import org.testng.annotations.Test

class WomenCollectionsTest : BaseTest() {

    @Test(description = "Women collections compering")
    fun womenCollectionsComperingTest() {
        logger.info { "<<<Compare women collections by price order>>>" }
        homePageSteps
            .openHomePage()
            .openWomenPage()
        womenPageSteps
            .changeProductTableView(sortOrder = "desc", collectionView = "list")
            .storeProductInfoInDB()

        Assert.assertTrue(
            womenPageSteps.isProductPricesInDescOrder(),
            """The price list isn't in descending order
                |Actual price list: ${womenPageSteps.actualProductPricesList}
                """.trimMargin()
        )
    }
}
