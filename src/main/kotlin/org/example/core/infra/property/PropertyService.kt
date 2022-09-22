package org.example.core.infra.property

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

    @Config.Key("db.table.name")
    fun dbTableName(): String?

    @Config.Key("logo.title")
    fun logoTitle(): String?

    @Config.Key("collection.view")
    fun collectionView(): String?

    @Config.Key("10minutes.mail.url")
    fun tenMinutesMailUrl(): String
}

class PropertyService {
    companion object {
        fun getProperty(): Configuration {
            return ConfigCache.getOrCreate(Configuration::class.java)
        }
    }
}
