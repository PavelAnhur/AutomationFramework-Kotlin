package org.example.core.infra.webdriver

import org.example.core.infra.browser.IBrowser
import org.openqa.selenium.WebDriver
import java.time.Duration

interface IWebDriverConfig<out T : WebDriver> {
    fun setupWebDriver(): T?

    class BaseImpl : IWebDriverConfig<WebDriver> {
        override fun setupWebDriver(): WebDriver? {
            val browserName = IBrowser.BaseImpl().getBrowser()
            val driver: WebDriver? = browserName?.let { createWebDriver(it) }
            driver?.manage()?.window()?.maximize()
            driver?.manage()?.timeouts()?.implicitlyWait(Duration.ofSeconds(IMPLICIT_TIMEOUT_SEC))
            return driver
        }

        private fun createWebDriver(browserName: String): WebDriver? =
            when {
                browserName.startsWith(BROWSER_PREFIX_REMOTE) -> RemoteWebDriverFactory().getDriver(browserName)
                else -> LocalWebDriverFactory().getDriver(browserName)
            }
    }

    companion object {
        const val EXPLICIT_TIMEOUT_SEC = 20L
        const val IMPLICIT_TIMEOUT_SEC = 3L
        const val BROWSER_PREFIX_REMOTE = "remote"
    }
}
