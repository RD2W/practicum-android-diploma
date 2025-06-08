package ru.practicum.android.diploma.filter.domain.repository

import ru.practicum.android.diploma.region.domain.model.Country

interface CountriesListGetter {
    fun getCountriesList(): List<Country>
}
