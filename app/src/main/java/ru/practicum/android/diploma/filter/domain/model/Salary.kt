package ru.practicum.android.diploma.filter.domain.model

data class Salary(
    val currency: String = "RUR",
    val from: Int? = null,
    val gross: Boolean? = true,
    val to: Int? = null
)
