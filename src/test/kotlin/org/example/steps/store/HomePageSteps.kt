package org.example.steps.store

import io.qameta.allure.Step
import org.example.core.pageobject.store.HomePage
import org.example.steps.BaseStep

class HomePageSteps(
    private val homePage: HomePage
) : BaseStep() {

    @Step("Open {0} web page")
    fun openHomePage(url: String? = homePage.homePageUrl()): HomePageSteps {
        homePage.openHomePage()
        reporter.info("$url page is opened")
        return this
    }

    @Step("Open Women Collections page")
    fun openWomenPage() {
        homePage.clickWomenTitle()
        reporter.info("Women Collections page is opened")
    }

    @Step("Verify home page logo title")
    fun isLogoTitle(logo: String): Boolean {
        return logo == homePage.getLogoTitle()
    }

    @Step("Input search value '{0}' to the field")
    fun searchForValue(input: String) {
        homePage.inputSearch(input)
    }

    @Step("Click on Sign in button")
    fun clickSignInButton() {
        homePage.clickSignInButton()
    }
}
