package org.example.core.infra.webdriver.config

import org.example.core.infra.browser.BrowserImpl.Companion.WINDOW_HEIGHT
import org.example.core.infra.browser.BrowserImpl.Companion.WINDOW_WIDTH
import org.example.core.infra.webdriver.creator.IWebDriverCreator
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import java.time.Duration

class WebDriverConfigImpl(
    private val driverCreator: IWebDriverCreator<WebDriver>
) : IWebDriverConfig<WebDriver> {
    override fun config(): WebDriver {
        val driver = driverCreator.create()
        driver.manage().window().size = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_TIMEOUT_SEC))
        return driver
    }

    companion object {
        const val EXPLICIT_TIMEOUT_SEC = 20L
        const val IMPLICIT_TIMEOUT_SEC = 3L
    }
}
