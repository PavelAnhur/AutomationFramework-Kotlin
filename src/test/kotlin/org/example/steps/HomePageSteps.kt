package org.example.steps

import mu.KLogger
import mu.KotlinLogging
import org.example.core.pageobject.HomePage

class HomePageSteps(
    private val homePage: HomePage = HomePage(),
    private val logger: KLogger = KotlinLogging.logger {},
) {
    fun openHomePage(): HomePageSteps {
        logger.info { "Opening 'My Store' home page.." }
        homePage.openHomePage()
        return this
    }
    
    fun openWomenPage() {
        logger.info { "Click on 'Women' title" }
        homePage.clickWomenTitle()
    }
    
    fun isLogoTitle(logo: String): Boolean {
        logger.info { "Verifying logo title.." }
        return logo == homePage.getLogoTitle()
    }
}
