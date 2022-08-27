package org.example.core.infra.webdriver.factory.browser

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

class FirefoxLocalDriver : BrowserDriver<WebDriver> {
    override fun setUp(): WebDriver {
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
}

class FirefoxRemoteDriver(private val virtualUrl: String) : BrowserDriver<RemoteWebDriver> {
    override fun setUp(): RemoteWebDriver {
        val firefoxOptions = FirefoxOptions()
        firefoxOptions.setAcceptInsecureCerts(true)
        firefoxOptions.addArguments("--disable-notifications")
        firefoxOptions.setCapability("platformName", Platform.ANY)
        log.info { "remote firefox driver ready" }
        return RemoteWebDriver(URL(virtualUrl), firefoxOptions)
    }
}
