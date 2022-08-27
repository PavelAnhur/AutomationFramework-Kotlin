package org.example.core.infra.webdriver.factory

import org.example.core.infra.browser.Browser
import org.example.core.infra.browser.BrowserImpl
import org.example.core.infra.exceptions.LocalWebDriverException
import org.example.core.infra.exceptions.RemoteWebDriverException
import org.example.core.infra.property.PropertyService
import org.example.core.infra.webdriver.factory.browser.ChromeLocalDriver
import org.example.core.infra.webdriver.factory.browser.ChromeRemoteDriver
import org.example.core.infra.webdriver.factory.browser.EdgeLocalDriver
import org.example.core.infra.webdriver.factory.browser.EdgeRemoteDriver
import org.example.core.infra.webdriver.factory.browser.FirefoxLocalDriver
import org.example.core.infra.webdriver.factory.browser.FirefoxRemoteDriver
import org.example.core.infra.webdriver.factory.browser.OperaLocalDriver
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver

class WebDriverFactory : IWebDriverFactory<WebDriver> {
    override fun getDriver(browserName: String): WebDriver =
        if (browserName.startsWith(BrowserImpl.BROWSER_PREFIX_REMOTE)) {
            remoteDriver(browserName)
        } else {
            localDriver(browserName)
        }

    private fun remoteDriver(browserName: String): RemoteWebDriver {
        val browser = browserName.removePrefix(BrowserImpl.BROWSER_PREFIX_REMOTE).lowercase()
        val virtualUrl = PropertyService.getProperty().virtualUrl().toString()
        var driver: RemoteWebDriver? = null
        try {
            driver = when (browser) {
                Browser.CHROME.value -> ChromeRemoteDriver(virtualUrl).getInstance()
                Browser.FIREFOX.value -> FirefoxRemoteDriver(virtualUrl).getInstance()
                Browser.EDGE.value -> EdgeRemoteDriver(virtualUrl).getInstance()
                else -> throw RemoteWebDriverException("can't create remote web driver for $browser browser")
            }
        } catch (e: Exception) {
            log.error(e.message)
        }
        return driver ?: throw RuntimeException("remote web driver creation process failed")
    }

    private fun localDriver(browserName: String): WebDriver {
        var driver: WebDriver? = null
        try {
            driver = when (browserName) {
                Browser.CHROME.value -> ChromeLocalDriver().getInstance()
                Browser.FIREFOX.value -> FirefoxLocalDriver().getInstance()
                Browser.EDGE.value -> EdgeLocalDriver().getInstance()
                Browser.OPERA.value -> OperaLocalDriver().getInstance()
                else -> throw LocalWebDriverException("can't create local web driver for $browserName browser")
            }
        } catch (e: Exception) {
            log.error(e.message)
        }
        return driver ?: throw RuntimeException("local web driver creation process failed")
    }
}
