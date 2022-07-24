package org.example.core.infra.webdriver

import mu.KotlinLogging
import org.openqa.selenium.WebDriver

object WebDriverSingleton {
    init {
        KotlinLogging.logger {}.info { "web driver initialization.." }
    }

    val instance: WebDriver? = IWebDriverConfig.BaseImpl().setupWebDriver()
}
