package ru.practicum.android.diploma.filter.domain.model

import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.workplace.domain.model.Workplace

data class Filter(
    val workplace: Workplace?,
    val industry: Industry?,
    val salary: Salary?,
    val salaryMustHaveFlag: Boolean? = false
)
