package org.example.core.file

import mu.KotlinLogging
import java.io.File

class FileReader {
    companion object {
        private val logger = KotlinLogging.logger {}
        fun readLineFromFile(filePath: String): String? {
            logger.info { "reading file '$filePath'" }
            return File(filePath).bufferedReader().readLine()
        }
    }
}
