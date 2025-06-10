package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.GetCountriesListResult

interface CountriesListGetter {
    fun getCountriesList(): Flow<GetCountriesListResult>
}
