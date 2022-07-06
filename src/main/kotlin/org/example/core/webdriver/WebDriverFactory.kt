package org.example.core.webdriver

import mu.KLogger
import mu.KotlinLogging
import org.openqa.selenium.WebDriver

abstract class WebDriverFactory {
    val logger: KLogger
        get() = KotlinLogging.logger {}
    
    abstract fun getDriver(browser: String): WebDriver?
}