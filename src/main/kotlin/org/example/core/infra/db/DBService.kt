package org.example.core.infra.db

import org.example.core.infra.exceptions.DBServiceException
import org.example.core.infra.property.PropertyManager
import org.example.core.model.Product
import java.sql.Connection

interface IDBService {
    fun insertIfNotExists(product: Product)
}

class DBService(
    private val connection: Connection,
) : IDBService {

    override fun insertIfNotExists(product: Product) {
        val tableName = PropertyManager.config().dbTableName() ?: throw DBServiceException("table name not found")
        connection.let { DBQuery(it, product).insertInto(tableName) }
    }
}
