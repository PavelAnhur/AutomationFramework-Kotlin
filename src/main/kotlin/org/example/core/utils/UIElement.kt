package org.example.core.utils

import mu.KotlinLogging
import org.example.core.webdriver.WebDriverFactoryConst.DEFAULT_TIMEOUT_IN_SEC
import org.example.core.webdriver.WebDriverSingleton
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

@Suppress("unused")
class UIElement(
    private val by: By,
    private val description: String
) {
    private val logger = KotlinLogging.logger {}
    private val driver = WebDriverSingleton.instanceOf()
    private val jsExecutor = driver as JavascriptExecutor
    
    constructor(by: By) : this(by, by.toString())
    
    fun waitForDisplayed(timeout: Long = DEFAULT_TIMEOUT_IN_SEC) {
        logger.info { "waiting for element is displayed: $this" }
        WebDriverWait(driver, Duration.ofSeconds(timeout)).until(visibilityOfElementLocated(by))
    }
    
    fun getAttribute(attribute: String): String? {
        logger.info("getting attribute of element: $this")
        return getElement().getAttribute(attribute)
    }
    
    fun click() {
        logger.info { "clicking on the element: $this" }
        try {
            scrollToElement()
            waitForClickable()
            getElement().click()
        } catch (e: Exception) {
            logger.error { "Failed clicking on the element $this: ${e.message}" }
            assert(false) { "${e.message}" }
        }
    }
    
    private fun scrollToElement() {
        logger.info { "scrolling to the element: $this" }
        jsExecutor.executeScript("arguments[0].scrollIntoView();", getElement())
    }
    
    private fun waitForClickable(timeout: Long = DEFAULT_TIMEOUT_IN_SEC) {
        logger.info("waiting for element is clickable: $this")
        WebDriverWait(driver, Duration.ofSeconds(timeout)).until(elementToBeClickable(by))
    }
    
    private fun getElement(): WebElement = driver.findElement(by)
    
    override fun toString(): String {
        return "locator=$by"
    }
}
