package org.example.core.db

import mu.KotlinLogging
import java.sql.Connection

class DBQueries(private val connection: Connection) {
    private val logger = KotlinLogging.logger {}
    private val tableName = "product"
    
    fun insertInto(
        productName: String,
        cost: Double,
        description: String
    ) {
        logger.info {
            """executing query:
            | INSERT INTO $tableName
            | VALUES ($productName, $cost, $description);
            """.trimMargin()
        }
        val statement = connection.createStatement()
        val query = "INSERT INTO $tableName (name, coast, description) VALUES ($productName, $cost, $description);"
        connection.prepareStatement(query).execute()
        statement.closeOnCompletion()
    }
}