package org.example.core.ui

import mu.KotlinLogging
import org.example.core.infra.file.FileReader
import org.example.core.infra.webdriver.WebDriverSingleton
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement

interface IElementHighlighter {
    fun highlightAndUnhighlightElement(element: UIElement)
    
    class BaseImpl : IElementHighlighter {
        private val scriptUnhighlightElement by lazy { FileReader.readTextFromFile("HighlightElement/SCRIPT_UNHIGHLIGHT_ELEMENT") }
        private val scriptGetElementBorder by lazy { FileReader.readTextFromFile("HighlightElement/SCRIPT_GET_ELEMENT_BORDER") }
        private val jsExecutor = WebDriverSingleton.instance as JavascriptExecutor
        private var lastElem: WebElement? = null
        private var lastBorder: String? = null
        private val logger = KotlinLogging.logger {}
        
        override fun highlightAndUnhighlightElement(element: UIElement) {
            val webElement = element.getElement()
            unhighlightLast()
            webElement?.let { highlightElement(it) }
        }
        
        private fun unhighlightLast() {
            if (null != lastElem) {
                try {
                    jsExecutor.executeScript(scriptUnhighlightElement, lastElem, lastBorder)
                } finally {
                    lastElem = null
                }
            }
        }
        
        private fun highlightElement(element: WebElement) =
            try {
                unhighlightLast()
                lastElem = element
                lastBorder = jsExecutor.executeScript(scriptGetElementBorder, element) as String
            } catch (e: Exception) {
                logger.info { "Could not highlight element $e" }
            }
    }
}