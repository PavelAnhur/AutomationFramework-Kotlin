package org.example.core.webdriver

import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.managers.OperaDriverManager
import mu.KLogger
import mu.KotlinLogging
import org.example.core.browser.Browser
import org.example.core.browser.IBrowser
import org.example.core.exceptions.LocalWebDriverException
import org.example.core.exceptions.RemoteWebDriverException
import org.example.core.webdriver.WebDriverFactoryConst.IMPLICIT_ELEMENT_TIMEOUT
import org.openqa.selenium.Platform
import org.openqa.selenium.UnexpectedAlertBehaviour
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL
import java.time.Duration

object WebDriverFactoryConst {
    const val IMPLICIT_ELEMENT_TIMEOUT = 3L
    const val DEFAULT_TIMEOUT_IN_SEC = 20L
}

interface IWebDriverFactory {
    val logger: KLogger
    fun setupWebDriver(): WebDriver
    
    class LocalWebDriverFactory : IWebDriverFactory {
        override val logger = KotlinLogging.logger {}
        
        override fun setupWebDriver(): WebDriver {
            val browserName = IBrowser.BaseImpl().getBrowser()
            var driver: WebDriver? = null
            try {
                driver =
                    when (browserName) {
                        Browser.CHROME.value -> getChromeDriver()
                        Browser.FIREFOX.value -> getFirefoxDriver()
                        Browser.EDGE.value -> getEdgeDriver()
                        Browser.OPERA.value -> getOperaDriver()
                        else -> throw LocalWebDriverException("can't create local web driver for $browserName browser")
                    }
            } catch (e: Exception) {
                logger.error(e.message)
            }
            driver?.manage()?.window()?.maximize()
            driver?.manage()?.timeouts()?.implicitlyWait(Duration.ofSeconds(IMPLICIT_ELEMENT_TIMEOUT))
            return driver ?: throw LocalWebDriverException("can't create local web driver")
        }
        
        private fun getChromeDriver(): WebDriver {
            WebDriverManager.chromedriver().setup()
            logger.info { "chrome web driver ready" }
            return ChromeDriver()
        }
        
        private fun getFirefoxDriver(): WebDriver {
            WebDriverManager.firefoxdriver().setup()
            val profile = FirefoxProfile()
            profile.setPreference("intl.accept_languages", "en")
            profile.setPreference("fission.webContentIsolationStrategy", 0)
            profile.setPreference("fission.bfcacheInParent", false)
            val firefoxOptionsLocal = FirefoxOptions()
            firefoxOptionsLocal.profile = profile
            logger.info { "firefox web driver ready" }
            return FirefoxDriver(firefoxOptionsLocal)
        }
        
        private fun getEdgeDriver(): WebDriver {
            WebDriverManager.edgedriver().setup()
            logger.info { "edge web driver ready" }
            return EdgeDriver()
        }
        
        private fun getOperaDriver(): WebDriver {
            WebDriverManager.operadriver().setup()
            logger.info { "opera web driver ready" }
            return OperaDriverManager.operadriver().create()
        }
    }
    
    class RemoteWebDriverFactory(private val virtualUrl: String?) : IWebDriverFactory {
        override val logger = KotlinLogging.logger {}
        
        override fun setupWebDriver(): WebDriver {
            val browserName = IBrowser.BaseImpl().getBrowser()
            var driver: WebDriver? = null
            
            try {
                driver =
                    when (browserName) {
                        Browser.REMOTE_CHROME.value -> getRemoteChromeDriver()
                        Browser.REMOTE_FIREFOX.value -> getRemoteFirefoxDriver()
                        Browser.REMOTE_EDGE.value -> getRemoteEdgeWebDriver()
                        else -> throw RemoteWebDriverException("can't create remote web driver for $browserName browser")
                    }
            } catch (e: Exception) {
                logger.error(e.message)
            }
            
            driver?.manage()?.window()?.maximize()
            driver?.manage()?.timeouts()?.implicitlyWait(Duration.ofSeconds(IMPLICIT_ELEMENT_TIMEOUT))
            return driver ?: throw RemoteWebDriverException("can't create remote web driver")
        }
        
        private fun getRemoteChromeDriver(): WebDriver {
            val cap = DesiredCapabilities()
            cap.platform = Platform.ANY
            cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE)
            cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true)
            
            val chromeOptions = ChromeOptions()
            cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions)
            chromeOptions.addArguments("--disable-notifications")
            chromeOptions.addArguments("--no-sandbox")
            logger.info { "remote chrome driver ready" }
            return RemoteWebDriver(URL(virtualUrl), chromeOptions)
        }
        
        private fun getRemoteFirefoxDriver(): WebDriver {
            val cap = DesiredCapabilities()
            cap.platform = Platform.ANY
            cap.setCapability("firefox.switches", listOf("--disable-notifications"))
            val firefoxOptions = FirefoxOptions(cap)
            return RemoteWebDriver(URL(virtualUrl), firefoxOptions)
        }
        
        private fun getRemoteEdgeWebDriver(): WebDriver {
            val options = EdgeOptions()
            val edgePrefs = HashMap<String, Any>()
            edgePrefs["profile.default_content_settings.popups"] = 0
            options.setCapability("prefs", edgePrefs)
            options.setCapability("useAutomationExtension", false)
            return RemoteWebDriver(URL(virtualUrl), options)
        }
    }
}
