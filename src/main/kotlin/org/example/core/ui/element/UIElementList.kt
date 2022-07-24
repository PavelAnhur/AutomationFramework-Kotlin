package org.example.core.ui.element

import org.example.core.infra.webdriver.WebDriverSingleton
import org.openqa.selenium.By

class UIElementList(
    by: By,
    description: String,
) : UIElement(by, description) {

    fun size() = WebDriverSingleton.instance?.findElements(by)?.size
}
