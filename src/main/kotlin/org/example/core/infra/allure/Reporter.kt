package org.example.core.infra.allure

import io.qameta.allure.Allure
import io.qameta.allure.model.Status
import io.qameta.allure.model.StepResult
import mu.KLogger
import mu.KotlinLogging
import java.util.UUID

class Reporter private constructor() {
    val log: KLogger = KotlinLogging.logger {}

    fun log(message: String?, status: Status? = Status.PASSED) {
        log.info(message)
        val allureLifecycle = Allure.getLifecycle()
        val stepResult = StepResult()
        stepResult.name = message
        stepResult.status = status
        allureLifecycle.startStep(UUID.randomUUID().toString(), stepResult)
        allureLifecycle.stopStep()
    }

    companion object {
        private var reporter: Reporter? = null
        val instance: Reporter
            get() {
                if (reporter == null) {
                    reporter = Reporter()
                }
                return reporter as Reporter
            }
    }
}
