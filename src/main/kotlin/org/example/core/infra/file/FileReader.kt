package org.example.core.infra.file

import java.io.File

class FileReader {
    companion object {
        fun readLineFromFile(filePath: String): String? {
            return File(getAbsolutePath(filePath)).bufferedReader().readLine()
        }
        
        fun readTextFromFile(filePath: String): String {
            return File(getAbsolutePath(filePath)).bufferedReader().readText()
        }
        
        private fun getAbsolutePath(relativePath: String): String {
            val pathPrefix = "src/main/resources"
            return if (relativePath.startsWith(pathPrefix)) relativePath
            else "$pathPrefix/$relativePath"
        }
    }
}
