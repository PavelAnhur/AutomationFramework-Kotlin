package org.example.core.webdriver

import mu.KLogger
import mu.KotlinLogging
import org.openqa.selenium.WebDriver

interface IWebDriverFactory {
    val logger: KLogger
        get() = KotlinLogging.logger {}
    
    fun getDriver(browser: String): WebDriver?
}