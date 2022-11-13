package org.example.steps.tenminmail

import io.qameta.allure.Step
import org.example.core.pageobject.tenminmail.TenMinutesMailPage
import org.example.steps.BaseStep

class TenMinutesMailPageSteps(
    private val tenMinutesMailPage: TenMinutesMailPage = TenMinutesMailPage()
) : BaseStep() {

    @Step("Get 10 minutes mail from {0}")
    fun get10MinutestMail(url: String = tenMinutesMailPage.url()): String {
        return tenMinutesMailPage
            .open10MinutesMailPage(url)
            .copy10MinutesMail()
            .close10MinutesMailPage()
            .getEmail()
    }
}
