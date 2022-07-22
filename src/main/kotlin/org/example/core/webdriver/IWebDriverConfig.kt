package org.example.core.webdriver

import org.example.core.browser.IBrowser
import org.openqa.selenium.WebDriver
import java.time.Duration

const val EXPLICIT_TIMEOUT_SEC = 20L
const val IMPLICIT_TIMEOUT_SEC = 3L
const val BROWSER_PREFIX_REMOTE = "remote"

interface IWebDriverConfig {
    var driver: WebDriver?
    fun setupWebDriver(): WebDriver
    
    class BaseImpl(override var driver: WebDriver? = null) : IWebDriverConfig {
        override fun setupWebDriver(): WebDriver {
            val browserName = IBrowser.BaseImpl().getBrowser()
            browserName?.let { webDriver(it) }
            driver?.manage()?.window()?.maximize()
            driver?.manage()?.timeouts()?.implicitlyWait(Duration.ofSeconds(IMPLICIT_TIMEOUT_SEC))
            return driver!!
        }
        
        private fun webDriver(browserName: String) = when {
            browserName.startsWith(BROWSER_PREFIX_REMOTE) -> {
                driver = RemoteWebDriverFactory().getDriver(browserName)
            }
            else -> {
                driver = LocalWebDriverFactory().getDriver(browserName)
            }
        }
    }
}
