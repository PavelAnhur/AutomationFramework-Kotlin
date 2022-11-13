package org.example.steps.store

import org.example.core.model.Account
import org.example.core.pageobject.store.CreateAccountPage
import org.example.steps.BaseStep

class CreateAccountPageSteps(
    private val createAccountPage: CreateAccountPage,
) : BaseStep() {
    fun fillCreateAccountForm(account: Account) {
        reporter.info("fill create new account using $account")
        createAccountPage
            .selectGenderRadioButton(account.gender)
            .inputFirstName(account.firstName)
            .inputLastName(account.lastName)
            .inputEmail(account.email)
            .inputPassword(account.password)
            .selectDayOfBirth(account.birthDay)
            .selectMonthOfBirth(account.birthMonth)
            .selectYearOfBirth(account.birthYear)
    }
}
