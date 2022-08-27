package org.example.core.infra.allure

import io.qameta.allure.Allure
import io.qameta.allure.model.Status
import io.qameta.allure.model.StepResult
import mu.KLogger
import mu.KotlinLogging
import java.util.UUID

object Reporter {
    val instance: Reporter
        get() {
            if (reporter == null) {
                reporter = Reporter
            }
            return reporter as Reporter
        }
    private var reporter: Reporter? = null
    private val log: KLogger = KotlinLogging.logger {}

    fun info(message: String?, status: Status = Status.PASSED) {
        log(message, status)
    }

    fun warn(message: String?, status: Status = Status.BROKEN) {
        log(message, status)
    }

    private fun log(message: String?, status: Status?) {
        log.info(message)
        val allureLifecycle = Allure.getLifecycle()
        val stepResult = StepResult()
        stepResult.name = message
        stepResult.status = status
        allureLifecycle.startStep(UUID.randomUUID().toString(), stepResult)
        allureLifecycle.stopStep()
    }
}
