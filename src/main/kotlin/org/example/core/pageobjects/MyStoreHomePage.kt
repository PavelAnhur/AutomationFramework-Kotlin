package org.example.core.pageobjects

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class MyStoreHomePage(driver: WebDriver) : BasePage() {

    private val logo = driver.findElement(By.xpath("//*[@id='header_logo']/a"))

    fun getLogoTitle(): String {
        val title = logo.getAttribute("title") ?: "Title element doesn't exist"
        logger.info { "Title='$title'" }
        return title
    }
}
