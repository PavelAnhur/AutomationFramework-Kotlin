package org.example.core.ui.element

import org.example.core.infra.webdriver.WebDriverSingleton
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class UIElementList(by: By, description: String) : UIElement(by, description) {

    fun size() = getElements().size
    private fun getElements(): MutableList<WebElement> = WebDriverSingleton.instance.findElements(by)
}
