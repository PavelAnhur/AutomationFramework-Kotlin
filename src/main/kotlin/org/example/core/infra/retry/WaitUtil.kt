package org.example.core.infra.retry

import mu.KotlinLogging
import java.util.concurrent.TimeUnit
import java.util.function.Supplier

const val RETRY_TIMOUT_SEC = 3L
const val NUMBER_OF_ATTEMPTS = 15

class WaitUtil {

    companion object {
        private val log = KotlinLogging.logger {}
        fun waitForTrue(
            supplier: Supplier<Boolean>,
            errorMessage: String,
            numberOfAttempts: Int = NUMBER_OF_ATTEMPTS,
        ) {
            var counter = 0
            while (counter < numberOfAttempts && !supplier.get()) {
                counter++
                log.info { "number of retry: $counter" }
                sleep(RETRY_TIMOUT_SEC)
            }
            if (counter == numberOfAttempts) {
                error(errorMessage)
            }
        }

        fun <T : Number> sleep(timeout: T) =
            try {
                if (timeout is Long) {
                    KotlinLogging.logger {}.info { "sleeping $timeout seconds.." }
                    TimeUnit.SECONDS.sleep(timeout)
                } else {
                    TimeUnit.MILLISECONDS.sleep(timeout.toLong())
                }
            } catch (e: InterruptedException) {
                log.warn { e.stackTrace }
            }
    }
}
