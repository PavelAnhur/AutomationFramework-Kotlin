package org.example.core.infra.webdriver

import mu.KotlinLogging
import org.example.core.infra.browser.BrowserImpl
import org.example.core.infra.webdriver.config.WebDriverConfigImpl
import org.example.core.infra.webdriver.creator.WebDriverCreatorImpl
import org.example.core.infra.webdriver.factory.WebDriverFactory
import org.openqa.selenium.WebDriver

object WebDriverSingleton {
    private val INSTANCE = ThreadLocal<WebDriver>()
    val instance: WebDriver
        get() {
            if (null == INSTANCE.get()) {
                KotlinLogging.logger {}.info { "web driver initialization.." }
                INSTANCE.set(
                    WebDriverConfigImpl(
                        WebDriverCreatorImpl(
                            BrowserImpl(),
                            WebDriverFactory()
                        )
                    ).config()
                )
            }
            return INSTANCE.get()
        }
}
