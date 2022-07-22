package org.example.tests.ui

import org.testng.Assert
import org.testng.annotations.Test

class HomePageLogoTest : BaseTest() {
    
    @Test(description = "Validate logo title")
    fun homePageLogoTest() {
        logger.info { "<<<Validate 'My Store' home page title>>>" }
        homePageSteps.openHomePage()
        Assert.assertTrue(homePageSteps.isLogoTitle("My Store"), "Invalid logo title")
    }
}
