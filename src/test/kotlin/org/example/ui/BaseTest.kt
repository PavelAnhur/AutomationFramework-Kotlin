package org.example.ui

import mu.KotlinLogging
import org.example.core.webdriver.WebDriverSingleton
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest

open class BaseTest {
    protected val driver = WebDriverSingleton.instanceOf()
    protected val logger = KotlinLogging.logger {}

    @BeforeTest
    fun beforeTest() {
        logger.info { "opening 'My Store' home page.." }
        driver.get("http://automationpractice.com/index.php")
    }

    @AfterTest(alwaysRun = true)
    fun tearDown() {
        logger.info { "closing browser and web driver.." }
        driver.quit()
    }
}
