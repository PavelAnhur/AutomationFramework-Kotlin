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
    
    @Config.Key("homepage.url")
    fun homePageUrl(): String?
    
    @Config.Key("db.url")
    fun dbUrl(): String?
    
    @Config.Key("db.user")
    fun dbUser(): String?
    
    @Config.Key("db.password.path")
    fun dbPasswordPath(): String?
}

class ConfigManager {
    companion object {
        fun configuration(): Configuration {
            return ConfigCache.getOrCreate(Configuration::class.java)
        }
    }
}
