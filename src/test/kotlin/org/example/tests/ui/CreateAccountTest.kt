package org.example.tests.ui

import org.example.core.infra.date.DateFormatterImpl
import org.example.core.infra.property.PropertyService.Companion.getProperty
import org.example.core.model.Account
import org.example.core.pageobject.store.AuthenticationPage
import org.example.core.pageobject.store.CreateAccountPage
import org.example.core.pageobject.store.HomePage
import org.example.steps.store.AuthenticationPageSteps
import org.example.steps.store.CreateAccountPageSteps
import org.example.steps.store.HomePageSteps
import org.example.steps.tenminmail.TenMinutesMailPageSteps
import org.testng.annotations.Test

class CreateAccountTest : BaseTest() {

    @Test(description = "Check if it's possible to create a new account")
    fun createAccountTest() {
        homePageSteps = HomePageSteps(HomePage()).also {
            it.openHomePage().clickSignInButton()
        }
        val email = TenMinutesMailPageSteps().get10MinutestMail()
        val dateOfBirth = DateFormatterImpl().format(getProperty().dateOfBirth().toString())
        val account: Account? = Account.Builder()
            .gender(getProperty().accountGender().toString())
            .firstName(getProperty().accountFirstName().toString())
            .lastName(getProperty().accountLastName().toString())
            .email(email)
            .password(getProperty().accountPassword().toString())
            .dayOfBirth(dateOfBirth.dayOfMonth)
            .monthOFBirth(dateOfBirth.month)
            .yearOfBirth(dateOfBirth.year)
            .build()
        authenticationPageSteps = AuthenticationPageSteps(AuthenticationPage())
            .also { it.registerNewAccount(email) }
        account?.let { CreateAccountPageSteps(CreateAccountPage()).fillCreateAccountForm(it) }

        Thread.sleep(5000)
    }
}
