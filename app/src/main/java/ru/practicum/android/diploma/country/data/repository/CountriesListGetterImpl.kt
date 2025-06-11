package ru.practicum.android.diploma.country.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.country.domain.model.GetCountriesListResult
import ru.practicum.android.diploma.country.domain.repository.CountriesListGetter
import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.search.data.source.remote.HHApiRequest
import ru.practicum.android.diploma.search.data.source.remote.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.OK

class CountriesListGetterImpl(private val networkClient: NetworkClient) : CountriesListGetter {
    override fun getCountriesList(): Flow<GetCountriesListResult> = flow {
        val response = networkClient.doRequest(HHApiRequest.Countries)
        when (response.responseCode) {
            OK -> {
                val countries = (response as HHApiResponse.Countries).countries.map {
                    Country(
                        id = it.id,
                        name = it.name
                    )
                }
                emit(GetCountriesListResult.Success(countries))
            }
            else -> {
                emit(GetCountriesListResult.Problem)
            }
        }
    }
}
