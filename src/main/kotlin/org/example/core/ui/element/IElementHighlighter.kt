package org.example.core.ui.element

import mu.KotlinLogging
import org.example.core.infra.file.FileReader
import org.example.core.infra.retry.WaitUtil
import org.example.core.infra.webdriver.WebDriverSingleton
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement

interface IElementHighlighter {
    fun highlightAndUnhighlight()
    
    class Impl(private val element: UIElement) : IElementHighlighter {
        private var lastElem: WebElement? = null
        private var lastBorder: String? = null
        private val jsExecutor = WebDriverSingleton.instance as JavascriptExecutor
        
        override fun highlightAndUnhighlight() {
            element.getElement()?.run { highlightElement(this) }
            WaitUtil.sleep(timeoutInMillisecond = 100L)
            unhighlightLast()
        }
        
        private fun highlightElement(element: WebElement) {
            try {
                lastElem = element
                lastBorder = jsExecutor.executeScript(scriptGetElementBorder, lastElem) as String
            } catch (e: Exception) {
                KotlinLogging.logger {}.info { "Could not highlight element $e" }
            }
        }
        
        private fun unhighlightLast() {
            try {
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
}
