package ru.practicum.android.diploma.filter.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.domain.model.GetIndustriesListResult
import ru.practicum.android.diploma.filter.domain.repository.IndustriesListGetter
import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import java.net.HttpURLConnection

class IndustriesListGetterImpl(private val networkClient: NetworkClient) : IndustriesListGetter {
    override fun getIndustriesList(): Flow<GetIndustriesListResult> = flow {
        val response = networkClient.doRequest(HHApiRequest.Industries)
        when (response.responseCode) {
            HttpURLConnection.HTTP_OK -> {
                val industries = (response as HHApiResponse.Industries).industries.map {
                    Industry(
                        id = it.id,
                        name = it.name
                    )
                }
                emit(GetIndustriesListResult.Success(industries))
            }
            else -> {
                emit(GetIndustriesListResult.Problem)
            }
        }
    }
}
