package org.example.core.reflection

import mu.KotlinLogging
import java.lang.reflect.InvocationTargetException

@Suppress("UNCHECKED_CAST")
class PageManager {
    private val logger = KotlinLogging.logger {}
    private var mapOfPages: MutableMap<String, Any?>? = null
    
    fun <T> getPage(clazz: Class<T>): T? {
        if (null == mapOfPages) mapOfPages = HashMap()
        if (null == mapOfPages!![clazz.name]) mapOfPages!![clazz.name] = createPage(clazz)
        return mapOfPages!![clazz.name] as T?
    }
    
    private fun <T> createPage(clazz: Class<T>): T? {
        var page: T? = null
        try {
            page = clazz.getConstructor().newInstance()
        } catch (e: InstantiationException) {
            logger.error(e.message)
        } catch (e: IllegalAccessException) {
            logger.error(e.message)
        } catch (e: NoSuchMethodException) {
            logger.error(e.message)
        } catch (e: InvocationTargetException) {
            logger.error(e.message)
        }
        return page
    }
}
