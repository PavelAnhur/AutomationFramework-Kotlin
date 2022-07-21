package org.example.steps

import mu.KLogger
import mu.KotlinLogging
import org.example.core.pageobjects.HomePage

class HomePageSteps(
    private val homePage: HomePage = HomePage(),
    private val logger: KLogger = KotlinLogging.logger {},
) {
    fun openHomePage(): HomePageSteps {
        logger.info { "opening 'My Store' home page.." }
        homePage.openHomePage()
        return this
    }
    
    fun openWomenCollectionsPage() {
        logger.info { "Click on 'Women' title" }
        homePage.clickWomenTitle()
    }
    
    fun isLogoTitle(logo: String): Boolean {
        logger.info { "verifying logo title.." }
        return logo == homePage.getLogoTitle()
    }
}
