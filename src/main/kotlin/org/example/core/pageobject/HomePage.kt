package org.example.core.pageobject

import mu.KLogger
import mu.KotlinLogging
import org.example.core.ui.element.UIElement
import org.openqa.selenium.By

class HomePage : BasePage() {
    private val log: KLogger = KotlinLogging.logger {}
    private val logo = UIElement(By.xpath("//*[@id='header_logo']/a"), "homepage logo")
    private val womenTitle = UIElement(By.xpath("//a[@title='Women']"), "'Women' title")
    private val searchField = UIElement(By.id("search_query_top"), "search field")

    fun openHomePage(): HomePage {
        driver.get(homePageUrl)
        return this
    }

    fun getLogoTitle(): String {
        logo.waitForDisplayed()
        val title = logo.getAttribute("title") ?: "Title element doesn't exist"
        log.info { "title= '$title'" }
        return title
    }

    fun clickWomenTitle(): WomenPage {
        try {
            womenTitle.click()
        } catch (e: Exception) {
            log.warn {
                """${e.message}
                    |need to refresh the page""".trimMargin()
            }
            driver.navigate().refresh()
            womenTitle.click()
        }
        return WomenPage()
    }

    fun homePageUrl() = homePageUrl

    fun inputSearch(input: String) {
        searchField.inputValue(input)
    }
}
