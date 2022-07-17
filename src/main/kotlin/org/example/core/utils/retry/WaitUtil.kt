package org.example.core.utils.retry

import mu.KotlinLogging
import java.util.concurrent.TimeUnit
import java.util.function.Supplier

const val RETRY_TIMOUT_SEC = 5L

class WaitUtil {
    
    companion object {
        private val logger = KotlinLogging.logger {}
        fun waitForTrue(
            supplier: Supplier<Boolean>,
            numberOfAttempts: Int,
            errorMessage: String
        ) {
            var counter = 0
            while (counter < numberOfAttempts && !supplier.get()) {
                counter++
                logger.info { "number of retry: $counter" }
                sleep(RETRY_TIMOUT_SEC)
            }
            if (counter == numberOfAttempts) {
                error(errorMessage)
            }
        }
        
        fun sleep(timeoutInSec: Long) =
            try {
                KotlinLogging.logger {}.info { "sleeping $timeoutInSec seconds.." }
                TimeUnit.SECONDS.sleep(timeoutInSec)
            } catch (e: InterruptedException) {
                throw InterruptedException("${e.message}")
            }
    }
}
