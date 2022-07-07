package org.example.core.pageobjects

import org.example.core.utils.UIElement
import org.example.core.webdriver.WebDriverConst.DEFAULT_TIMEOUT_IN_SEC
import org.openqa.selenium.By

class MyStoreHomePage : BasePage() {
    
    private val logo = UIElement(By.xpath("//*[@id='header_logo']/a"), "homepage logo")
    private val womenTitle = UIElement(By.xpath("//a[@title='Women']"), "'Women' title")
    
    fun getLogoTitle(): String {
        logo.waitForDisplayed(DEFAULT_TIMEOUT_IN_SEC)
        val title = logo.getAttribute("title") ?: "Title element doesn't exist"
        logger.info { "Title= '$title'" }
        return title
    }
    
    fun clickWomenTitle(): WomenPage {
        logger.info { "Click on 'Women' title" }
        womenTitle.click()
        return WomenPage()
    }
}
