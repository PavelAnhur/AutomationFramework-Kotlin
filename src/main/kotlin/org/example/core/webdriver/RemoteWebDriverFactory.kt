package org.example.core.webdriver

import org.example.core.browser.Browser
import org.example.core.configuration.property.ConfigManager
import org.example.core.exceptions.RemoteWebDriverException
import org.openqa.selenium.Platform
import org.openqa.selenium.UnexpectedAlertBehaviour
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

class RemoteWebDriverFactory : WebDriverFactory() {
    
    override fun getDriver(browser: String): WebDriver? {
        val virtualUrl = ConfigManager.configuration().virtualUrl()
        var driver: RemoteWebDriver? = null
        try {
            driver =
                when (browser) {
                    Browser.REMOTE_CHROME.value -> getRemoteChromeDriver(virtualUrl)
                    Browser.REMOTE_FIREFOX.value -> getRemoteFirefoxDriver(virtualUrl)
                    Browser.REMOTE_EDGE.value -> getRemoteEdgeWebDriver(virtualUrl)
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
        cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE)
        cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true)
        
        val chromeOptions = ChromeOptions()
        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions)
        chromeOptions.addArguments("--disable-notifications")
        chromeOptions.addArguments("--no-sandbox")
        logger.info { "remote chrome driver ready" }
        return RemoteWebDriver(URL(virtualUrl), chromeOptions)
    }
    
    private fun getRemoteFirefoxDriver(virtualUrl: String?): RemoteWebDriver {
        val cap = DesiredCapabilities()
        cap.platform = Platform.ANY
        cap.setCapability("firefox.switches", listOf("--disable-notifications"))
        val firefoxOptions = FirefoxOptions(cap)
        return RemoteWebDriver(URL(virtualUrl), firefoxOptions)
    }
    
    private fun getRemoteEdgeWebDriver(virtualUrl: String?): RemoteWebDriver {
        val options = EdgeOptions()
        val edgePrefs = HashMap<String, Any>()
        edgePrefs["profile.default_content_settings.popups"] = 0
        options.setCapability("prefs", edgePrefs)
        options.setCapability("useAutomationExtension", false)
        return RemoteWebDriver(URL(virtualUrl), options)
    }
}
