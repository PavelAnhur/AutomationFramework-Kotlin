package org.example.core.pageobject

import mu.KotlinLogging
import org.example.core.infra.property.PropertyManager
import org.example.core.infra.webdriver.WebDriverSingleton

open class BasePage {
    protected val driver = WebDriverSingleton.instance
    protected val log = KotlinLogging.logger {}
    protected val homePageUrl: String? = PropertyManager.config().homePageUrl()
}
