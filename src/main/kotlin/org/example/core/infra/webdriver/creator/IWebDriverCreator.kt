package org.example.core.infra.webdriver.creator

import org.openqa.selenium.WebDriver

interface IWebDriverCreator<out T : WebDriver> {
    fun create(): T
}
