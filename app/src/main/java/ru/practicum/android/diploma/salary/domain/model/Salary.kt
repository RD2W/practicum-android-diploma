package ru.practicum.android.diploma.salary.domain.model

data class Salary(
    val currency: String,
    val from: Int,
    val gross: Boolean,
    val to: Int?
)
