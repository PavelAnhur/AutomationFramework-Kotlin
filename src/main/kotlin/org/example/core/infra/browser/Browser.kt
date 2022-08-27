package org.example.core.infra.browser

import org.example.core.infra.exceptions.BrowserException
import org.example.core.infra.property.PropertyService

interface IBrowser {
    fun getBrowser(): String
}

class BrowserImpl : IBrowser {
    override fun getBrowser(): String {
        return PropertyService.getProperty().browser() ?: throw BrowserException("can't read browser name")
    }

    companion object {
        const val BROWSER_PREFIX_REMOTE = "remote"
        const val WINDOW_WIDTH = 1294
        const val WINDOW_HEIGHT = 906
    }
}

enum class Browser(val value: String) {
    CHROME("chrome"),
    FIREFOX("firefox"),
    OPERA("opera"),
    EDGE("edge")
}
