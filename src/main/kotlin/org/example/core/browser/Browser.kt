package org.example.core.browser

import org.example.core.configuration.property.ConfigManager

interface IBrowser {
    fun getBrowser(): String?
    
    class BaseImpl : IBrowser {
        override fun getBrowser(): String? {
            return ConfigManager.configuration().browser()
        }
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
