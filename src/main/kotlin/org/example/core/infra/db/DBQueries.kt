package org.example.core.infra.db

import org.example.core.infra.ObjectManager
import org.example.core.infra.property.PropertyManager
import java.sql.Connection

class DBQueries(private val connection: Connection) {
    private val logger = ObjectManager.logger
    private val tableName = PropertyManager.config().dbTableName()

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
        val query = """INSERT INTO $tableName (name,price,description) 
                |VALUES ('${productName.prepareString()}',$price,'${description.prepareString()}') 
                |ON CONFLICT (name) DO UPDATE SET price = $price;""".trimMargin()
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
