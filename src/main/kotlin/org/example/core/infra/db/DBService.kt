package org.example.core.infra.db

import org.example.core.model.Product
import java.sql.Connection

interface IDBService {
    fun insertIfNotExists(tableName: String, product: Product)
}

class DBService(private val connection: Connection) : IDBService {
    override fun insertIfNotExists(tableName: String, product: Product) {
        connection.let { DBQuery(it, product).insertInto(tableName) }
    }
}
