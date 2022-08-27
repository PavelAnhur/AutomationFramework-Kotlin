package org.example.core.infra.webdriver.creator

import org.example.core.infra.browser.BrowserImpl.Companion.BROWSER_PREFIX_REMOTE
import org.example.core.infra.browser.IBrowser
import org.example.core.infra.webdriver.factory.LocalWebDriverFactory
import org.example.core.infra.webdriver.factory.RemoteWebDriverFactory
import org.openqa.selenium.WebDriver

class WebDriverCreatorImpl(private val browser: IBrowser) : IWebDriverCreator<WebDriver> {
    override fun create(): WebDriver {
        val browserName = browser.getBrowser()
        return when {
            browserName.startsWith(BROWSER_PREFIX_REMOTE) -> RemoteWebDriverFactory().getDriver(browserName)
            else -> LocalWebDriverFactory().getDriver(browserName)
        }
    }
}
