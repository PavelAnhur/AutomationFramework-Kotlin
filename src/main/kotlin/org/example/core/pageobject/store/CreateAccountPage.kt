package org.example.core.pageobject.store

import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.exceptions.CreateAccountPageException
import org.example.core.infra.retry.WaitUtil
import org.example.core.pageobject.BasePage
import org.example.core.ui.element.UIElement
import org.openqa.selenium.By
import java.time.Month
import java.util.function.Supplier

private const val NUMBER_OF_ATTEMPTS = 2

class CreateAccountPage : BasePage() {
    private val log: KLogger = KotlinLogging.logger {}
    private val accountCreationForm = UIElement(By.id("account-creation_form"), "account creation form")
    private val genderTitle = UIElement(By.xpath("//form//*[@class='clearfix']"), "gender radio buttons")
    private val mrRadioButton = UIElement(By.id("id_gender1"), "radio button Mr.")
    private val titleMrsRadioButton = UIElement(By.id("id_gender2"), "title radio button Mrs.")
    private val firstNameInput = UIElement(By.id("customer_firstname"), "firstname input field")
    private val lastNameInput = UIElement(By.id("customer_lastname"), "lastname input field")
    private val emailInput = UIElement(By.id("email"), "email input field")
    private val passwordInput = UIElement(By.id("passwd"), "password input field")
    private val dayOfBirth = UIElement(By.id("days"), "day of birth dropdown")
    private val monthOfBirth = UIElement(By.id("months"), "month of birth dropdown")
    private val yearOfBirth = UIElement(By.id("years"), "year of birth dropdown")

    fun selectGenderRadioButton(gender: String): CreateAccountPage {
        val isCreateAccountFormLoaded = Supplier<Boolean> {
            try {
                accountCreationForm.waitForDisplayed()
                log.info { "selecting ${genderTitle.description} with $gender.." }
                when (gender) {
                    "mister" -> mrRadioButton.click()
                    "misses" -> titleMrsRadioButton.click()
                    else -> throw CreateAccountPageException("gender is unknown")
                }
                return@Supplier true
            } catch (e: Exception) {
                log.warn { "create account form isn't loaded due to ${e.localizedMessage}" }
                driver.navigate().refresh()
                return@Supplier false
            }
        }
        WaitUtil.waitForTrue(
            isCreateAccountFormLoaded,
            numberOfAttempts = NUMBER_OF_ATTEMPTS,
            errorMessage = "Failed to load account creation form"
        )
        return this
    }

    fun inputFirstName(firstName: String): CreateAccountPage {
        log.info { "entering $firstName to the ${firstNameInput.description}.." }
        firstNameInput.inputValue(firstName)
        return this
    }

    fun inputLastName(lastName: String): CreateAccountPage {
        log.info { "entering $lastName to the ${lastNameInput.description}.." }
        lastNameInput.inputValue(lastName)
        return this
    }

    fun inputEmail(email: String): CreateAccountPage {
        log.info { "entering $email to the ${emailInput.description}.." }
        emailInput.inputValue(email)
        return this
    }

    fun inputPassword(password: String): CreateAccountPage {
        log.info { "entering $password to the ${passwordInput.description}.." }
        passwordInput.inputValue(password)
        return this
    }

    fun selectDayOfBirth(day: String): CreateAccountPage {
        log.info("select $day from ${dayOfBirth.description}")
        dayOfBirth.select(day)
        return this
    }

    fun selectMonthOfBirth(month: Month): CreateAccountPage {
        log.info { "select $month from ${monthOfBirth.description}" }
        monthOfBirth.select(month.value.toString())
        return this
    }

    fun selectYearOfBirth(year: String): CreateAccountPage {
        log.info { "select $year from ${yearOfBirth.description}" }
        yearOfBirth.select(year)
        return this
    }
}
