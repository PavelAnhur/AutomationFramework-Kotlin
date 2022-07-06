package org.example.core.webdriver

import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.managers.OperaDriverManager
import org.example.core.browser.Browser
import org.example.core.exceptions.LocalWebDriverException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile

class LocalWebDriverFactory :IWebDriverFactory {
    
    override fun getDriver(browser: String): WebDriver? {
        var driver: WebDriver? = null
        try {
            driver =
                when (browser) {
                    Browser.CHROME.value -> getChromeDriver()
                    Browser.FIREFOX.value -> getFirefoxDriver()
                    Browser.EDGE.value -> getEdgeDriver()
                    Browser.OPERA.value -> getOperaDriver()
                    else -> throw LocalWebDriverException("can't create local web driver for $browser browser")
                }
        } catch (e: Exception) {
            logger.error(e.message)
        }
        return driver
    }
    
    private fun getChromeDriver(): WebDriver {
        WebDriverManager.chromedriver().setup()
        logger.info { "chrome web driver ready" }
        return ChromeDriver()
    }
    
    private fun getFirefoxDriver(): WebDriver {
        WebDriverManager.firefoxdriver().setup()
        val profile = FirefoxProfile()
        profile.setPreference("intl.accept_languages", "en")
        profile.setPreference("fission.webContentIsolationStrategy", 0)
        profile.setPreference("fission.bfcacheInParent", false)
        val firefoxOptionsLocal = FirefoxOptions()
        firefoxOptionsLocal.profile = profile
        logger.info { "firefox web driver ready" }
        return FirefoxDriver(firefoxOptionsLocal)
    }
    
    private fun getEdgeDriver(): WebDriver {
        WebDriverManager.edgedriver().setup()
        logger.info { "edge web driver ready" }
        return EdgeDriver()
    }
    
    private fun getOperaDriver(): WebDriver {
        WebDriverManager.operadriver().setup()
        logger.info { "opera web driver ready" }
        return OperaDriverManager.operadriver().create()
    }
}