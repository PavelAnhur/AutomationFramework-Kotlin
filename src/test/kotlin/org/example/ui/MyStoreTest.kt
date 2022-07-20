package org.example.ui

import org.testng.Assert
import org.testng.annotations.Test

class MyStoreTest : BaseTest() {
    @Test(description = "Validate home page logo title")
    fun homePageLogoTest() {
        logger.info { "<<<Validate 'My Store' home page title>>>" }
        val actualTitle = homePage?.openHomePage()?.getLogoTitle()
        Assert.assertEquals(actualTitle, "My Store", "Invalid title text")
    }
}
