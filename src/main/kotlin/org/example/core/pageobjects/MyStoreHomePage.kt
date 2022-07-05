package org.example.core.pageobjects

import org.example.core.utils.UIElement
import org.openqa.selenium.By

class MyStoreHomePage : BasePage() {
    
    private val logo = UIElement(By.xpath("//*[@id='header_logo']/a"), "homepage logo")
    private val womenTitle = UIElement(By.xpath("//a[@title='Women']"), "'Women' title")
    
    fun getLogoTitle(): String {
        logo.waitForDisplayed(5)
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
