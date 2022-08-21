package org.example.tests.ui

import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.property.PropertyService
import org.springframework.boot.test.context.SpringBootTest
import org.testng.Assert
import org.testng.annotations.Test

@SpringBootTest(classes = [HomePageLogoTest::class])
class HomePageLogoTest : BaseTest() {
    private val log: KLogger = KotlinLogging.logger {}
    private val logoTitle = PropertyService.getProperty().logoTitle().toString()

    @Test(description = "Validate logo title")
    fun homePageLogoTest() {
        log.info { "<<<Validate 'My Store' home page title>>>" }
        homePageSteps.openHomePage()

        Assert.assertTrue(homePageSteps.isLogoTitle(logoTitle), "Invalid logo title")
    }
}
