package org.example.core.infra.webdriver.config

import org.example.core.infra.browser.BrowserImpl
import org.example.core.infra.webdriver.factory.LocalWebDriverFactory
import org.example.core.infra.webdriver.factory.RemoteWebDriverFactory
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import java.time.Duration

class WebDriverConfig : IWebDriverConfig<WebDriver> {
    override fun setupWebDriver(): WebDriver {
        val browserName = BrowserImpl().getBrowser()
        val driver = createWebDriver(browserName)
        driver.manage().window().size = Dimension(1294, 906)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_TIMEOUT_SEC))
        return driver
    }

    private fun createWebDriver(browserName: String): WebDriver =
        when {
            browserName.startsWith(BROWSER_PREFIX_REMOTE) -> RemoteWebDriverFactory().getDriver(browserName)
            else -> LocalWebDriverFactory().getDriver(browserName)
        }

    companion object {
        const val EXPLICIT_TIMEOUT_SEC = 20L
        const val IMPLICIT_TIMEOUT_SEC = 3L
        const val BROWSER_PREFIX_REMOTE = "remote"
    }
}
