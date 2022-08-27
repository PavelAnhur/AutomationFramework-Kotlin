package org.example.core.infra.listeners

import io.qameta.allure.Attachment
import mu.KotlinLogging
import org.example.core.infra.exceptions.TestListenerException
import org.example.core.infra.webdriver.WebDriverSingleton
import org.openqa.selenium.WebDriver
import org.testng.ITestListener
import org.testng.ITestResult
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

private const val SCROLL_TIMEOUT = 100

class TestListener : ITestListener {
    private val log = KotlinLogging.logger {}
    private val driver = WebDriverSingleton.instance

    override fun onTestFailure(result: ITestResult?) {
        log.info("${getTestMethodName(result)} test is failed.")
        log.info { "Screenshot captured for test: ${getTestMethodName(result)}" }
        saveScreenshotPNG(driver)
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    fun saveScreenshotPNG(driver: WebDriver): ByteArray? {
        return AShot().shootingStrategy(ShootingStrategies.viewportPasting(SCROLL_TIMEOUT))
            .takeScreenshot(driver)
            .image.run {
                val byteArrayOutputStream = ByteArrayOutputStream()
                ImageIO.write(this, "png", byteArrayOutputStream)
                byteArrayOutputStream.toByteArray()
            }
    }

    private fun getTestMethodName(iTestResult: ITestResult?): String {
        return iTestResult?.method?.constructorOrMethod?.name
            ?: throw TestListenerException("failed to get the test method name")
    }
}
