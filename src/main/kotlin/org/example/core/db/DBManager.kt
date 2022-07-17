package org.example.core.db

import mu.KotlinLogging
import org.example.core.configuration.property.ConfigManager
import org.example.core.file.FileReader
import java.sql.Connection
import java.sql.DriverManager

class DBManager(private var connection: Connection? = null) {
    private val logger = KotlinLogging.logger {}
    private val jdbcUrl = ConfigManager.configuration().dbUrl()
    private val dbUser = ConfigManager.configuration().dbUser()
    private val dbPasswordPath = ConfigManager.configuration().dbPasswordPath()
    private val dbPassword = dbPasswordPath?.let { FileReader.readLineFromFile(it) }
    
    fun connectToDb(): DBManager {
        connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)
        if (connection!!.isValid(0)) {
            logger.info { "connection is established" }
        } else {
            logger.info { "connection is failed" }
        }
        return this
    }
    
//    fun insertIntoIfNotExists(product: Product) {
//        connection?.let { DBQueries(it).insertInto(product.name, product.cost, product.description) }
//    }
}
