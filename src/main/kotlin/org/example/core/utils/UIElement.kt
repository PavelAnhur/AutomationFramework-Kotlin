package org.example.core.utils

import mu.KotlinLogging
import org.example.core.webdriver.WebDriverSingleton
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

@Suppress("unused")
class UIElement(
    private val by: By,
    private val description: String
) {
    object UIElementConst {
        const val DEFAULT_TIMEOUT_IN_SEC = 5L
    }

    private val logger = KotlinLogging.logger {}
    private val driver = WebDriverSingleton.instanceOf()
    private val jsExecutor = driver as JavascriptExecutor

    constructor(by: By) : this(by, by.toString())


    fun click() {
        logger.info { "clicking on element: $this" }
        try {
            scrollToElement()
        } catch (e: Exception) {
            logger.error { "Failed scrolling to element: ${e.message}" }
        }
    }

    fun waitForDisplayed(timeout: Long = UIElementConst.DEFAULT_TIMEOUT_IN_SEC) {
        logger.info { "waiting for element is displayed: $this" }
        WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfElementLocated(by))
    }

    private fun scrollToElement() {
        logger.info { "scrolling to the element: $this" }
        jsExecutor.executeScript("arguments[0].scrollIntoView();", driver.findElement(by))
    }

    fun getAttribute(attribute: String): String? {
        logger.info("getting attribute of element: $this")
        return driver.findElement(by).getAttribute(attribute)
    }

    override fun toString(): String {
        return "locator=$by \"$description\""
    }
}