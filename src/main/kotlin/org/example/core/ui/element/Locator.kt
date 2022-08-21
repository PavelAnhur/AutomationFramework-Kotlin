package org.example.core.ui.element

class Locator {
    companion object {
        fun sortLocatorAttributeValue(sortOrder: String): String? {
            var value: String? = null
            when {
                sortOrder.lowercase().startsWith("desc") -> value = "price:desc"
                sortOrder.lowercase().startsWith("asc") -> value = "price:asc"
            }
            return value
        }

        fun viewLocatorId(view: String): String? {
            var idValue: String? = null
            when {
                view.lowercase() == "list" -> idValue = "list"
                view.lowercase() == "grid" -> idValue = "grid"
            }
            return idValue
        }
    }
}
