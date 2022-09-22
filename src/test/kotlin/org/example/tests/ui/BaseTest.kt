@file:JvmName("WebDriverSingletonJava")

package org.example.tests.ui

import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.allure.Reporter
import org.example.core.infra.webdriver.WebDriverSingleton
import org.example.steps.store.AuthenticationPageSteps
import org.example.steps.store.HomePageSteps
import org.example.steps.store.WomenPageSteps
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeMethod
import java.util.Optional

open class BaseTest(
    protected val reporter: Reporter = Reporter.instance,
    private val log: KLogger = KotlinLogging.logger {}
) {
    private val driver by lazy { WebDriverSingleton.instance }
    protected lateinit var homePageSteps: HomePageSteps
    protected lateinit var womenPageSteps: WomenPageSteps
    protected lateinit var authenticationPageSteps: AuthenticationPageSteps

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

    @AfterTest
    fun tearDown() {
        log.info { "closing browser and web driver.." }
        driver.quit()
    }
}
