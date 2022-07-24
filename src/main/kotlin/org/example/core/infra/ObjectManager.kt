package org.example.core.infra

import mu.KLogger
import mu.KotlinLogging

object ObjectManager {
    val logger: KLogger
        get() = KotlinLogging.logger {}
}
