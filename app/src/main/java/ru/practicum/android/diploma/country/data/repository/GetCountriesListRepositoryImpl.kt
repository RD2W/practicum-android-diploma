package ru.practicum.android.diploma.country.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.country.domain.model.GetCountriesListResult
import ru.practicum.android.diploma.country.domain.repository.GetCountriesListRepository
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient

class GetCountriesListRepositoryImpl(
    private val networkClient: NetworkClient
) : GetCountriesListRepository {
    override fun getCountriesList(): Flow<GetCountriesListResult> = flow {
        emit(GetCountriesListResult.Problem)
    }
}
