package org.example.core.db

import mu.KotlinLogging
import org.example.core.configuration.property.ConfigManager
import java.sql.Connection

class DBQueries(private val connection: Connection) {
    private val logger = KotlinLogging.logger {}
    private val tableName = ConfigManager.configuration().dbTableName()
    
    fun insertInto(
        productName: String,
        price: Double,
        description: String,
    ) {
        logger.info {
            """executing query:
            | INSERT INTO $tableName (name,price,description)
            | VALUES ('${productName.prepareString()}', $price, '${description.prepareString()}')
            | ON CONFLICT (name) DO UPDATE
            | SET price = $price;
            """.trimMargin()
        }
        val statement = connection.createStatement()
        val query =
            "INSERT INTO $tableName (name,price,description) " +
                    "VALUES ('${productName.prepareString()}',$price,'${description.prepareString()}')" +
                    " ON CONFLICT (name) DO UPDATE SET price = $price;"
        connection.prepareStatement(query).execute()
        statement.closeOnCompletion()
    }
    
    private fun String.prepareString(): String {
        return this.replace("'", "`")
    }
    
    fun select(column: String) {
        logger.info {
            """executing query:
            | SELECT $column FROM $tableName;
        """.trimMargin()
        }
    }
}
