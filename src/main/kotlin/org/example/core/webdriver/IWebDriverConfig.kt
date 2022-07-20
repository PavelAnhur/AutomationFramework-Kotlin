package org.example.core.webdriver

import org.example.core.browser.IBrowser
import org.example.core.utils.constants.ProjectConst
import org.openqa.selenium.WebDriver
import java.time.Duration

interface IWebDriverConfig {
    var driver: WebDriver?
    fun setupWebDriver(): WebDriver
    
    class BaseImpl(override var driver: WebDriver? = null) : IWebDriverConfig {
        override fun setupWebDriver(): WebDriver {
            val browserName = IBrowser.BaseImpl().getBrowser()
            browserName?.let { webDriver(it) }
            driver?.manage()?.window()?.maximize()
            driver?.manage()?.timeouts()?.implicitlyWait(Duration.ofSeconds(ProjectConst.IMPLICIT_TIMEOUT_SEC))
            return driver!!
        }
        
        private fun webDriver(browserName: String) = when {
            browserName.startsWith(ProjectConst.BROWSER_PREFIX_REMOTE) -> {
                driver = RemoteWebDriverFactory().getDriver(browserName)
            }
            else -> {
                driver = LocalWebDriverFactory().getDriver(browserName)
            }
        }
    }
}
