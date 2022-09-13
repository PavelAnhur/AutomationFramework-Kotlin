package org.example.core.infra.db

import mu.KotlinLogging
import org.example.core.infra.exceptions.DBManagerException
import java.sql.Connection
import java.sql.DriverManager

interface IDBManager {
    fun connectToDb(jdbcUrl: String, dbUser: String, dbPassword: String): Connection
    fun closeDb(connection: Connection)
}

class DBManager : IDBManager {
    private val log = KotlinLogging.logger {}

    override fun connectToDb(jdbcUrl: String, dbUser: String, dbPassword: String): Connection {
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
