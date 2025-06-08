package ru.practicum.android.diploma.salary.domain.model

data class Salary(
    val currency: String = "RUR",
    val from: Int = 0,
    val gross: Boolean = true,
    val to: Int?
)
