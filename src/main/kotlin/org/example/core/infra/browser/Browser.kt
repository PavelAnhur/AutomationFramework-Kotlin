package org.example.core.infra.browser

import org.example.core.infra.exceptions.BrowserException
import org.example.core.infra.property.PropertyManager

interface IBrowser {
    fun getBrowser(): String?
}

class BrowserImpl : IBrowser {
    override fun getBrowser(): String {
        return PropertyManager.config().browser() ?: throw BrowserException("can't read browser name")
    }
}

enum class Browser(
    val value: String,
) {
    CHROME("chrome"),
    FIREFOX("firefox"),
    OPERA("opera"),
    EDGE("edge")
}
