package org.example.core.infra.db

import mu.KLogger
import mu.KotlinLogging
import org.example.core.infra.property.PropertyManager
import org.example.core.infra.file.FileReader
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
    override val jdbcUrl = PropertyManager.config().dbUrl()
    override val dbUser = PropertyManager.config().dbUser()
    override val dbPasswordPath = PropertyManager.config().dbPasswordPath()
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
        connection.let { DBQueries(it).insertInto(product.name, product.price, product.description) }
    }
    
    fun selectByColumn(column: String) {
        connection.let { DBQueries(it).select(column) }
    }
}
