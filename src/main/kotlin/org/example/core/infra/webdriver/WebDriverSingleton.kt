package org.example.core.infra.webdriver

import mu.KotlinLogging
import org.example.core.infra.browser.BrowserImpl
import org.example.core.infra.webdriver.config.WebDriverConfigImpl
import org.example.core.infra.webdriver.creator.WebDriverCreatorImpl
import org.openqa.selenium.WebDriver
import kotlin.concurrent.getOrSet

object WebDriverSingleton {
    init {
        KotlinLogging.logger {}.info { "web driver initialization.." }
    }

    val instance = ThreadLocal<WebDriver>()
        .getOrSet { WebDriverConfigImpl(WebDriverCreatorImpl(BrowserImpl())).config() }
}
