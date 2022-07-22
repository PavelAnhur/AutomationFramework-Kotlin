package org.example.core.infra.browser

import org.example.core.infra.property.PropertyManager

interface IBrowser {
    fun getBrowser(): String?
    
    class BaseImpl : IBrowser {
        override fun getBrowser(): String? {
            return PropertyManager.config().browser()
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
