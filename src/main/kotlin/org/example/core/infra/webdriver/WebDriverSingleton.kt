package org.example.core.infra.webdriver

import mu.KotlinLogging
import org.example.core.infra.browser.BrowserImpl
import org.example.core.infra.webdriver.config.WebDriverConfigImpl
import org.example.core.infra.webdriver.creator.WebDriverCreatorImpl

object WebDriverSingleton {
    init {
        KotlinLogging.logger {}.info { "web driver initialization.." }
    }
    val instance = WebDriverConfigImpl(WebDriverCreatorImpl(BrowserImpl())).config()
}
