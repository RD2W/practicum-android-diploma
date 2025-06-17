package ru.practicum.android.diploma.filter.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.filter.data.mapper.toArea
import ru.practicum.android.diploma.filter.data.mapper.toCountry
import ru.practicum.android.diploma.filter.data.mapper.toIndustry
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.region.domain.model.Area
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient

class FilterRepositoryImpl(
    private val networkClient: NetworkClient,
) : FilterRepository {
    override suspend fun getCountries(): Flow<RequestResult<List<Country>>> = flow {
        val request = HHApiRequest.Countries
        when (val response = networkClient.doRequest(request)) {
            is HHApiResponse.Countries -> {
                emit(RequestResult.Success(response.countries.map { countryDto ->
                    countryDto.toCountry()
                }))
            }
            else -> {
                emit(RequestResult.Error(response.responseCode))
            }
        }
    }

    override suspend fun getAreas(id: String?): Flow<RequestResult<List<Area>>> = flow {
        val request = HHApiRequest.Areas(id ?: "")
        when (val response = networkClient.doRequest(request)) {
            is HHApiResponse.Areas -> {
                emit(RequestResult.Success(response.areas.map { areaDto ->
                    areaDto.toArea()
                }))
            }
            else -> {
                emit(RequestResult.Error(response.responseCode))
            }
        }
    }

    override suspend fun getIndustries(): Flow<RequestResult<List<Industry>>> = flow {
        val request = HHApiRequest.Industries
        when (val response = networkClient.doRequest(request)) {
            is HHApiResponse.Industries -> {
                emit(RequestResult.Success(response.industries.map { industryDto ->
                    industryDto.toIndustry()
                }))
            }
            else -> {
                emit(RequestResult.Error(response.responseCode))
            }
        }
    }
}
