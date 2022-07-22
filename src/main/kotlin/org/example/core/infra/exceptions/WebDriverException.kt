package org.example.core.infra.exceptions

class LocalWebDriverException(message: String?) : RuntimeException(message)

class RemoteWebDriverException(message: String?) : RuntimeException(message)
