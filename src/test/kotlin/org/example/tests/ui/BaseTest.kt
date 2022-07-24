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
    private val driver: WebDriver? = WebDriverSingleton.instance,
    protected val homePageSteps: HomePageSteps = StepsManager().getStepClass(HomePageSteps::class.java)!!,
    protected val womenPageSteps: WomenPageSteps = StepsManager().getStepClass(WomenPageSteps::class.java)!!,
    protected val logger: KLogger = KotlinLogging.logger {}
) {
    @BeforeMethod(alwaysRun = true)
    open fun beforeMethod(result: ITestResult) {
        logger.info("****************************************************************")
        logger.info("Starting new test: ${result.method.methodName}")
        logger.info("****************************************************************")
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
        logger.info("****************************************************************")
        logger.info("Test: ${result.method.methodName} $status")
        logger.info("****************************************************************")
    }

    @AfterTest(alwaysRun = true)
    fun tearDown() {
        logger.info { "closing browser and web driver.." }
        driver?.quit()
    }
}
