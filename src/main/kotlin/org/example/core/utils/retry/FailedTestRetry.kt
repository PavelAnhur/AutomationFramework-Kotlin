package org.example.core.utils.retry

import mu.KotlinLogging
import org.example.core.utils.retry.WaitUtilConst.RETRY_TIMOUT_SEC
import org.testng.IRetryAnalyzer
import org.testng.ITestResult

class FailedTestRetry : IRetryAnalyzer {
    companion object {
        private const val MAX_RETRY_COUNT = 2
    }
    
    private var retryCount = 0
    private val logger = KotlinLogging.logger {}
    
    override fun retry(iTestResult: ITestResult): Boolean =
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++
            logger.info("Retrying ${iTestResult.name} and count of retries is $retryCount")
            WaitUtil.sleep(RETRY_TIMOUT_SEC)
            true
        } else false
}
