package org.example.core.webdriver

import mu.KotlinLogging
import org.openqa.selenium.WebDriver

object WebDriverSingleton {
    private val logger = KotlinLogging.logger {}
    private val instance: WebDriver
    
    init {
        logger.info { "initializing web driver.." }
        instance = WebDriverConfig().setupWebDriver()
    }
    
    fun instanceOf(): WebDriver {
        return instance
    }
}
