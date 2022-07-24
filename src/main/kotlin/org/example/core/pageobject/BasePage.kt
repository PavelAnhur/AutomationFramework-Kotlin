package org.example.core.pageobject

import mu.KotlinLogging
import org.example.core.infra.webdriver.WebDriverSingleton

open class BasePage {
    protected val driver = WebDriverSingleton.instance
    protected val logger = KotlinLogging.logger {}
}
