package org.example.core.infra.exceptions

class DBManagerException(override val message: String?) : RuntimeException(message)
