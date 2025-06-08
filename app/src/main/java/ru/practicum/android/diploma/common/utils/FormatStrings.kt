package ru.practicum.android.diploma.common.utils

import ru.practicum.android.diploma.search.data.model.dto.SalaryDto
import java.text.NumberFormat
import java.util.Locale

object FormatStrings {
    fun formatSalary(salaryDto: SalaryDto?): String? {
        if (salaryDto == null) return null

        val from = if (salaryDto.from != null) {
            addSpacesInNumbers(salaryDto.from)
        } else {
            null
        }
        val to = if (salaryDto.to != null) {
            addSpacesInNumbers(salaryDto.to)
        } else {
            null
        }
        val currency = salaryDto.currency ?: ""

        return when {
            from != null && to != null -> "от $from до $to $currency"
            from != null -> "от $from $currency"
            to != null -> "до $to $currency"
            else -> null
        }
    }

    private fun addSpacesInNumbers(number: Int): String {
        val russianLocale = Locale("ru", "RU")
        val formatter = NumberFormat.getInstance(russianLocale)
        return formatter.format(number)
    }
}
