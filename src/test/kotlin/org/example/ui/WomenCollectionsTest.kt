package org.example.ui

import org.testng.Assert
import org.testng.annotations.Test

class WomenCollectionsTest : BaseTest() {
    @Test(description = "Women collections compering")
    fun womenCollectionsComperingTest() {
        logger.info { "<<<Compare women collections by price order>>>" }
        homePage?.openHomePage()
            ?.clickWomenTitle()
            ?.selectSortOrder("desc")
            ?.selectCollectionView("list")
            ?.collectProductsInfo()
            ?.getPriceListFromDb()
        
        println("_________\n${womenPage?.expectedPriceList}\n_________")
        
        womenPage?.let { Assert.assertTrue(it.isProductPricesOrdered(),
            """The price list isn't in descending order
                |Actual price list: ${womenPage.actualPriceList}
                |Expected price list: ${womenPage.expectedPriceList}
            """.trimMargin()) }
    }
}
