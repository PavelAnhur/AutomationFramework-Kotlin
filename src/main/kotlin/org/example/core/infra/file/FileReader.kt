package org.example.core.infra.file

import java.io.File

class FileReader {
    companion object {
        fun readLineFromFile(filePath: String): String {
            return File(filePath.getAbsolutePath()).bufferedReader().readLine()
        }

        fun readTextFromFile(filePath: String): String {
            return File(filePath.getAbsolutePath()).bufferedReader().readText()
        }

        private fun String.getAbsolutePath(): String {
            val pathPrefix = "src/main/resources"
            return if (this.startsWith(pathPrefix)) {
                this
            } else {
                "$pathPrefix/$this"
            }
        }
    }
}
