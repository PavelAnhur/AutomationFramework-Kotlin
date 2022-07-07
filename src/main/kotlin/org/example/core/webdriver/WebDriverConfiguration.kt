package org.example.core.webdriver

import org.example.core.browser.IBrowser
import org.example.core.webdriver.WebDriverConst.IMPLICIT_ELEMENT_TIMEOUT
import org.openqa.selenium.WebDriver
import java.time.Duration

class WebDriverConfig {
    private var driver: WebDriver? = null
    
    fun setupWebDriver(): WebDriver {
        val browserName = IBrowser.BaseImpl().getBrowser()
        browserName?.let { webDriver(it) }
        driver?.manage()?.window()?.maximize()
        driver?.manage()?.timeouts()?.implicitlyWait(Duration.ofSeconds(IMPLICIT_ELEMENT_TIMEOUT))
        return driver!!
    }
    
    private fun webDriver(browserName: String) = when {
        browserName.startsWith("remote") -> {
            driver = RemoteWebDriverFactory().getDriver(browserName)
        }
        else -> {
            driver = LocalWebDriverFactory().getDriver(browserName)
        }
    }
}
