package ru.practicum.android.diploma.country.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.country.domain.model.GetCountriesListResult
import ru.practicum.android.diploma.country.domain.repository.GetCountriesListRepository
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import java.net.HttpURLConnection

class GetCountriesListRepositoryImpl(
    private val networkClient: NetworkClient
) : GetCountriesListRepository {
    override fun getCountriesList(): Flow<GetCountriesListResult> = flow {
        val response = networkClient.doRequest(HHApiRequest.Countries)
        when (response.responseCode) {
            HttpURLConnection.HTTP_OK -> {
                val countries = (response as HHApiResponse.Countries).countries.map {
                    Country(
                        id = it.id,
                        name = it.name
                    )
                }
                emit(GetCountriesListResult.Success(countries))
            }
            else -> {
                emit(GetCountriesListResult.Error)
            }
        }
    }
}
