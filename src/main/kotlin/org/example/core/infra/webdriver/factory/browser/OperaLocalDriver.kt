package org.example.core.infra.webdriver.factory.browser

import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.managers.OperaDriverManager
import org.openqa.selenium.WebDriver

class OperaLocalDriver : BrowserDriver<WebDriver> {
    override fun setUp(): WebDriver {
        WebDriverManager.operadriver().setup()
        log.info { "opera web driver ready" }
        return OperaDriverManager.operadriver().create()
    }
}
