package org.example.core.infra.webdriver.config

import org.openqa.selenium.WebDriver

interface IWebDriverConfig<out T : WebDriver> {
    fun config(): T?
}
