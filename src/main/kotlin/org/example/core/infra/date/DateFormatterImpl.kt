package org.example.core.infra.date

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateFormatterImpl : DateFormatter {
    override fun format(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }
}
