package org.example.core.infra.webdriver

import mu.KotlinLogging.logger
import org.example.core.infra.webdriver.config.WebDriverConfig

object WebDriverSingleton {
    init {
        logger {}.info { "web driver initialization.." }
    }

    val instance = WebDriverConfig().setupWebDriver()
}
