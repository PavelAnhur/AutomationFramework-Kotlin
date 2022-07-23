package org.example.core.infra.retry

import mu.KotlinLogging
import java.util.concurrent.TimeUnit
import java.util.function.Supplier

const val RETRY_TIMOUT_SEC = 3
const val NUMBER_OF_ATTEMPTS = 15

class WaitUtil {
    companion object {
        private val logger = KotlinLogging.logger {}
        fun waitForTrue(
            supplier: Supplier<Boolean>,
            errorMessage: String,
            numberOfAttempts: Int = NUMBER_OF_ATTEMPTS,
        ) {
            var counter = 0
            while (counter < numberOfAttempts && !supplier.get()) {
                counter++
                logger.info { "number of retry: $counter" }
                sleep()
            }
            if (counter == numberOfAttempts) {
                error(errorMessage)
            }
        }
        
        fun sleep(timeoutInSec: Int = RETRY_TIMOUT_SEC) =
            try {
                KotlinLogging.logger {}.info { "sleeping $timeoutInSec seconds.." }
                TimeUnit.SECONDS.sleep(timeoutInSec.toLong())
            } catch (e: InterruptedException) {
                throw InterruptedException("${e.message}")
            }
        
        fun sleep(timeoutInMillisecond: Long) {
            try {
                TimeUnit.MILLISECONDS.sleep(timeoutInMillisecond)
            } catch (e: InterruptedException) {
                throw InterruptedException("${e.message}")
            }
        }
    }
}
