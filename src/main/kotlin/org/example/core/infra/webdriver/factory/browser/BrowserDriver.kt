package org.example.core.infra.webdriver.factory.browser

import mu.KLogger
import mu.KotlinLogging
import org.openqa.selenium.WebDriver

interface BrowserDriver<out T : WebDriver> {
    val log: KLogger
        get() = KotlinLogging.logger {}

    fun setUp(): T
}
