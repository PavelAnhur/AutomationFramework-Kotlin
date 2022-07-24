package org.example.core.infra.webdriver.factory

import mu.KLogger
import mu.KotlinLogging
import org.openqa.selenium.WebDriver

interface IWebDriver<out T : WebDriver> {
    val logger: KLogger
        get() = KotlinLogging.logger {}

    fun getDriver(browserName: String): T?
}
