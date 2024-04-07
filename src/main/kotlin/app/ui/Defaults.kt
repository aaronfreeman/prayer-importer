package app.ui

import java.io.File

class Defaults {
    private val delimiter = "|o|"
    var excelFile = ""
    var powerpointFile = ""
    var outputDir = ""
    var fileName = ""

    fun save() {
        path.writeText("$excelFile$delimiter$powerpointFile$delimiter$outputDir$delimiter$fileName")
    }

    fun load(): Defaults {
        if (path.exists()) {
            val contents = path.readText().split(delimiter)
            excelFile = contents[0]
            powerpointFile = contents[1]
            outputDir = contents[2]
            fileName = contents[3]
        }

        return this
    }

    private val path: File
        get() {
            val base = if (System.getProperty("os.name").lowercase().contains("win")) {
                File(System.getProperty("user.home")).resolve("Local Settings").resolve("Application Data")
            } else {
                File(System.getProperty("user.home"))
            }

            return File(base, ".prayer-request-settings")
        }
}