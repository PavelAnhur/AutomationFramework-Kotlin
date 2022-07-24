package org.example.core.infra.retry

import mu.KotlinLogging.logger
import org.example.core.infra.retry.WaitUtil.Companion.sleep
import org.testng.IRetryAnalyzer
import org.testng.ITestResult

class FailedTestRetry : IRetryAnalyzer {
    companion object {
        private const val MAX_RETRY_COUNT = 2
    }

    private var retryCount = 0

    override fun retry(iTestResult: ITestResult): Boolean =
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++
            logger {}.info("Retrying ${iTestResult.name} and count of retries is $retryCount")
            sleep(RETRY_TIMOUT_SEC)
            true
        } else false
}
