package org.example.ui

import org.example.core.pageobjects.MyStoreHomePage
import org.testng.annotations.Test

class WomenCollectionsTest : BaseTest() {
    
    @Test(description = "Women collections compering")
    fun womenCollectionsComperingTest() {
        logger.info { "Compare women collections by price order" }
        MyStoreHomePage().clickWomenTitle()
            .selectSortOrder("desc")
            .selectProductView("list")
            .collectProductInfo()
        
    }
}