package org.example.core.infra.webdriver

import mu.KotlinLogging
import org.openqa.selenium.WebDriver

object WebDriverSingleton {
    private val driverConfig = IWebDriverConfig.BaseImpl()
    private val logger = KotlinLogging.logger {}
    var instance: WebDriver? = null
        get() {
            if (field == null) {
                logger.info { "web driver initialization.." }
                field = driverConfig.setupWebDriver()
            }
            return field
        }
}
