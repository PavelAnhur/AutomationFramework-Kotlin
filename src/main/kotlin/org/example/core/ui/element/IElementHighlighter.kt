package org.example.core.ui.element

import mu.KotlinLogging
import org.example.core.infra.file.FileReader
import org.example.core.infra.retry.WaitUtil
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

private const val HIGHLIGHT_VISIBILITY_TIMEOUT_MILLISECOND = 100

interface IElementHighlighter {
    fun highlightAndUnhighlight(driver: WebDriver)
}

class ElementHighlighterImpl(private val element: UIElement) : IElementHighlighter {
    private var lastElem: WebElement? = null
    private var lastBorder: String? = null
    private lateinit var jsExecutor: JavascriptExecutor

    override fun highlightAndUnhighlight(driver: WebDriver) {
        highlightElement(element.webElement, driver)
        WaitUtil.sleep(HIGHLIGHT_VISIBILITY_TIMEOUT_MILLISECOND)
        unhighlightLast(driver)
    }

    private fun highlightElement(element: WebElement, driver: WebDriver) {
        try {
            jsExecutor = driver as JavascriptExecutor
            lastElem = element
            lastBorder = jsExecutor.executeScript(scriptGetElementBorder, lastElem) as String
        } catch (e: Exception) {
            KotlinLogging.logger {}.info { "Could not highlight element $e" }
        }
    }

    private fun unhighlightLast(driver: WebDriver) {
        try {
            jsExecutor = driver as JavascriptExecutor
            jsExecutor.executeScript(scriptUnhighlightElement, lastElem, lastBorder)
        } finally {
            lastElem = null
            lastBorder = null
        }
    }

    companion object {
        private val scriptGetElementBorder
            get() = FileReader.readTextFromFile("HighlightElement/SCRIPT_GET_ELEMENT_BORDER")
        private val scriptUnhighlightElement
            get() = FileReader.readTextFromFile("HighlightElement/SCRIPT_UNHIGHLIGHT_ELEMENT")
    }
}
