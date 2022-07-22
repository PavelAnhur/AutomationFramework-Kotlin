package org.example.core.ui

class Locator {
    companion object {
        fun getSortLocatorAttributeValue(sortOrder: String): String? {
            var value: String? = null
            when {
                sortOrder.lowercase().startsWith("desc") -> value = "price:desc"
                sortOrder.lowercase().startsWith("asc") -> value = "price:asc"
            }
            return value
        }
        
        fun getViewLocatorId(view: String): String? {
            var idValue: String? = null
            when {
                view.lowercase() == "list" -> idValue = "list"
                view.lowercase() == "grid" -> idValue = "grid"
            }
            return idValue
        }
    }
}
