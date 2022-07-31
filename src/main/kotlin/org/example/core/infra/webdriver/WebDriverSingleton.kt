package org.example.core.infra.webdriver

import mu.KotlinLogging
import org.example.core.infra.webdriver.config.WebDriverConfig

object WebDriverSingleton {
    init {
        KotlinLogging.logger {}.info { "web driver initialization.." }
    }

    val instance = WebDriverConfig().setupWebDriver()
}
