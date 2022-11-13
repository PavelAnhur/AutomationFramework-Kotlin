package org.example.core.pageobject.tenminmail

import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.browser.BrowserTabHandler
import org.example.core.infra.browser.BrowserWindowHandler
import org.example.core.infra.webdriver.config.WebDriverConfigImpl.Companion.EXPLICIT_TIMEOUT_SEC
import org.example.core.pageobject.BasePage
import org.example.core.ui.element.UIElement
import org.openqa.selenium.By
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable

class TenMinutesMailPage : BasePage() {
    private val log: KLogger = KotlinLogging.logger {}
    private val copyIcon = UIElement(By.cssSelector("span.copy_icon"), "copy mail icon")
    private lateinit var tabs: Set<String>
    private lateinit var email: String
    private val tabsHandler: BrowserTabHandler = BrowserTabHandler(driver)

    fun open10MinutesMailPage(url: String): TenMinutesMailPage {
        log.info { "opening $url page.." }
        tabs = tabsHandler.openPageOnNewTab(url)
        return this
    }

    fun copy10MinutesMail(): TenMinutesMailPage {
        log.info { "copying email to the clipboard.." }
        BrowserWindowHandler(driver).waitForPageLoaded(EXPLICIT_TIMEOUT_SEC)
        copyIcon.click()
        val contents: Transferable = Toolkit.getDefaultToolkit().systemClipboard.getContents(null)
        if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            email = contents.getTransferData(DataFlavor.stringFlavor) as String
        }
        return this
    }

    fun close10MinutesMailPage(): TenMinutesMailPage {
        tabsHandler.switchToTab(tabs.elementAt(0))
        return this
    }

    fun getEmail(): String {
        log.info { "email from ${url()}: $email" }
        return email
    }

    fun url(): String {
        return tenMinutesMailUrl
    }
}
