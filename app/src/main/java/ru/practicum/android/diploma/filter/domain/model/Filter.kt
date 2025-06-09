package ru.practicum.android.diploma.filter.domain.model

import ru.practicum.android.diploma.salary.domain.model.Salary

data class Filter(
    val areaId: String?,
    val industryId: String?,
    val salary: Salary?
)
