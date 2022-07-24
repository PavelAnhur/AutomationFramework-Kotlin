package org.example.core.infra.webdriver

import mu.KLogger
import org.openqa.selenium.WebDriver

interface IWebDriver<out T : WebDriver> {
    val logger: KLogger
    fun getDriver(browserName: String): T?
}
