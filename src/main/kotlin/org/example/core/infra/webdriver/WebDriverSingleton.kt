package org.example.core.infra.webdriver

import org.example.core.infra.ObjectManager
import org.openqa.selenium.WebDriver

object WebDriverSingleton {
    init {
        ObjectManager.logger.info { "web driver initialization.." }
    }

    val instance: WebDriver? = IWebDriverConfig.BaseImpl().setupWebDriver()
}
