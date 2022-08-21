package org.example.core.infra.db

import mu.KotlinLogging
import org.example.core.infra.exceptions.DBManagerException
import org.example.core.infra.file.FileReader
import org.example.core.infra.property.PropertyManager
import java.sql.Connection
import java.sql.DriverManager

interface IDBManager {
    fun connectToDb(): Connection
    fun closeDb(connection: Connection)
}

class DBManager : IDBManager {
    private val log = KotlinLogging.logger {}
    private val jdbcUrl = PropertyManager.config().dbUrl()
    private val dbUser = PropertyManager.config().dbUser()
    private val dbPasswordPath = PropertyManager.config().dbPasswordPath()
    private val dbPassword = dbPasswordPath?.let { FileReader.readLineFromFile(it) }

    override fun connectToDb(): Connection {
        val connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)
        return if (connection.isValid(0)) {
            log.info { "connection is established" }
            connection
        } else {
            throw DBManagerException("connection is failed")
        }
    }

    override fun closeDb(connection: Connection) {
        log.info { "closing database connection.." }
        connection.close()
    }
}
