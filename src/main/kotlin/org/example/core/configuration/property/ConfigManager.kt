package org.example.core.configuration.property

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.LoadPolicy
import org.aeonbits.owner.Config.Sources
import org.aeonbits.owner.ConfigCache

@LoadPolicy(Config.LoadType.MERGE)
@Sources("system:properties", "classpath:configuration.properties")
interface Configuration : Config {

    @Config.Key("browser")
    fun browser(): String?

    @Config.Key("virtual.url")
    fun virtualUrl(): String?
}

class ConfigManager {
    companion object {
        fun configuration(): Configuration {
            return ConfigCache.getOrCreate(Configuration::class.java)
        }
    }
}
