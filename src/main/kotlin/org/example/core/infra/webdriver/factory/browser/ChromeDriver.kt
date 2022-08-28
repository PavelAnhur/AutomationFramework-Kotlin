package org.example.core.infra.webdriver.factory.browser

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.Platform
import org.openqa.selenium.UnexpectedAlertBehaviour
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

class ChromeLocalDriver : BrowserDriver<WebDriver> {
    override fun getInstance(): WebDriver {
        WebDriverManager.chromedriver().setup()
        val opt = ChromeOptions()
        opt.addArguments("headless", "no-sandbox")
        log.info { "chrome web driver ready" }
        return ChromeDriver(opt)
    }
}

class ChromeRemoteDriver(private val virtualUrl: String) : BrowserDriver<RemoteWebDriver> {
    override fun getInstance(): RemoteWebDriver {
        val cap = DesiredCapabilities()
        cap.platform = Platform.ANY
        cap.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE)
        cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true)

        val chromeOptions = ChromeOptions()
        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions)
        chromeOptions.addArguments("--disable-notifications")
        chromeOptions.addArguments("--no-sandbox")
        log.info { "remote chrome driver ready" }
        return RemoteWebDriver(URL(virtualUrl), chromeOptions)
    }
}
