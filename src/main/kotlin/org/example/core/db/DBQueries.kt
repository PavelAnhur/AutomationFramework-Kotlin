package org.example.core.db

import mu.KotlinLogging
import org.example.core.configuration.property.ConfigManager
import java.sql.Connection

class DBQueries(private val connection: Connection) {
    private val logger = KotlinLogging.logger {}
    private val tableName = ConfigManager.configuration().dbTableName()
    
    fun insertInto(
        productName: String,
        cost: Double,
        description: String
    ) {
        logger.info {
            """executing query:
            | INSERT INTO $tableName (name,cost,description)
            | VALUES ('${productName.prepareString()}', $cost, '${description.prepareString()}')
            | ON CONFLICT (name) DO UPDATE
            | SET cost = $cost;
            """.trimMargin()
        }
        val statement = connection.createStatement()
        val query =
            "INSERT INTO $tableName (name,cost,description) " +
                    "VALUES ('${productName.prepareString()}',$cost,'${description.prepareString()}')" +
                    " ON CONFLICT (name) DO UPDATE SET cost = $cost;"
        connection.prepareStatement(query, intArrayOf(1, 3)).execute()
        statement.closeOnCompletion()
    }
    
    private fun String.prepareString(): String {
        return this.replace("'", "`")
    }
}
