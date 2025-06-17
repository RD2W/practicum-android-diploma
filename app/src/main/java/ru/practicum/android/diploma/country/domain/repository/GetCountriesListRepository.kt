package ru.practicum.android.diploma.country.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.country.data.repository.model.GetCountriesListResult

interface GetCountriesListRepository {
    fun getCountriesList(): Flow<GetCountriesListResult>
}
