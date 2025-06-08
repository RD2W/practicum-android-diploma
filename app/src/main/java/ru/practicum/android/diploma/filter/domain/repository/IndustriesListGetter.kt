package ru.practicum.android.diploma.filter.domain.repository

import ru.practicum.android.diploma.industry.domain.model.Industry

interface IndustriesListGetter {
    fun getIndustriesList(): List<Industry>
}
