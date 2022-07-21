package org.example.core.db

import mu.KLogger
import mu.KotlinLogging
import org.example.core.configuration.property.ConfigManager
import org.example.core.file.FileReader
import org.example.core.model.Product
import java.sql.Connection
import java.sql.DriverManager

interface IDBManager {
    var connection: Connection
    val logger: KLogger
    val jdbcUrl: String?
    val dbUser: String?
    val dbPasswordPath: String?
    val dbPassword: String?
    
    fun connectToDb()
    fun closeDb()
}

class DBManager : IDBManager {
    override lateinit var connection: Connection
    override val logger = KotlinLogging.logger {}
    override val jdbcUrl = ConfigManager.configuration().dbUrl()
    override val dbUser = ConfigManager.configuration().dbUser()
    override val dbPasswordPath = ConfigManager.configuration().dbPasswordPath()
    override val dbPassword = dbPasswordPath?.let { FileReader.readLineFromFile(it) }
    
    override fun connectToDb() {
        connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)
        if (connection.isValid(0)) {
            logger.info { "connection is established" }
        } else {
            logger.info { "connection is failed" }
        }
    }
    
    override fun closeDb() {
        logger.info { "closing database connection.." }
        connection.close()
    }
    
    fun insertIntoIfNotExists(product: Product) {
        connection.let { DBQueries(it).insertInto(product.name, product.cost, product.description) }
    }
    
    fun selectByColumn(column: String) {
        connection.let { DBQueries(it).select(column) }
    }
}
