package org.example.ui

import org.example.core.pageobjects.MyStoreHomePage
import org.testng.Assert
import org.testng.annotations.Test

class MyStoreTest : BaseTest() {

    private val homePage: MyStoreHomePage
        get() = MyStoreHomePage(driver)

    @Test(description = "Validate home page logo title")
    fun homePageLogoTest() {
        logger.info { "validating 'My Store' home page title" }
        val actualLogoTitle = homePage.getLogoTitle()
        Assert.assertEquals(actualLogoTitle, "My Store", "Invalid title text")
    }
}
