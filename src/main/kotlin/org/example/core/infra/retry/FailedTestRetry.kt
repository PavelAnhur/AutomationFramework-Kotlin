package org.example.core.infra.retry

import org.example.core.infra.ObjectManager
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
            ObjectManager.logger.info("Retrying ${iTestResult.name} and count of retries is $retryCount")
            WaitUtil.sleepSecond()
            true
        } else false
}
