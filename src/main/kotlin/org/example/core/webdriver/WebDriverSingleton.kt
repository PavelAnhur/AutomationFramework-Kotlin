package org.example.core.webdriver

import mu.KotlinLogging
import org.openqa.selenium.WebDriver

object WebDriverSingleton {
    
    private val logger = KotlinLogging.logger {}
    var instance: WebDriver? = null
        get() {
            if (field == null) {
                logger.info { "web driver initialization.." }
                field = WebDriverConfig().setupWebDriver()
            }
            return field
        }
}
