package org.example.core.webdriver

import org.example.core.browser.IBrowser
import org.example.core.exceptions.LocalWebDriverException
import org.example.core.webdriver.WebDriverFactoryConst.IMPLICIT_ELEMENT_TIMEOUT
import org.openqa.selenium.WebDriver
import java.time.Duration

object WebDriverFactoryConst {
    const val IMPLICIT_ELEMENT_TIMEOUT = 3L
    const val DEFAULT_TIMEOUT_IN_SEC = 20L
}

class WebDriverConfig {
    private var driver: WebDriver? = null
    
    fun setupWebDriver(): WebDriver {
        val browserName = IBrowser.BaseImpl().getBrowser()
        browserName?.let { webDriver(it) }
        driver?.manage()?.window()?.maximize()
        driver?.manage()?.timeouts()?.implicitlyWait(Duration.ofSeconds(IMPLICIT_ELEMENT_TIMEOUT))
        return driver ?: throw LocalWebDriverException("can't create local web driver")
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
