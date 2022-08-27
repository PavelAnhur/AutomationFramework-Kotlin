package org.example.core.infra.webdriver.creator

import org.example.core.infra.browser.IBrowser
import org.example.core.infra.exceptions.WebDriverCreatorException
import org.example.core.infra.webdriver.factory.IWebDriverFactory
import org.openqa.selenium.WebDriver

class WebDriverCreatorImpl(
    private val browser: IBrowser,
    private val webDriverFactory: IWebDriverFactory<WebDriver>
) : IWebDriverCreator<WebDriver> {

    override fun create(): WebDriver {
        val browserName = browser.getBrowser()
        return webDriverFactory.getDriver(browserName) ?: throw WebDriverCreatorException("create webdriver failed")
    }
}
