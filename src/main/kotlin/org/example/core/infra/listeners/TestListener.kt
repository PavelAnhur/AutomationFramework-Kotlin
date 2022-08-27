package org.example.core.infra.listeners

import io.qameta.allure.Attachment
import mu.KotlinLogging
import org.example.core.infra.exceptions.TestListenerException
import org.example.core.infra.screenshot.IScreenshotMaker
import org.example.core.infra.screenshot.ScreenshotMaker
import org.example.core.infra.webdriver.WebDriverSingleton
import org.testng.ITestListener
import org.testng.ITestResult

class TestListener : ITestListener {
    private val log = KotlinLogging.logger {}
    private val driver = WebDriverSingleton.instance

    override fun onTestFailure(result: ITestResult?) {
        log.info("${getTestMethodName(result)} test is failed.")
        log.info { "Screenshot captured for test: ${getTestMethodName(result)}" }
        saveScreenshotPNG(ScreenshotMaker(driver))
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    fun saveScreenshotPNG(screenshot: IScreenshotMaker): ByteArray {
        log.info { "creating screenshot.." }
        return screenshot.create()
    }

    private fun getTestMethodName(iTestResult: ITestResult?): String {
        return iTestResult?.method?.constructorOrMethod?.name
            ?: throw TestListenerException("failed to get the test method name")
    }
}
