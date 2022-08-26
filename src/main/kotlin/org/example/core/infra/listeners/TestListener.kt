package org.example.core.infra.listeners

import com.aventstack.extentreports.Status
import io.qameta.allure.Attachment
import mu.KotlinLogging
import org.example.core.infra.exceptions.TestListenerException
import org.example.core.infra.reports.ExtentManager
import org.example.core.infra.reports.ExtentTestManager
import org.example.core.infra.webdriver.WebDriverSingleton
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.testng.ITestContext
import org.testng.ITestNGListener
import org.testng.ITestResult

class TestListener : ITestNGListener {
    private fun getTestMethodName(iTestResult: ITestResult): String {
        return iTestResult.method.constructorOrMethod.name
    }

    private val log = KotlinLogging.logger {}
    private val driver = WebDriverSingleton.instance

    @Attachment(value = "Page screenshot", type = "image/png")
    fun saveScreenshotPNG(driver: WebDriver): ByteArray? {
        return (driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
    }

    // Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    fun saveTextLog(message: String?): String? {
        return message
    }

    // HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    fun attachHtml(html: String?): String? {
        return html
    }

    fun onStart(iTestContext: ITestContext) {
        log.info("I am in onStart method " + iTestContext.name)
        iTestContext.setAttribute("WebDriver", this.driver)
    }

    fun onFinish(iTestContext: ITestContext) {
        log.info("I am in onFinish method " + iTestContext.name)
        // Do tier down operations for ExtentReports reporting!
        ExtentManager.extentReports.flush()
    }

    fun onTestStart(iTestResult: ITestResult) {
        log.info(getTestMethodName(iTestResult) + " test is starting.")
    }

    fun onTestSuccess(iTestResult: ITestResult) {
        log.info(getTestMethodName(iTestResult) + " test is succeed.")
        // ExtentReports log operation for passed tests.
        ExtentTestManager.test?.log(Status.PASS, "Test passed")
    }

    fun onTestFailure(iTestResult: ITestResult) {
        log.info(getTestMethodName(iTestResult) + " test is failed.")

        // Get driver from BaseTest and assign to local webdriver variable.
        val testClass: Any = iTestResult.instance

        // Allure ScreenShotRobot and SaveTestLog
        log.info { "Screenshot captured for test case:" + getTestMethodName(iTestResult) }
        saveScreenshotPNG(driver)

        // Save a log on allure.
        saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!")

        // Take base64Screenshot screenshot for extent reports
        val base64Screenshot =
            "data:image/png;base64," + (driver as TakesScreenshot).getScreenshotAs(OutputType.BASE64)

        // ExtentReports log and screenshot operations for failed tests.
        ExtentTestManager.test?.log(
            Status.FAIL, "Test Failed",
            ExtentTestManager.test?.addScreenCaptureFromBase64String(base64Screenshot)?.model?.media?.get(0)
                ?: throw TestListenerException("screenshot operation is failed")
        )
    }

    fun onTestSkipped(iTestResult: ITestResult) {
        log.info(getTestMethodName(iTestResult) + " test is skipped.")
        // ExtentReports log operation for skipped tests.
        ExtentTestManager.test?.log(Status.SKIP, "Test Skipped")
    }

    fun onTestFailedButWithinSuccessPercentage(iTestResult: ITestResult) {
        log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult))
    }
}
