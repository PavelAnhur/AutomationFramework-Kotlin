package org.example.core.pageobject.tenminmail

import mu.KLogger
import mu.KotlinLogging
import org.example.core.pageobject.BasePage
import org.example.core.ui.element.UIElement
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable

class TenMinutesMailPage : BasePage() {
    private val log: KLogger = KotlinLogging.logger {}
    private val copyIcon = UIElement(By.cssSelector("span.copy_icon"), "copy mail icon")
    private lateinit var tabs: Set<String>
    private lateinit var email: String

    fun open10MinutesMailPage(url: String): TenMinutesMailPage {
        log.info { "opening $url page.." }
        val jsExecutor = driver as JavascriptExecutor
        jsExecutor.executeScript("window.open('$url','_blank')")
        tabs = driver.windowHandles
        driver.switchTo().window(tabs.elementAt(1))
        driver.get(url)
        return this
    }

    fun copy10MinutesMail(): TenMinutesMailPage {
        log.info { "copying email to the clipboard.." }
        copyIcon.click()
        val contents: Transferable = Toolkit.getDefaultToolkit().systemClipboard.getContents(null)
        if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            email = contents.getTransferData(DataFlavor.stringFlavor) as String
        }
        return this
    }

    fun close10MinutesMailPage(): String {
        log.info { "closing ${url()} page.." }
        driver.close()
        driver.switchTo().window(tabs.elementAt(0))
        return email
    }

    fun url(): String {
        return tenMinutesMailUrl
    }
}
