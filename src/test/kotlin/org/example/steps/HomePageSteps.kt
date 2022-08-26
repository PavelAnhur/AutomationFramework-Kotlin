package org.example.steps

import io.qameta.allure.Step
import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.allure.Reporter
import org.example.core.pageobject.HomePage

class HomePageSteps(
    private val log: KLogger = KotlinLogging.logger {},
    private val reporter: Reporter = Reporter.instance,
    private val homePage: HomePage = HomePage()
) {
    @Step("Open {0} web page")
    fun openHomePage(url: String? = homePage.homePageUrl()): HomePageSteps {
        log.info { "opening 'My Store' home page.." }
        homePage.openHomePage()
        reporter.log("$url page is opened")
        return this
    }

    @Step("Open Women Collections page")
    fun openWomenPage() {
        log.info { "click on 'Women' title" }
        homePage.clickWomenTitle()
        reporter.log("Women Collections page is opened")
    }

    @Step("Verify home page logo title")
    fun isLogoTitle(logo: String): Boolean {
        log.info { "Verifying logo title.." }
        return logo == homePage.getLogoTitle()
    }
}
