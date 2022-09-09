package org.example.core.infra.webdriver.config

import org.example.core.infra.browser.BrowserImpl
import org.example.core.infra.webdriver.creator.IWebDriverCreator
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import java.time.Duration

class WebDriverConfigImpl(
    private val driverCreator: IWebDriverCreator<WebDriver>
) : IWebDriverConfig<WebDriver> {

    override fun config(): WebDriver {
        val driver = driverCreator.create()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_TIMEOUT_SEC))
        driver.manage().window().size = Dimension(BrowserImpl.WINDOW_WIDTH, BrowserImpl.WINDOW_HEIGHT)
        return driver
    }

    companion object {
        const val EXPLICIT_TIMEOUT_SEC = 20L
        const val IMPLICIT_TIMEOUT_SEC = 3L
    }
}
