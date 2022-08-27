package org.example.core.infra.webdriver.factory.browser

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

class EdgeLocalDriver : BrowserDriver<WebDriver> {
    override fun setUp(): WebDriver {
        WebDriverManager.edgedriver().setup()
        log.info { "edge web driver ready" }
        return EdgeDriver()
    }
}

class EdgeRemoteDriver(private val virtualUrl: String) : BrowserDriver<RemoteWebDriver> {
    override fun setUp(): RemoteWebDriver {
        val options = EdgeOptions()
        options.setAcceptInsecureCerts(true)
        log.info { "remote edge driver ready" }
        return RemoteWebDriver(URL(virtualUrl), options)
    }
}
