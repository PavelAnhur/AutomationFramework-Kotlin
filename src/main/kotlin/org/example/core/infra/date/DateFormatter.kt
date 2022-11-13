package org.example.core.infra.date

import java.time.LocalDate

interface DateFormatter {
    fun format(date: String): LocalDate
}
