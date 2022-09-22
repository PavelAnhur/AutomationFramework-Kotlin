package org.example.tests.ui

import org.example.core.pageobject.store.AuthenticationPage
import org.example.core.pageobject.store.HomePage
import org.example.steps.store.AuthenticationPageSteps
import org.example.steps.store.HomePageSteps
import org.example.steps.tenminmail.TenMinutesMailPageSteps
import org.testng.annotations.Test

class CreateAccountTest : BaseTest() {

    @Test(description = "Check if it's possible to create a new account")
    fun createAccountTest() {
        homePageSteps = HomePageSteps(HomePage())
            .also {
                it.openHomePage()
                    .clickSignInButton()
            }
        val email = TenMinutesMailPageSteps().get10MinutestMail()
        authenticationPageSteps = AuthenticationPageSteps(AuthenticationPage())
            .also { it.registerNewAccount(email) }

        Thread.sleep(5000)
    }
}
