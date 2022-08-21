package org.example.core.infra.webdriver.factory

import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.managers.OperaDriverManager
import org.example.core.infra.browser.Browser
import org.example.core.infra.exceptions.LocalWebDriverException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile

class LocalWebDriverFactory : IWebDriverFactory<WebDriver> {

    override fun getDriver(browserName: String): WebDriver {
        var driver: WebDriver? = null
        try {
            driver =
                when (browserName) {
                    Browser.CHROME.value -> getChromeDriver()
                    Browser.FIREFOX.value -> getFirefoxDriver()
                    Browser.EDGE.value -> getEdgeDriver()
                    Browser.OPERA.value -> getOperaDriver()
                    else -> throw LocalWebDriverException("can't create local web driver for $browserName browser")
                }
        } catch (e: Exception) {
            log.error(e.message)
        }
        return driver ?: throw RuntimeException("local web driver creation process failed")
    }

    private fun getChromeDriver(): WebDriver {
        WebDriverManager.chromedriver().setup()
        log.info { "chrome web driver ready" }
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
        log.info { "firefox web driver ready" }
        return FirefoxDriver(firefoxOptionsLocal)
    }

    private fun getEdgeDriver(): WebDriver {
        WebDriverManager.edgedriver().setup()
        log.info { "edge web driver ready" }
        return EdgeDriver()
    }

    private fun getOperaDriver(): WebDriver {
        WebDriverManager.operadriver().setup()
        log.info { "opera web driver ready" }
        return OperaDriverManager.operadriver().create()
    }
}
