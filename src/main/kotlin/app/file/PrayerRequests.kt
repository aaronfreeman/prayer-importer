package app.file

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class PrayerRequests(filePath: String) {
    val items = parse(filePath)

    private fun parse(filePath: String): List<PrayerRow> {
        return XSSFWorkbook(filePath).use { workbook ->
            workbook.getSheetAt(0)
                .map { PrayerRow(it.getValue(0), it.getValue(2)) }
                .drop(1)
                .filter { it.hasValues() }
        }
    }

    private fun Row.getValue(num: Int): String {
        return getCell(num)?.toString() ?: return ""
    }
}

data class PrayerRow(val one: String, val two: String) {
    fun hasValues(): Boolean {
        return one.isNotBlank() || two.isNotBlank()
    }
}
