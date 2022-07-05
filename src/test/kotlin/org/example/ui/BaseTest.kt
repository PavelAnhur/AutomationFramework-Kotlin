package org.example.ui

import mu.KotlinLogging
import org.example.core.webdriver.WebDriverSingleton
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import java.util.Optional

open class BaseTest {
    protected val logger = KotlinLogging.logger {}
    private val driver = WebDriverSingleton.instanceOf()
    
    @BeforeClass(alwaysRun = true)
    fun before() {
        logger.info { "clearing cookies.." }
        driver.manage().deleteAllCookies()
        logger.info { "opening 'My Store' home page.." }
        driver.get("http://automationpractice.com/index.php")
    }
    
    @BeforeMethod(alwaysRun = true)
    open fun beforeMethod(result: ITestResult) {
        logger.info("****************************************************************")
        logger.info("Starting new test: " + result.method.methodName)
        logger.info("****************************************************************")
    }
    
    @AfterMethod(alwaysRun = true)
    fun afterMethod(result: ITestResult) {
        val throwableText = Optional.ofNullable<Throwable>(result.throwable)
            .map { x: Throwable -> "${x.message}" }
            .orElse("")
        
        val status = when (result.status) {
            ITestResult.SUCCESS -> "passed"
            ITestResult.FAILURE -> "failed: $throwableText"
            else -> "skipped: $throwableText"
        }
        logger.info("****************************************************************")
        logger.info("Test: " + result.method.methodName + " " + status)
        logger.info("****************************************************************")
    }
    
    @AfterTest(alwaysRun = true)
    fun tearDown() {
        logger.info { "closing browser and web driver.." }
        driver.quit()
    }
}
