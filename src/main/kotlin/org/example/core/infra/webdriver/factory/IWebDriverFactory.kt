package org.example.core.infra.webdriver.factory

import mu.KLogger
import mu.KotlinLogging
import org.openqa.selenium.WebDriver

interface IWebDriverFactory<out T : WebDriver> {
    val log: KLogger
        get() = KotlinLogging.logger {}

    fun getDriver(browserName: String): T?
}
