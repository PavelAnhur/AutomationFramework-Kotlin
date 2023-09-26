package org.example.core.infra.screenshot

import org.openqa.selenium.WebDriver
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

private const val SCROLL_TIMEOUT = 100
private const val IMAGE_FORMAT = "png"

class ScreenshotMaker(private val driver: WebDriver) : IScreenshotMaker {
    override fun create(): ByteArray =
        AShot().shootingStrategy(
            ShootingStrategies.viewportPasting(SCROLL_TIMEOUT)
        ).takeScreenshot(driver).image.run {
            val byteArrayOutputStream = ByteArrayOutputStream()
            ImageIO.write(this, IMAGE_FORMAT, byteArrayOutputStream)
            byteArrayOutputStream.toByteArray()
        }
}
