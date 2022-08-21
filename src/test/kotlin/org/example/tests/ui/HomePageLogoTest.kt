package org.example.tests.ui

import org.example.core.infra.property.PropertyService
import org.springframework.boot.test.context.SpringBootTest
import org.testng.Assert
import org.testng.annotations.Test

@SpringBootTest(classes = [HomePageLogoTest::class])
class HomePageLogoTest : BaseTest() {
    private val logo = PropertyService.getProperty().logoTitle().toString()

    @Test(description = "Validate logo title")
    fun homePageLogoTest() {
        log.info { "<<<Validate 'My Store' home page title>>>" }
        homePageSteps.openHomePage()

        Assert.assertTrue(homePageSteps.isLogoTitle(logo), "Invalid logo title")
    }
}
