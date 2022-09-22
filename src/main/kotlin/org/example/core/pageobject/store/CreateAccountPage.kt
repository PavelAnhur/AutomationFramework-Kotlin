package org.example.core.pageobject.store

import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.exceptions.CreateAccountPageException
import org.example.core.ui.element.UIElement
import org.openqa.selenium.By

class CreateAccountPage {
    private val log: KLogger = KotlinLogging.logger {}
    private val titleMrRadioButton = UIElement(By.id("id_gender1"), "title radio button Mr.")
    private val titleMrsRadioButton = UIElement(By.id("id_gender2"), "title radio button Mrs.")
    private val firstNameInput = UIElement(By.id("customer_firstname"), "firstname input field")
    private val lastNameInput = UIElement(By.id("customer_lastname"), "lastname input field")
    private val emailInput = UIElement(By.id("email"), "email input field")
    private val passwordInput = UIElement(By.id("passwd"), "password input field")

    fun checkTitleRadioButton(gender: String) {
        log.info { "checking title radio button $gender.." }
        when (gender) {
            "mister" -> titleMrRadioButton.click()
            "misses" -> titleMrsRadioButton.click()
            else -> throw CreateAccountPageException("gender is unknown")
        }
    }

    fun inputFirstName(firstName: String) {
        log.info { "entering $firstName to the ${firstNameInput.description}.." }
        firstNameInput.inputValue(firstName)
    }

    fun inputLastName(lastName: String) {
        log.info { "entering $lastName to the ${lastNameInput.description}.." }
        lastNameInput.inputValue(lastName)
    }

    fun inputEmail(email: String) {
        log.info { "entering $email to the ${emailInput.description}.." }
        emailInput.inputValue(email)
    }

    fun inputPassword(password: String) {
        log.info { "entering $password to the ${passwordInput.description}.." }
        passwordInput.inputValue(password)
    }
}
