package org.example.core.utils

import org.example.core.webdriver.WebDriverSingleton
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class UIElementList(by: By, description: String) : UIElement(by, description) {
    
    fun size() = getElements()?.size
    
    private fun getElements(): MutableList<WebElement>? = WebDriverSingleton.instance?.findElements(by)
}