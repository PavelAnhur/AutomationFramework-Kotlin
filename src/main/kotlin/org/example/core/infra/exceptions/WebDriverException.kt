package org.example.core.infra.exceptions

class LocalWebDriverException(override val message: String?) : RuntimeException(message)
class RemoteWebDriverException(override val message: String?) : RuntimeException(message)
