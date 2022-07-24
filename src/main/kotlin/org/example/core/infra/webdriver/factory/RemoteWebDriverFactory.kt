package org.example.core.infra.webdriver.factory

import org.example.core.infra.browser.Browser
import org.example.core.infra.exceptions.RemoteWebDriverException
import org.example.core.infra.property.PropertyManager
import org.example.core.infra.webdriver.config.WebDriverConfig.Companion.BROWSER_PREFIX_REMOTE
import org.openqa.selenium.Platform
import org.openqa.selenium.UnexpectedAlertBehaviour
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

class RemoteWebDriverFactory : IWebDriver<RemoteWebDriver> {

    override fun getDriver(browserName: String): RemoteWebDriver? {
        val virtualUrl = PropertyManager.config().virtualUrl()
        var driver: RemoteWebDriver? = null
        val browser = browserName.removePrefix(BROWSER_PREFIX_REMOTE).lowercase()
        try {
            driver =
                when (browser) {
                    Browser.CHROME.value -> getRemoteChromeDriver(virtualUrl)
                    Browser.FIREFOX.value -> getRemoteFirefoxDriver(virtualUrl)
                    Browser.EDGE.value -> getRemoteEdgeWebDriver(virtualUrl)
                    else -> throw RemoteWebDriverException("can't create remote web driver for $browser browser")
                }
        } catch (e: Exception) {
            logger.error(e.message)
        }
        return driver
    }

    private fun getRemoteChromeDriver(virtualUrl: String?): RemoteWebDriver {
        val cap = DesiredCapabilities()
        cap.platform = Platform.ANY
        cap.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE)
        cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true)

        val chromeOptions = ChromeOptions()
        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions)
        chromeOptions.addArguments("--disable-notifications")
        chromeOptions.addArguments("--no-sandbox")
        logger.info { "remote chrome driver ready" }
        return RemoteWebDriver(URL(virtualUrl), chromeOptions)
    }

    private fun getRemoteFirefoxDriver(virtualUrl: String?): RemoteWebDriver {
        val firefoxOptions = FirefoxOptions()
        firefoxOptions.setAcceptInsecureCerts(true)
        firefoxOptions.addArguments("--disable-notifications")
        firefoxOptions.setCapability("platformName", Platform.ANY)
        logger.info { "remote firefox driver ready" }
        return RemoteWebDriver(URL(virtualUrl), firefoxOptions)
    }

    private fun getRemoteEdgeWebDriver(virtualUrl: String?): RemoteWebDriver {
        val options = EdgeOptions()
        options.setAcceptInsecureCerts(true)
        logger.info { "remote edge driver ready" }
        return RemoteWebDriver(URL(virtualUrl), options)
    }
}
