package org.example.ui

import org.example.core.pageobjects.MyStoreHomePage
import org.testng.Assert
import org.testng.annotations.Test

class MyStoreTest : BaseTest() {
    
    @Test(description = "Validate home page logo title")
    fun homePageLogoTest() {
        logger.info { "<<<Validate 'My Store' home page title>>>" }
        val actualTitle = MyStoreHomePage().openHomePage().getLogoTitle()
        Assert.assertEquals(actualTitle, "My Store", "Invalid title text")
    }
}
