package org.example.tests.ui

import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.allure.Reporter
import org.example.core.infra.webdriver.WebDriverSingleton
import org.example.steps.HomePageSteps
import org.example.steps.SearchPageSteps
import org.example.steps.WomenPageSteps
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ThreadGuard
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeMethod
import org.testng.annotations.BeforeTest
import java.util.Optional

open class BaseTest(
    protected val homePageSteps: HomePageSteps = HomePageSteps(),
    protected val searchPageSteps: SearchPageSteps = SearchPageSteps(),
    protected val womenPageSteps: WomenPageSteps = WomenPageSteps(),
    protected val reporter: Reporter = Reporter.instance,
    private val log: KLogger = KotlinLogging.logger {}
) {
    private lateinit var driver: WebDriver

    @BeforeTest(alwaysRun = true)
    fun beforeTest() {
        driver = ThreadGuard.protect(WebDriverSingleton.instance)
    }

    @BeforeMethod(alwaysRun = true)
    fun beforeMethod(result: ITestResult) {
        log.info("****************************************************************")
        log.info("Starting new test: ${result.method.methodName}")
        log.info("****************************************************************")
    }

    @AfterMethod(alwaysRun = true)
    fun afterMethod(result: ITestResult) {
        val throwableText =
            Optional.ofNullable<Throwable>(result.throwable)
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
