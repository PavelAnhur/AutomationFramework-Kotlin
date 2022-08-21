package org.example.core.pageobject

import org.example.core.infra.property.PropertyService
import org.example.core.infra.webdriver.WebDriverSingleton
import org.openqa.selenium.WebDriver

open class BasePage {
    protected val driver: WebDriver = WebDriverSingleton.instance
    protected val homePageUrl: String? = PropertyService.getProperty().homePageUrl()
}
