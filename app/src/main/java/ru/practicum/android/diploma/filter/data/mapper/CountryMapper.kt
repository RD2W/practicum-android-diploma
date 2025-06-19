package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.search.data.model.dto.AreaDto

fun AreaDto.toCountry() = Country(
    id = this.id,
    name = this.name,
)
