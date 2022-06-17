package org.example.core.webdriver

import org.example.core.browser.IBrowser
import org.example.core.configuration.property.ConfigManager
import org.openqa.selenium.WebDriver

interface IAbstractDriverFactory<T> {
    fun create(): T

    class BaseImpl : IAbstractDriverFactory<WebDriver> {
        override fun create(): WebDriver {
            val browserName = IBrowser.BaseImpl().getBrowser()
            return when {
                browserName?.startsWith("remote") == true -> {
                    IWebDriverFactory.RemoteWebDriverFactory(virtualUrl = ConfigManager.configuration().virtualUrl())
                        .setupWebDriver()
                }
                else -> {
                    IWebDriverFactory.LocalWebDriverFactory().setupWebDriver()
                }
            }
        }
    }
}
