package org.example.core.webdriver

import mu.KLogger
import mu.KotlinLogging
import org.openqa.selenium.WebDriver

interface IWebDriver {
    val logger: KLogger
        get() = KotlinLogging.logger {}
    
    fun getDriver(browserName: String): WebDriver?
}