package org.example.core.infra.exceptions

class DBServiceException(override val message: String?) : RuntimeException(message)
