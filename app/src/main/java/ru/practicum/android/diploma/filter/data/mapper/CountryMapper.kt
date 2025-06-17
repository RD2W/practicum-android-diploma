package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.search.data.model.dto.CountryDto

fun CountryDto.toCountry() = Country(
    id = this.id,
    name = this.name,
)
