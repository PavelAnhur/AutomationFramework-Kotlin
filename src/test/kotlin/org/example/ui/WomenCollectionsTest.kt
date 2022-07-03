package org.example.ui

import org.example.core.pageobjects.MyStoreHomePage
import org.example.core.pageobjects.WomenPage
import org.testng.annotations.Test

class WomenCollectionsTest : BaseTest() {

    private val homePage: MyStoreHomePage
        get() = MyStoreHomePage()
    private val womenPage: WomenPage
        get() = WomenPage()

    @Test(description = "Women collections compering")
    fun womenCollectionsComperingTest() {
        logger.info { "Compare women collections by price order" }
        homePage.clickWomenTitle()
        womenPage.selectSortOrder("desc")
    }
}