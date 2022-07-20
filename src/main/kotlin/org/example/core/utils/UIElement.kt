package org.example.core.utils

import mu.KotlinLogging
import org.example.core.utils.constants.ProjectConst.EXPLICIT_TIMEOUT_SEC
import org.example.core.utils.constants.ProjectConst.NUMBER_OF_ATTEMPTS
import org.example.core.utils.retry.WaitUtil
import org.example.core.webdriver.WebDriverSingleton
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.function.Supplier

@Suppress("unused")
open class UIElement(
    val by: By,
    private val description: String
) {
    private val logger = KotlinLogging.logger {}
    private val driver = WebDriverSingleton.instance
    private val jsExecutor = driver as JavascriptExecutor
    
    constructor(by: By) : this(by, by.toString())
    
    override fun toString(): String {
        return "locator=$by"
    }
    
    fun waitForDisplayed(timeout: Long = EXPLICIT_TIMEOUT_SEC) {
        logger.info { "waiting for element is displayed: $this" }
        waitForCondition(timeout, visibilityOfElementLocated(by))
    }
    
    fun getAttribute(attribute: String): String? {
        logger.info("getting attribute of element: $this")
        return getElement()?.getAttribute(attribute)
    }
    
    fun click() {
        logger.info { "click on the element: $description" }
        try {
            scrollToElement()
            waitForClickable()
            getElement()?.click()
        } catch (e: Exception) {
            logger.error { "Failed clicking on the element $this: ${e.message}" }
            error(e.message.toString())
        }
    }
    
    fun getText(): String {
        logger.info { "getting text of element.." }
        return getElement()?.text ?: "element $this doesn't contain test"
    }
    
    fun waitForDisappear() {
        logger.info { "waiting for element $this to disappear.." }
        val isElementDisappeared = Supplier<Boolean> {
            try {
                getElement()?.isDisplayed
                logger.info { "element $this is visible" }
                false
            } catch (e: org.openqa.selenium.NoSuchElementException) {
                logger.info { "element $this disappeared" }
                true
            }
        }
        WaitUtil.waitForTrue(
            isElementDisappeared,
            NUMBER_OF_ATTEMPTS,
            "element $this is visible after $NUMBER_OF_ATTEMPTS retries"
        )
    }
    
    private fun scrollToElement() {
        logger.info { "scrolling to the element: $this" }
        jsExecutor.executeScript("arguments[0].scrollIntoView();", getElement())
    }
    
    private fun waitForClickable(timeout: Long = EXPLICIT_TIMEOUT_SEC) {
        logger.info("waiting for element is clickable: $this")
        waitForCondition(timeout, elementToBeClickable(by))
    }
    
    private fun waitForCondition(timeout: Long, expectedCondition: ExpectedCondition<WebElement>) {
        WebDriverWait(driver, Duration.ofSeconds(timeout)).until(expectedCondition)
    }
    
    private fun getElement(): WebElement? = driver?.findElement(by)
}
