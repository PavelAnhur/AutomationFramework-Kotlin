package org.example.core.infra.browser

import mu.KotlinLogging
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

interface IPageLoader<in T : Number> {
    fun waitForPageLoaded(timeout: T)
}

class BrowserWindowHandler(
    private val driver: WebDriver
) : IPageLoader<Long> {
    override fun waitForPageLoaded(timeout: Long) {
        KotlinLogging.logger {}.info { "waiting until page is completely loaded.." }
        val jsExecutor = driver as JavascriptExecutor
        WebDriverWait(driver, Duration.ofSeconds(timeout))
            .until { jsExecutor.executeScript("return document.readyState").equals("complete") }
    }
}
