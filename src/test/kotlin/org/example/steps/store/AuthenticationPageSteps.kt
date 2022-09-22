package org.example.steps.store

import io.qameta.allure.Step
import org.example.core.pageobject.store.AuthenticationPage
import org.example.steps.BaseStep

class AuthenticationPageSteps(
    private val authenticationPage: AuthenticationPage
) : BaseStep() {
    @Step("Register new account using email '{0}'")
    fun registerNewAccount(email: String) {
        authenticationPage.inputEmail(email)
        authenticationPage.clickCreateAccount()
    }
}
