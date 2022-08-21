package org.example.core.infra.db

import mu.KotlinLogging
import org.example.core.model.Product
import java.sql.Connection

interface InsertDBQuery {
    fun insertInto(tableName: String)
}

class DBQuery(
    private val connection: Connection,
    private val product: Product
) : InsertDBQuery {

    override fun insertInto(tableName: String) {
        KotlinLogging.logger {}.info {
            """executing query:
            | INSERT INTO $tableName (name,price,description)
            | VALUES ('${product.name.prepareString()}', ${product.price}, '${product.description.prepareString()}')
            | ON CONFLICT (name) DO UPDATE
            | SET price = ${product.price};
            """.trimMargin()
        }
        val statement = connection.createStatement()
        val query = """INSERT INTO $tableName (name,price,description) 
                |VALUES ('${product.name.prepareString()}',${product.price},'${product.description.prepareString()}') 
                |ON CONFLICT (name) DO UPDATE SET price = ${product.price};""".trimMargin()
        connection.prepareStatement(query).execute()
        statement.closeOnCompletion()
    }

    private fun String.prepareString(): String = this.replace("'", "`")
}
