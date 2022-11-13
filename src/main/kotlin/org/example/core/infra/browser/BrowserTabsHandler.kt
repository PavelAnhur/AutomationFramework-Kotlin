package org.example.core.infra.browser

import mu.KLogger
import mu.KotlinLogging
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

interface INewBrowserTabOpener<T, K> {
    fun openPageOnNewTab(url: T): Set<K>
}

interface IBrowserTabSwitcher<T> {
    fun switchToTab(tab: T)
}

interface IBrowserTabCloser {
    fun closeTab(tab: String)
}

class BrowserTabHandler(
    private val driver: WebDriver
) : INewBrowserTabOpener<String, String>, IBrowserTabSwitcher<String>, IBrowserTabCloser {

    private val log: KLogger = KotlinLogging.logger {}

    override fun openPageOnNewTab(url: String): Set<String> {
        val jsExecutor = driver as JavascriptExecutor
        jsExecutor.executeScript("window.open('$url','_blank')")
        val tabs = driver.windowHandles
        log.info { "switching to the new tab.." }
        driver.switchTo().window(tabs.elementAt(1))
        driver.get(url)
        return tabs
    }

    override fun switchToTab(tab: String) {
        log.info { "switching to $tab.." }
        driver.switchTo().window(tab)
    }

    override fun closeTab(tab: String) {
        log.info { "closing tab $tab.." }
        driver.close()
    }
}
