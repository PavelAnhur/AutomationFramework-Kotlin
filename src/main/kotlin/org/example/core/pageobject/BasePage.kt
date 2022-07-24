package org.example.core.pageobject

import org.example.core.infra.ObjectManager
import org.example.core.infra.webdriver.WebDriverSingleton

open class BasePage {
    protected val driver = WebDriverSingleton.instance
    protected val logger = ObjectManager.logger
}
