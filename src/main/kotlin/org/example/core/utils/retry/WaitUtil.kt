package org.example.core.utils.retry

import mu.KotlinLogging
import java.util.concurrent.TimeUnit
import java.util.function.Supplier

object WaitUtilConst {
    const val RETRY_TIMOUT_SEC = 5L
}

class WaitUtil {
    
    private val logger = KotlinLogging.logger {}
    
    fun waitForTrue(
        supplier: Supplier<Boolean>,
        numberOfAttempts: Int,
        errorMessage: String
    ) {
        var counter = 0
        while (counter < numberOfAttempts && !supplier.get()) {
            counter++
            sleep(WaitUtilConst.RETRY_TIMOUT_SEC)
            logger.info { "number of attempt: $counter" }
        }
        if (counter == numberOfAttempts) {
            error(errorMessage)
        }
    }
    
    companion object {
        fun sleep(timeoutInSec: Long) =
            try {
                KotlinLogging.logger {}.info { "sleep $timeoutInSec seconds.." }
                TimeUnit.SECONDS.sleep(timeoutInSec)
            } catch (e: InterruptedException) {
                throw InterruptedException("${e.message}")
            }
    }
}
