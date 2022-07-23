package org.example.core.infra.file

import mu.KotlinLogging
import java.io.File

class FileReader {
    companion object {
        private val logger = KotlinLogging.logger {}
        fun readLineFromFile(filePath: String): String? {
            val absoluteFilePath = getAbsolutePath(filePath)
            logger.info { "reading line from file '$absoluteFilePath'" }
            return File(absoluteFilePath).bufferedReader().readLine()
        }
        
        fun readTextFromFile(filePath: String): String {
            val absoluteFilePath = getAbsolutePath(filePath)
            logger.info { "reading text from file '$absoluteFilePath'" }
            return File(absoluteFilePath).bufferedReader().readText()
        }
        
        private fun getAbsolutePath(relativePath: String): String {
            val pathPrefix = "src/main/resources"
            return if (relativePath.startsWith(pathPrefix)) relativePath
            else "$pathPrefix/$relativePath"
        }
    }
}
