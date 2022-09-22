package org.example.core.pageobject.store

import mu.KLogger
import mu.KotlinLogging
import org.example.core.pageobject.BasePage
import org.example.core.ui.element.UIElement
import org.openqa.selenium.By

class AuthenticationPage : BasePage() {
    private val log: KLogger = KotlinLogging.logger {}
    private val emailCreateInput = UIElement(By.id("email_create"), "authentication email input")
    private val createAccountButton = UIElement(By.xpath("//i[@class='icon-user left']"), "create account button")

    fun inputEmail(email: String) {
        log.info { "insert '$email' to ${emailCreateInput.description}.." }
        emailCreateInput.inputValue(email)
    }

    fun clickCreateAccount() {
        log.info { "clicking on ${createAccountButton.description}.." }
        createAccountButton.click()
    }
}
