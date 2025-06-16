package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.search.data.model.dto.IndustryDto

fun IndustryDto.toIndustry() = Industry(
    id = this.id,
    name = this.name,
)
