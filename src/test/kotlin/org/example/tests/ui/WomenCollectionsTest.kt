package org.example.tests.ui

import io.qameta.allure.Description
import org.example.core.infra.db.DBManager
import org.example.core.infra.property.PropertyService
import org.example.core.pageobject.store.HomePage
import org.example.core.pageobject.store.WomenPage
import org.example.steps.store.HomePageSteps
import org.example.steps.store.WomenPageSteps
import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class WomenCollectionsTest : BaseTest() {
    private val collectionView = PropertyService.getProperty().collectionView().toString()

    @Test(dataProvider = "womenCollectionTestData", description = "Women collection verification")
    @Description("Women collection price order verification test")
    fun compareWomenCollectionTest(sortOrder: String) {
        reporter.info("<<<Compare women collection by price order>>>")
        homePageSteps = HomePageSteps(HomePage())
            .also {
                it.openHomePage()
                    .openWomenPage()
            }

        womenPageSteps = WomenPageSteps(WomenPage(), DBManager())
            .also {
                it.changeProductTableView(sortOrder, collectionView)
                    .storeProductInfoInDB()
            }

        Assert.assertTrue(
            womenPageSteps.isProductPricesInOrder(sortOrder),
            """The price list isn't in $sortOrder order 
                |Actual price list: ${womenPageSteps.actualProductPricesList}""".trimMargin()
        )
    }

    @DataProvider
    fun womenCollectionTestData(): Iterator<String> {
        val data = listOf("ascending", "descending")
        return data.listIterator()
    }
}
