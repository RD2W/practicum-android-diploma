package ru.practicum.android.diploma.common.utils

import android.text.Html
import android.text.Spanned
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
        val currency = replaceCurrencyWithSymbol(salaryDto.currency)

        return when {
            from != null && to != null -> "от $from до $to $currency"
            from != null -> "от $from $currency"
            to != null -> "до $to $currency"
            else -> null
        }
    }

    // добавляет разбиение на разряды для больших чисел (например, 10 000)
    private fun addSpacesInNumbers(number: Int): String {
        val russianLocale = Locale("ru", "RU")
        val formatter = NumberFormat.getInstance(russianLocale)
        return formatter.format(number)
    }

    // заменяет код валюты на соответствуюший символ
    private val currencySymbols = mapOf(
        "AZN" to "₼",
        "BYR" to "Br",
        "EUR" to "€",
        "GEL" to "₾",
        "KGS" to "с",
        "KZT" to "₸",
        "RUB" to "₽",
        "RUR" to "₽",
        "UAH" to "₴",
        "USD" to "$",
        "UZS" to "сўм",
    )

    private fun replaceCurrencyWithSymbol(currency: String?): String {
        if (currency == null) return ""
        return currencySymbols[currency] ?: ""
    }

    // преобразует HTML-разметку в текст с применёнными стилями (жирный, курсив, списки, ссылки и т.д.) для вставки в TextView
    fun htmlToFormattedText(html: String): Spanned {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    }

    fun keySkillsToHtmlList(keySkills: String?): String {
        return keySkills?.split(",")
            ?.map { it.trim() }
            ?.filter { it.isNotEmpty() }
            ?.joinToString(prefix = "<ul>", postfix = "</ul>", separator = "") { skill ->
                "<li>&nbsp;$skill</li>"
            } ?: ""
    }
}
