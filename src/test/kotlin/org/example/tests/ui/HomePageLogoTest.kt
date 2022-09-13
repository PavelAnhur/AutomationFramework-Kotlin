package org.example.tests.ui

import io.qameta.allure.Description
import org.example.core.infra.property.PropertyService
import org.example.steps.HomePageSteps
import org.testng.Assert
import org.testng.annotations.Test

class HomePageLogoTest : BaseTest() {
    private val logoTitle = PropertyService.getProperty().logoTitle().toString()

    @Test(description = "Validate logo title")
    @Description("'My Store' home page title verification test")
    fun homePageLogoTest() {
        reporter.info("<<<Validate 'My Store' home page title>>>")
        homePageSteps = HomePageSteps()
            .also { it.openHomePage() }

        Assert.assertTrue(homePageSteps.isLogoTitle(logoTitle), "Invalid logo title")
    }
}
