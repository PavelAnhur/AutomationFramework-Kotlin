package org.example.tests.ui

import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.reflection.StepsManager
import org.example.core.infra.webdriver.WebDriverSingleton
import org.example.steps.HomePageSteps
import org.example.steps.WomenPageSteps
import org.openqa.selenium.WebDriver
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeMethod
import java.util.Optional

open class BaseTest(
    protected val homePageSteps: HomePageSteps = StepsManager().getStepClass(HomePageSteps::class.java)!!,
    protected val womenPageSteps: WomenPageSteps = StepsManager().getStepClass(WomenPageSteps::class.java)!!,
    private val driver: WebDriver = WebDriverSingleton.instance,
    private val log: KLogger = KotlinLogging.logger {}
) {

    @BeforeMethod(alwaysRun = true)
    fun beforeMethod(result: ITestResult) {
        log.info("****************************************************************")
        log.info("Starting new test: ${result.method.methodName}")
        log.info("****************************************************************")
    }

    @AfterMethod(alwaysRun = true)
    fun afterMethod(result: ITestResult) {
        val throwableText = Optional.ofNullable<Throwable>(result.throwable)
            .map { throwable -> "${throwable.message}" }
            .orElse("")

        val status = when (result.status) {
            ITestResult.SUCCESS -> "passed"
            ITestResult.FAILURE -> "failed: $throwableText"
            else -> "skipped: $throwableText"
        }
        log.info("****************************************************************")
        log.info("Test: ${result.method.methodName} $status")
        log.info("****************************************************************")
    }

    @AfterTest(alwaysRun = true)
    fun tearDown() {
        log.info { "closing browser and web driver.." }
        driver.quit()
    }
}
