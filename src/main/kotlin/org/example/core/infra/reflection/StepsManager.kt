package org.example.core.infra.reflection

import mu.KotlinLogging
import java.lang.reflect.InvocationTargetException

@Suppress("UNCHECKED_CAST")
class StepsManager {
    private val logger = KotlinLogging.logger {}
    private var stepMap: MutableMap<String, Any?>? = null

    fun <T> getStepClass(clazz: Class<T>): T? {
        if (null == stepMap) stepMap = HashMap()
        if (null == stepMap!![clazz.name]) stepMap!![clazz.name] = createStepClass(clazz)
        return stepMap!![clazz.name] as T?
    }

    private fun <T> createStepClass(clazz: Class<T>): T? {
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
