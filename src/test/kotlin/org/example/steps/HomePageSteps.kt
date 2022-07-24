package org.example.steps

import mu.KLogger
import org.example.core.infra.ObjectManager
import org.example.core.pageobject.HomePage

class HomePageSteps(
    private val homePage: HomePage = HomePage(),
    private val logger: KLogger = ObjectManager.logger,
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
