package ru.practicum.android.diploma.country.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.country.domain.model.GetCountriesListResult

interface CountriesListGetter {
    fun getCountriesList(): Flow<GetCountriesListResult>
}
