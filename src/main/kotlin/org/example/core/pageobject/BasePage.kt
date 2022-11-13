package org.example.core.pageobject

import org.example.core.infra.property.PropertyService
import org.example.core.infra.webdriver.WebDriverSingleton

open class BasePage {
    protected val driver by lazy { WebDriverSingleton.instance }
    protected val homePageUrl by lazy { PropertyService.getProperty().homePageUrl() }
    protected val tenMinutesMailUrl by lazy { PropertyService.getProperty().tenMinutesMailUrl().toString() }
}
