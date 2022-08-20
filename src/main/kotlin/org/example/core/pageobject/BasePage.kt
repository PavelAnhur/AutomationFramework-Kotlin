package org.example.core.pageobject

import mu.KotlinLogging
import org.example.core.infra.property.PropertyManager
import org.example.core.infra.webdriver.WebDriverSingleton
import org.openqa.selenium.WebDriver

open class BasePage {
    protected val driver: WebDriver = WebDriverSingleton.instance
    protected val log = KotlinLogging.logger {}
    protected val homePageUrl: String? = PropertyManager.config().homePageUrl()
}
